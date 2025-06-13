package modelo;

import java.awt.Color;

/**
 * @author Daniel Rios Fraga
 * Esta clase representa la parte lógica del tablero del juego
 * Gestiona el estado del juego, los movimientos de los jugadores y las verificaciones
 * de victoria/empate. Contiene una matriz de casillas que representan el estado
 * actual del tablero y gestiona los turnos de los jugadores
 */
public class Tablero {
    private Color[][] casillas;
    private int filas;
    private int columnas;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;

    /**
     * Constructor que crea un tablero con las dimensiones especificadas.
     * Inicializa todas las casillas en color blanco (vacías).
     * 
     * @param filas Número de filas del tablero
     * @param columnas Número de columnas del tablero
     */
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new Color[filas][columnas];
        inicializarTablero();
    }

    /**
     * Inicializa todas las casillas del tablero con color blanco (vacías).
     */
    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = Color.WHITE;
            }
        }
    }

    /**
     * Establece los jugadores que participarán en el juego.
     * 
     * @param jugador1 Primer jugador
     * @param jugador2 Segundo jugador
     */
    public void setJugadores(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1;
    }

    /**
     * Obtiene el jugador cuyo turno es actual.
     * 
     * @return Jugador actual
     */
    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    /**
     * Obtiene el primer jugador.
     * 
     * @return Primer jugador
     */
    public Jugador getJugador1() {
        return jugador1;
    }

    /**
     * Obtiene el segundo jugador.
     * 
     * @return Segundo jugador
     */
    public Jugador getJugador2() {
        return jugador2;
    }

    /**
     * Cambia el turno al otro jugador.
     */
    public void cambiarTurno() {
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

    /**
     * Realiza un movimiento en la columna especificada para el jugador actual.
     * 
     * @param columna Columna donde se realizará el movimiento (0-based)
     * @return true si el movimiento fue válido y se realizó, false en caso contrario
     */
    public boolean hacerMovimiento(int columna) {
        if (columna < 0 || columna >= columnas || casillas[0][columna] != Color.WHITE) {
            return false;
        }

        for (int fila = filas - 1; fila >= 0; fila--) {
            if (casillas[fila][columna].equals(Color.WHITE)) {
                casillas[fila][columna] = jugadorActual.getColor();
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza un movimiento automático para el jugador BOT
     * Implementa una inteligencia artificial básica que intenta ganar, bloquear
     * al oponente o hacer movimientos estratégicos
     * 
     * @return true si se pudo realizar un movimiento, false si no hubo movimientos válidos
     */
    public boolean hacerMovimientoBot() {
        // 1. Intentar ganar
        for (int col = 0; col < columnas; col++) {
            if (puedeGanar(col, jugadorActual.getColor()) && !columnaLlena(col)) {
                return colocarFichaEnColumna(col, jugadorActual.getColor());
            }
        }

        // 2. Bloquear al oponente
        Jugador oponente = (jugadorActual == jugador1) ? jugador2 : jugador1;
        for (int col = 0; col < columnas; col++) {
            if (puedeGanar(col, oponente.getColor()) && !columnaLlena(col)) {
                return colocarFichaEnColumna(col, jugadorActual.getColor());
            }
        }

        // 3. Priorizar columna central
        int centro = columnas / 2;
        if (!columnaLlena(centro)) {
            return colocarFichaEnColumna(centro, jugadorActual.getColor());
        }

        // 4. Movimiento aleatorio válido
        for (int intentos = 0; intentos < columnas * 2; intentos++) {
            int colAleatoria = (int) (Math.random() * columnas);
            if (!columnaLlena(colAleatoria)) {
                return colocarFichaEnColumna(colAleatoria, jugadorActual.getColor());
            }
        }

        // 5. Como último recurso, buscar cualquier columna disponible
        for (int col = 0; col < columnas; col++) {
            if (!columnaLlena(col)) {
                return colocarFichaEnColumna(col, jugadorActual.getColor());
            }
        }

        return false;
    }

    /**
     * Coloca una ficha del color especificado en la columna indicada.
     * 
     * @param columna Columna donde colocar la ficha
     * @param color Color de la ficha a colocar
     * @return true si se pudo colocar la ficha, false si la columna estaba llena
     */
    private boolean colocarFichaEnColumna(int columna, Color color) {
        for (int fila = filas - 1; fila >= 0; fila--) {
            if (casillas[fila][columna].equals(Color.WHITE)) {
                casillas[fila][columna] = color;
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si una columna está completamente llena.
     * 
     * @param columna Columna a verificar
     * @return true si la columna está llena, false si tiene espacios disponibles
     */
    public boolean columnaLlena(int columna) {
        return casillas[0][columna] != Color.WHITE;
    }

    /**
     * Simula si colocar una ficha en la columna especificada resultaría en victoria.
     * 
     * @param columna Columna a verificar
     * @param color Color de la ficha a simular
     * @return true si el movimiento resultaría en victoria, false en caso contrario
     */
    private boolean puedeGanar(int columna, Color color) {
        for (int fila = filas - 1; fila >= 0; fila--) {
            if (casillas[fila][columna].equals(Color.WHITE)) {
                casillas[fila][columna] = color;
                boolean gana = hayGanador(color);
                casillas[fila][columna] = Color.WHITE;
                return gana;
            }
        }
        return false;
    }

    /**
     * Verifica si el jugador actual ha ganado la partida.
     * 
     * @return true si hay un ganador, false en caso contrario
     */
    public boolean hayGanador() {
        return hayGanador(jugadorActual.getColor());
    }

    /**
     * Verifica si el color especificado ha formado 4 en raya.
     * 
     * @param color Color a verificar
     * @return true si el color ha ganado, false en caso contrario
     */
    public boolean hayGanador(Color color) {
        return verificarLineasHorizontales(color) ||
               verificarLineasVerticales(color) ||
               verificarDiagonales(color);
    }

    /**
     * Verifica si el tablero está completamente lleno (empate).
     * 
     * @return true si no hay movimientos posibles y no hay ganador, false en caso contrario
     */
    public boolean hayEmpate() {
        for (int j = 0; j < columnas; j++) {
            if (!columnaLlena(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica líneas horizontales de 4 fichas del mismo color.
     * 
     * @param color Color a verificar
     * @return true si encuentra 4 en raya horizontal, false en caso contrario
     */
    private boolean verificarLineasHorizontales(Color color) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas - 3; j++) {
                if (casillas[i][j].equals(color) &&
                    casillas[i][j+1].equals(color) &&
                    casillas[i][j+2].equals(color) &&
                    casillas[i][j+3].equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica líneas verticales de 4 fichas del mismo color.
     * 
     * @param color Color a verificar
     * @return true si encuentra 4 en raya vertical, false en caso contrario
     */
    private boolean verificarLineasVerticales(Color color) {
        for (int i = 0; i < filas - 3; i++) {
            for (int j = 0; j < columnas; j++) {
                if (casillas[i][j].equals(color) &&
                    casillas[i+1][j].equals(color) &&
                    casillas[i+2][j].equals(color) &&
                    casillas[i+3][j].equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica líneas diagonales de 4 fichas del mismo color.
     * 
     * @param color Color a verificar
     * @return true si encuentra 4 en raya diagonal, false en caso contrario
     */
    private boolean verificarDiagonales(Color color) {
        return verificarDiagonalesAscendentes(color) || 
               verificarDiagonalesDescendentes(color);
    }

    /**
     * Verifica diagonales ascendentes (de izquierda a derecha) de 4 fichas del mismo color.
     * 
     * @param color Color a verificar
     * @return true si encuentra 4 en raya diagonal ascendente, false en caso contrario
     */
    private boolean verificarDiagonalesAscendentes(Color color) {
        for (int i = 3; i < filas; i++) {
            for (int j = 0; j < columnas - 3; j++) {
                if (casillas[i][j].equals(color) &&
                    casillas[i-1][j+1].equals(color) &&
                    casillas[i-2][j+2].equals(color) &&
                    casillas[i-3][j+3].equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica diagonales descendentes (de izquierda a derecha) de 4 fichas del mismo color.
     * 
     * @param color Color a verificar
     * @return true si encuentra 4 en raya diagonal descendente, false en caso contrario
     */
    private boolean verificarDiagonalesDescendentes(Color color) {
        for (int i = 0; i < filas - 3; i++) {
            for (int j = 0; j < columnas - 3; j++) {
                if (casillas[i][j].equals(color) &&
                    casillas[i+1][j+1].equals(color) &&
                    casillas[i+2][j+2].equals(color) &&
                    casillas[i+3][j+3].equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Reinicia el tablero a su estado inicial (todas las casillas vacías).
     * El turno vuelve al primer jugador.
     */
    public void reiniciar() {
        inicializarTablero();
        jugadorActual = jugador1;
    }

    /**
     * Obtiene la matriz de casillas que representan el estado actual del tablero.
     * 
     * @return Matriz bidimensional de colores representando el tablero
     */
    public Color[][] getCasillas() {
        return casillas;
    }

    /**
     * Obtiene el número de filas del tablero.
     * 
     * @return Número de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Obtiene el número de columnas del tablero.
     * 
     * @return Número de columnas
     */
    public int getColumnas() {
        return columnas;
    }
}