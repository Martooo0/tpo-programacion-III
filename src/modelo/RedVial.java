package modelo;

import tda.GrafoMatriz;
import tda.GrafoTDA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedVial {
    private Map<Integer, Ubicacion> ubicaciones; // guarda el id (vertice) como clave y luego lo demás como valores
    private List<Conexion> conexiones; // aristas

    public RedVial() {
        ubicaciones = new HashMap<>();
        conexiones = new ArrayList<>();
    }

    public void agregarUbicacion(Ubicacion ubicacion) {
        ubicaciones.put(ubicacion.getId(), ubicacion);
    } // agrega ubicaciones (es para testear si anda)


    public void agregarConexion(int origen, int destino, int tiempo) {
        if (!ubicaciones.containsKey(origen) || !ubicaciones.containsKey(destino)) { // si no existe una u otra no se puede hacer la arista
            throw new IllegalArgumentException("La conexion debe unir ubicaciones existentes");
        }
        conexiones.add(new Conexion(origen, destino, tiempo)); // importa el orden
    } // Este es el que agrega las aristas entre los nodos

    public Ubicacion getUbicacion(int id) {
        return ubicaciones.get(id);
    }

    public Map<Integer, String> getNombres() { // Es para que cuando se imprima se lea mejor con los nombres de los vertices. Si no sería "se confirma 3 con tiempo 1", en vez de "Se confirma el Centro Oeste con tiempo 1". Dijkstra solo trabaja con números, por eso.
        Map<Integer, String> nombres = new HashMap<>();
        for (Ubicacion ubicacion : ubicaciones.values()) {
            nombres.put(ubicacion.getId(), ubicacion.getNombre());
        }
        return nombres;
    }

    // Deshabilita una arista
    public void deshabilitarConexion(int origen, int destino) {
        buscarConexion(origen, destino).setHabilitada(false);
    }

    // La volves a habilitar
    public void habilitarConexion(int origen, int destino) {
        buscarConexion(origen, destino).setHabilitada(true);
    }

    public GrafoTDA construirGrafo() { // El más importante de todos porque aca arma el grafo para mandarlo a Dijkstra (lo va a usar Resolucion y se lo pasa esa clase en realidad).
        GrafoTDA grafo = new GrafoMatriz();
        for (int id : ubicaciones.keySet()) {
            grafo.agregarVertice(id); // agrega cada clave de ubicaciones como nodos del grafo
        }
        for (Conexion conexion : conexiones) {
            if (conexion.isHabilitada()) {
                grafo.agregarArista(conexion.getOrigen(), conexion.getDestino(), conexion.getTiempo()); // si la conexion está habilitada, crea la arista también.
            }
        }
        return grafo;
    }


    private Conexion buscarConexion(int origen, int destino) {
        for (Conexion conexion : conexiones) {
            if (conexion.getOrigen() == origen && conexion.getDestino() == destino) {
                return conexion;
            } // busca una arista si existe basicmante.
        }
        throw new IllegalArgumentException("No existe la conexion " + origen + " -> " + destino);
    }
}
