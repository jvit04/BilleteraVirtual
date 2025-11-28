package Repositorios;

public interface Repositorio {
    // cargar datos desde un archivo
    public void cargarDesdeArchivo(String archivo); // recibiria la ruta del archivo

    // buscar un elemento por ID y nos deberia devolver el tipo de transaccion del tipo de repositorio que esta buscando
    //public T buscar(String id);


    // guardar/agregar un nuevo elemento
    //void guardar(T elemento);

}

