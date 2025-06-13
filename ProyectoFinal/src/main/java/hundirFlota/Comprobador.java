package hundirFlota;

import java.util.HashMap;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class Comprobador {
	
	HashMap<Integer, Integer> barcos=new HashMap<Integer, Integer>();
	private boolean [][] fichas;
	private int fila;
	private int colum;
	
	/**
	 * Constructor that introduces into a HashMap the ships to follow the rhythm of the match.
	 */
	
	public Comprobador() {
		barcos.put(1, 1);
		barcos.put(2, 2);
		barcos.put(3, 3);
		barcos.put(4, 4);
		fichas=new boolean[10][10];
	}
	
	public int getFila() {
		return fila;
	}
	
	public void setFila(int fila) {
		this.fila = fila;
	}
	
	public int getColum() {
		return colum;
	}
	
	public void setColum(int colum) {
		this.colum = colum;
	}
	/**
	 * Function that throws the bot's bomb.
	 * @param tablero
	 * @return The type of bomb that is.
	 */
	public int bombaIA(int[][] tablero) {
		int f = (int)(Math.random()*10);
		int c = (int)(Math.random()*10);
		while(fichas[f][c]) {
			f = (int)(Math.random()*10);
			c = (int)(Math.random()*10);
		}
		setColum(c);
		setFila(f);
		return this.comprobado(f, c, tablero);
	}
	
	/**
	 * 
	 * @param fila
	 * @param columna
	 * @param tablero
	 * @return How the bomb affect to the match
	 */
	
	public int comprobado(int fila, int colum, int[][] tablero) {
		//2 tocado y hundido, 1 tocado, 0 agua, -1 repetido
		if (fichas[fila][colum]) {
			return -1;
		}else {
			if(tablero[fila][colum]==1) {
				barcos.put(1, barcos.get(1)-1);
				fichas[fila][colum]=true;
				return 2;
			}else if(tablero[fila][colum]==2) {
				barcos.put(2, barcos.get(2)-1);
				fichas[fila][colum]=true;
				if(barcos.get(2)==0) {
					return 2;
				}else {
					return 1;
				}
			}else if(tablero[fila][colum]==3) {
				barcos.put(3, barcos.get(3)-1);
				fichas[fila][colum]=true;
				if(barcos.get(3)==0) {
					return 2;
				}else {
					return 1;
				}
			}else if(tablero[fila][colum]==4) {
				barcos.put(4, barcos.get(4)-1);
				fichas[fila][colum]=true;
				if(barcos.get(4)==0) {
					return 2;
				}else {
					return 1;
				}
			}else {
				fichas[fila][colum]=true;
				return 0;
			}
		}
	}
	
	/**
	 * 
	 * @return True, if the player won the match.
	 */
	
	public boolean Victoria() {
		boolean victoria=true;
		for(Integer value : barcos.keySet()) {
			if(barcos.get(value)>0) {
				victoria=false;
			}
		}
		return victoria;
	}
	
}