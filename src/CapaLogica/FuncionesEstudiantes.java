/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica;

import CapaDatos.Conexion;
import CapaPresentacion.AgregarReCon;
import CapaPresentacion.FrmLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hetzo
 */
public class FuncionesEstudiantes extends Conexion {

    AgregarReCon agregarReCon = new AgregarReCon();
    FrmLogin fr = new FrmLogin();

    public void guardarUsuario(String usuario, String password, String rol) {
        Conexion db = new Conexion();
        String sql = "insert into usuarios(username, password, rol) values ('" + usuario + "', '" + password + "','" + rol + "');";
        Statement st;
        Connection conexion = db.getConnection();
        try {
            st = conexion.createStatement();
            int rs = st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Guardado correctamente");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void accesoUsuario(String username, String password) {
        // TODO add your handling code here:
        Conexion db = new Conexion();
        // Se inicializa a null
        String usuarioCorrecto = null;
        String passCorrecto = null;
        try {

            Connection cn = db.getConnection();
            PreparedStatement pst = cn.prepareStatement("SELECT username,password FROM usuarios");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                usuarioCorrecto = rs.getString(1);
                passCorrecto = rs.getString(2);
            }

            if (username.equals(usuarioCorrecto) && username.equals(passCorrecto)) {
                JOptionPane.showMessageDialog(null, "Login correcto Bienvenido " + username);
                agregarReCon.setVisible(true);

            } else if (!username.equals(usuarioCorrecto) || password.equals(passCorrecto)) {

                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                fr.setVisible(true);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    /*  public void mostrarDatosEstudiantes(DefaultTableModel modelo) {
        String consultasql = "SELECT id_estudiante, nombre, apellidos, fecha_nacimiento, sexo FROM estudiantes";
        String data[] = new String[5];

        Connection cn = getConnection(); // Usar el método de la clase padre

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consultasql);
            while (rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                data[4] = rs.getString(5);
                modelo.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e);
        } finally {
            close(); // Cerrar la conexión al finalizar
        }
    }
     */
    public void limpiarTabla(DefaultTableModel modelo) {
        modelo.setRowCount(0);
    }

    public void buscarEstudiantePorId(DefaultTableModel modelo, int id_estudiante) {
        limpiarTabla(modelo); // Limpia la tabla antes de mostrar nuevos resultados

        String consultasql = "SELECT id_estudiante, nombre, apellidos, fecha_nacimiento, sexo FROM estudiantes WHERE id_estudiante = ?";
        String data[] = new String[5];

        Connection cn = getConnection(); // Obtener la conexión

        try {
            PreparedStatement pst = cn.prepareStatement(consultasql);
            pst.setInt(1, id_estudiante);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                data[4] = rs.getString(5);

                modelo.addRow(data);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el estudiante: " + e);
        } finally {
            close(); // Cerrar la conexión al finalizar
        }
    }

   
}
