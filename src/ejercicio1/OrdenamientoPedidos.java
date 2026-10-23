package ejercicio1;

import java.util.Comparator;

// Contiene criterios de comparación y el algoritmo Mergesort. Recibe arreglos para ordenarlos

public final class OrdenamientoPedidos {

    private OrdenamientoPedidos() {
    }

    // Cada Comparator decide cuál de los pedidos debe ir primero.
    // POR_ID compara en orden ascendente: por ejemplo, el ID 2 va antes que el 7.
    public static final Comparator<Pedido> POR_ID = new Comparator<Pedido>() {
        @Override
        // compare devuelve negativo si a va primero, cero si empatan y positivo si b va primero.
        public int compare(Pedido a, Pedido b) {
            return Integer.compare(a.getId(), b.getId());
        }
    };

    // Comparamos pedido según su fecha, la más cercana va primero,
    // si son diferentes devolvemos su resultado, si empatan decide el ID menor.
    public static final Comparator<Pedido> POR_FECHA = new Comparator<Pedido>() {
        @Override
        public int compare(Pedido a, Pedido b) {
            int resultado = a.getFechaLimite().compareTo(b.getFechaLimite());
            if (resultado != 0) {
                return resultado;
            } else {
                return POR_ID.compare(a, b);
            }
        }
    };

    // Primero va la prioridad mayor: compare recibe b antes que a
    // Si empatan en prioridad, POR_FECHA compara la fecha y si también empatan, el ID decide.
    public static final Comparator<Pedido> POR_PRIORIDAD = new Comparator<Pedido>() {
        @Override
        public int compare(Pedido a, Pedido b) {
            int resultado = Integer.compare(b.getPrioridad(), a.getPrioridad());
            if (resultado != 0) {
                return resultado;
            } else {
                return POR_FECHA.compare(a, b);
            }
        }
    };

    // Recibe un arreglo de pedidos con el criterio para comprararlos y devuelve un nuevo arreglo ordenado
    // conservando el orden del arreglo recibido.
    public static Pedido[] ordenar(Pedido[] pedidos, Comparator<Pedido> criterio) {
        if (pedidos == null || criterio == null) {
            throw new IllegalArgumentException("El arreglo y el criterio son obligatorios.");
        }
        Pedido[] copia = pedidos.clone();
        for (Pedido pedido : copia) {
            if (pedido == null) {
                throw new IllegalArgumentException("No se admiten pedidos nulos.");
            }
        }
        mergeSort(copia, 0, copia.length - 1, criterio);
        return copia;
    }

    private static void mergeSort(Pedido[] S, int inicio, int fin, Comparator<Pedido> criterio) {
        if (inicio < fin) {
            int medio = inicio + (fin - inicio) / 2;
            mergeSort(S, inicio, medio, criterio);
            mergeSort(S, medio + 1, fin, criterio);
            merge(S, inicio, fin, criterio);
        }
    }

    // S es el arreglo original y R contiene la mezcla del tramo actual.
    // combina las dos mitades ordenadas
    private static void merge(Pedido[] S, int inicio, int fin, Comparator<Pedido> criterio) {
        Pedido[] R = new Pedido[fin - inicio + 1];
        int medio = inicio + (fin - inicio) / 2;
        int i = inicio;
        int j = medio + 1;

        for (int k = 0; k <= fin - inicio; k++) {
            if (i <= medio && (j > fin || criterio.compare(S[i], S[j]) <= 0)) {
                R[k] = S[i];
                i++;
            } else {
                R[k] = S[j];
                j++;
            }
        }

        for (int k = 0; k <= fin - inicio; k++) {
            S[inicio + k] = R[k];
        }
    }
}
