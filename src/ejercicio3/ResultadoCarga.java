package ejercicio3;

// Reúne los datos para mostrarlos después de elegir la carga.
public final class ResultadoCarga {
    private final Lote[] lotesSeleccionados;
    private final int pesoTotal;
    private final long prioridadTotal;
    private final int capacidadRestante;

    // El algoritmo llama a este constructor con su selección final.
    ResultadoCarga(Lote[] seleccionados, int capacidadMaxima) {
        this.lotesSeleccionados = seleccionados.clone();
        int peso = 0;
        long prioridad = 0;
        for (int i = 0; i < seleccionados.length; i++) {
            peso = peso + seleccionados[i].getPeso();
            prioridad = prioridad + seleccionados[i].getPrioridad();
        }
        this.pesoTotal = peso;
        this.prioridadTotal = prioridad;
        this.capacidadRestante = capacidadMaxima - peso;
    }

    // Devolvemos una copia para que cambiar el arreglo recibido no altere el resultado.
    public Lote[] getLotesSeleccionados() {
        return lotesSeleccionados.clone();
    }
    public int getPesoTotal() {
        return pesoTotal;
    }
    public long getPrioridadTotal() {
        return prioridadTotal;
    }
    public int getCapacidadRestante() {
        return capacidadRestante;
    }
}
