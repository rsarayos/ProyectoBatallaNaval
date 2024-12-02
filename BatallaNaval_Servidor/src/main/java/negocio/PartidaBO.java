package negocio;

import Convertidores.toJSON;
import dominio.Jugador;
import dominio.Partida;
import comunicacion.ClientManager;
import comunicacion.MessageUtil;
import dominio.Casilla;
import dominio.Coordenada;
import dominio.Disparo;
import dominio.Tablero;
import dominio.TipoUnidad;
import dominio.UbicacionUnidad;
import dominio.Unidad;
import enums.AccionesJugador;
import enums.ControlPartida;
import enums.EstadoCasilla;
import enums.ResultadosAtaques;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class PartidaBO {

    private Partida partida;
    private UnidadBO unidadBO;

    public PartidaBO() {
        this.partida = Partida.getInstance();
        this.unidadBO = new UnidadBO();
    }

    // Método para crear una partida
    public Map<String, Object> crearPartida(Map<String, Object> data, String clientId) {
        // Verifica si el cliente ya está registrado
        if (ClientManager.getClientSocket(clientId) == null) {
            throw new IllegalStateException("El cliente no está conectado.");
        }

        String codigoAcceso = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        partida.setCodigoAcceso(codigoAcceso);
        System.out.println("El codigo de acceso es: " + codigoAcceso);
        Jugador host = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(host);
        // se habla al metodo para iniciar su tablero y unidades
        iniciarTableroyUnidades(clientId);
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, host);
        return toJSON.dataToJSON("accion", "CREAR_PARTIDA", "id", host.getId(), "codigo_acceso", codigoAcceso);
    }

    // Método para que un jugador se una a una partida
    public Map<String, Object> unirsePartida(Map<String, Object> data, String clientId) {
        String codigo_acceso = (String) data.get("codigo_acceso");
        System.out.println(codigo_acceso);
        if (!partida.getCodigoAcceso().equalsIgnoreCase(codigo_acceso)) {
            return toJSON.dataToJSON("accion", "UNIRSE_PARTIDA", "error", "El código de acceso no coincide");
        }
        Jugador jugador = crearJugador(clientId, (String) data.get("nombre"));
        partida.addJugador(jugador);
        // se habla al metodo para iniciar su tablero y unidades
        iniciarTableroyUnidades(clientId);
        ClientManager.addClient(ClientManager.getClientSocket(clientId), clientId, jugador);
        partida.getJugadores().stream().forEach(p -> System.out.println("Jugador en la partida " + p.getId()));
        List<String> nombresJugadores = partida.getJugadores().stream()
                .map(Jugador::getNombre)
                .collect(Collectors.toList());

        // Notificar a todos los jugadores que un nuevo jugador se ha unido
        notificarNuevoJugador(jugador);

        return toJSON.dataToJSON(
                "accion", "UNIRSE_PARTIDA",
                "id", jugador.getId(),
                "codigo_acceso", codigo_acceso,
                "nombres_jugadores", nombresJugadores);
    }

    private void notificarNuevoJugador(Jugador nuevoJugador) {
        for (Jugador jugadorExistente : partida.getJugadores()) {
            if (!jugadorExistente.getId().equals(nuevoJugador.getId())) {
                String clientIdExistente = jugadorExistente.getId();
                Socket socketJugador = ClientManager.getClientSocket(clientIdExistente);

                Map<String, Object> mensajeNotificacion = toJSON.dataToJSON(
                        "accion", "NUEVO_JUGADOR",
                        "nombre_jugador", nuevoJugador.getNombre());

                MessageUtil.enviarMensaje(socketJugador, mensajeNotificacion);
            }
        }
    }

    public Jugador crearJugador(String id, String nombre) {
        return new Jugador(id, nombre);
    }

    public Map<String, Object> jugadorListo(Map<String, Object> request, String clientId) {
        Jugador jugador = ClientManager.getJugadorByClientId(clientId);
        if (jugador != null) {
            jugador.setListo(true);

            // Notificar a todos los jugadores que este jugador está listo
            notificarEstadoListo(jugador);

            // Si todos los jugadores están listos, notificar para avanzar
            if (partida.todosLosJugadoresListos()) {
                // Reiniciar el estado listo de los jugadores para la siguiente fase
                reiniciarEstadoListo();

                // Notificar a los jugadores para iniciar la organización
                notificarIniciarOrganizar();
            }
        }
        return null;
    }

    private void notificarEstadoListo(Jugador jugadorListo) {
        for (Jugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "ACTUALIZAR_ESTADO_LISTO",
                    "id_jugador", jugadorListo.getId(),
                    "nombre_jugador", jugadorListo.getNombre(),
                    "listo", jugadorListo.isListo());
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    private void notificarTodosListos() {
        for (Jugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "TODOS_LISTOS");
            MessageUtil.enviarMensaje(socketJugador, mensaje);

        }
    }

    private void notificarIniciarOrganizar() {
        for (Jugador jugador : partida.getJugadores()) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "INICIAR_ORGANIZAR");
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    private void notificarIniciarJuego() {
        Jugador jugadorInicial = partida.getJugadorTurno();
        List<Jugador> jugadores = partida.getJugadores();

        for (Jugador jugador : jugadores) {
            Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
            boolean esSuTurno = jugador.getId().equals(jugadorInicial.getId());

            // Get opponent's name
            Jugador oponente = jugadores.stream()
                    .filter(j -> !j.getId().equals(jugador.getId()))
                    .findFirst()
                    .orElse(null);
            String nombreOponente = oponente != null ? oponente.getNombre() : "Oponente";

            Map<String, Object> mensaje = toJSON.dataToJSON(
                    "accion", "INICIAR_JUEGO",
                    "jugador_inicial_id", jugadorInicial.getId(),
                    "tu_turno", esSuTurno,
                    "nombre_oponente", nombreOponente);
            MessageUtil.enviarMensaje(socketJugador, mensaje);
        }
    }

    public void colocarUnidadTablero(Map<String, Object> request, String clientId) {
        Tablero tableroUsuario = this.partida.getTableros().get(clientId);
        Jugador jugador = ClientManager.getJugadorByClientId(clientId);

        if (jugador == null) {
            // Manejar error si el jugador no existe
            // return toJSON.dataToJSON("accion", "ERROR", "mensaje", "Jugador no
            // encontrado");
        }

        List<Map<String, Object>> unidadesData = (List<Map<String, Object>>) request.get("unidades");

        for (Map<String, Object> unidadData : unidadesData) {
            int numNave = (Integer) unidadData.get("numNave");
            Unidad unidad = unidadBO.obtenerUnidadPorNumNave(jugador, numNave);

            if (unidad == null) {
                System.out.println("No se encontro la unidad");
                break;
            }

            Map<Casilla, Boolean> casillas = unidadBO.obtenerCoordenadas(unidadData);

            UbicacionUnidad ubicacionUnidad = new UbicacionUnidad(unidad, casillas);

            tableroUsuario.agregarUbicacion(ubicacionUnidad);
        }

        // Marcar al jugador como listo
        jugador.setListo(true);
        notificarEstadoListo(jugador);

        System.out.println("Jugador: " + clientId);
        System.out.println(tableroUsuario.getUnidades());

        // Si todos los jugadores están listos, notificar para iniciar el juego
        if (partida.todosLosJugadoresListos()) {
            // iniciar el temporizador
            partida.iniciarPartida();

            // Notificar para iniciar el juego
            determinarJugadorInicial();
            notificarIniciarJuego();
        } else {
            notificarJugadorEsperando(jugador);
        }

        // return toJSON.dataToJSON("accion", AccionesJugador.ORDENAR.name(), "id",
        // clientId);
    }

    private void iniciarTableroyUnidades(String clientId) {
        // se agrega el tablero del jugador
        partida.addTablero(clientId, new Tablero());

        // se crea la lista de unidades del jugador
        List<Unidad> unidades = new ArrayList<>();
        // 2 portaaviones
        int numNave = 1;
        // se crean los portaaviones
        while (numNave <= 2) {
            Unidad nave = UnidadFactory.crearUnidad(TipoUnidad.PORTAAVIONES.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // se crean los cruceros
        while (numNave <= 4) {
            Unidad nave = UnidadFactory.crearUnidad(TipoUnidad.CRUCERO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // se crean los submarinos
        while (numNave <= 8) {
            Unidad nave = UnidadFactory.crearUnidad(TipoUnidad.SUBMARINO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }
        // se crean los barcos
        while (numNave <= 11) {
            Unidad nave = UnidadFactory.crearUnidad(TipoUnidad.BARCO.NOMBRE);
            nave.setNumNave(numNave);
            unidades.add(nave);
            numNave++;
        }

        // se guardan las unidades del jugador
        for (Jugador jugador : partida.getJugadores()) {
            if (jugador.getId() == clientId) {
                jugador.setUnidades(unidades);
            }
        }
    }

    public void reiniciarEstadoListo() {
        for (Jugador jugador : partida.getJugadores()) {
            jugador.setListo(false);
        }
    }

    private void determinarJugadorInicial() {
        List<Jugador> jugadores = partida.getJugadores();
        Random random = new Random();
        int indice = random.nextInt(jugadores.size());
        Jugador jugadorInicial = jugadores.get(indice);
        partida.setJugadorTurno(jugadorInicial);
    }

    private void notificarJugadorEsperando(Jugador jugadorListo) {
        for (Jugador jugador : partida.getJugadores()) {
            if (!jugador.getId().equals(jugadorListo.getId())) {
                Socket socketJugador = ClientManager.getClientSocket(jugador.getId());
                Map<String, Object> mensaje = toJSON.dataToJSON(
                        "accion", "JUGADOR_ESPERANDO",
                        "nombre_jugador", jugadorListo.getNombre());
                MessageUtil.enviarMensaje(socketJugador, mensaje);
            }
        }
    }

    public Map<String, Object> ubicarAtaque(Map<String, Object> data, String clientId) {

        Jugador jugadorAtacado = ClientManager.getOtherPlayer(clientId);
        Tablero tableroAtacado = this.partida.getTableroJugador(jugadorAtacado.getId());
        List<UbicacionUnidad> ubicacionUnidades = tableroAtacado.getUnidades();
        String mensaje = null;
        int vidaNave = 0;
        int numeroNave = 0;
        // Fue lo primero que se me ocurrio habria que checar si se puede hacer mejor xd
        // en el estado de la partida
        // false seria que no está terminada y true que ya mamó
        boolean estadoPartida = false;
        int x = (int) data.get("x");
        int y = (int) data.get("y");

        Optional<UbicacionUnidad> ubicacionUnidadImpactada = ubicacionUnidades.stream()
                .filter(ubicacionUnidad -> ubicacionUnidad.getCasillas().entrySet().stream()
                .filter(entry -> entry.getValue())
                .anyMatch(entry -> entry.getKey().getCoordenada().getX() == x
                && entry.getKey().getCoordenada().getY() == y))
                .findFirst();

        boolean impacto = ubicacionUnidadImpactada.isPresent();

        if (impacto) {
            ubicacionUnidadImpactada.get().getUnidad()
                    .setVida(ubicacionUnidadImpactada.get().getUnidad().getVida() - 1);

            mensaje = impacto ? "Haz impactado en una nave enemiga" : "Haz fallado el ataque";

            if (ubicacionUnidadImpactada.get().getUnidad().getVida() == 0) {
                tableroAtacado.addNaveDestruida();
                mensaje = "La nave ha sido destruida";
                estadoPartida = verificarEstadoPartida(tableroAtacado);
            }
            numeroNave = impacto ? ubicacionUnidadImpactada.get().getUnidad().getNumNave() : 0;
            vidaNave = impacto ? ubicacionUnidadImpactada.get().getUnidad().getVida() : 0;

            UbicacionUnidad ubicacionUnidad = ubicacionUnidadImpactada.get();

            ubicacionUnidad.getCasillas().entrySet().stream()
                    .filter(entry -> entry.getValue()) // Casillas activas
                    .filter(entry -> entry.getKey().getCoordenada().getX() == x
                    && entry.getKey().getCoordenada().getY() == y)
                    .findFirst()
                    .ifPresent(entry -> {
                        entry.setValue(false);
                        EstadoCasilla nuevoEstado = EstadoCasilla.IMPACTADA;

                        Disparo disparo = new Disparo(entry.getKey(), nuevoEstado);
                        tableroAtacado.addDisparoRecibido(disparo);
                    });
        } else {
            Disparo disparo = new Disparo(new Casilla(new Coordenada(x, y)), EstadoCasilla.VACIA);
            tableroAtacado.addDisparoRecibido(disparo);
        }
        // falta guardar los disparos que fallan
        if (estadoPartida) {
            // para el tiempo
            partida.finalizarPartida();
            return generarMensajeVictoria(ClientManager.getJugadorByClientId(clientId));
        }

        Map<String, Object> respuesta = generarRespuestaAtaque(impacto, mensaje, vidaNave, numeroNave, x, y, clientId,
                jugadorAtacado);

        return respuesta;
    }

    private Map<String, Object> generarRespuestaAtaque(boolean impacto, String mensaje, int vidaNave, int numeroNave,
            int x, int y, String clientId, Jugador jugadorAtacado) {
        Jugador jugadorTurno = impacto ? ClientManager.getJugadorByClientId(clientId)
                : ClientManager.getJugadorByClientId(jugadorAtacado.getId());
        this.partida.setJugadorTurno(jugadorTurno);

        Map<String, Object> jugador1Response = new HashMap<>();
        jugador1Response.put("accion", AccionesJugador.ATACAR.name());
        jugador1Response.put("resultado", impacto ? ResultadosAtaques.RESULTADO_ATAQUE_REALIZADO_IMPACTO.name()
                : ResultadosAtaques.RESULTADO_ATAQUE_REALIZADO_FALLO.name());
        jugador1Response.put("mensaje", mensaje);
        jugador1Response.put("vida_nave", vidaNave);
        jugador1Response.put("numero_nave", numeroNave);
        jugador1Response.put("x", x);
        jugador1Response.put("y", y);
        jugador1Response.put(ControlPartida.DETERMINAR_TURNO.name(), jugadorTurno.getNombre());

        Map<String, Object> jugador2Response = new HashMap<>();
        jugador2Response.put("accion", AccionesJugador.ATACAR.name());
        jugador2Response.put("resultado", impacto ? ResultadosAtaques.RESULTADO_ATAQUE_RECIBIDO_IMPACTO.name()
                : ResultadosAtaques.RESULTADO_ATAQUE_RECIBIDO_FALLO.name());
        jugador2Response.put("mensaje", impacto ? "Tu nave fue impactada" : "El impacto falló");
        jugador2Response.put("vida_nave", vidaNave);
        jugador2Response.put("numero_nave", numeroNave);
        jugador2Response.put("x", x);
        jugador2Response.put("y", y);
        jugador2Response.put(ControlPartida.DETERMINAR_TURNO.name(), jugadorTurno.getNombre());

        Map<String, Object> response = new HashMap<>();
        response.put(clientId, jugador1Response);
        response.put(jugadorAtacado.getId(), jugador2Response);

        return response;
    }

    private Map<String, Object> generarMensajeVictoria(Jugador jugadorGanador) {
        Map<String, Object> mensajeVictoria = new HashMap<>();

        mensajeVictoria.put("resultado", ControlPartida.PARTIDA_FINALIZADA.name());
        mensajeVictoria.put("mensaje", "¡Felicidades! " + jugadorGanador.getNombre() + " ha ganado la partida.");
        mensajeVictoria.put("accion", AccionesJugador.ATACAR.name());
        mensajeVictoria.put("ganador", jugadorGanador.getNombre());
        mensajeVictoria.put(ControlPartida.DETERMINAR_TURNO.name(), null);
        
        
        Map<String, Object> estadisticas = obtenerEstadisticasJugador(jugadorGanador.getId());
        mensajeVictoria.put("estadisticas", estadisticas);
        
        // Agregar el tiempo de la partida
        String tiempoPartida = formatoDuracion(partida.getDuracion());
        mensajeVictoria.put("tiempo_partida", tiempoPartida);

        // mensajeVictoria.put("turnos_jugados", turnosJugados);
        // mensajeVictoria.put("naves_restantes", navesRestantes);
        return mensajeVictoria;
    }

    public boolean verificarEstadoPartida(Tablero tablero) {
        // Aqui abria que tener en alguna parte el numero definido de naves para no
        // hardcodearlo
        return tablero.getNumNavesDestruidas() == 11;
    }

    public void rendirse(Map<String, Object> request, String clientId) {
        Jugador jugadorQueSeRinde = ClientManager.getJugadorByClientId(clientId);
        if (jugadorQueSeRinde == null) {
            // Manejar error si el jugador no existe
            return;
        }

        Jugador jugadorGanador = ClientManager.getOtherPlayer(clientId);

        if (jugadorGanador == null) {
            // Manejar error si el otro jugador no existe
            return;
        }

        // Finalizar la partida
        partida.finalizarPartida();
        partida.setGanador(jugadorGanador);

        // Generar el mensaje de victoria con las estadísticas
        Map<String, Object> mensajeVictoria = generarMensajeVictoria(jugadorGanador);

        // Enviar el mensaje a ambos jugadores
        Socket jugadorSocket = ClientManager.getClientSocket(jugadorGanador.getId());
        MessageUtil.enviarMensaje(jugadorSocket, mensajeVictoria);
        
        Socket otroJugadorSocket = ClientManager.getClientSocket(jugadorQueSeRinde.getId());
        MessageUtil.enviarMensaje(otroJugadorSocket, mensajeVictoria);
    }

    public Map<String, Object> obtenerEstadisticasJugador(String clientId) {
        Jugador jugadorPrincipal = ClientManager.getJugadorByClientId(clientId);
        Jugador jugadorEnemigo = ClientManager.getOtherPlayer(clientId);

        Tablero tableroPrincipal = partida.getTableroJugador(jugadorPrincipal.getId());
        Tablero tableroEnemigo = partida.getTableroJugador(jugadorEnemigo.getId());

        Map<String, Object> jugadoresRespuestas = new HashMap<>();
        jugadoresRespuestas.put(clientId, generarEstadisticas(jugadorPrincipal, tableroPrincipal, tableroEnemigo, partida.getDuracion()));
        jugadoresRespuestas.put(jugadorEnemigo.getId(), generarEstadisticas(jugadorEnemigo, tableroEnemigo, tableroPrincipal, partida.getDuracion()));

        return jugadoresRespuestas;
    }

    private Map<String, Object> generarEstadisticas(Jugador jugador, Tablero tableroPropio, Tablero tableroEnemigo, long duracionPartida) {
        Map<String, Object> estadisticas = new HashMap<>();

        estadisticas.put("nombre", jugador.getNombre());
        estadisticas.put("naves_destruidas", tableroPropio.getNumNavesDestruidas());
        estadisticas.put("naves_restantes", 11 - tableroPropio.getNumNavesDestruidas());
        estadisticas.put("tiempo_partida", duracionPartida);

        int disparosAcertados = 0;
        int disparosFallados = 0;

        for (var disparo : tableroEnemigo.getDisparosRecibidos()) {
            if (disparo.getEstado() == EstadoCasilla.IMPACTADA) {
                disparosAcertados++;
            } else {
                disparosFallados++;
            }
        }

        estadisticas.put("disparos_acertados", disparosAcertados);
        estadisticas.put("disparos_fallados", disparosFallados);
        estadisticas.put("disparos_totales", tableroEnemigo.getDisparosRecibidos().size());

        return estadisticas;
    }

    public void reiniciarPartida() {
        partida.ReiniciarPartida();
    }

    public void finalizarPartida() {
        Partida.instance = null;
    }

    private String formatoDuracion(long duracionMilisegundos) {
        long segundos = duracionMilisegundos / 1000;
        long minutos = segundos / 60;
        segundos = segundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
    
    private Map<String, Object> generarMensajeVictoriaPorRendicion(Jugador jugadorGanador) {
        Map<String, Object> mensajeVictoria = new HashMap<>();

        mensajeVictoria.put("resultado", ControlPartida.PARTIDA_FINALIZADA.name());
        mensajeVictoria.put("mensaje", "El jugador oponente se ha rendido. " + jugadorGanador.getNombre() + " ha ganado la partida.");
        mensajeVictoria.put("accion", AccionesJugador.RENDIRSE.name());
        mensajeVictoria.put("ganador", jugadorGanador.getNombre());
        mensajeVictoria.put(ControlPartida.DETERMINAR_TURNO.name(), null);

        // Obtener las estadísticas
        Map<String, Object> estadisticas = obtenerEstadisticasJugador(jugadorGanador.getId());
        mensajeVictoria.put("estadisticas", estadisticas); // Agregar las estadísticas bajo la clave "estadisticas"

        // Agregar el tiempo de la partida
        String tiempoPartida = formatoDuracion(partida.getDuracion());
        mensajeVictoria.put("tiempo_partida", tiempoPartida);

        return mensajeVictoria;
    }
    
}
