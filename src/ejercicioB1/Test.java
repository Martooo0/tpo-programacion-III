package ejercicioB1;

import modelo.RedVial;
import modelo.TipoUbicacion;
import modelo.Ubicacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        RedVial red = crearRed();
        Resolucion resolucion = new Resolucion();

        titulo("Caso 1: cuatro centros cargados desordenados (esperado: Norte, Este, Sur, Oeste con 16 minutos)");
        resolucion.resolver(red, 1, Arrays.asList(4, 2, 5, 3));

        titulo("Caso 2: tres centros, el de la prueba de escritorio (esperado: Norte, Este, Sur con 16 minutos)");
        resolucion.resolver(red, 1, Arrays.asList(2, 3, 4));

        titulo("Caso 3: un solo centro, los tramos pasan por ubicaciones no seleccionadas (esperado: 7 minutos)");
        resolucion.resolver(red, 1, Arrays.asList(2));

        titulo("Caso 4: sin centros para visitar (esperado: 0 minutos)");
        resolucion.resolver(red, 1, new ArrayList<>());

        titulo("Caso 5: un centro inalcanzable (esperado: no existe recorrido)");
        resolucion.resolver(red, 1, Arrays.asList(2, 7));

        titulo("Caso 6: ocho centros, el maximo permitido (esperado: Centro 2 al Centro 9 en orden con 9 minutos)");
        resolucion.resolver(crearRedAnillo(), 1, Arrays.asList(9, 4, 2, 7, 5, 3, 8, 6));

        titulo("Caso 7: mas de ocho centros");
        probarInvalido(red, 1, Arrays.asList(2, 3, 4, 5, 6, 7, 2, 3, 4));

        titulo("Caso 8: centro repetido");
        probarInvalido(red, 1, Arrays.asList(2, 3, 2));

        titulo("Caso 9: el punto de partida no es un deposito");
        probarInvalido(red, 2, Arrays.asList(3, 4));

        titulo("Caso 10: un centro sin regreso al deposito (esperado: no existe recorrido)");
        resolucion.resolver(red, 1, Arrays.asList(2, 8));
    }

    private static RedVial crearRed() {
        RedVial red = new RedVial();
        red.agregarUbicacion(new Ubicacion(1, "Deposito Central", TipoUbicacion.DEPOSITO));
        red.agregarUbicacion(new Ubicacion(2, "Centro Norte", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(3, "Centro Este", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(4, "Centro Sur", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(5, "Centro Oeste", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(6, "Centro Plaza", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(7, "Centro Aislado", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(8, "Centro Sin Salida", TipoUbicacion.CENTRO));

        red.agregarConexion(1, 2, 4);
        red.agregarConexion(2, 1, 6);
        red.agregarConexion(1, 3, 3);
        red.agregarConexion(3, 1, 3);
        red.agregarConexion(2, 3, 2);
        red.agregarConexion(3, 2, 5);
        red.agregarConexion(2, 4, 7);
        red.agregarConexion(4, 2, 7);
        red.agregarConexion(3, 4, 4);
        red.agregarConexion(4, 3, 6);
        red.agregarConexion(4, 5, 3);
        red.agregarConexion(5, 4, 2);
        red.agregarConexion(5, 1, 5);
        red.agregarConexion(1, 5, 9);
        red.agregarConexion(3, 5, 8);
        red.agregarConexion(1, 6, 1);
        red.agregarConexion(6, 2, 1);
        red.agregarConexion(7, 1, 2);
        red.agregarConexion(1, 8, 2);
        return red;
    }

    private static RedVial crearRedAnillo() { // Mismo procedimiento que en el flujo de Dijkstra
        RedVial red = new RedVial();
        red.agregarUbicacion(new Ubicacion(1, "Deposito Central", TipoUbicacion.DEPOSITO));
        for (int id = 2; id <= 9; id++) {
            red.agregarUbicacion(new Ubicacion(id, "Centro " + id, TipoUbicacion.CENTRO));
        }
        for (int origen = 1; origen <= 9; origen++) {
            int siguiente = origen % 9 + 1;
            for (int destino = 1; destino <= 9; destino++) {
                if (destino == siguiente) {
                    red.agregarConexion(origen, destino, 1);
                } else if (destino != origen) {
                    red.agregarConexion(origen, destino, 10);
                }
            }
        }
        return red;
    }

    private static void probarInvalido(RedVial red, int idDeposito, List<Integer> idsCentros) {
        try {
            new Resolucion().resolver(red, idDeposito, idsCentros);
            System.out.println("Error: se aceptaron datos invalidos");
        } catch (IllegalArgumentException e) {
            System.out.println("Datos rechazados: " + e.getMessage());
        }
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println(" " + texto + " ");
    }
}
