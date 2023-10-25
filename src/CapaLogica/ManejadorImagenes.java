/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica;
// Paquete para clases de utilidad


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ManejadorImagenes {

    public static byte[] leerImagenComoBytes(String ruta) {
        try (FileInputStream fis = new FileInputStream(new File(ruta))) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer la imagen");
        }
    }
}
