package ejercicioB2;

import ejercicio3.Lote;
import modelo.Vehiculo;

public class Test {

    public static void main(String[] args) {
        Resolucion resolucion = new Resolucion();

        titulo("Caso 1: solucion directa (esperado: L1 y L2 en V1, L3 en V2, sin vueltas atras)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 4, 0), new Lote(2, 6, 0), new Lote(3, 5, 0)},
                new Vehiculo[] {new Vehiculo(1, 10), new Vehiculo(2, 8)},
                new int[][] {{1, 2}, {1}, {2}});

        titulo("Caso 2: greedy falla (esperado: L1 en V2, L2 y L3 en V1)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 6, 0), new Lote(2, 5, 0), new Lote(3, 5, 0)},
                new Vehiculo[] {new Vehiculo(1, 10), new Vehiculo(2, 6)},
                new int[][] {{1, 2}, {1, 2}, {1, 2}});

        titulo("Caso 3: con compatibilidades (esperado: L1 en V2, L2 y L4 en V1, L3 en V3; 10 probadas, 1 podada, 6 deshechas)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 5, 0), new Lote(2, 4, 0), new Lote(3, 4, 0), new Lote(4, 4, 0)},
                new Vehiculo[] {new Vehiculo(1, 8), new Vehiculo(2, 6), new Vehiculo(3, 4)},
                new int[][] {{1, 2}, {1, 3}, {2, 3}, {1, 2}});

        titulo("Caso 4: un lote sin vehiculos habilitados (esperado: no existe, chequeo previo)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 3, 0), new Lote(2, 2, 0)},
                new Vehiculo[] {new Vehiculo(1, 10)},
                new int[][] {{1}, {}});

        titulo("Caso 5: un lote pesa mas que todos sus vehiculos habilitados (esperado: no existe, chequeo previo)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 3, 0), new Lote(2, 8, 0)},
                new Vehiculo[] {new Vehiculo(1, 5), new Vehiculo(2, 20)},
                new int[][] {{1, 2}, {1}});

        titulo("Caso 6: el peso total supera la capacidad total (esperado: no existe, chequeo previo)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 4, 0), new Lote(2, 4, 0), new Lote(3, 4, 0)},
                new Vehiculo[] {new Vehiculo(1, 5), new Vehiculo(2, 5)},
                new int[][] {{1, 2}, {1, 2}, {1, 2}});

        titulo("Caso 7: capacidad total suficiente pero sin solucion (esperado: no existe, despues del backtracking)");
        resolucion.resolver(
                new Lote[] {new Lote(1, 4, 0), new Lote(2, 3, 0), new Lote(3, 3, 0)},
                new Vehiculo[] {new Vehiculo(1, 5), new Vehiculo(2, 5)},
                new int[][] {{1, 2}, {1, 2}, {1, 2}});

        titulo("Caso 8: lote con peso no positivo (esperado: rechazado)");
        try {
            new Lote(1, 0, 0);
            System.out.println("Error: se acepto un lote con peso 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Datos rechazados: " + e.getMessage());
        }

        titulo("Caso 9: vehiculo con capacidad no positiva (esperado: rechazado)");
        try {
            new Vehiculo(1, 0);
            System.out.println("Error: se acepto un vehiculo con capacidad 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Datos rechazados: " + e.getMessage());
        }

        titulo("Caso 10: compatibilidad con un vehiculo inexistente (esperado: rechazado)");
        try {
            resolucion.resolver(
                    new Lote[] {new Lote(1, 3, 0)},
                    new Vehiculo[] {new Vehiculo(1, 10)},
                    new int[][] {{1, 5}});
            System.out.println("Error: se acepto un vehiculo inexistente");
        } catch (IllegalArgumentException e) {
            System.out.println("Datos rechazados: " + e.getMessage());
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println(" " + texto + " ");
    }
}
