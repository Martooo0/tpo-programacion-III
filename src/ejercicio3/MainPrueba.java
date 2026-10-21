package ejercicio3;

public class MainPrueba {
    public static void main(String[] args) {
        // Cada constructor recibe ID, peso y prioridad, en ese orden.
        Lote[] lotes = {
                new Lote(1, 1, 10),
                new Lote(2, 4, 3),
                new Lote(3, 5, 5),
                new Lote(4, 7, 8)
        };
        int capacidadVehiculo = 8;
        ResultadoCarga resultado = SeleccionCargas.seleccionar(lotes, capacidadVehiculo);

        System.out.println("Capacidad del vehiculo: " + capacidadVehiculo + " kg");
        System.out.println();
        System.out.println("Lotes seleccionados (esperados: 1 y 4):");

        Lote[] seleccionados = resultado.getLotesSeleccionados();

        if (seleccionados.length == 0) {
            System.out.println("No se seleccionaron lotes.");
        }

        // El ciclo imprime todos los lotes que eligió el algoritmo.
        for (int i = 0; i < seleccionados.length; i++) {
            System.out.println(seleccionados[i]);
        }
        System.out.println();
        System.out.println("Resultado:");
        System.out.println("Peso total: " + resultado.getPesoTotal() + " kg");
        System.out.println("Prioridad total: " + resultado.getPrioridadTotal());
        System.out.println("Capacidad restante: " + resultado.getCapacidadRestante() + " kg");
    }
}
