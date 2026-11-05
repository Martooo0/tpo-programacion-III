package ejercicioB1;

import algoritmos.BacktrackingRecorrido;
import algoritmos.Floyd;
import modelo.RedVial;
import modelo.TipoUbicacion;
import modelo.Ubicacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Resolucion {
    private static final int MAXIMO_CENTROS = 8;

    private Floyd floyd;

    public void resolver(RedVial red, int idDeposito, List<Integer> idsCentros) {
        validar(red, idDeposito, idsCentros);

        List<Ubicacion> paradas = new ArrayList<>();
        paradas.add(red.getUbicacion(idDeposito));
        for (int idCentro : idsCentros) {
            paradas.add(red.getUbicacion(idCentro));
        }

        int[][] tiempos = matrizTiempos(red, paradas);
        BacktrackingRecorrido backtracking = new BacktrackingRecorrido();
        backtracking.ejecutar(tiempos);
        mostrar(red, paradas, tiempos, backtracking);
    }

    private void validar(RedVial red, int idDeposito, List<Integer> idsCentros) {
        if (idsCentros.size() > MAXIMO_CENTROS) {
            throw new IllegalArgumentException("Se pueden visitar como maximo " + MAXIMO_CENTROS + " centros");
        }
        Ubicacion deposito = red.getUbicacion(idDeposito);
        if (deposito == null || deposito.getTipo() != TipoUbicacion.DEPOSITO) {
            throw new IllegalArgumentException("La ubicacion " + idDeposito + " no es un deposito de la red");
        }
        Set<Integer> centrosRevisados = new HashSet<>();
        for (int idCentro : idsCentros) {
            Ubicacion centro = red.getUbicacion(idCentro);
            if (centro == null || centro.getTipo() != TipoUbicacion.CENTRO) {
                throw new IllegalArgumentException("La ubicacion " + idCentro + " no es un centro de la red");
            }
            if (centrosRevisados.contains(idCentro)) {
                throw new IllegalArgumentException("El centro " + centro + " esta repetido");
            }
            centrosRevisados.add(idCentro);
        }
    }

    private int[][] matrizTiempos(RedVial red, List<Ubicacion> paradas) {
        floyd = new Floyd(red.construirGrafo());
        int cantidadParadas = paradas.size();
        int[][] tiempos = new int[cantidadParadas][cantidadParadas];

        for (int i = 0; i < cantidadParadas; i++) {
            int origen = paradas.get(i).getId();
            for (int j = 0; j < cantidadParadas; j++) {
                int destino = paradas.get(j).getId();
                tiempos[i][j] = (int) floyd.tiempoMinimo(origen, destino);
            }
        }
        return tiempos;
    }

    private List<Ubicacion> reconstruirTramo(RedVial red, int origen, int destino) {
        List<Ubicacion> tramo = new ArrayList<>();
        for (int id : floyd.reconstruirRecorrido(origen, destino)) {
            tramo.add(red.getUbicacion(id));
        }
        return tramo;
    }

    private void mostrar(RedVial red, List<Ubicacion> paradas, int[][] tiempos, BacktrackingRecorrido backtracking) {
        System.out.println("Deposito: " + paradas.get(0));
        System.out.println("Centros a visitar: " + paradas.subList(1, paradas.size()));

        if (!backtracking.existeRecorrido()) {
            System.out.println("No existe un recorrido que visite todos los centros y regrese al deposito");
        } else {
            int[] mejorRecorrido = backtracking.getMejorRecorrido();
            System.out.println("Orden de visita:");
            for (int i = 1; i < mejorRecorrido.length; i++) {
                mostrarTramo(red, paradas, tiempos, mejorRecorrido[i - 1], mejorRecorrido[i]);
            }
            if (mejorRecorrido.length > 1) {
                mostrarTramo(red, paradas, tiempos, mejorRecorrido[mejorRecorrido.length - 1], 0);
            }
            System.out.println("Tiempo total: " + backtracking.getMejorTiempo() + " minutos");
        }

        System.out.println("Nodos explorados: " + backtracking.getNodosExplorados());
        System.out.println("Ramas podadas: " + backtracking.getRamasPodadas());
    }

    private void mostrarTramo(RedVial red, List<Ubicacion> paradas, int[][] tiempos, int desde, int hasta) {
        int origen = paradas.get(desde).getId();
        int destino = paradas.get(hasta).getId();
        List<Ubicacion> tramo = reconstruirTramo(red, origen, destino);

        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < tramo.size(); i++) {
            if (i > 0) {
                texto.append(" -> ");
            }
            texto.append(tramo.get(i));
        }
        System.out.println("  " + texto + " (" + tiempos[desde][hasta] + " minutos)");
    }
}
