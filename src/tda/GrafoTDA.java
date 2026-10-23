package tda;

import java.util.Set;

public interface GrafoTDA { // es el contrato que firma GrafoMatriz. Son las operaciones que va a poder hacer el grafo.

    void inicializarGrafo();

    void agregarVertice(int v);

    void eliminarVertice(int v);

    void agregarArista(int origen, int destino, int peso); // agrega o reemplaza

    void eliminarArista(int origen, int destino);

    boolean existeArista(int origen, int destino);

    int pesoArista(int origen, int destino); // para retornar el peso de la arista

    Set<Integer> vertices(); // para ver cuantos vertices hay

    Set<Integer> adyacentes(int v); // son los destinos de las aristas que salen del nodo "v", o sea los nodos adyacentes.
}
