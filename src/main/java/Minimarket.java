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
    private static final Logger jslogger = LogManager.getLogger(Minimarket.class);
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
            System.out.println("Incializando Minimarket Mitocondria........");
            logger.info("Abriendo Minimarket");
            jslogger.info("Abriendo Minimarket");
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
            System.out.println("3- Pago a un Proveedor");
            System.out.println("4- Consultar Ventas(diarias/mensuales)");
            System.out.println("5- Mostrar Balance");
            System.out.println("6- Solicitar Comanda a Cocina");
            System.out.println("7- Pagar Cuenta");
            System.out.println("8- Estadisticas de platos mas pedidos");
            System.out.println("9- Salida.");

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
                    menuProveedor();
                    logger.info("Se ingresa a pago de proveedores");
                    break;
                case 4:
                    consultarVentas();
                    logger.info("Se consultaron las ventas del minimarket");
                    break;
                case 5:
                    mostrarBalance();
                    logger.info("Se mostro el balance");
                    break;
                case 6:
                    crearComanda();
                    logger.info("Se solicita una comanda");
                    break;
                case 7:
                    mostrarComandasImpagas();
                    pagarComanda();
                    logger.info("Se paga una cuenta.");
                    break;
                case 8:
                    mostrarPlatosMasPedidos();
                    logger.info("Se muestran estadisticas de plato");
                    break;
                case 9:
                    salida = true;
                    break;
                default:
                    System.out.println("Ingrese un número válido.");
                    break;
            }
        }
        logger.info("Empleado deslogueado legajo: "+ idEmpleado);
    }
    //Salida
    public static void salida(){

    }
    //Mostrar Balance
    public static void mostrarBalance() throws SQLException {
        System.out.println("Ventas");
        System.out.println(SEPARADOR);
        mostrarVentas();
        System.out.println(SEPARADOR);
        System.out.println("Comandas");
        System.out.println(SEPARADOR);
        mostrarComandas();
        System.out.println(SEPARADOR);
        mostrarPagosPendientes();
        System.out.println(SEPARADOR);
        try{
            System.out.println("Ganancias totales: "+calcularGanancias());
            System.out.println("Gastos totales: "+calcularPerdidas());
            System.out.println("Balance: "+ (calcularGanancias()-calcularPerdidas()));
            logger.info("Se muestra el balance");
        }catch (SQLException e){
            System.out.println("Error: "+ e.getMessage());
            logger.error(e.getMessage());
        }

    }
    //Pagar Comanda
    public static void pagarComanda(){
        try{
            System.out.println("Ingresar ID de la Comanda:");
            int idComanda = sc.nextInt();
            sc.nextLine();
            String sql = "UPDATE COMANDA SET COMANDAPAGADO = TRUE WHERE IDCOMANDA = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idComanda);
            int pagar = pstmt.executeUpdate();
            if (pagar > 0){
                System.out.println("Comanda paga correctamente");
            }else{
                System.out.println("Error al pagar la comanda");
            }
        }catch (SQLException e){
            System.out.println("Error al pagar la comanda:" + e.getMessage());
            logger.error(e.getMessage());
        }


    }
    //Crear Comanda
    public static void crearComanda(){
        try {
            System.out.println("Ingresar ID de la Comanda:");
            int idComanda = sc.nextInt();
            sc.nextLine();

            List<int[]> comidaComanda = new ArrayList<>();
            double precioComandaTotal = 0.0;
            boolean agregarOtroPlato = true;

            while (agregarOtroPlato) {
                mostrarTodosLosPlatos();
                System.out.println("Elegir el Plato (ID):");
                int idPlato = sc.nextInt();
                sc.nextLine();
                System.out.println("Ingrese cantidad del plato:");
                int cantidadPlato = sc.nextInt();
                sc.nextLine();

                comidaComanda.add(new int[]{idPlato, cantidadPlato});
                precioComandaTotal += obtenerPrecioPlato(idPlato) * cantidadPlato;

                System.out.println("¿Desea agregar otro plato? (S/N):");
                String respuesta = sc.nextLine();
                if (!respuesta.equalsIgnoreCase("S")) {
                    agregarOtroPlato = false;
                }
            }
            System.out.println("Ingresar la Mesa:");
            int mesa = sc.nextInt();
            sc.nextLine();
            registrarComanda(idComanda, mesa, precioComandaTotal);
            logger.info("Se registra comanda: id: " +  idComanda + " Mesa: " + mesa + " Preciototal: " + precioComandaTotal );
            for (int[] ints : comidaComanda) {
                if (agregarPlatoComanda(idComanda, ints[0], ints[1])){
                    System.out.println("Plato agregado a la comanda");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar la venta: " + e.getMessage());
            logger.error("Error al procesar la venta: " + e.getMessage());
        }
    }
    //Consultar Ventas
    public static void consultarVentas(){
        try{
            System.out.println(SEPARADOR);
            System.out.println("1 - Diaria");
            System.out.println("2 - Mensual");
            System.out.println(SEPARADOR);
            while (true){
                System.out.println("Elija que desea consultar:");
                int opcionConsulta = sc.nextInt();
                sc.nextLine();
                if (opcionConsulta == 1 || opcionConsulta == 2){
                    mostrarVentasFiltrada(opcionConsulta);
                    break;
                }else {
                    System.out.println("Opcion no valida");
                }
            }
        }catch(SQLException e){
            System.out.println(e);
        }
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
            ResultSet rsProducto;
            double precioProducto = 0;
            if (rs){
                System.out.println("Stock Actualizado");
                System.out.println("Ingrese el id para el cupon de pago");
                int idCupon = sc.nextInt();
                sc.nextLine();
                String sql = "SELECT PRECIOPRODUCTO FROM PRODUCTO WHERE IDPRODUCTO = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, idProducto);
                    rsProducto = pstmt.executeQuery();
                    if(rsProducto.next()){
                        precioProducto = rsProducto.getDouble("PRECIOPRODUCTO");
                    }
                }
                generarCuponPago(idCupon, precioProducto * cantidadProducto, idProveedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar la venta: " + e.getMessage());
            logger.error("Error al intentar ingresar mercaderia: " + e.getMessage());
        }

    }

    //Generar Cupon de Pago
    private static void generarCuponPago(int idPago, double costoTotal, int idProveedor){
        String sql = "INSERT INTO PAGOMERCADERIA (IDPAGOMERCADERIA, COSTOTOTALMERCADERIA, FECHADEPAGOMERCADERIA, ESTAPAGADO, PROVEEDOR_IDPROVEEDOR) VALUES (?, ?, CURRENT_DATE, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPago);
            pstmt.setDouble(2, costoTotal);
            pstmt.setBoolean(3, false);
            pstmt.setInt(4, idProveedor);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(SEPARADOR);
                System.out.println("Se genero el Cupon de pago correctamente");
            } else {
                System.out.println("No se pudo insertar la fila.");
            }
        } catch (SQLException e) {
            System.out.println(e);
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
        String sql = "UPDATE PRODUCTO SET STOCKPRODUCTO = STOCKPRODUCTO + ? WHERE IDPRODUCTO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, idProducto);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    private static double calcularGanancias() throws SQLException {
        String sqlVentas = "SELECT PRECIOVENTATOTAL FROM VENTA";
        String sqlComandas = "SELECT PRECIOCOMANDA FROM COMANDA";
        PreparedStatement pstmtVentas = conn.prepareStatement(sqlVentas);
        PreparedStatement pstmtComandas = conn.prepareStatement(sqlComandas);
        ResultSet rsVentas = pstmtVentas.executeQuery();
        ResultSet rsComandas = pstmtComandas.executeQuery();
        double gananciaTotal = 0;
        while (rsComandas.next()){
            double precioComanda = rsComandas.getDouble("PRECIOCOMANDA");
            gananciaTotal += precioComanda;
        }
        while (rsVentas.next()){
            double percioVenta = rsVentas.getDouble("PRECIOVENTATOTAL");
            gananciaTotal += percioVenta;
        }
        return gananciaTotal;
    }

    private static double calcularPerdidas() throws SQLException {
        String sql = "SELECT COSTOTOTALMERCADERIA FROM PAGOMERCADERIA";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        double perdidasTotales = 0;
        while (rs.next()){
            double precioPago = rs.getDouble("COSTOTOTALMERCADERIA");
            perdidasTotales += precioPago;
        }
        return perdidasTotales;
    }

    private static void mostrarVentas() throws SQLException {
        String sql = "SELECT * FROM VENTA ";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            System.out.printf("%-10s %-12s %-14s %-10s%n", "Id Venta", "Id Cliente", "Precio Total", "Fecha");
            System.out.println(SEPARADOR);
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                int idVenta = rs.getInt("IDVENTA");
                String idcliente = rs.getString("IDCLIENTE");
                String precioVentaTotal = rs.getString("PRECIOVENTATOTAL");
                Date fechaVenta = rs.getDate("FECHAVENTA");
                String fechaVentaStr = (fechaVenta != null) ? fechaVenta.toString() : "null";

                System.out.printf("%-10d %-12s %-14s %-20s%n", idVenta, idcliente, precioVentaTotal, fechaVentaStr);
            }

            if (!hasResults) {
                System.out.println("No se encontraron ventas para el dia especificado.");
            }
        }
    }

    private static void mostrarVentasFiltrada(int opcion) throws SQLException {
        String sql;
        if (opcion == 1) {
            sql = "SELECT * FROM VENTA WHERE DAY(FECHAVENTA) = ? AND MONTH(FECHAVENTA) = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                System.out.println("Ingrese el día que desea consultar");
                int diaConsulta = sc.nextInt();
                sc.nextLine();
                System.out.println("Ingrese el mes que desea consultar");
                int mesConsulta = sc.nextInt();
                sc.nextLine();
                pstmt.setInt(1, diaConsulta);
                pstmt.setInt(2, mesConsulta);
                mostrarConsultaVentasFiltrado(pstmt);
            }
        } else if (opcion == 2) {
            sql = "SELECT * FROM VENTA WHERE MONTH(FECHAVENTA) = ? AND YEAR(FECHAVENTA) = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                System.out.println("Ingrese el mes que desea consultar");
                int mesConsulta = sc.nextInt();
                sc.nextLine();
                System.out.println("Ingrese el anio que desea consultar");
                int anioConsulta = sc.nextInt();
                sc.nextLine();
                pstmt.setInt(1, mesConsulta);
                pstmt.setInt(2, anioConsulta);
                mostrarConsultaVentasFiltrado(pstmt);
            }
        } else {
            throw new IllegalArgumentException("Opción no válida");
        }
    }

    private static double obtenerPrecioPlato(int idProducto) throws SQLException {
        String sql = "SELECT PRECIOPLATO FROM PLATO WHERE IDPLATO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("PRECIOPLATO");
            } else {
                throw new SQLException("Producto no encontrado.");
            }
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

    private static boolean agregarPlatoComanda(int idComanda, int idPlato, int cantidadPlato) throws SQLException{
        String sql = "INSERT INTO PLATOSENCOMANDA  (IDPLATO, IDCOMANDA, CANTIDADDEPLATOS) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idPlato);
        pstmt.setInt(2, idComanda);
        pstmt.setInt(3, cantidadPlato);
        int rs = pstmt.executeUpdate();
        if (rs > 0){
            return true;
        }else{
            System.out.println("No se pudo agregar el plato a la comanda");
            return false;
        }
    }

    private static void mostrarComandas() throws SQLException{
        String sql = "SELECT * FROM COMANDA";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("%-10s %-12s %-14s %-10s%n", "Id Comanda", "Mesa", "Fecha", "Precio");
        System.out.println(SEPARADOR);
        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            int idComana = rs.getInt("IDCOMANDA");
            String mesa = rs.getString("COMANDAMESA");
            Date fechaComanda = rs.getDate("FECHACOMANDA");
            double precioComanda = rs.getDouble("PRECIOCOMANDA");

            System.out.printf("%-10d %-12s %-14s %-20s%n", idComana, mesa, fechaComanda, precioComanda);
        }

        if (!hasResults) {
            System.out.println("No se encontraron ventas para el dia especificado.");
        }
    }

    private static void mostrarComandasImpagas() throws SQLException{
        String sql = "SELECT * FROM COMANDA WHERE COMANDAPAGADO = FALSE";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.printf("%-10s %-12s %-14s %-10s%n", "Id Comanda", "Mesa", "Fecha", "Precio");
        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            int idComana = rs.getInt("IDCOMANDA");
            String mesa = rs.getString("COMANDAMESA");
            Date fechaComanda = rs.getDate("FECHACOMANDA");
            double precioComanda = rs.getDouble("PRECIOCOMANDA");

            System.out.printf("%-10d %-12s %-14s %-20s%n", idComana, mesa, fechaComanda, precioComanda);
        }

        if (!hasResults) {
            System.out.println("No se encontraron ventas para el dia especificado.");
        }
    }

    private static void registrarComanda(int idComanda,int mesa,double precioComandaTotal) throws SQLException {
        String sql = "INSERT INTO COMANDA (IDCOMANDA, COMANDAMESA, FECHACOMANDA, PRECIOCOMANDA, COMANDAPAGADO) VALUES (?, ?, CURRENT_DATE, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idComanda);
        pstmt.setInt(2, mesa);
        pstmt.setDouble(3, precioComandaTotal);
        pstmt.setBoolean(4, false);
        int rs = pstmt.executeUpdate();
        if (rs > 0){
            System.out.println("Comanda Creada");
        }else{
            System.out.println("No se pudo crear la comanda");
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

    private static void mostrarConsultaVentasFiltrado(PreparedStatement pstmt) throws SQLException {
        ResultSet rs = pstmt.executeQuery();
        System.out.printf("%-10s %-12s %-14s %-10s%n", "Id Venta", "Id Cliente", "Precio Total", "Fecha");
        System.out.println(SEPARADOR);
        boolean hasResults = false;
        while (rs.next()) {
            hasResults = true;
            int idVenta = rs.getInt("IDVENTA");
            String idcliente = rs.getString("IDCLIENTE");
            String precioVentaTotal = rs.getString("PRECIOVENTATOTAL");
            Date fechaVenta = rs.getDate("FECHAVENTA");
            String fechaVentaStr = (fechaVenta != null) ? fechaVenta.toString() : "null";

            System.out.printf("%-10d %-12s %-14s %-20s%n", idVenta, idcliente, precioVentaTotal, fechaVentaStr);
        }

        if (!hasResults) {
            System.out.println("No se encontraron ventas para el dia especificado.");
        }
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

    public static void mostrarTodosLosPlatos() {
        String sql = "SELECT * FROM PLATO";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.printf("%-10s %-22s %-10s%n", "ID", "Nombre", "Precio");
            System.out.println(SEPARADOR);
            while (rs.next()) {
                int idProducto = rs.getInt("IDPLATO");
                String nombreProducto = rs.getString("NOMBREPLATO");
                double precioProducto = rs.getDouble("PRECIOPLATO");
                System.out.printf("%-10s %-22s %-10s%n", idProducto, nombreProducto, precioProducto);
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
    //METODOS DE PROVEEDOR
    public static void menuProveedor(){

        boolean salir = false;

        while (!salir) {
            System.out.println(SEPARADOR);
            System.out.println("Menu de Pago Proveedores:");
            System.out.println("1. Mostrar Pagos Pendientes");
            System.out.println("2. Realizar Pago");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Pendientes:");
                    mostrarPagosPendientes();
                    break;
                case 2:
                    realizarPagoProveedor();
                    break;
                case 3:
                    salir = true;

                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
    // METODOS PARA PAGO A PROVEEDORES

    public static void mostrarPagosPendientes() {
        String sql = "SELECT * FROM PAGOMERCADERIA WHERE ESTAPAGADO = FALSE";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Pagos Proveedores:");
            System.out.println(SEPARADOR);
            System.out.println("ID Pago  | Costo Total | Fecha de Pago | Proveedor     ");
            System.out.println(SEPARADOR);
            while (rs.next()) {
                int idPago = rs.getInt("IDPAGOMERCADERIA");
                double costoTotal = rs.getDouble("COSTOTOTALMERCADERIA");
                Date fechaPago = rs.getDate("FECHADEPAGOMERCADERIA");
                int idProveedor = rs.getInt("PROVEEDOR_IDPROVEEDOR");
                String nombreProveedor = obtenerNombreProveedor(idProveedor);
                System.out.printf("%-8d | %11.2f | %-12tF | %-12s%n", idPago, costoTotal, fechaPago, nombreProveedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar pagos pendientes: " + e.getMessage());
        }
    }

    private static String obtenerNombreProveedor(int idProveedor) {
        String sql = "SELECT RAZONSOCIALPROVEEDOR   FROM PROVEEDOR WHERE IDPROVEEDOR = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idProveedor);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("RAZONSOCIALPROVEEDOR");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombre del proveedor: " + e.getMessage());
        }
        return "Proveedor Desconocido";
    }

    public static void realizarPagoProveedor() {

        mostrarPagosPendientes();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el ID del pago que desea pagar:");
        int idPago = sc.nextInt();
        sc.nextLine();

        String sql = "UPDATE PAGOMERCADERIA SET ESTAPAGADO = TRUE WHERE IDPAGOMERCADERIA = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idPago);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Pago marcado como pagado exitosamente.");
            } else {
                System.out.println("No se encontró ningún pago con el ID especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al marcar el pago como pagado: " + e.getMessage());
            logger.error(e.getMessage());
        }
    }

    public static void mostrarPlatosMasPedidos() {
        String sql = "SELECT p.NOMBREPLATO AS Plato, SUM(pc.CANTIDADDEPLATOS) AS Cantidad_Pedidos " +
                "FROM PLATOSENCOMANDA pc " +
                "JOIN PLATO p ON pc.IDPLATO = p.IDPLATO " +
                "GROUP BY p.NOMBREPLATO " +
                "ORDER BY Cantidad_Pedidos DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Platos más pedidos:");
            System.out.println(SEPARADOR);
            while (rs.next()) {
                String plato = rs.getString("Plato");
                int cantidadPedidos = rs.getInt("Cantidad_Pedidos");
                System.out.println(plato + ": " + cantidadPedidos);
                logger.info("Cantidad del plato:" + plato + ": " + cantidadPedidos);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

}