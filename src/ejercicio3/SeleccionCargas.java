package ejercicio3;

// Resuelve la mochila 0/1 mediante Programación Dinámica.
public final class SeleccionCargas {
    private SeleccionCargas() { }

    // O es el arreglo de lotes y P es la capacidad máxima, en kg enteros.
    // Cada posición de O debe representar un lote distinto disponible para este viaje.
    public static ResultadoCarga seleccionar(Lote[] O, int P) {
        if (O == null) {
            throw new IllegalArgumentException("El arreglo de lotes es obligatorio.");
        }
        if (P < 0 || P == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        for (int i = 0; i < O.length; i++) {
            if (O[i] == null) {
                throw new IllegalArgumentException("No se admiten lotes nulos.");
            }
        }

        // Si no hay lotes, evitamos acceder a la fila O.length - 1, que sería -1.
        if (O.length == 0) {
            return new ResultadoCarga(new Lote[0], P);
        }

        // M[i][j] guarda la máxima prioridad usando los lotes de 0 a i, con una capacidad de j.
        // Hay una fila por lote y columnas desde 0 hasta P, ambas incluidas.
        // long permite sumar prioridades sin desbordar el rango de un int.
        long[][] M = new long[O.length][P + 1];

        for (int i = 0; i < O.length; i++) {
            int peso = O[i].getPeso();
            int valor = O[i].getPrioridad();
            for (int j = 0; j <= P; j++) {
                // Caso 1: el primer lote no entra en esta capacidad.
                if (i == 0 && peso > j) {
                    M[i][j] = 0;
                } else {
                    // Caso 2: el primer lote sí entra; guardamos su prioridad.
                    if (i == 0) {
                        M[i][j] = valor;
                    } else {
                        // Caso 3: este lote no entra; conservamos la solución anterior.
                        if (peso > j) {
                            M[i][j] = M[i - 1][j];
                        } else {
                            // Caso 4: comparamos NO cargarlo contra cargarlo completo.
                            long sinCargar = M[i - 1][j];
                            long cargando = M[i - 1][j - peso] + valor;
                            if (cargando > sinCargar) {
                                M[i][j] = cargando;
                            } else {
                                M[i][j] = sinCargar;
                            }
                        }
                    }
                }
            }
        }

        // Necesito saber cuales fueron los lotes, por eso llamo a "reconstruir".
        return reconstruir(O, P, M);
    }

    private static ResultadoCarga reconstruir(Lote[] O, int P, long[][] M) {
        // elegidos[i] indica si el lote de la posición i pertenece a la solución.
        boolean[] elegidos = new boolean[O.length];
        int cantidad = 0;
        int j = P;

        // Recorremos la tabla desde el último lote hacia el primero.
        for (int i = O.length - 1; i > 0; i--) {
            if (M[i][j] != M[i - 1][j]) {
                // La prioridad mejoró al incorporar este lote: lo seleccionamos.
                elegidos[i] = true;
                cantidad++;
                // Los lotes anteriores deben resolver la capacidad que queda.
                j = j - O[i].getPeso();
            }
            // Si los valores coinciden, podemos obtener el mismo óptimo sin este lote.
            // En empates elegimos no agregarlo.
        }

        // El primer lote se trata aparte porque no tiene una fila anterior.
        // Si su prioridad es cero, no hace falta cargarlo para alcanzar el óptimo.
        if (M[0][j] > 0) {
            elegidos[0] = true;
            cantidad++;
        }

        // Construimos un arreglo con solo los lotes elegidos, en el orden de entrada.
        Lote[] seleccionados = new Lote[cantidad];
        int posicion = 0;
        for (int i = 0; i < O.length; i++) {
            if (elegidos[i]) {
                seleccionados[posicion] = O[i];
                posicion++;
            }
        }
        return new ResultadoCarga(seleccionados, P);
    }
}
