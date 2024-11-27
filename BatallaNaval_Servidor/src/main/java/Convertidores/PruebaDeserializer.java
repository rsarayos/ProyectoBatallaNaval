package Convertidores;

import dto.PruebaDTO;
import enums.AccionesJugador;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import java.io.IOException;

/**
 * Clase que proporciona la funcionalidad para deserializar un mensaje empaquetado en formato MessagePack.
 */
public class PruebaDeserializer {

    /**
     * Método que deserializa un mensaje empaquetado y lo convierte en un objeto PruebaDTO.
     *
     * @param message El mensaje en formato de bytes que se va a deserializar.
     * @return Un objeto PruebaDTO con los datos deserializados.
     * @throws IOException Si ocurre un error durante la deserialización del mensaje.
     */
    public static PruebaDTO deserializer(byte[] message) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(message);

        int x = unpacker.unpackInt();
        int y = unpacker.unpackInt();
        String accion = "";

        unpacker.close();
        return new PruebaDTO(x, y, AccionesJugador.valueOf(accion));
    }
}
