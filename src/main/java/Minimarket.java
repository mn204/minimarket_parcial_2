import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test2";

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
            System.out.println("Incializando Minimarket Mitocondria........");
            logger.info("Abriendo Minimarket");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //Paso 3: Trabajar con la tabla
            menu();

            // Paso 4: limpiar el ambiente
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //administrar errores para JDBC
            se.printStackTrace();
            logger.error(se.getMessage());
        } catch(Exception e) {
            //administrar errores para Class.forName
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
                logger.error(se.getMessage());

            }
        }
        System.out.println("Cerrando Minimarket;");
        logger.info("Cerrando Minimarket");
    }

    public static void menu() throws SQLException {
        boolean salida = false;
        System.out.println("Seleccione el Empleado :(para hacerlo tipee el id del empleado)");
        mostrarTodosLosEmpleados();
        int idEmpleado = sc.nextInt();
        sc.nextLine();
        logger.info("Empleado logueado legajo: " + idEmpleado);
        while (!salida) {
            System.out.println(SEPARADOR);
            System.out.println("Menu Minimarket Mitocondria         Empleado Legajo: "+ idEmpleado);
            System.out.println(SEPARADOR);
            System.out.println("1- Vender Producto");
            System.out.println("2- Ingreso de mercadería");
            System.out.println("3- Agregar Ausente.");
            System.out.println("4- Agregar Familiar.");
            System.out.println("5- Mostrar Prodructos");
            System.out.println("6- Salida.");
            System.out.println(SEPARADOR);
            int entrada = sc.nextInt();
            sc.nextLine();
            switch (entrada) {
                case 1:
                    venderProducto();
                    logger.info("Se ingreso al menu de ventas");
                    break;
                case 2:
                    ingresarMercaderia();
                    logger.info("Se ingreso mercaderia al minimarket");
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    mostrarTodosLosProductos();
                    break;
                case 6:
                    salida = true;
                    break;
                default:
                    System.out.println("Ingrese un número válido.");
                    break;
            }
        }
        logger.info("Empleado deslogueado legajo: "+ idEmpleado);
    }
    //Ingreso de mercadería
    public static void ingresarMercaderia(){
        try{
            int idProveedor;
            while (true) {
                System.out.println("Ingrese el ID del Proveedor:");
                idProveedor = sc.nextInt();
                sc.nextLine();
                if (proveedorExiste(idProveedor)) {
                    break;
                } else {
                    System.out.println("Proveedor no encontrado. Intente nuevamente.");
                }
            }

            mostrarTodosLosProductos();
            System.out.println("Elegir Producto a Ingresar(ID):");
            int idProducto = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese cantidad del producto:");
            int cantidadProducto = sc.nextInt();
            sc.nextLine();

            boolean rs = agregarStockProducto(idProducto, cantidadProducto);

            if (rs){
                System.out.println("Stock Actualizado");
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar la venta: " + e.getMessage());
            logger.error("Error al intentar ingresar mercaderia: " + e.getMessage());
        }

    }
    //METODOS DE VENTA
    public static void venderProducto() {
        try {
            int idCliente;
            while (true) {
                System.out.println("Ingrese el ID del Cliente:");
                idCliente = sc.nextInt();
                sc.nextLine();
                if (clienteExiste(idCliente)) {
                    break;
                } else {
                    System.out.println("Cliente no encontrado. Intente nuevamente.");
                }
            }

            List<int[]> productosComprados = new ArrayList<>();
            double precioVentaTotal = 0.0;
            boolean agregarOtroProducto = true;

            while (agregarOtroProducto) {
                mostrarTodosLosProductos();
                System.out.println("Elegir Producto (ID):");
                int idProducto = sc.nextInt();
                sc.nextLine();
                System.out.println("Ingrese cantidad del producto:");
                int cantidadProducto = sc.nextInt();
                sc.nextLine();

                if (actualizarStockProducto(idProducto, cantidadProducto)) {
                    productosComprados.add(new int[]{idProducto, cantidadProducto});
                    precioVentaTotal += obtenerPrecioProducto(idProducto) * cantidadProducto;
                } else {
                    System.out.println("Stock insuficiente para el producto seleccionado.");
                }

                System.out.println("¿Desea agregar otro producto? (S/N):");
                String respuesta = sc.nextLine();
                if (!respuesta.equalsIgnoreCase("S")) {
                    agregarOtroProducto = false;
                }
            }
            if (clienteHabitual(idCliente)){
                precioVentaTotal = precioVentaTotal * 0.95;
            }
            registrarVenta(idCliente, precioVentaTotal, productosComprados);
        } catch (SQLException e) {
            System.out.println("Error al procesar la venta: " + e.getMessage());
            logger.error("Error al procesar la venta: " + e.getMessage());
        }
    }

    private static boolean clienteExiste(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CLIENTE WHERE IDCLIENTE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    private static boolean proveedorExiste(int idProveedor) throws SQLException {
        String sql = "SELECT COUNT(*) FROM PROVEEDOR WHERE IDPROVEEDOR = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProveedor);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    private static boolean actualizarStockProducto(int idProducto, int cantidad) throws SQLException {
        String sql = "UPDATE PRODUCTO SET STOCKPRODUCTO = STOCKPRODUCTO - ? WHERE IDPRODUCTO = ? AND STOCKPRODUCTO >= ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, idProducto);
            pstmt.setInt(3, cantidad);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    private static boolean agregarStockProducto(int idProducto, int cantidad) throws SQLException {
        String sql = "UPDATE PRODUCTO SET STOCKPRODUCTO = STOCKPRODUCTO + ? WHERE IDPRODUCTO = ? AND STOCKPRODUCTO >= ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, idProducto);
            pstmt.setInt(3, cantidad);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    private static double obtenerPrecioProducto(int idProducto) throws SQLException {
        String sql = "SELECT PRECIOPRODUCTO FROM PRODUCTO WHERE IDPRODUCTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("PRECIOPRODUCTO");
            } else {
                throw new SQLException("Producto no encontrado.");
            }
        }
    }

    private static boolean clienteHabitual(int idCliente) throws SQLException {
        String sql = "SELECT CANTIDADCOMPRASCLIENTE FROM CLIENTE WHERE IDCLIENTE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cantidadCompras = rs.getInt("CANTIDADCOMPRASCLIENTE");
                return cantidadCompras >= 5;
            } else {
                return false; // Client not found or no cantidadCompraCliente set
            }
        }
    }

    private static void registrarVenta(int idCliente, double precioTotal, List<int[]> productosComprados) throws SQLException {
        String sqlVenta = "INSERT INTO VENTA (IDCLIENTE, PRECIOVENTATOTAL, FECHAVENTA) VALUES (?, ?, CURRENT_DATE)";
        String sqlUpdateCliente = "UPDATE CLIENTE SET CANTIDADCOMPRASCLIENTE = CANTIDADCOMPRASCLIENTE + 1 WHERE IDCLIENTE = ?";
        String sqlVentaProducto = "INSERT INTO VENTAPRODUCTO (IDVENTA, IDPRODUCTO, CANTIDADVENDIDA) VALUES (?, ?, ?)";

        try (PreparedStatement pstmtVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtCliente = conn.prepareStatement(sqlUpdateCliente);
             PreparedStatement pstmtVentaProducto = conn.prepareStatement(sqlVentaProducto)) {

            pstmtCliente.setInt(1, idCliente);
            pstmtCliente.executeUpdate();

            pstmtVenta.setInt(1, idCliente);
            pstmtVenta.setDouble(2, precioTotal);
            pstmtVenta.executeUpdate();

            ResultSet generatedKeys = pstmtVenta.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idVenta = generatedKeys.getInt(1);

                for (int[] producto : productosComprados) {
                    pstmtVentaProducto.setInt(1, idVenta);
                    pstmtVentaProducto.setInt(2, producto[0]);
                    pstmtVentaProducto.setInt(3, producto[1]);
                    pstmtVentaProducto.executeUpdate();
                }
            }
        }
        System.out.println("Venta registrada exitosamente. Precio Total: " + precioTotal);
        logger.info("Venta registrada: Cliente ID=" + idCliente + ", Total=" + precioTotal);
    }



    public static void mostrarTodosLosProductos() {
        String sql = "SELECT idProducto, nombreProducto, precioProducto, stockProducto FROM Producto";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Nombre", "Precio", "Stock");
            System.out.println(SEPARADOR);
            while (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombreProducto = rs.getString("nombreProducto");
                double precioProducto = rs.getDouble("precioProducto");
                int stockProducto = rs.getInt("stockProducto");
                System.out.printf("%-10d %-20s %-10.2f %-10d%n", idProducto, nombreProducto, precioProducto, stockProducto);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar todos los productos: " + e.getMessage());
            logger.error("Error al mostrar todos los productos: " + e.getMessage());
        }
    }
    //METODOS DE EMPLEADO

    public static void mostrarTodosLosEmpleados() {
        String sql = "SELECT idEmpleado, nombreEmpleado, apellidoEmpleado FROM Empleado";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-10s %-20s %-20s%n", "Legajo", "Nombre", "Apellido");
            System.out.println(SEPARADOR);
            while (rs.next()) {
                int idEmpleado = rs.getInt("idEmpleado");
                String nombreEmpleado = rs.getString("nombreEmpleado");
                String apellidoEmpleado = rs.getString("apellidoEmpleado");
                System.out.printf("%-10d %-20s %-20s%n", idEmpleado, nombreEmpleado, apellidoEmpleado);

            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar todos los empleados: " + e.getMessage());
            logger.error("Error al mostrar todos los empleados: " + e.getMessage());
        }
    }


}

