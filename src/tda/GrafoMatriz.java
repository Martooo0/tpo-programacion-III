package tda;

import java.util.HashSet;
import java.util.Set;

public class GrafoMatriz implements GrafoTDA {
    private static final int CAPACIDAD = 100; // esta constante te da la cantidad de nodos que puede tener el grafo

    private int[][] mAdy; // la más importante porque te guarda donde hay arista o no (las conexiones)
    // Básicamente, las filas son las de origen y las columnas los destinos

    //           destinos → pos0  pos1  pos2  pos3  pos4  pos5
    //                      (1)   (2)   (3)   (4)   (5)   (6)
    //  orígenes pos0 (1) [  0     4     1     0     0     0 ]
    //           pos1 (2) [  0     0     0     5     0     0 ]
    //           pos2 (3) [  0     2     0     8    12     0 ]
    //           pos3 (4) [  0     0     0     0     3     0 ]
    //           pos4 (5) [  0     0     0     0     0     0 ]
    //           pos5 (6) [  0     0     0     0     0     0 ]

    // Entonces hace mAdy[1][2] y te va a dar 4, por ende, desde la posición 0 hasta la posición 1 hay una arista que pesa 4. De hecho si se hace mAdy[2][1] no te va a dar una arista porque ahi comprobás que un grafo dirigido, si no sería simétrico eso

    private int[] etiquetas; // aca se guardan los ids de los nodos que estan en la posicion i de la matriz
    private int cantNodos; // cantidad de nodos creados. Sirve para despues validar que no se pase de CANTIDAD.

    public GrafoMatriz() {
        inicializarGrafo();
    }

    @Override
    public void inicializarGrafo() {
        mAdy = new int[CAPACIDAD][CAPACIDAD];
        etiquetas = new int[CAPACIDAD];
        cantNodos = 0;
    }

    @Override
    public void agregarVertice(int v) { // agrega el nodo "v" en la posición que este libre
        if (indice(v) != -1) {
            return; // si ya hay algo, no hace nada
        }
        if (cantNodos == CAPACIDAD) {
            throw new IllegalStateException("Se alcanzo la capacidad maxima del grafo"); // está lleno
        }
        etiquetas[cantNodos] = v; // Aca guardas el id del nodo en la siguiente posición libre, ya que el id siempre va adelante de las posiciones: 3 nodos (posiciones 0, 1, 2). El número de nodos cargados siempre coincide con el primer lugar libre.
        for (int i = 0; i <= cantNodos; i++) {
            mAdy[cantNodos][i] = 0;
            mAdy[i][cantNodos] = 0;
        } // el for este limpia la fila y columna del id .
        cantNodos++; // aumentas en uno para que no se use de nuevo esa position
    }

    //    cantNodos = 0, etiquetas = [ _, _, _, ... ]
    //    agregarVertice(100)
    //  - ¿Existe 100? No. ¿Hay lugar? Sí.
    //  - etiquetas[0] = 100
    //  - Limpia la fila 0 y la columna 0.
    //  - cantNodos pasa a 1.
    //  - etiquetas = [100, _, _, ...]

    @Override
    public void eliminarVertice(int v) { // lo contrario a agregarVertice
        int ind = indice(v);
        if (ind == -1) {
            return;
        }
        int ultimo = cantNodos - 1;
        for (int k = 0; k < cantNodos; k++) {
            mAdy[k][ind] = mAdy[k][ultimo];
        }
        for (int k = 0; k < cantNodos; k++) {
            mAdy[ind][k] = mAdy[ultimo][k];
        }
        etiquetas[ind] = etiquetas[ultimo];
        cantNodos--; // reduce para desocupar
    }

    @Override
    public void agregarArista(int origen, int destino, int peso) {
        int o = indiceExistente(origen);
        int d = indiceExistente(destino);
        mAdy[o][d] = peso; // pone el peso en la matriz
    }

    @Override
    public void eliminarArista(int origen, int destino) {
        int o = indiceExistente(origen);
        int d = indiceExistente(destino);
        mAdy[o][d] = 0;
    }

    @Override
    public boolean existeArista(int origen, int destino) {
        int o = indiceExistente(origen);
        int d = indiceExistente(destino);
        return mAdy[o][d] != 0;
    }

    @Override
    public int pesoArista(int origen, int destino) {
        int o = indiceExistente(origen);
        int d = indiceExistente(destino);
        return mAdy[o][d];
    }

    @Override
    public Set<Integer> vertices() {
        Set<Integer> resultado = new HashSet<>();
        for (int i = 0; i < cantNodos; i++) {
            resultado.add(etiquetas[i]);
        }
        return resultado; // devuelve todos los ids de los vertices
    }

    @Override
    public Set<Integer> adyacentes(int v) {
        Set<Integer> resultado = new HashSet<>();
        int o = indiceExistente(v); // guardas en o la posición (fila) de v
        for (int i = 0; i < cantNodos; i++) {
            if (mAdy[o][i] != 0) {
                resultado.add(etiquetas[i]);
            } // recorres las columnas a ver si una arista de v que vaya a la posición i
        }
        return resultado; // devolves un conjunto de nodos adyacentes
    }

    private int indice(int v) { // En que posición está el nodo con id = v?
        int i = cantNodos - 1;
        while (i >= 0 && etiquetas[i] != v) {
            i--;
        }
        return i;
    }

    private int indiceExistente(int v) { // solamente verifica que exista el nodo, porque si índice devolvió -1 (o sea que no lo encontró es porque no está el nodo)
        int i = indice(v);
        if (i == -1) {
            throw new IllegalArgumentException("El vertice " + v + " no existe en el grafo");
        }
        return i;
    }
}
