/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica;

import CapaDatos.Conexion;
import CapaPresentacion.AgregarReCon;
import CapaPresentacion.FrmLogin;
import java.awt.Image;

import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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

        String consultasql = "SELECT id_estudiante, nombre, apellidos, fecha_nacimiento, sexo, grado, seccion FROM estudiantes WHERE id_estudiante = ?";
        String data[] = new String[7];

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
                data[5] = rs.getString(6);
                data[6] = rs.getString(7);

                modelo.addRow(data);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el estudiante: " + e);
        } finally {
            close(); // Cerrar la conexión al finalizar
        }
    }

    public void eliminarEstudiante(int idEstudiante, DefaultTableModel modelo) {
        Conexion db = new Conexion();
        Connection cn = db.getConnection();
        String sql = "DELETE FROM estudiantes WHERE id_estudiante = ?";

        try {
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, idEstudiante);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Estudiante eliminado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar estudiante: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.close(); // Cerrar la conexión al finalizar
        }
    }

//se pararaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    public void mostrarEstudiantesEnTabla(JTable tabla, JButton btnEstudiante, JButton btnProfesor) {
        // Deshabilitar el botón de estudiantes
        btnEstudiante.setEnabled(false);
        // Habilitar el botón de profesores
        btnProfesor.setEnabled(true);

        // Limpiar la tabla antes de mostrar nuevos resultados
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        limpiarTabla(modelo);

        String consultasql = "SELECT id_estudiante, nombre, apellidos, fecha_nacimiento, sexo, grado, seccion FROM estudiantes";
        String data[] = new String[7]; // Ajusta el tamaño del array

        Connection cn = getConnection(); // Obtener la conexión

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consultasql);
            while (rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                data[4] = rs.getString(5);
                data[5] = rs.getString(6);  // Agregar la columna de grado
                data[6] = rs.getString(7);  // Agregar la columna de sección
                modelo.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e);
        } finally {
            close(); // Cerrar la conexión al finalizar
        }
    }

    public void mostrarProfesoresEnTabla(JTable tabla, JButton btnEstudiante, JButton btnProfesor) {
        // Deshabilitar el botón de profesores
        btnProfesor.setEnabled(false);
        // Habilitar el botón de estudiantes
        btnEstudiante.setEnabled(true);

        // Limpiar la tabla antes de mostrar nuevos resultados
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        limpiarTabla(modelo);

        String consultasql = "SELECT id_catedratico, nombre, apellidos, fecha_nacimiento FROM catedraticos";
        String data[] = new String[4];

        Connection cn = getConnection(); // Obtener la conexión

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consultasql);
            while (rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                modelo.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e);
        } finally {
            close(); // Cerrar la conexión al finalizar
        }
    }

    public void mostrarCatedraticosEnTabla(JTable tabla) {
        // Limpiar la tabla antes de mostrar nuevos resultados
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        limpiarTabla(modelo);

        String consultasql = "SELECT id_catedratico, nombre, apellidos, fecha_nacimiento FROM catedraticos";
        String data[] = new String[4];

        Connection cn = getConnection(); // Obtener la conexión

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(consultasql);
            while (rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                modelo.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos de catedráticos: " + e);
        } finally {
            close(); // Cerrar la conexión al finalizar
        }
    }

    //imagennnnnnn
    /*public void cargarImagenEstudiante(String idEstudiante, JLabel lblImagen) {
        Connection cn = getConnection();

        try {
            String consultaSql = "SELECT imagen FROM estudiantes WHERE id_estudiante = ?";
            PreparedStatement ps = cn.prepareStatement(consultaSql);
            ps.setString(1, idEstudiante);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imagenBytes = rs.getBytes("imagen");
                ImageIcon imagenIcon = new ImageIcon(imagenBytes);
                lblImagen.setIcon(imagenIcon);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e);
        } finally {
            close();
        }
    }
     */
    public void cargarImagenEstudiante(String idEstudiante, JLabel lblImagen) {
        Connection cn = getConnection();

        try {
            String consultaSql = "SELECT imagen FROM estudiantes WHERE id_estudiante = ?";
            PreparedStatement ps = cn.prepareStatement(consultaSql);
            ps.setString(1, idEstudiante);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imagenBytes = rs.getBytes("imagen");
                ImageIcon imagenIcon = new ImageIcon(imagenBytes);

                // Obtener la imagen del ImageIcon
                Image imagenOriginal = imagenIcon.getImage();

                // Redimensionar la imagen a 100 x 100 píxeles
                Image imagenRedimensionada = imagenOriginal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                // Crear un nuevo ImageIcon con la imagen redimensionada
                ImageIcon imagenRedimensionadaIcon = new ImageIcon(imagenRedimensionada);

                // Mostrar la imagen en lblImagen
                lblImagen.setIcon(imagenRedimensionadaIcon);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e);
        } finally {
            close();
        }
    }

    public void cargarImagenEstudiantes(String idEstudiante, JLabel lblImagen) {
        Connection cn = getConnection();

        try {
            String consultaSql = "SELECT imagen FROM estudiantes WHERE id_estudiante = ?";
            PreparedStatement ps = cn.prepareStatement(consultaSql);
            ps.setString(1, idEstudiante);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imagenBytes = rs.getBytes("imagen");
                ImageIcon imagenIcon = new ImageIcon(imagenBytes);

                // Obtener la imagen del ImageIcon
                Image imagenOriginal = imagenIcon.getImage();

                // Redimensionar la imagen a 100 x 100 píxeles
                Image imagenRedimensionada = imagenOriginal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

                // Crear un nuevo ImageIcon con la imagen redimensionada
                ImageIcon imagenRedimensionadaIcon = new ImageIcon(imagenRedimensionada);

                // Mostrar la imagen en lblImagen
                lblImagen.setIcon(imagenRedimensionadaIcon);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la imagen: " + e);
        } finally {
            close();
        }
    }

    //reportes
    private Connection conexion;

    public FuncionesEstudiantes() {
        Conexion con = new Conexion();
        conexion = con.getConnection();
    }

    public String obtenerReportePorId(int id) {
        String query = "SELECT reporte FROM estudiantes WHERE id_estudiante = ?";
        try (PreparedStatement pst = conexion.prepareStatement(query)) {
            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("reporte");
                } else {
                    return "Estudiante no encontrado";
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error al obtener el reporte";
        }
    }

    //metodo de cursos
    public static void llenarTablaPorCurso(String cursoSeleccionado, JTable tabla) {
        // Crear una conexión a la base de datos
        Conexion conexionDB = new Conexion();
        Connection connection = conexionDB.getConnection();

        // Consulta SQL para obtener registros según el curso
        String consultaSQL = "SELECT * FROM estudiantes WHERE cursos = ?";

        try {
            // Preparar la consulta
            PreparedStatement pstmt = connection.prepareStatement(consultaSQL);
            pstmt.setString(1, cursoSeleccionado);

            // Ejecutar la consulta
            ResultSet rs = pstmt.executeQuery();

            // Crear un modelo de tabla para la TablaPrincipal
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Nombre");
            modeloTabla.addColumn("Apellidos");
            modeloTabla.addColumn("Sexo");
            modeloTabla.addColumn("Grado");
            modeloTabla.addColumn("Sección");
            modeloTabla.addColumn("Reporte");
            modeloTabla.addColumn("Cursos");
            modeloTabla.addColumn("Aprobado");

            while (rs.next()) {
                Object[] fila = new Object[9];  // Se reduce el tamaño a 9, excluyendo la columna de fecha de nacimiento
                fila[0] = rs.getInt("id_estudiante");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellidos");
                fila[3] = rs.getString("sexo");
                fila[4] = rs.getString("grado");
                fila[5] = rs.getString("seccion");
                fila[6] = rs.getString("reporte");
                fila[7] = rs.getString("cursos");
                fila[8] = "si".equalsIgnoreCase(rs.getString("aprobado"));

                modeloTabla.addRow(fila);
            }

            // Cerrar la conexión y mostrar los resultados en la tabla
            conexionDB.close();
            tabla.setModel(modeloTabla);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la consulta SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
