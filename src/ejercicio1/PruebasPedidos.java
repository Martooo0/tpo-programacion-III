package ejercicio1;

import ejercicio1.GestorPedidos;
import ejercicio1.Pedido;

import java.time.LocalDate;

public class PruebasPedidos {
    public static void main(String[] args) {
        // Cada llamada a pedido recibe: ID, dia de noviembre y prioridad.
        // Los pedidos 1 y 2 tienen igual fecha y prioridad para probar desempate por ID.
        Pedido[] entrada = {
                pedido(4, 12, 3),
                pedido(2, 10, 5),
                pedido(1, 10, 5),
                pedido(3, 9, 2),
                pedido(5, 11, 5)
        };
        GestorPedidos gestor = new GestorPedidos(entrada);

        // CASO 1: ordenar desde la fecha mas cercana a la mas lejana.
        // Primero va el dia 9 (ID 3). El dia 10 empatan los IDs 1 y 2:
        // elegimos el ID menor. Despues siguen los dias 11 y 12.
        verificarIds(gestor.consultarPorFecha(), 3, 1, 2, 5, 4);
        System.out.println("OK: ordenamiento por fecha");

        // CASO 2: ordenar por prioridad descendente y aplicar los desempates.
        // Los IDs 1, 2 y 5 tienen prioridad 5: se ordenan por fecha.
        // Los IDs 1 y 2 tambien empatan en fecha: primero va el ID 1.
        // Despues van el ID 4 (prioridad 3) y el ID 3 (prioridad 2).
        verificarIds(gestor.consultarPorPrioridad(), 1, 2, 5, 4, 3);
        System.out.println("OK: ordenamiento por prioridad y desempates");

        // CASO 3: buscar un ID que existe y verificar que sea el pedido correcto.
        // Primero comprobamos que no sea null antes de consultar su ID.
        Pedido encontrado = gestor.buscarPorId(2);
        comprobar(encontrado != null, "No se encontro el pedido 2");
        comprobar(encontrado.getId() == 2, "La busqueda devolvio otro pedido");
        System.out.println("OK: busqueda de un ID existente");

        // CASO 4: buscar un ID inexistente tiene que devolver null.
        comprobar(gestor.buscarPorId(99) == null, "El ID 99 no deberia existir");
        System.out.println("OK: busqueda de un ID inexistente");

        // CASO 5: sin pedidos, las consultas tienen que devolver arreglos vacios
        // y la busqueda tiene que devolver null, sin provocar errores.
        GestorPedidos vacio = new GestorPedidos(new Pedido[0]);
        verificarIds(vacio.consultarPorFecha());
        verificarIds(vacio.consultarPorPrioridad());
        comprobar(vacio.buscarPorId(1) == null, "Busqueda en una lista vacia");
        System.out.println("OK: lista vacia");

        // CASO 6: con un solo pedido, ambos ordenamientos deben conservarlo.
        // La busqueda debe encontrar ese ID y devolver null para otro ID.
        GestorPedidos unico = new GestorPedidos(new Pedido[] {pedido(7, 1, 0)});
        verificarIds(unico.consultarPorFecha(), 7);
        verificarIds(unico.consultarPorPrioridad(), 7);
        Pedido encontradoUnico = unico.buscarPorId(7);
        comprobar(encontradoUnico != null, "No se encontro el unico pedido");
        comprobar(encontradoUnico.getId() == 7, "El unico pedido debe tener ID 7");
        comprobar(unico.buscarPorId(8) == null, "El ID 8 no deberia existir");
        System.out.println("OK: lista con un solo pedido");

        // Solo llegamos a esta linea si ninguna comprobacion anterior fallo.
        System.out.println("Todas las pruebas basicas pasaron.");
    }

    // Creo un pedido de ejemplo sin repetir el constructor completo en cada caso.
    // Todos usan noviembre de 2026 y un peso de 10 kg.
    private static Pedido pedido(int id, int dia, int prioridad) {
        return new Pedido(id, "Centro " + id, LocalDate.of(2026, 11, dia), prioridad, 10);
    }

    // int... para recibir varios IDs y usarlos como un arreglo dentro del metodo.
    // Si no pasamos IDs, esperamos un arreglo de pedidos vacio.
    private static void verificarIds(Pedido[] pedidos, int... ids) {
        comprobar(pedidos.length == ids.length, "Cantidad de pedidos incorrecta");
        for (int i = 0; i < ids.length; i++) {
            comprobar(pedidos[i].getId() == ids[i], "ID incorrecto en la posicion " + i);
        }
    }

    // Si la condicion es verdadera, seguimos con la prueba.
    // Si es falsa, detenemos el programa y mostramos que comprobacion fallo.
    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError("Fallo: " + mensaje);
        }
    }
}
