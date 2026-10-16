package ejercicio1;

public final class GestorPedidos {
    private final Pedido[] pedidosPorId;

    public GestorPedidos(Pedido[] pedidos) {
        pedidosPorId = OrdenamientoPedidos.ordenar(pedidos, OrdenamientoPedidos.POR_ID);
        for (int i = 1; i < pedidosPorId.length; i++) {
            if (pedidosPorId[i - 1].getId() == pedidosPorId[i].getId()) {
                throw new IllegalArgumentException("ID repetido: " + pedidosPorId[i].getId());
            }
        }
    }

    public Pedido[] consultarPorFecha() {
        return OrdenamientoPedidos.ordenar(pedidosPorId, OrdenamientoPedidos.POR_FECHA);
    }

    public Pedido[] consultarPorPrioridad() {
        return OrdenamientoPedidos.ordenar(pedidosPorId, OrdenamientoPedidos.POR_PRIORIDAD);
    }

    // Busqueda binaria propia sobre el arreglo ordenado por ID.
    // Devuelve null cuando no existe el identificador.
    public Pedido buscarPorId(int id) {
        return busquedaBin(pedidosPorId, id, 0, pedidosPorId.length - 1);
    }

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
        // Se excluye la mitad porque ya sabemos que su ID no es x.
        return busquedaBin(S, x, mitad + 1, fin);
    }
}
