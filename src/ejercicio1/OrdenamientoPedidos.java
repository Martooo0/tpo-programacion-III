package ejercicio1;

import java.util.Comparator;

public final class OrdenamientoPedidos {
    private OrdenamientoPedidos() { }

    public static final Comparator<Pedido> POR_ID = new Comparator<Pedido>() {
        @Override
        public int compare(Pedido a, Pedido b) {
            return Integer.compare(a.getId(), b.getId());
        }
    };

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
