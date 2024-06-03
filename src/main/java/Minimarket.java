import java.sql.*;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Minimarket {
    static final String SEPARADOR = "---------------------------------------------------------";
    static Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(Minimarket.class);
    // driver JDBC y URL de la BD
    static final String JDBC_DRIVER = "org.h2.Driver";
    //static final String DB_URL = "jdbc:h2:~/testdb";
    //static final String DB_URL = "jdbc:h2:~/h2/testdb";
    //static final String DB_URL = "jdbc:h2:mem:test"; <---- se crea en memoria, se va cuando termine el programa,
    //static final String DB_URL = "jdbc:h2:tcp://localhost/~/h2/test";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

    //  Credenciales
    static final String USER = "sa";
    static final String PASS = "";

    static Connection conn = null;
    static Statement stmt = null;

    public static void conexionBaseDatosMinimarket() {

        try {
            // Paso 1: Registrar el driver jdbc
            Class.forName(JDBC_DRIVER);

            //Paso 2: Abrir una conexión
            System.out.println("Conectándose a la base de datos...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //Paso 3: Trabajar con la tabla
            menu();

            // Paso 4: limpiar el ambiente
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //administrar errores para JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //administrar errores para Class.forName
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    public static void menu() {
        boolean salida = false;

        while (!salida) {
            System.out.println(SEPARADOR);
            System.out.println("Menu Minimarket\t Empleado nro: ");
            System.out.println(SEPARADOR);

            System.out.println("1- Mostrar Producto");
            System.out.println("2- Agregar Recurso Humano.");
            System.out.println("3- Agregar Ausente.");
            System.out.println("4- Agregar Familiar.");
            System.out.println("5- Agregar Liquidación.");
            System.out.println("6- Salida.");
            System.out.println(SEPARADOR);
            int entrada = sc.nextInt();
            sc.nextLine();
            switch (entrada) {
                case 1:
                    mostrarTodosLosProductos();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;

                case 6:
                    salida = true;
                    break;
                default:
                    System.out.println("Ingrese un número válido.");
                    break;
            }
        }
    }

    public void  menuProducto(){

        System.out.println("Productos de la tienda:");
        System.out.println(SEPARADOR);
        mostrarTodosLosProductos();
        System.out.println(SEPARADOR);
        System.out.println("Elegir Producto:");
        int opcionProducto = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese cantidad del producto:");
        int cantidadProducto = sc.nextInt();


    }

    public static void mostrarTodosLosProductos() {
        String sql = "SELECT idProducto, nombreProducto, precioProducto, stockProducto FROM Producto";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("ID | Nombre | Precio | Stock");
            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombreProducto = rs.getString("nombreProducto");
                double precioProducto = rs.getDouble("precioProducto");
                int stockProducto = rs.getInt("stockProducto");
                System.out.printf("%d | %s | %.2f | %d%n", idProducto, nombreProducto, precioProducto, stockProducto);
                logger.info("Producto mostrado: ID=" + idProducto + ", Nombre=" + nombreProducto + ", Precio=" + precioProducto + ", Stock=" + stockProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar todos los productos: " + e.getMessage());
        }
    }

}

