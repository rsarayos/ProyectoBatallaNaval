package Convertidores;

import dto.PruebaDTO;
import enums.AccionesJugador;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import java.io.IOException;

public class PruebaDeserializer {

    public static PruebaDTO deserializer(byte[] message) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(message);

        int x = unpacker.unpackInt();
        int y = unpacker.unpackInt();
        String accion = "";

        unpacker.close();
        return new PruebaDTO(x, y, AccionesJugador.valueOf(accion));
    }
}
