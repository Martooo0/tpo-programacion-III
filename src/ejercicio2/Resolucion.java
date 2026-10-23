package ejercicio2;

import algoritmos.Dijkstra;
import modelo.RedVial;
import modelo.Ubicacion;
import tda.GrafoTDA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Resolucion { // Aca esta t0do, arma el grafo y se lo pasa a Dijkstra, reconstruye el camino que hizo con anterior y lo muestra después con el Test

    public ResultadoRuta calcularRutaMasRapida(RedVial red, int origen, int destino) {
        Ubicacion ubicacionOrigen = red.getUbicacion(origen);
        Ubicacion ubicacionDestino = red.getUbicacion(destino);
        if (ubicacionOrigen == null || ubicacionDestino == null) {
            throw new IllegalArgumentException("El origen y destino tienen que estar en la red");
        }

        if (origen == destino) {
            List<Ubicacion> recorrido = new ArrayList<>();
            recorrido.add(ubicacionOrigen);
            List<String> decisiones = new ArrayList<>();
            decisiones.add("El origen coincide con el destino");
            return new ResultadoRuta(ubicacionOrigen, ubicacionDestino, true, recorrido, 0, decisiones);
        }

        GrafoTDA grafo = red.construirGrafo(); // armas el grafo
        Dijkstra dijkstra = new Dijkstra(red.getNombres());
        GrafoTDA caminosMinimos = dijkstra.ejecutar(grafo, origen); // se lo pasas a Dijkstra

        if (!caminosMinimos.existeArista(origen, destino)) {
            return new ResultadoRuta(ubicacionOrigen, ubicacionDestino, false, new ArrayList<>(), 0, dijkstra.getDecisiones()); // si no hay una arista que vaya desde el dominio hasta el destino
        }

        List<Ubicacion> recorrido = reconstruirRecorrido(red, dijkstra.getAnterior(), origen, destino);
        int tiempo = caminosMinimos.pesoArista(origen, destino);
        return new ResultadoRuta(ubicacionOrigen, ubicacionDestino, true, recorrido, tiempo, dijkstra.getDecisiones());
        // Si hay una ruta posible entre origen y destino, recrea el grafo con las decisiones, el tiempo y demás.
    }

    public void mostrar(ResultadoRuta resultado) {
        System.out.println("Ruta desde " + resultado.getOrigen() + " hasta " + resultado.getDestino());
        System.out.println("Decisiones:");
        for (String decision : resultado.getDecisiones()) { // recorre la lista de decisiones que armó Dijkstra
            System.out.println("  " + decision); // y las imprime
        }
        if (!resultado.existeRuta()) {
            System.out.println("No existe una ruta disponible entre " + resultado.getOrigen() + " y " + resultado.getDestino());
            return;
        }
        StringBuilder recorrido = new StringBuilder(); // armas un string del recorrido completo
        for (int i = 0; i < resultado.getRecorrido().size(); i++) {
            if (i > 0) {
                recorrido.append(" -> "); // para que se vea más prolijo y entendible
            }
            recorrido.append(resultado.getRecorrido().get(i));
        }
        System.out.println("Recorrido: " + recorrido);
        System.out.println("Tiempo total: " + resultado.getTiempoTotal() + " minutos");
    }

    private List<Ubicacion> reconstruirRecorrido(RedVial red, Map<Integer, Integer> anterior, int origen, int destino) {
        List<Ubicacion> recorrido = new ArrayList<>();
        int actual = destino;
        while (actual != origen) {
            recorrido.add(0, red.getUbicacion(actual));
            actual = anterior.get(actual);
        }
        recorrido.add(0, red.getUbicacion(origen));
        return recorrido;
    } // no imprime nada, solo arma la lista de ubicaciones que tiene camino. Porque Dijkstra solo te dice que llego desde un nodo hasta tal nodo. Este metodo como que sigue eso y te arma la lista ordenada.
}
