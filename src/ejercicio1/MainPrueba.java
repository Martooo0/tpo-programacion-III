package ejercicio1;

import java.time.LocalDate;

public class MainPrueba {
    public static void main(String[] args) {
        Pedido[] pedidos = {
            new Pedido(4, "Centro A", LocalDate.of(2026, 11, 12), 3, 80),
            new Pedido(2, "Centro B", LocalDate.of(2026, 11, 10), 5, 40),
            new Pedido(1, "Centro C", LocalDate.of(2026, 11, 10), 5, 60),
            new Pedido(3, "Centro D", LocalDate.of(2026, 11, 9), 2, 25),
            new Pedido(5, "Centro E", LocalDate.of(2026, 11, 11), 5, 30)
        };
        GestorPedidos gestor = new GestorPedidos(pedidos);

        mostrar("Por fecha limite (IDs esperados: 3, 1, 2, 5, 4)", gestor.consultarPorFecha());
        mostrar("Por prioridad (IDs esperados: 1, 2, 5, 4, 3)", gestor.consultarPorPrioridad());

        for (int id : new int[] {2, 99}) {
            Pedido encontrado = gestor.buscarPorId(id);
            if (encontrado == null) {
                System.out.println("Busqueda del ID " + id + ": Pedido no encontrado");
            } else {
                System.out.println("Busqueda del ID " + id + ": " + encontrado);
            }
        }
    }

    private static void mostrar(String titulo, Pedido[] pedidos) {
        System.out.println("\n" + titulo);
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}
