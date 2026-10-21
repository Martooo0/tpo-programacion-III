package algoritmos;

import tda.GrafoMatriz;
import tda.GrafoTDA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dijkstra {
    private Map<Integer, String> nombres;
    private Map<Integer, Integer> anterior;
    private List<String> decisiones;

    public Dijkstra() {
        this(new HashMap<>());
    }

    public Dijkstra(Map<Integer, String> nombres) {
        this.nombres = nombres;
        this.anterior = new HashMap<>();
        this.decisiones = new ArrayList<>();
    }

    public GrafoTDA ejecutar(GrafoTDA g, int v) {
        anterior = new HashMap<>();
        decisiones = new ArrayList<>();

        Set<Integer> visitados = new HashSet<>();
        visitados.add(v);

        GrafoTDA a = new GrafoMatriz();
        for (int w : g.vertices()) { // para cada vertice w que este dentro del grafo g hace lo siguiente:
            a.agregarVertice(w); // agrégalos a todos al grafo resultado "a" (todavía no se conocen los caminos). Cosa obvia porque necesitas optimizarlo, no borrar aristas
        }
        for (int vPrima : g.adyacentes(v)) { // agrega las aristas
            a.agregarArista(v, vPrima, g.pesoArista(v, vPrima));
            anterior.put(vPrima, v);
            decisiones.add("Inicio: se alcanza " + nombre(vPrima) + " con tiempo " + g.pesoArista(v, vPrima) + " por conexion directa desde " + nombre(v));
        }

        Set<Integer> pendientes = new HashSet<>(g.vertices());
        pendientes.removeAll(visitados);

        while (!pendientes.isEmpty()) {
            int w = seleccionarMinimo(a, v, pendientes);
            if (w == -1) {
                decisiones.add("No quedan ubicaciones alcanzables entre los pendientes " + nombres(pendientes));
                break;
            }
            visitados.add(w);
            pendientes.remove(w);
            decisiones.add("Se confirma " + nombre(w) + " con tiempo " + a.pesoArista(v, w));

            Set<Integer> auxPendientes = new HashSet<>(pendientes);
            while (!auxPendientes.isEmpty()) {
                int p = auxPendientes.iterator().next();
                auxPendientes.remove(p);
                if (a.existeArista(v, w) && g.existeArista(w, p)) {
                    int nuevoPeso = a.pesoArista(v, w) + g.pesoArista(w, p);
                    if (a.existeArista(v, p)) {
                        if (nuevoPeso < a.pesoArista(v, p)) {
                            decisiones.add("  Mejora " + nombre(p) + ": de " + a.pesoArista(v, p) + " a " + nuevoPeso + " pasando por " + nombre(w));
                            a.agregarArista(v, p, nuevoPeso);
                            anterior.put(p, w);
                        } else {
                            decisiones.add("  No mejora " + nombre(p) + ": " + nuevoPeso + " pasando por " + nombre(w) + " no es menor que " + a.pesoArista(v, p));
                        }
                    } else {
                        a.agregarArista(v, p, nuevoPeso);
                        anterior.put(p, w);
                        decisiones.add("  Se alcanza " + nombre(p) + " con tiempo " + nuevoPeso + " pasando por " + nombre(w));
                    }
                }
            }
        }
        return a;
    }

    public Map<Integer, Integer> getAnterior() {
        return anterior;
    }


    public List<String> getDecisiones() {
        return decisiones;
    }

    private int seleccionarMinimo(GrafoTDA a, int v, Set<Integer> pendientes) {
        int minimo = -1;
        int pesoMinimo = Integer.MAX_VALUE; // Significa infinito
        for (int n : pendientes) { // para cada vertice "n" que este dentro del conjunto de los vertices pendientes, hace lo siguiente:
            if (a.existeArista(v, n) && a.pesoArista(v, n) < pesoMinimo) { // si a tiene una arista "provisoria" entre el origen y un vertice dentro de pendientes y el peso de esa arista es menor a pesoMinimo encontrado hasta ahora
                pesoMinimo = a.pesoArista(v, n); // asigna ese peso a pesoMinimo
                minimo = n; // y tambien pone a ese vertice n en la variable minimo
            }
        }
        return minimo;
    }

    private String nombre(int id) { // es para devolver el nombre de un vertice
        if (nombres.containsKey(id)) {
            return nombres.get(id);
        }
        return String.valueOf(id);
    }

    private String nombres(Set<Integer> ids) { // y aca se guardan todos
        List<String> resultado = new ArrayList<>();
        for (int id : ids) {
            resultado.add(nombre(id));
        }
        return resultado.toString();
    }
}
