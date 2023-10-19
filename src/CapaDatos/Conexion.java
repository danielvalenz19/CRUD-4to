/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "toor";
    private static final String URL = "jdbc:mysql://localhost:3306/gestionescolar?autoReconnect=true&useSSL=false";
    private Connection CN;

    public Conexion() {
        CN = null;
    }

    /**
     * establece coenxion a la base de datos
     *
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            CN = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error al conectar con la base de datos", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return CN;
    }

    /**
     * cierra las conexiones de la base de datos
     */
    public void close() {
        try {
            CN.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error al cerrrar la conexion de la base de datos ", JOptionPane.ERROR_MESSAGE);

        }
    }

}