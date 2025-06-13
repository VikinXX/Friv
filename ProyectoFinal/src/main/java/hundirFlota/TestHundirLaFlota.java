package hundirFlota;

import java.util.Scanner;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class TestHundirLaFlota {
	
	public static void PintarTablero(int[][] tableroJuego) {
		System.out.println("---------------------");
		for(int i=0 ; i<tableroJuego.length ; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				if(j==0) {
					System.out.print("|");
					if(tableroJuego[i][j]==9) {
						System.out.print(" "+"|");
					}else if(tableroJuego[i][j]!=9) {
						System.out.print(tableroJuego[i][j]+"|");
					}
				}else {
					if(tableroJuego[i][j]==9) {
						System.out.print(" "+"|");
					}else if(tableroJuego[i][j]!=9) {
						System.out.print(tableroJuego[i][j]+"|");
					}
				}
			}
			System.out.println();
			System.out.println("---------------------");
		}
	}
	
	public static void main(String[] args) {
		Scanner ent=new Scanner(System.in);
		Tablero t1=new Tablero();
		Comprobador yo=new Comprobador();
		Comprobador ia=new Comprobador();
		t1.setTableroJuego();
		
		for(int barco=1 ; barco<5 ; barco++) {
			System.out.println("¿En qué direccion quieres poner el "+barco+" barco? U Arriba, R Derecha, D Abajo, L Izquierda.");
			char direc=ent.next().toUpperCase().charAt(0);
			System.out.println("¿En qué fila quieres meter el barco?");
			int fila=ent.nextInt();
			System.out.println("¿En qué columna quieres meter el barco?");
			int colum=ent.nextInt();
			
			t1.setTablero(fila, colum, barco, direc);
			PintarTablero(t1.getTablero());
		}
		System.out.println();
		PintarTablero(t1.getTableroJuego());
		
		while(!yo.Victoria() || !ia.Victoria()) {
			System.out.println("¿En qué fila quieres poner la bomba?");
			int fila=ent.nextInt();
			System.out.println("¿En qué columna quieres poner la bomba?");
			int colum=ent.nextInt();
			
			if(ia.comprobado(fila, colum, t1.getTableroEnemigo())==0) {
				System.out.println("Agua");
				t1.bomba(fila, colum, ia.comprobado(fila, colum, t1.getTableroEnemigo()));
			}else if(ia.comprobado(fila, colum, t1.getTableroEnemigo())==1) {
				System.out.println("Tocado");
				t1.bomba(fila, colum, ia.comprobado(fila, colum, t1.getTableroEnemigo()));
			}else {
				System.out.println("Tocado y hundido");
				t1.bomba(fila, colum, ia.comprobado(fila, colum, t1.getTableroEnemigo()));
			}
			
			if(ia.Victoria()) break;
			
			fila=(int)(Math.random()*10);
			colum=(int)(Math.random()*10);
			
			if(yo.comprobado(fila, colum, t1.getTablero())==0) {
				System.out.println("Agua");
			}else if(yo.comprobado(fila, colum, t1.getTablero())==1) {
				System.out.println("Tocado");
			}else {
				System.out.println("Tocado y hundido");
			}
			PintarTablero(t1.getTableroJuego());
		}
		
		if(ia.Victoria()) {
			System.out.println("Has ganado");
		}else {
			System.out.println("Has perdido");
		}
		
//		for (int i = 0; i < te.length; i++) {
//			for (int j = 0; j < te[i].length; j++) {
//				System.out.print(te[i][j]);
//			}
//			System.out.println();
//		}
	}
}