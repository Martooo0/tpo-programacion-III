package modelo;

public class Vehiculo {
    private int id;
    private int capacidad;

    public Vehiculo(int id, int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad del vehiculo debe ser positiva");
        }
        this.id = id;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return "Vehiculo " + id;
    }
}
