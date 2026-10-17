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


        // Primera búsqueda: el ID 2 existe.
        Pedido encontrado2 = gestor.buscarPorId(2);

        // Si devuelve null, no se encontró. Si devuelve un pedido, lo mostramos.
        if (encontrado2 == null) {
            System.out.println("Busqueda del ID 2: Pedido no encontrado");
        } else {
            System.out.println("Busqueda del ID 2: " + encontrado2);
        }

        // Segunda búsqueda: el ID 99 no existe, por lo que esperamos recibir null.
        Pedido encontrado99 = gestor.buscarPorId(99);
        if (encontrado99 == null) {
            System.out.println("Busqueda del ID 99: Pedido no encontrado");
        } else {
            System.out.println("Busqueda del ID 99: " + encontrado99);
        }
    }

    private static void mostrar(String titulo, Pedido[] pedidos) {
        System.out.println("\n" + titulo);
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}
