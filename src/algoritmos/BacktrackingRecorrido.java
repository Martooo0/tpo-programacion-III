package algoritmos;

public class BacktrackingRecorrido {
    public static final int SIN_CAMINO = -1;

    private int[][] tiempos;
    private int[] recorridoActual;
    private boolean[] visitados;
    private int[] mejorRecorrido;
    private int mejorTiempo;
    private int recorridosCompletos;
    private int ramasPodadas;

    public void ejecutar(int[][] tiempos) {
        this.tiempos = tiempos;
        int cantidadParadas = tiempos.length;
        recorridoActual = new int[cantidadParadas];
        visitados = new boolean[cantidadParadas];
        mejorRecorrido = null;
        mejorTiempo = Integer.MAX_VALUE; // infinito
        recorridosCompletos = 0;
        ramasPodadas = 0;

        recorridoActual[0] = 0;
        visitados[0] = true;

        if (cantidadParadas == 1) {
            mejorRecorrido = new int[] {0};
            mejorTiempo = 0;
            return;
        }
        backtracking(1, 0);
    }

    private void backtracking(int etapa, int tiempoActual) {
        int paradaAnterior = recorridoActual[etapa - 1];
        for (int centro = 1; centro < tiempos.length; centro++) {
            if (!visitados[centro] && tiempos[paradaAnterior][centro] != SIN_CAMINO) {
                recorridoActual[etapa] = centro;
                visitados[centro] = true;
                int tiempoNuevo = tiempoActual + tiempos[paradaAnterior][centro];

                if (tiempoNuevo < mejorTiempo) {
                    if (etapa == tiempos.length - 1) {
                        evaluarSolucion(tiempoNuevo);
                    } else {
                        backtracking(etapa + 1, tiempoNuevo);
                    }
                } else {
                    ramasPodadas++;
                }

                visitados[centro] = false;
            }
        }
    }

    private void evaluarSolucion(int tiempoHastaUltimoCentro) {
        recorridosCompletos++;
        int ultimoCentro = recorridoActual[tiempos.length - 1];
        if (tiempos[ultimoCentro][0] == SIN_CAMINO) {
            return;
        }
        int tiempoTotal = tiempoHastaUltimoCentro + tiempos[ultimoCentro][0];
        if (tiempoTotal < mejorTiempo) {
            mejorTiempo = tiempoTotal;
            mejorRecorrido = new int[recorridoActual.length];
            for (int i = 0; i < recorridoActual.length; i++) {
                mejorRecorrido[i] = recorridoActual[i];
            }
        }
    }

    public boolean existeRecorrido() {
        return mejorRecorrido != null;
    }

    public int[] getMejorRecorrido() {
        return mejorRecorrido;
    }

    public int getMejorTiempo() {
        return mejorTiempo;
    }

    public int getRecorridosCompletos() {
        return recorridosCompletos;
    }

    public int getRamasPodadas() {
        return ramasPodadas;
    }
}
