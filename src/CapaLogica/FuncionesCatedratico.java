/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica;

import CapaDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class FuncionesCatedratico {

    public void agregarCatedraticos(String nombre, String apellidos, String fechaNacimiento) {
        Conexion db = new Conexion();
        String sql = "INSERT INTO catedraticos(nombre, apellidos, fecha_nacimiento) VALUES (?, ?, ?)";

        try (
                Connection conexion = db.getConnection(); PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, fechaNacimiento);

            int resultado = preparedStatement.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Catedrático agregado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar catedrático", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error al conectar con la base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void cargarDatosSeleccionadosEnTextBox(JTable tabla, JTextField txtId, JTextField txtNombre, JTextField txtApellidos, JTextField txtFechaNacimiento) {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada >= 0) {
            String id = tabla.getValueAt(filaSeleccionada, 0).toString();
            String nombre = tabla.getValueAt(filaSeleccionada, 1).toString();
            String apellidos = tabla.getValueAt(filaSeleccionada, 2).toString();
            String fechaNacimiento = tabla.getValueAt(filaSeleccionada, 3).toString(); // Ajusta el índice según tu estructura

            txtId.setText(id);
            txtNombre.setText(nombre);
            txtApellidos.setText(apellidos);
            txtFechaNacimiento.setText(fechaNacimiento);
            // Puedes agregar más líneas para otros campos si es necesario
        } else {
            // Si no hay fila seleccionada, puedes mostrar un mensaje o realizar alguna acción
            // Puedes mostrar un mensaje de error o simplemente limpiar los campos
            txtId.setText("");
            txtNombre.setText("");
            txtApellidos.setText("");
            txtFechaNacimiento.setText("");

            // Puedes agregar líneas adicionales para otros campos si es necesario
        }
    }

    public static boolean actualizarDatosEnBD(JTable tabla, JTextField txtId, JTextField txtNombre, JTextField txtApellidos, JTextField txtFechaNacimiento) {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada >= 0) {
            String nuevoNombre = txtNombre.getText();
            String nuevosApellidos = txtApellidos.getText();
            String nuevaFechaNacimiento = txtFechaNacimiento.getText();
            String idCatedratico = txtId.getText();

            // Aquí debes agregar la lógica para actualizar los datos en la base de datos
            // Utiliza una consulta UPDATE en SQL con parámetros
            String sql = "UPDATE catedraticos SET nombre = ?, apellidos = ?, fecha_nacimiento = ? WHERE id_catedratico = ?";

            // Luego ejecutas la consulta en tu conexión a la base de datos utilizando PreparedStatement
            Conexion db = new Conexion();
            try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, nuevoNombre);
                preparedStatement.setString(2, nuevosApellidos);
                preparedStatement.setString(3, nuevaFechaNacimiento);
                preparedStatement.setString(4, idCatedratico);

                int filasAfectadas = preparedStatement.executeUpdate();

                // Si filasAfectadas es mayor que 0, la actualización fue exitosa
                return filasAfectadas > 0;
            } catch (SQLException e) {
                // Manejo de errores: Puedes mostrar un mensaje de error si la actualización falla
                e.printStackTrace();
                return false;
            }
        }

        // Si no hay fila seleccionada, también consideramos que la actualización falló
        return false;
    }

    public static boolean eliminarRegistroEnBD(JTable tabla, JTextField txtId) {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada >= 0) {
            String idCatedratico = txtId.getText();

            // Aquí debes agregar la lógica para eliminar la fila en la base de datos
            // Utiliza una consulta DELETE en SQL con parámetros
            String sql = "DELETE FROM catedraticos WHERE id_catedratico = ?";

            // Luego ejecutas la consulta en tu conexión a la base de datos utilizando PreparedStatement
            Conexion db = new Conexion();
            try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, idCatedratico);

                int filasAfectadas = preparedStatement.executeUpdate();

                // Si filasAfectadas es mayor que 0, la eliminación fue exitosa
                return filasAfectadas > 0;
            } catch (SQLException e) {
                // Manejo de errores: Puedes mostrar un mensaje de error si la eliminación falla
                e.printStackTrace();
                return false;
            }
        }

        // Si no hay fila seleccionada, también consideramos que la eliminación falló
        return false;
    }
}
