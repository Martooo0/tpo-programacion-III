package ejercicioA1;

import modelo.Ubicacion;
import java.util.ArrayList;
import java.util.List;

// Agrupa la respuesta de una consulta. No calcula caminos.
public final class ResultadoConsultaRuta {
    private final Ubicacion origen;
    private final Ubicacion destino;
    private final long tiempoTotal;
    private final List<Ubicacion> recorrido;

    public ResultadoConsultaRuta(Ubicacion origen, Ubicacion destino, long tiempoTotal, List<Ubicacion> recorrido) {
        this.origen = origen;
        this.destino = destino;
        this.tiempoTotal = tiempoTotal;
        this.recorrido = new ArrayList<Ubicacion>(recorrido);
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public boolean existeRuta() {
        return tiempoTotal >= 0;
    }

    // -1 indica ausencia de ruta. No es un tiempo de viaje valido.
    public long getTiempoTotal() {
        return tiempoTotal;
    }

    public List<Ubicacion> getRecorrido() {
        // La copia evita que quien consulta modifique el recorrido guardado.
        return new ArrayList<Ubicacion>(recorrido);
    }

    @Override
    public String toString() {
        if (!existeRuta()) {
            return "Desde " + origen + " hasta " + destino + ": no existe ruta disponible.";
        }
        return "Desde " + origen + " hasta " + destino + ": " + recorrido + ". Tiempo total: " + tiempoTotal + " minutos.";
    }
}
