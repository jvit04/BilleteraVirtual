package Repositorios;

import java.io.IOException;

public interface Almacenable {
    void guardarEnArchivo() throws IOException;
    void cargarDesdeArchivo(String ruta);
}