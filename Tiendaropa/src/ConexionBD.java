import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ConexionBD {

    private static final String USUARIO = "sebastian corredor";
    private static final String PASSWORD = "Folagor03Msalily2024";
    private static final String URL = "jdbc:mysql://localhost:3306/pruebaJDBC";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos.");

            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM persona");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Nombre: " + rs.getString("nombre") +
                                   ", Edad: " + rs.getInt("age"));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error al cargar el Driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar o consultar la base de datos: " + e.getMessage());
        }

        return conexion;
    }

    public static void main(String[] args) {
        conectar();
    }
}
