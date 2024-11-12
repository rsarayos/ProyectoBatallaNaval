/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Convertidores;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author af_da
 */
public class toJSON {

    public static Map<String, Object> dataToJSON(Object... args) {
        Map<String, Object> data = new HashMap<>();

        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i] instanceof String) {
                String key = (String) args[i];
                Object value = args[i + 1];
                data.put(key, value);
            } else {
                throw new IllegalArgumentException("Clave debe ser de tipo String");
            }
        }

        return data;
    }
}
