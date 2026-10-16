package ejercicio1;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Random;

public class PruebasPedidos {
    public static void main(String[] args) {

        Pedido[] entrada = {
                pedido(4, 12, 3),
                pedido(2, 10, 5),
                pedido(1, 10, 5),
                pedido(3, 9, 2),
                pedido(5, 11, 5)
        };

        GestorPedidos gestor = new GestorPedidos(entrada);

        verificarIds(gestor.consultarPorFecha(), 3, 1, 2, 5, 4);

        verificarIds(gestor.consultarPorPrioridad(), 1, 2, 5, 4, 3);

        verificarIds(entrada, 4, 2, 1, 3, 5);

        for (Pedido pedido : entrada) {
            comprobar(gestor.buscarPorId(pedido.getId()) == pedido, "Busqueda de ID existente");
        }

        comprobar(gestor.buscarPorId(0) == null, "ID menor al minimo");
        comprobar(gestor.buscarPorId(99) == null, "ID mayor al maximo");
        GestorPedidos conHueco = new GestorPedidos(new Pedido[] {pedido(1, 1, 1), pedido(3, 1, 1)});
        comprobar(conHueco.buscarPorId(2) == null, "ID ausente entre dos IDs existentes");

        // Las modificaciones a arreglos externos no deben romper el indice por ID.
        entrada[0] = pedido(99, 1, 1);
        Pedido[] consulta = gestor.consultarPorFecha();
        consulta[0] = null;
        comprobar(gestor.buscarPorId(4) != null, "Copia del arreglo de entrada");
        comprobar(gestor.buscarPorId(3) != null, "Copia del resultado de consulta");

        GestorPedidos vacio = new GestorPedidos(new Pedido[0]);
        verificarIds(vacio.consultarPorFecha());
        verificarIds(vacio.consultarPorPrioridad());
        comprobar(vacio.buscarPorId(1) == null, "Busqueda en arreglo vacio");
        GestorPedidos unico = new GestorPedidos(new Pedido[] {pedido(7, 1, 0)});
        verificarIds(unico.consultarPorFecha(), 7);
        verificarIds(unico.consultarPorPrioridad(), 7);
        comprobar(unico.buscarPorId(7) != null, "Un solo pedido");
        comprobar(unico.buscarPorId(8) == null, "ID ausente en un solo pedido");

        // Casos de limites de Merge y de intervalos vacios en BusquedaBin.
        for (int[] ids : new int[][] {{1, 3}, {3, 1}, {1, 2, 3, 4}, {4, 3, 2, 1}}) {
            Pedido[] caso = new Pedido[ids.length];
            for (int i = 0; i < ids.length; i++) {
                caso[i] = pedido(ids[i], ids[i], ids[i]);
            }
            GestorPedidos limites = new GestorPedidos(caso);
            Pedido[] ordenados = limites.consultarPorFecha();
            for (int i = 1; i < ordenados.length; i++) {
                comprobar(ordenados[i - 1].getId() < ordenados[i].getId(), "Agotamiento de mitades");
            }
            for (int id = 0; id <= 5; id++) {
                Pedido esperado = null;
                for (Pedido p : caso) {
                    if (p.getId() == id) {
                        esperado = p;
                    }
                }
                comprobar(limites.buscarPorId(id) == esperado, "Limites de busqueda recursiva");
            }
        }
        Pedido[] iguales = {pedido(9, 1, 1), pedido(8, 1, 1), pedido(7, 1, 1)};
        Comparator<Pedido> todosIguales = new Comparator<Pedido>() {
            @Override
            public int compare(Pedido a, Pedido b) {
                return 0;
            }
        };
        verificarIds(OrdenamientoPedidos.ordenar(iguales, todosIguales), 9, 8, 7);

        boolean rechazoDuplicado = false;
        try {
            new GestorPedidos(new Pedido[] {pedido(1, 1, 1), pedido(1, 2, 2)});
        } catch (IllegalArgumentException e) {
            rechazoDuplicado = true;
        }
        comprobar(rechazoDuplicado, "Rechazo de IDs duplicados");

        // Verifica orden y conservacion de todos los pedidos en un conjunto mayor.
        Random azar = new Random(42);
        Pedido[] muchos = new Pedido[1000];
        for (int i = 0; i < muchos.length; i++) {
            muchos[i] = pedido(muchos.length - i, 1 + azar.nextInt(28), azar.nextInt(10));
        }
        GestorPedidos grande = new GestorPedidos(muchos);
        for (Pedido[] resultado : new Pedido[][] {grande.consultarPorFecha(), grande.consultarPorPrioridad()}) {
            boolean[] vistos = new boolean[muchos.length + 1];
            comprobar(resultado.length == muchos.length, "Cantidad conservada");
            for (Pedido p : resultado) {
                comprobar(!vistos[p.getId()], "Sin repeticiones");
                vistos[p.getId()] = true;
                comprobar(grande.buscarPorId(p.getId()) == p, "Pedido conservado y localizable");
            }
        }

        Pedido[] fechas = grande.consultarPorFecha();
        Pedido[] prioridades = grande.consultarPorPrioridad();
        for (int i = 1; i < muchos.length; i++) {
            Pedido a = fechas[i - 1], b = fechas[i];
            comprobar(a.getFechaLimite().isBefore(b.getFechaLimite())
                            || (a.getFechaLimite().equals(b.getFechaLimite()) && a.getId() < b.getId()),
                    "Orden por fecha y desempate");
            a = prioridades[i - 1];
            b = prioridades[i];
            comprobar(a.getPrioridad() > b.getPrioridad() || (a.getPrioridad() == b.getPrioridad()
                            && (a.getFechaLimite().isBefore(b.getFechaLimite())
                            || (a.getFechaLimite().equals(b.getFechaLimite()) && a.getId() < b.getId()))),
                    "Orden por prioridad y desempates");
        }
        System.out.println("Todas las pruebas pasaron (1000 pedidos, limites recursivos y estabilidad).");
    }

    private static Pedido pedido(int id, int dia, int prioridad) {
        return new Pedido(id, "Centro " + id, LocalDate.of(2026, 11, dia), prioridad, 10);
    }

    private static void verificarIds(Pedido[] pedidos, int... ids) {
        comprobar(pedidos.length == ids.length, "Cantidad esperada");
        for (int i = 0; i < ids.length; i++) {
            comprobar(pedidos[i].getId() == ids[i], "ID esperado en posicion " + i);
        }
    }

    private static void comprobar(boolean condicion, String caso) {
        if (!condicion) {
            throw new AssertionError("Fallo: " + caso);
        }
    }
}
