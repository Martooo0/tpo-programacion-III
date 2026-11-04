package algoritmos;

public class BacktrackingDistribucion {
    public static final int SIN_ASIGNAR = -1;

    private int[] pesos;
    private boolean[][] habilitado;
    private int[] capacidadRestante;
    private int[] asignacion;
    private boolean existeDistribucion;
    private int asignacionesProbadas;
    private int ramasPodadas;
    private int asignacionesDeshechas;

    public void ejecutar(int[] pesos, int[] capacidades, boolean[][] habilitado) {
        this.pesos = pesos;
        this.habilitado = habilitado;
        capacidadRestante = new int[capacidades.length];
        for (int vehiculo = 0; vehiculo < capacidades.length; vehiculo++) {
            capacidadRestante[vehiculo] = capacidades[vehiculo];
        }
        asignacion = new int[pesos.length];
        for (int lote = 0; lote < pesos.length; lote++) {
            asignacion[lote] = SIN_ASIGNAR;
        }
        asignacionesProbadas = 0;
        ramasPodadas = 0;
        asignacionesDeshechas = 0;

        if (pesos.length == 0) {
            existeDistribucion = true;
            return;
        }
        existeDistribucion = backtracking(0);
    }

    private boolean backtracking(int lote) {
        boolean solucion = false;
        int vehiculo = 0;
        while (vehiculo < capacidadRestante.length && !solucion) {
            if (habilitado[lote][vehiculo]) {
                asignacionesProbadas++;
                asignacion[lote] = vehiculo;
                capacidadRestante[vehiculo] = capacidadRestante[vehiculo] - pesos[lote];

                if (capacidadRestante[vehiculo] >= 0) {
                    if (lote == pesos.length - 1) {
                        solucion = true;
                    } else if (hayLotePendienteSinLugar(lote + 1)) {
                        ramasPodadas++;
                    } else {
                        solucion = backtracking(lote + 1);
                    }
                }

                if (!solucion) {
                    capacidadRestante[vehiculo] = capacidadRestante[vehiculo] + pesos[lote];
                    asignacion[lote] = SIN_ASIGNAR;
                    asignacionesDeshechas++;
                }
            }
            vehiculo++;
        }
        return solucion;
    }

    private boolean hayLotePendienteSinLugar(int primerPendiente) {
        for (int lote = primerPendiente; lote < pesos.length; lote++) {
            boolean tieneLugar = false;
            for (int vehiculo = 0; vehiculo < capacidadRestante.length; vehiculo++) {
                if (habilitado[lote][vehiculo] && capacidadRestante[vehiculo] >= pesos[lote]) {
                    tieneLugar = true;
                }
            }
            if (!tieneLugar) {
                return true;
            }
        }
        return false;
    }

    public boolean existeDistribucion() {
        return existeDistribucion;
    }

    public int[] getAsignacion() {
        return asignacion;
    }

    public int[] getCapacidadRestante() {
        return capacidadRestante;
    }

    public int getAsignacionesProbadas() {
        return asignacionesProbadas;
    }

    public int getRamasPodadas() {
        return ramasPodadas;
    }

    public int getAsignacionesDeshechas() {
        return asignacionesDeshechas;
    }
}
