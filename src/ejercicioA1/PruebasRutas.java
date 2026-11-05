package ejercicioA1;

import modelo.RedVial;
import modelo.TipoUbicacion;
import modelo.Ubicacion;
import java.util.List;

public class PruebasRutas {
    public static void main(String[] args) {
        RedVial red = crearRed();
        // Floyd se ejecuta al construir el gestor. Las consultas siguientes usan sus tablas.
        GestorRutas gestor = new GestorRutas(red);

        // CASO 1: Ruta mínima y recorrido correcto.
        ResultadoConsultaRuta ruta = gestor.consultar(1, 5);
        comprobar(ruta.existeRuta(), "Debe existir una ruta de 1 a 5.");
        comprobar(ruta.getTiempoTotal() == 11, "El minimo debe ser 11.");
        verificarRecorrido(ruta, 1, 3, 2, 4, 5);
        System.out.println("OK 1: " + ruta);

        // CASO 2: otro origen y destino con el mismo gestor, sin recalcular.
        ResultadoConsultaRuta otra = gestor.consultar(3, 4);
        comprobar(otra.getTiempoTotal() == 7, "De 3 a 4 debe tardar 7.");
        verificarRecorrido(otra, 3, 2, 4);
        comprobar(gestor.consultarTiempo(3, 4) == 7, "La consulta de tiempo debe coincidir.");
        System.out.println("OK 2: " + otra);

        // CASO 3: las conexiones son dirigidas; el camino inverso puede no existir.
        ResultadoConsultaRuta inversa = gestor.consultar(5, 1);
        comprobar(!inversa.existeRuta(), "No debe existir ruta de 5 a 1.");
        comprobar(inversa.getTiempoTotal() == -1, "Sin ruta se devuelve -1.");
        verificarRecorrido(inversa);
        System.out.println("OK 3: " + inversa);

        // CASO 4: Si el "origen" es igual al "destino" no hace falta viajar.
        ResultadoConsultaRuta mismo = gestor.consultar(1, 1);
        comprobar(mismo.existeRuta() && mismo.getTiempoTotal() == 0, "Debe costar 0.");
        verificarRecorrido(mismo, 1);
        System.out.println("OK 4: origen igual a destino.");

        // CASO 5: al cambiar la red actualizamos explicitamente las tablas.
        // Sin la conexion de 2 a 4, el mejor camino pasa a ser 1, 3, 4, 5.
        red.deshabilitarConexion(2, 4);
        gestor.recalcular();
        ResultadoConsultaRuta alternativa = gestor.consultar(1, 5);
        comprobar(alternativa.getTiempoTotal() == 12, "La nueva ruta debe tardar 12.");
        comprobar(alternativa.existeRuta(), "Debe existir una ruta alternativa.");
        verificarRecorrido(alternativa, 1, 3, 4, 5);
        System.out.println("OK 5: " + alternativa);

        // Solo llegamos aca si todos los chequeos anteriores pasan.
        System.out.println("Todas las pruebas basicas de A1 pasaron.");
    }

    private static RedVial crearRed() {
        RedVial red = new RedVial();
        red.agregarUbicacion(new Ubicacion(1, "Deposito Central", TipoUbicacion.DEPOSITO));
        red.agregarUbicacion(new Ubicacion(2, "Centro Norte", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(3, "Centro Este", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(4, "Centro Oeste", TipoUbicacion.CENTRO));
        red.agregarUbicacion(new Ubicacion(5, "Centro Sur", TipoUbicacion.CENTRO));
        red.agregarConexion(1, 2, 4);
        red.agregarConexion(1, 3, 1);
        red.agregarConexion(3, 2, 2);
        red.agregarConexion(2, 4, 5);
        red.agregarConexion(3, 4, 8);
        red.agregarConexion(4, 5, 3);
        red.agregarConexion(3, 5, 12);
        return red;
    }

    // El mensaje solo aparece si la condicion es falsa; en ese caso la prueba falla.
    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    // int... permite indicar los IDs esperados; sin IDs, esperamos un recorrido vacio.
    private static void verificarRecorrido(ResultadoConsultaRuta resultado, int... ids) {
        List<Ubicacion> recorrido = resultado.getRecorrido();
        comprobar(recorrido.size() == ids.length, "Cantidad de ubicaciones incorrecta.");
        for (int i = 0; i < ids.length; i++) {
            comprobar(recorrido.get(i).getId() == ids[i], "Ubicacion incorrecta en el recorrido.");
        }
    }

}
