package ejercicioB2;

import algoritmos.BacktrackingDistribucion;
import ejercicio3.Lote;
import modelo.Vehiculo;

public class Resolucion {

    public void resolver(Lote[] lotes, Vehiculo[] vehiculos, int[][] vehiculosHabilitados) {
        validar(lotes, vehiculos, vehiculosHabilitados);

        int[] pesos = new int[lotes.length];
        for (int lote = 0; lote < lotes.length; lote++) {
            pesos[lote] = lotes[lote].getPeso();
        }
        int[] capacidades = new int[vehiculos.length];
        for (int vehiculo = 0; vehiculo < vehiculos.length; vehiculo++) {
            capacidades[vehiculo] = vehiculos[vehiculo].getCapacidad();
        }
        boolean[][] habilitado = new boolean[lotes.length][vehiculos.length];
        for (int lote = 0; lote < lotes.length; lote++) {
            for (int idVehiculo : vehiculosHabilitados[lote]) {
                habilitado[lote][posicionDeVehiculo(vehiculos, idVehiculo)] = true;
            }
        }

        if (!cumpleChequeoPrevio(lotes, vehiculos, pesos, capacidades, habilitado)) {
            System.out.println("No existe una distribucion valida");
            System.out.println("Se descarto en el chequeo previo, no se ejecuto el backtracking");
            return;
        }

        BacktrackingDistribucion backtracking = new BacktrackingDistribucion();
        backtracking.ejecutar(pesos, capacidades, habilitado);
        mostrar(lotes, vehiculos, backtracking);
    }

    private void validar(Lote[] lotes, Vehiculo[] vehiculos, int[][] vehiculosHabilitados) {
        if (vehiculosHabilitados.length != lotes.length) {
            throw new IllegalArgumentException("Tiene que haber una lista de vehiculos habilitados por cada lote");
        }
        for (int lote = 0; lote < lotes.length; lote++) {
            for (int idVehiculo : vehiculosHabilitados[lote]) {
                if (posicionDeVehiculo(vehiculos, idVehiculo) == -1) {
                    throw new IllegalArgumentException("El vehiculo " + idVehiculo + " habilitado para el lote " + lotes[lote].getId() + " no existe");
                }
            }
        }
    }

    private int posicionDeVehiculo(Vehiculo[] vehiculos, int idVehiculo) {
        for (int vehiculo = 0; vehiculo < vehiculos.length; vehiculo++) {
            if (vehiculos[vehiculo].getId() == idVehiculo) {
                return vehiculo;
            }
        }
        return -1;
    }

    private boolean cumpleChequeoPrevio(Lote[] lotes, Vehiculo[] vehiculos, int[] pesos, int[] capacidades, boolean[][] habilitado) {
        int pesoTotal = 0;
        int capacidadTotal = 0;

        for (int lote = 0; lote < lotes.length; lote++) {
            boolean tieneVehiculos = false;
            boolean entraEnAlguno = false;
            for (int vehiculo = 0; vehiculo < vehiculos.length; vehiculo++) {
                if (habilitado[lote][vehiculo]) {
                    tieneVehiculos = true;
                    if (capacidades[vehiculo] >= pesos[lote]) {
                        entraEnAlguno = true;
                    }
                }
            }
            if (!tieneVehiculos) {
                System.out.println("El lote " + lotes[lote].getId() + " no tiene vehiculos habilitados");
                return false;
            }
            if (!entraEnAlguno) {
                System.out.println("El lote " + lotes[lote].getId() + " pesa mas que todos sus vehiculos habilitados");
                return false;
            }
            pesoTotal = pesoTotal + pesos[lote];
        }

        for (int vehiculo = 0; vehiculo < vehiculos.length; vehiculo++) {
            capacidadTotal = capacidadTotal + capacidades[vehiculo];
        }
        if (pesoTotal > capacidadTotal) {
            System.out.println("El peso total de los lotes (" + pesoTotal + " kg) supera la capacidad total (" + capacidadTotal + " kg)");
            return false;
        }
        return true;
    }

    private void mostrar(Lote[] lotes, Vehiculo[] vehiculos, BacktrackingDistribucion backtracking) {
        if (!backtracking.existeDistribucion()) {
            System.out.println("No existe una distribucion valida");
        } else {
            int[] asignacion = backtracking.getAsignacion();
            int[] capacidadRestante = backtracking.getCapacidadRestante();
            System.out.println("Distribucion encontrada:");
            for (int vehiculo = 0; vehiculo < vehiculos.length; vehiculo++) {
                String lotesDelVehiculo = "";
                for (int lote = 0; lote < lotes.length; lote++) {
                    if (asignacion[lote] == vehiculo) {
                        if (!lotesDelVehiculo.isEmpty()) {
                            lotesDelVehiculo = lotesDelVehiculo + ", ";
                        }
                        lotesDelVehiculo = lotesDelVehiculo + "Lote " + lotes[lote].getId() + " (" + lotes[lote].getPeso() + " kg)";
                    }
                }
                if (lotesDelVehiculo.isEmpty()) {
                    lotesDelVehiculo = "sin lotes";
                }
                int pesoCargado = vehiculos[vehiculo].getCapacidad() - capacidadRestante[vehiculo];
                System.out.println("  " + vehiculos[vehiculo] + " (capacidad " + vehiculos[vehiculo].getCapacidad() + " kg): " + lotesDelVehiculo);
                System.out.println("    Peso cargado: " + pesoCargado + " kg | Capacidad restante: " + capacidadRestante[vehiculo] + " kg");
            }
        }

        System.out.println("Asignaciones probadas: " + backtracking.getAsignacionesProbadas());
        System.out.println("Ramas podadas: " + backtracking.getRamasPodadas());
        System.out.println("Asignaciones deshechas: " + backtracking.getAsignacionesDeshechas());
    }
}
