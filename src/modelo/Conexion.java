package modelo;

public class Conexion { // Vendría siendo la arista entre dos ubicaciones
    private int origen;
    private int destino;
    private int tiempo;
    private boolean habilitada;

    public Conexion(int origen, int destino, int tiempo) {
        if (tiempo <= 0) {
            throw new IllegalArgumentException("El tiempo de viaje debe ser positivo"); // es porque tienen que ser positivos los pesos
        }
        this.origen = origen;
        this.destino = destino;
        this.tiempo = tiempo;
        this.habilitada = true; // se va a usar para deshabilitar una arista
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public int getTiempo() {
        return tiempo;
    }

    public boolean isHabilitada() {
        return habilitada;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }
}
