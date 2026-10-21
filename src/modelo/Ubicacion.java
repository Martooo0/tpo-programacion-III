package modelo;

public class Ubicacion { // Lo que tiene cada nodo(deposito o centro) "adentro".
    private int id;
    private String nombre;
    private TipoUbicacion tipo;

    public Ubicacion(int id, String nombre, TipoUbicacion tipo) {
        this.id = id; // el id se vertice
        this.nombre = nombre;
        this.tipo = tipo; // si es deposito o centro
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoUbicacion getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre;
    } // para imprimirlo mejor
}
