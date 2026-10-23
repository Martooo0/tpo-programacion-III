package ejercicio3;

// Casos pequeños con resultados conocidos para verificar el algoritmo y la reconstrucción.
public class PruebasCargas {
    public static void main(String[] args) {
        // CASO 1: Los lotes 1 y 4 suman 8 kg y prioridad 18.
        Lote[] ejemplo = {
                new Lote(1, 1, 10),
                new Lote(2, 4, 3),
                new Lote(3, 5, 5),
                new Lote(4, 7, 8)
        };
        verificar("Ejemplo ", SeleccionCargas.seleccionar(ejemplo, 8), new int[] {1, 4}, 8, 18, 0);

        // CASO 2: elegir primero la mayor prioridad individual no alcanza.
        // Es mejor tomar los lotes 2 y 3 (16 puntos) que el lote 1 (9 puntos).
        Lote[] combinacion = {new Lote(1, 6, 9), new Lote(2, 5, 8), new Lote(3, 5, 8)};
        verificar("Mejor combinacion", SeleccionCargas.seleccionar(combinacion, 10),
                new int[] {2, 3}, 10, 16, 0);

        // CASO 3: aunque sobre espacio, un único lote no puede cargarse dos veces.
        Lote[] unico = {new Lote(7, 3, 5)};
        verificar("Un lote sin repetir", SeleccionCargas.seleccionar(unico, 8),
                new int[] {7}, 3, 5, 5);

        // CASO 4: si ningún lote entra, la selección queda vacía.
        Lote[] pesados = {new Lote(1, 6, 9), new Lote(2, 7, 12)};
        verificar("Ningun lote entra", SeleccionCargas.seleccionar(pesados, 5),
                new int[0], 0, 0, 5);

        // CASO 5: un vehículo con capacidad cero no puede transportar lotes.
        verificar("Capacidad cero", SeleccionCargas.seleccionar(unico, 0),
                new int[0], 0, 0, 0);

        // CASO 6: sin lotes disponibles, queda toda la capacidad libre.
        verificar("Sin lotes", SeleccionCargas.seleccionar(new Lote[0], 10),
                new int[0], 0, 0, 10);

        // CASO 7: Lotes con prioridad cero no tienen que cargarse.
        Lote[] sinPrioridad = {new Lote(1, 2, 0), new Lote(2, 1, 0)};
        verificar("Prioridad cero", SeleccionCargas.seleccionar(sinPrioridad, 5),
                new int[0], 0, 0, 5);

        // CASO 8: ante empate, conservamos la solución con los lotes anteriores.
        Lote[] empate = {new Lote(1, 3, 5), new Lote(2, 3, 5)};
        verificar("Empate de soluciones", SeleccionCargas.seleccionar(empate, 3),
                new int[] {1}, 3, 5, 0);

        System.out.println("Todas las pruebas de cargas pasaron.");
    }

    // Compara la selección y los tres totales con los valores esperados de cada caso.
    private static void verificar(String caso, ResultadoCarga resultado, int[] idsEsperados, int pesoEsperado, long prioridadEsperada, int restanteEsperado) {
        Lote[] seleccionados = resultado.getLotesSeleccionados();

        comprobar(seleccionados.length == idsEsperados.length, caso + ": cantidad de lotes");

        for (int i = 0; i < idsEsperados.length; i++) {
            comprobar(seleccionados[i].getId() == idsEsperados[i], caso + ": lote en posicion " + i);
        }

        comprobar(resultado.getPesoTotal() == pesoEsperado, caso + ": peso total");
        comprobar(resultado.getPrioridadTotal() == prioridadEsperada, caso + ": prioridad total");
        comprobar(resultado.getCapacidadRestante() == restanteEsperado, caso + ": capacidad restante");
        System.out.println("OK: " + caso);
    }

    // Una condición falsa detiene las pruebas y muestra qué resultado no coincidió.
    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError("Fallo: " + mensaje);
        }
    }
}
