package ejercicioA1;

import algoritmos.Floyd;
import modelo.RedVial;
import modelo.Ubicacion;
import tda.GrafoTDA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Prepara los resultados una vez y permite hacer muchas consultas.
public final class GestorRutas {
    private final RedVial red;
    private Floyd floyd;
    private Map<Integer, Ubicacion> ubicacionesCalculadas;

    public GestorRutas(RedVial red) {
        if (red == null) {
            throw new IllegalArgumentException("La red vial es obligatoria.");
        }
        this.red = red;
        recalcular();
    }

    // Llamar despues de agregar ubicaciones o conexiones, habilitarlas o deshabilitarlas.
    // Hasta recalcular, las consultas corresponden a la ultima red calculada.
    public void recalcular() {
        GrafoTDA grafo = red.construirGrafo();
        Floyd nuevoCalculo = new Floyd(grafo);
        Map<Integer, Ubicacion> nuevasUbicaciones = new HashMap<Integer, Ubicacion>();
        for (int id : grafo.vertices()) {
            nuevasUbicaciones.put(id, red.getUbicacion(id));
        }

        // Reemplazamos los resultados solo despues de completar el nuevo calculo.
        floyd = nuevoCalculo;
        ubicacionesCalculadas = nuevasUbicaciones;
    }

    // Si solo necesitamos el tiempo, no hace falta construir la lista del recorrido.
    public long consultarTiempo(int origen, int destino) {
        return floyd.tiempoMinimo(origen, destino);
    }

    public ResultadoConsultaRuta consultar(int origen, int destino) {
        long tiempo = floyd.tiempoMinimo(origen, destino);
        List<Integer> ids = floyd.reconstruirRecorrido(origen, destino);
        List<Ubicacion> recorrido = new ArrayList<Ubicacion>();

        // Floyd trabaja con IDs; aca recuperamos los objetos con sus nombres y tipos.
        for (int id : ids) {
            recorrido.add(ubicacionesCalculadas.get(id));
        }

        return new ResultadoConsultaRuta(ubicacionesCalculadas.get(origen), ubicacionesCalculadas.get(destino), tiempo, recorrido);
    }
}
