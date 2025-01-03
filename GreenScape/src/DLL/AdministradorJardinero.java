package DLL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import BLL.Administrador;
import BLL.Curso;
import BLL.Progreso;

public class AdministradorJardinero extends Administrador {
    private LinkedList<Progreso> progresoSupervisado;

    public AdministradorJardinero(String nombre, int idAdmin, String email, LinkedList<Progreso> progresoSupervisado) {
        super(nombre, idAdmin, email);
        this.progresoSupervisado = progresoSupervisado;
    }

    private static Connection con = Conexion.getInstance().getConnection();

    public static LinkedList<Curso> mostrarCursos() {
        LinkedList<Curso> cursos = new LinkedList<>();
        try {
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("SELECT * FROM `jardinero`");
            ResultSet resultados = statement.executeQuery();

            while (resultados.next()) {
                cursos.add(new Curso(resultados.getInt("idjardinero"), resultados.getString("nombre"), resultados.getString("descripcion")));
            }
            resultados.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cursos: " + e.getMessage());
        }

        return cursos;
    }

    public static Boolean crearCurso(Curso curso) {
        try {
            PreparedStatement statement = (PreparedStatement) con.prepareStatement(
                    "INSERT INTO `jardinero`(`nombre`,`descripcion`) VALUES (?,?)"
            );
            statement.setString(1, curso.getNombre());
            statement.setString(2, curso.getinformacion()); // informacion = informacion_jardineria

            int fila = statement.executeUpdate();

            if (fila > 0) {
                JOptionPane.showMessageDialog(null, "Curso guardado con éxito.");
                return true;
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el curso: " + e.getMessage());
        }
        return false;
    }

    public static Boolean actualizarCurso(Curso curso) {
        try {
            PreparedStatement statement = (PreparedStatement) con.prepareStatement(
                    "UPDATE `jardinero` SET `nombre` = ?, `descripcion` = ? WHERE `idjardinero` = ?"
            );
            statement.setString(1, curso.getNombre());
            statement.setString(2, curso.getinformacion()); // informacion = informacion_jardineria
            statement.setInt(3, curso.getIdCurso());

            int fila = statement.executeUpdate();

            if (fila > 0) {
                JOptionPane.showMessageDialog(null, "Curso actualizado con éxito.");
                return true;
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar curso: " + e.getMessage());
        }
        return false;
    }

    public static Boolean eliminarCurso(int idCurso) {
        try {
            PreparedStatement statement = (PreparedStatement) con.prepareStatement("DELETE FROM `jardinero` WHERE `idjardinero` = ?");
            statement.setInt(1, idCurso);

            int fila = statement.executeUpdate();

            if (fila > 0) {
                JOptionPane.showMessageDialog(null, "Curso eliminado con éxito.");
                return true;
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar curso: " + e.getMessage());
        }
        return false;
    }

    public LinkedList<Progreso> getProgresoSupervisado() {
        return progresoSupervisado;
    }

    public void setProgresoSupervisado(LinkedList<Progreso> progresoSupervisado) {
        this.progresoSupervisado = progresoSupervisado;
    }
}
