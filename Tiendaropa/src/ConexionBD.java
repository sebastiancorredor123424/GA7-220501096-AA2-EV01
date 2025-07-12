import java.sql.*;

public class ConexionBD {

    private static final String USUARIO = "sebastian";
    private static final String PASSWORD = "contraseña2025";
    private static final String URL = "jdbc:mysql://localhost:3306/TiendaRopaOnline";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error al cargar el Driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }

        return conexion;
    }

    // Método para insertar un usuario
    public static void insertarUsuario(String nombre, String email, String contraseña, String direccion, String telefono) {
        String sql = "INSERT INTO Usuarios (nombre, email, contraseña, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, contraseña);
            ps.setString(4, direccion);
            ps.setString(5, telefono);

            int filas = ps.executeUpdate();
            System.out.println("✅ Filas insertadas: " + filas);

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar usuario: " + e.getMessage());
        }
    }

    // Consultar todos los usuarios
    public static void consultarUsuarios() {
        String sql = "SELECT * FROM Usuarios";
        try (Connection conexion = conectar(); Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Nombre: " + rs.getString("nombre") +
                        ", Email: " + rs.getString("email") +
                        ", Dirección: " + rs.getString("direccion") +
                        ", Teléfono: " + rs.getString("telefono") +
                        ", Fecha registro: " + rs.getTimestamp("fecha_registro"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al consultar usuarios: " + e.getMessage());
        }
    }

    // Actualizar usuario
    public static void actualizarUsuario(int id, String nombre, String telefono) {
        String sql = "UPDATE Usuarios SET nombre = ?, telefono = ? WHERE id = ?";
        try (Connection conexion = conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setInt(3, id);

            int filas = ps.executeUpdate();
            System.out.println("✅ Filas actualizadas: " + filas);

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar usuario: " + e.getMessage());
        }
    }

    // Eliminar usuario
    public static void eliminarUsuario(int id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        try (Connection conexion = conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            System.out.println("✅ Filas eliminadas: " + filas);

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar usuario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Pruebas CRUD
        insertarUsuario("Juan Pérez", "juan@example.com", "clave123", "Cra 123", "3123456789");
        insertarUsuario("Ana López", "ana@example.com", "secreta456", "Calle 45", "3109876543");

        System.out.println("\n📋 Lista de usuarios:");
        consultarUsuarios();

        actualizarUsuario(1, "Juan Actualizado", "3000000000");
        System.out.println("\n🔄 Después de actualizar:");
        consultarUsuarios();

        eliminarUsuario(2);
        System.out.println("\n❌ Después de eliminar:");
        consultarUsuarios();
    }
}
