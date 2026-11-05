package algoritmos;

import tda.GrafoTDA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Calcula los caminos minimos entre todos los pares de vertices.
// Usa Programacion Dinamica: permite un nuevo vertice intermedio en cada vuelta de k.
public final class Floyd {
    private static final long INFINITO = Long.MAX_VALUE;

    // Los IDs no tienen por que ser consecutivos: el ID 50 puede estar en la fila 0.
    private final int[] ids;
    private final Map<Integer, Integer> posiciones;

    // tiempos[i][j]: menor tiempo conocido desde la posicion i hasta la j.
    private final long[][] tiempos;

    // siguiente[i][j]: posicion del primer paso para ir de i hasta j.
    // -1 significa que no hay camino. Es una posicion, no un ID de ubicacion.
    private final int[][] siguiente;

    public Floyd(GrafoTDA grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo es obligatorio.");
        }

        Set<Integer> vertices = grafo.vertices();
        int cantidad = vertices.size();
        ids = new int[cantidad];
        posiciones = new HashMap<Integer, Integer>();
        tiempos = new long[cantidad][cantidad];
        siguiente = new int[cantidad][cantidad];

        int posicion = 0;
        for (int id : vertices) {
            ids[posicion] = id;
            posiciones.put(id, posicion);
            posicion++;
        }

        inicializar(grafo);
        ejecutar();
    }

    private void inicializar(GrafoTDA grafo) {
        for (int i = 0; i < ids.length; i++) {
            for (int j = 0; j < ids.length; j++) {
                tiempos[i][j] = INFINITO;
                siguiente[i][j] = -1;

                if (grafo.existeArista(ids[i], ids[j])) {
                    int peso = grafo.pesoArista(ids[i], ids[j]);
                    if (peso <= 0) {
                        throw new IllegalArgumentException("Los tiempos deben ser positivos.");
                    }
                    tiempos[i][j] = peso;
                    siguiente[i][j] = j;
                }

                // Quedarse en la misma ubicacion cuesta 0, incluso si hay un lazo positivo.
                if (i == j) {
                    tiempos[i][j] = 0;
                    siguiente[i][j] = i;
                }
            }
        }
    }

    private void ejecutar() {
        for (int k = 0; k < ids.length; k++) {
            for (int i = 0; i < ids.length; i++) {
                for (int j = 0; j < ids.length; j++) {
                    if (i != j && tiempos[i][k] != INFINITO && tiempos[k][j] != INFINITO) {
                        long pasandoPorK = tiempos[i][k] + tiempos[k][j];

                        if (pasandoPorK < tiempos[i][j]) {tiempos[i][j] = pasandoPorK;
                            siguiente[i][j] = siguiente[i][k];
                        }
                    }
                }
            }
        }
    }

    // Consulta la tabla ya calculada: no vuelve a ejecutar Floyd.
    // Devuelve -1 cuando no hay ruta; 0 significa origen igual a destino.
    public long tiempoMinimo(int origen, int destino) {
        int i = posicionDe(origen);
        int j = posicionDe(destino);
        if (tiempos[i][j] == INFINITO) {
            return -1;
        }
        return tiempos[i][j];
    }

    // Devuelve los IDs del recorrido, en orden, incluyendo origen y destino.
    public List<Integer> reconstruirRecorrido(int origen, int destino) {
        int actual = posicionDe(origen);
        int llegada = posicionDe(destino);
        List<Integer> recorrido = new ArrayList<Integer>();

        if (siguiente[actual][llegada] == -1) {
            return recorrido;
        }

        recorrido.add(ids[actual]);

        while (actual != llegada) {
            actual = siguiente[actual][llegada];
            recorrido.add(ids[actual]);
        }
        return recorrido;
    }

    private int posicionDe(int id) {
        Integer posicion = posiciones.get(id);
        if (posicion == null) {
            throw new IllegalArgumentException("La ubicacion " + id + " no esta en el calculo.");
        }
        return posicion;
    }
}
