package ejercicio2;

import modelo.RedVial;
import modelo.TipoUbicacion;
import modelo.Ubicacion;

public class Test {

    public static void main(String[] args) {
        RedVial red = crearRed();
        Resolucion resolucion = new Resolucion();

        titulo("Caso 1: ruta mas rapida desde Deposito Central hasta Centro Sur");
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 1, 5));

        titulo("Caso 2: se deshabilita la conexion Centro Este -> Centro Norte y se recalcula");
        red.deshabilitarConexion(3, 2);
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 1, 5));

        titulo("Caso 3: se vuelve a habilitar la conexion Centro Este -> Centro Norte");
        red.habilitarConexion(3, 2);
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 1, 5));

        titulo("Caso 4: sentido contrario, desde Centro Sur hasta Deposito Central");
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 5, 1));

        titulo("Caso 5: destino aislado, desde Deposito Central hasta Deposito Rural");
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 1, 6));

        titulo("Caso 6: origen igual al destino");
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 1, 1));

        titulo("Caso 7: se deshabilitan todas las salidas del Deposito Central");
        red.deshabilitarConexion(1, 2);
        red.deshabilitarConexion(1, 3);
        resolucion.mostrar(resolucion.calcularRutaMasRapida(red, 1, 5));
        red.habilitarConexion(1, 2);
        red.habilitarConexion(1, 3);

        titulo("Caso 8: conexion con tiempo no positivo");
        try {
            red.agregarConexion(2, 5, 0);
            System.out.println("Error: se acepto una conexion con tiempo 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Conexion rechazada: " + e.getMessage());
        }
    }

    private static RedVial crearRed() {
        RedVial red = new RedVial();
        red.agregarUbicacion(new Ubicacion(1, "Deposito Central", TipoUbicacion.DEPOSITO));
        red.agregarUbicacion(new Ubicacion(2, "Centro Norte", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(3, "Centro Este", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(4, "Centro Oeste", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(5, "Centro Sur", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(6, "Deposito Rural", TipoUbicacion.DEPOSITO));

        red.agregarConexion(1, 2, 4);
        red.agregarConexion(1, 3, 1);
        red.agregarConexion(3, 2, 2);
        red.agregarConexion(2, 4, 5);
        red.agregarConexion(3, 4, 8);
        red.agregarConexion(4, 5, 3);
        red.agregarConexion(3, 5, 12);
        return red;
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println(" " + texto + " ");
    }
}
