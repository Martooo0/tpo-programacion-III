package ejercicio2;

import modelo.Ubicacion;

import java.util.List;

public class ResultadoRuta { // guarda los datos para armar la resolución
    private Ubicacion origen;
    private Ubicacion destino;
    private boolean existeRuta;
    private List<Ubicacion> recorrido; // todas las ubicaciones
    private int tiempoTotal; // suma de los pesos de las aristas
    private List<String> decisiones; // es lo que registro dijkstra

    public ResultadoRuta(Ubicacion origen, Ubicacion destino, boolean existeRuta, List<Ubicacion> recorrido, int tiempoTotal, List<String> decisiones) {
        this.origen = origen;
        this.destino = destino;
        this.existeRuta = existeRuta;
        this.recorrido = recorrido;
        this.tiempoTotal = tiempoTotal;
        this.decisiones = decisiones;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public boolean existeRuta() {
        return existeRuta;
    }

    public List<Ubicacion> getRecorrido() {
        return recorrido;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public List<String> getDecisiones() {
        return decisiones;
    }
}
