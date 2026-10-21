package ejercicio3;

// Representa un lote indivisible: se carga completo o se deja fuera del viaje.

public final class Lote {
    private final int id;
    private final int peso;
    private final int prioridad;

    public Lote(int id, int peso, int prioridad) {
        // La consigna exige peso entero positivo y prioridad entera no negativa.
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        if (prioridad < 0) {
            throw new IllegalArgumentException("La prioridad no puede ser negativa.");
        }
        this.id = id;
        this.peso = peso;
        this.prioridad = prioridad;
    }

    // getters
    public int getId() { return id; }
    public int getPeso() { return peso; }
    public int getPrioridad() { return prioridad; }

    @Override
    public String toString() {
        return "Lote " + id + " | peso: " + peso + " kg | prioridad: " + prioridad;
    }
}
