package ejercicio1;

// Administración de un conjunto de pedidos, consultas y busqueda por ID
public final class GestorPedidos {
    private final Pedido[] pedidosPorId;

    public GestorPedidos(Pedido[] pedidos) {
        // arreglo ordenado por ID ascendente para usar la busqueda binaria
        pedidosPorId = OrdenamientoPedidos.ordenar(pedidos, OrdenamientoPedidos.POR_ID);

        for (int i = 1; i < pedidosPorId.length; i++) {
            if (pedidosPorId[i - 1].getId() == pedidosPorId[i].getId()) {
                throw new IllegalArgumentException("ID repetido: " + pedidosPorId[i].getId());
            }
        }
    }

    // devuelve una copia ordenada por fecha.
    public Pedido[] consultarPorFecha() {
        return OrdenamientoPedidos.ordenar(pedidosPorId, OrdenamientoPedidos.POR_FECHA);
    }

    // devuelve una copia ordenada por prioridad - fecha y ID
    public Pedido[] consultarPorPrioridad() {
        return OrdenamientoPedidos.ordenar(pedidosPorId, OrdenamientoPedidos.POR_PRIORIDAD);
    }

    // Busqueda binaria propia sobre el arreglo ordenado por ID.
    // Devuelve null cuando no existe el identificador.
    public Pedido buscarPorId(int id) {
        return busquedaBin(pedidosPorId, id, 0, pedidosPorId.length - 1);
    }

    // Busqueda binaria recursiva
    private static Pedido busquedaBin(Pedido[] S, int x, int inicio, int fin) {
        if (inicio > fin) {
            return null;
        }
        if (inicio == fin) {
            if (S[inicio].getId() == x) {
                return S[inicio];
            } else {
                return null;
            }
        }
        int mitad = inicio + (fin - inicio) / 2;
        int y = S[mitad].getId();
        if (x == y) {
            return S[mitad];
        }
        if (x < y) {
            return busquedaBin(S, x, inicio, mitad - 1);
        }
        return busquedaBin(S, x, mitad + 1, fin);
    }
}
