//package Repositorios;
//
//import Logica.*;
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class RepositorioTransacciones implements Repositorio<Transaccion> {
//    private String rutaArchivo;
//
//    public RepositorioTransacciones(String rutaArchivo) {
//        this.rutaArchivo = rutaArchivo;
//    }
//
//    @Override
//    public void cargarDesdeArchivo(String archivo) {
//        List<Transaccion> lista = leerTodos();
//        // esta lista se puede cargar en la memoria de la billetera
//    }
//
//    // métodos auxiliar para leer todas las líneas y convertirlas a objetos
//    public List<Transaccion> leerTodos() {
//        List<Transaccion> transacciones = new ArrayList<>(); // va a crear una lista con objetos de tipo transaccion
//        File file = new File(rutaArchivo);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"); // Ajustar a tu formato
//
//        try (Scanner sc = new Scanner(file)) {
//            while (sc.hasNextLine()) {
//                String linea = sc.nextLine();
//                String[] datos = linea.split(","); // Suponiendo separador coma
//
//                // Estructura sugerida del CSV: TIPO,MONTO,CEDULA_USUARIO,FECHA
//                String tipo = datos[0];
//                double monto = Double.parseDouble(datos[1]);
//                String cedula = datos[2];
//                String fechaStr = datos[3];
//                LocalDateTime fecha = LocalDateTime.parse(fechaStr, formatter); // Ojo con el formato
//
//                // crear un usuario temporal solo con la cédula para asignar a la transacción
//                // (lo ideal sería buscar el usuario real en un RepositorioUsuarios)
//                Usuario usuarioTemp = new Usuario(cedula, "Desconocido");
//
//                Transaccion t = null;
//
//                // FACTORY: Decidir qué objeto crear según el tipo
//                switch (tipo) {
//                    case "DEPOSITO":
//                        t = new Deposito(monto, usuarioTemp, fecha);
//                        break;
//                    case "RETIRO":
//                        t = new Retiro(usuarioTemp, monto);
//                        // Nota: Retiro.java no tiene constructor con fecha en tu código actual,
//                        // tal vez necesites agregarlo o usar setters.
//                        break;
//                    case "TRANSFERENCIA":
//                        t = new Transferencia(monto, usuarioTemp);
//                        break;
//                    // ... otros casos
//                }
//
//                if (t != null) {
//                    transacciones.add(t);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Archivo no encontrado, se creará uno nuevo al guardar.");
//        } catch (Exception e) {
//            System.out.println("Error leyendo archivo: " + e.getMessage());
//        }
//        return transacciones;
//    }
//
//    @Override
//    public void guardar(Transaccion elemento) {
//        // true en FileWriter para modo "append" (agregar al final sin borrar lo anterior)
//        try (FileWriter fw = new FileWriter(rutaArchivo, true);
//             PrintWriter pw = new PrintWriter(fw)) {
//
//            String tipo = "";
//            if (elemento instanceof Deposito) tipo = "DEPOSITO";
//            else if (elemento instanceof Retiro) tipo = "RETIRO";
//            else if (elemento instanceof Transferencia) tipo = "TRANSFERENCIA";
//
//            // Necesitas obtener la fecha del objeto.
//            // Sugerencia: Agrega un getter para fechaHora en Transaccion.java
//            // String fechaStr = elemento.getFechaHora().format(...)
//
//            // Escribir en formato CSV
//            // TIPO,MONTO,CEDULA,FECHA
//            pw.println(tipo + "," +
//                    elemento.getMonto() + "," +
//                    // Asumiendo que agregas un getter para usuario en Transaccion
//                    // elemento.getUsuario().getCedula() + "," +
//                    "FECHA_AQUI");
//
//        } catch (IOException e) {
//            System.out.println("Error guardando: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void guardarEnArchivo(String archivo) {
//        // Este método podría sobrar si usas guardar() uno por uno,
//        // o podrías usarlo para guardar una lista completa de golpe.
//    }
//
//    @Override
//    public Transaccion buscar(String id) {
//        // Recorrer el archivo o la lista en memoria buscando por ID de transacción
//        return null;
//    }
//}