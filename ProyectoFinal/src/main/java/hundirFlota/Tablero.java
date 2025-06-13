package hundirFlota;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class Tablero {

	private int tablero[][] = new int[10][10];
	private int[][] tableroEnemigo = new int[10][10];
	private int[][] barcos = { { 1 }, { 1, 1 }, { 1, 1, 1 }, { 1, 1, 1, 1 } };
	private int[][] tableroJuego=new int[10][10];

	/**
	 * Constructor that calls a function to generate the bot's board.
	 */
	
	public Tablero() {
		crearTableroEnemigo();
		while(!comprobar(tableroEnemigo)) {
			resetTableroEnemigo();
			crearTableroEnemigo();
		}
	}

	public int[][] getTableroEnemigo() {
		return this.tableroEnemigo;
	}
	
	/**
	 * Function that creates the player's board on console.
	 * @param fila
	 * @param colum
	 * @param barco
	 * @param direccion
	 */
	
	public void setTablero(int fila, int colum, int barco, char direccion) {
		if(direccion=='U') {
			for(int i=fila-1, c=0 ; c<barco ; c++,i--) {
				if(i==fila-1) {
					this.tablero[i][colum]=barco;
				}else {
					this.tablero[i][colum]=barco;
				}
			}
		}else if(direccion=='R') {
			for(int i=colum-1, c=0; c<barco ;c++, i++) {
				if(i==colum-1) {
					this.tablero[fila][i]=barco;
				}else {
					this.tablero[fila][i]=barco;
				}
			}
		}else if(direccion=='D') {
			for(int i=fila-1, c=0; c<barco ;c++ ,i++) {
				if(i==fila-1) {
					this.tablero[i][colum]=barco;
				}else {
					this.tablero[i][colum]=barco;
				}
			}
		}else {
			for(int i=colum-1, c=0 ; c<barco ;c++, i--) {
				if(i==colum-1) {
					this.tablero[fila][i]=barco;
				}else {
					this.tablero[fila][i]=barco;
				}
			}
		}
	}
	
	/**
	 * Function that creates the player's board on Graphic Interface.
	 * @param tab
	 */
	
	public void setTablero(int [][] tab) {
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				this.tablero[i][j] = tab[i][j];
			}
		}
	}
	
	public int [][] getTablero(){
		return this.tablero;
	}
	
	/**
	 * Function that set the board to empty board.
	 */
	
	public void setTableroJuego() {
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tableroJuego[i][j]=9;
			}
		}
	}
	
	/**
	 * Function that set the board to empty board for graphic interface.
	 */
	
	public void resetTableroEnemigo() {
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tableroEnemigo[i][j]=0;
			}
		}
	}
	
	/**
	 * Set the bomb into the array.
	 * @param fila
	 * @param colum
	 * @param bomba
	 */
	
	public void bomba(int fila, int colum, int bomba) {
		tableroJuego[fila][colum]=bomba;
	}

	public int[][] getTableroJuego(){
		return tableroJuego;
	}
	
	/**
	 * Creates the bot's board, checking if the ships are overlapping each other and controlling that they don't go out the array.
	 */
	
	public void crearTableroEnemigo() {
		for (int i = 0; i < 4; i++) {
			byte num = (byte) (Math.random() * 10);
			byte num2 = (byte) (Math.random() * 10);
			if (i > 0) {
				// 0 horizontal, 1 vertical
				byte direccion = (byte) (Math.random() * 2);
				boolean sePuede = true;
				while (sePuede) {
					boolean superpuesto=false;
					if (direccion == 0 && num2 < i+1) {
						for (int j = 0; j <= i; j++) {
							if (tableroEnemigo[num][j] != 0) {
								num = (byte) (Math.random() * 11);
								num2 = (byte) (Math.random() * 11);
								i--;
								superpuesto=true;
								break;
							}
						}
						if(!sePuede || superpuesto)break;

						for (int j = num2, c=0; c < barcos[i].length; c++,j++) {
							tableroEnemigo[num][j] = i+1;
						}
						sePuede = false;

					} else if (direccion == 0 && 9 - num2 < i+1) {
						for (int j = num2, c = 0; c < barcos[i].length; j--, c++) {
							if (tableroEnemigo[num][j] != 0) {
								num = (byte) (Math.random() * 11);
								num2 = (byte) (Math.random() * 11);
								i--;
								superpuesto=true;
								break;
							}
						}
						if(!sePuede || superpuesto)break;

						for (int j = num2, c=0; c < barcos[i].length; c++,j--) {
							tableroEnemigo[num][j] = i+1;
						}
						sePuede = false;

					} else if (direccion == 1 && num < i+1) {
						for (int j = num; j <= i; j++) {
							if (tableroEnemigo[j][num2] != 0) {
								num = (byte) (Math.random() * 11);
								num2 = (byte) (Math.random() * 11);
								i--;
								superpuesto=true;
								break;
							}
						}
						if(!sePuede || superpuesto)break;

						for (int j = num, c=0; c < barcos[i].length; c++,j++) {
							tableroEnemigo[j][num2] = i+1;
						}
						sePuede = false;

					} else if (direccion == 1 && 9 - num < i+1) {
						for (int j = num, c = 0; c < barcos[i].length; j--, c++) {
							if (tableroEnemigo[j][num2] != 0) {
								num = (byte) (Math.random() * 11);
								num2 = (byte) (Math.random() * 11);
								i--;
								superpuesto=true;
								break;
							}
						}
						if(!sePuede || superpuesto)break;

						for (int j = num, c=0; c < barcos[i].length; c++,j--) {
							tableroEnemigo[j][num2] = i+1;
						}
						sePuede = false;

					} else if (direccion == 0) {
						for (int j = 0; j <= i; j++) {
							if (tableroEnemigo[num][j] != 0) {
								num = (byte) (Math.random() * 11);
								num2 = (byte) (Math.random() * 11);
								i--;
								superpuesto=true;
								break;
							}
						}
						if(!sePuede || superpuesto)break;

						for (int j = num2, c=0; c < barcos[i].length; c++,j++) {
							tableroEnemigo[num][j] = i+1;
						}
						sePuede = false;
					} else {
						for (int j = num, c = 0; c < barcos[i].length; j--, c++) {
							if (tableroEnemigo[j][num2] != 0) {
								num = (byte) (Math.random() * 11);
								num2 = (byte) (Math.random() * 11);
								i--;
								superpuesto=true;
								break;
							}
						}
						if(!sePuede || superpuesto)break;
						for (int j = num, c=0; c < barcos[i].length; c++,j++) {
							tableroEnemigo[j][num2] = i+1;
						}
						sePuede = false;
					}
				}
			} else {
				tableroEnemigo[num][num2] = 1;
			}
		}
	}
	
//	public static boolean comprobar(int [][] tab) {
//		HashMap<Integer, Integer> barquitos = new HashMap<>();
//		ArrayList<Integer> key = new ArrayList<>();
//		barquitos.put(1, 1);
//		barquitos.put(2, 2);
//		barquitos.put(3, 3);
//		barquitos.put(4, 4);
//		for (int i = 0; i < tab.length; i++) {
//			for (int j = 0; j < tab[i].length; j++) {
//				int i2 = i;
//				int j2 = j;
//				int c = 0;
//				if (tab[i][j] > 0) {
//					barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
//					if (barquitos.get(tab[i][j]) == 0) {
//						key.add(tab[i][j]);
//					}
//					while (barquitos.get(tab[i][j]) > 0 && c < 4) {
//						if(!key.contains(tab[i][j])) {
//							if (j2 != 9 && tab[i2][j2+1] == tab[i][j]) {
//								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
//								j2++;
//							}else if(i2 != 0 && tab[i2-1][j2] == tab[i][j]) {
//								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
//								i2--;
//							}else if(j2 != 0 && tab[i2][j2-1] == tab[i][j]) {
//								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
//								j2--;
//							}else if(i2 != 9 && tab[i2+1][j2] == tab[i][j]) {
//								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
//								i2++;
//							}
//							if (barquitos.get(tab[i][j]) == 0) {
//								key.add(tab[i][j]);
//							}
//						}
//						c++;
//					}
//				}
//				if (c >= 4) {
//					return false;
//				}
//			}
//		}
//		for (Integer hm : barquitos.values()) {
//			if (hm > 0) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	/**
	 * Checks the board to see if is correct or not
	 * @param tab
	 * @return True, id is correct, False if it is not.
	 */
	
	public static boolean comprobar(int [][] tab) {
		HashMap<Integer, Integer> barquitos = new HashMap<>();
		ArrayList<Integer> key = new ArrayList<>();
		barquitos.put(1, 1);
		barquitos.put(2, 2);
		barquitos.put(3, 3);
		barquitos.put(4, 4);
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				int i2 = i;
				int j2 = j;
				int c = 0;
				if (tab[i][j] > 0) {
					while (barquitos.get(tab[i][j]) > 0 && c < 4) {
						if(!key.contains(tab[i][j])) {
							if (tab[i][j] == 1) {
								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
							}
							if (j2 != 9 && tab[i2][j2+1] == tab[i][j]) {
								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
								j2++;
							}else if(i2 != 0 && tab[i2-1][j2] == tab[i][j]) {
								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
								i2--;
							}else if(j2 != 0 && tab[i2][j2-1] == tab[i][j]) {
								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
								j2--;
							}else if(i2 != 9 && tab[i2+1][j2] == tab[i][j]) {
								barquitos.put(tab[i][j], barquitos.get(tab[i][j])-1);
								i2++;
							}
							if (barquitos.get(tab[i][j]) == 0) {
								key.add(tab[i][j]);
							}
						}
						c++;
					}
				}
				if (c > 4) {
					return false;
				}
			}
		}
		for (Integer hm : barquitos.values()) {
			if (hm != 0) {
				return false;
			}
		}
		return true;
	}
}