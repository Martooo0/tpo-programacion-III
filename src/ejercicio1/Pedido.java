package ejercicio1;

import java.time.LocalDate;

public final class Pedido {
    // uso final porque no quiero modificar los datos luego de crearlos
    private final int id;
    private final String centroDestino;
    private final LocalDate fechaLimite;
    private final int prioridad;
    private final double pesoTotal;

    // Constructor junto a las validaciones de datos.
    public Pedido(int id, String centroDestino, LocalDate fechaLimite, int prioridad, double pesoTotal) {
        if (centroDestino == null || centroDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("El centro de destino es obligatorio.");
        }
        if (fechaLimite == null) {
            throw new IllegalArgumentException("La fecha limite es obligatoria.");
        }
        if (!Double.isFinite(pesoTotal) || pesoTotal <= 0) {
            throw new IllegalArgumentException("El peso debe ser positivo y finito.");
        }
        this.id = id;
        this.centroDestino = centroDestino;
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridad;
        this.pesoTotal = pesoTotal;
    }

    // getters
    public int getId() {
        return id;
    }
    public String getCentroDestino() {
        return centroDestino;
    }
    public LocalDate getFechaLimite() {
        return fechaLimite;
    }
    public int getPrioridad() {
        return prioridad;
    }
    public double getPesoTotal() {
        return pesoTotal;
    }

    @Override
    public String toString() {
        return "Pedido " + id + " | " + centroDestino + " | fecha: " + fechaLimite
                + " | prioridad: " + prioridad + " | peso: " + pesoTotal + " kg";
    }
}
