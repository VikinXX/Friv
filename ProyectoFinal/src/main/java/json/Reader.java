package json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class Reader {
	
	public Reader() {
		
	}
	
	/**
	 * Check if the user exists in the JSON database.
	 * @param usua, username
	 * @param contra, password
	 * @return True, if it exists and the password is correct. False, if does not.
	 */
	
	public static boolean lectorUsuarios(String usua, String contra) {
		String contenido = null;
		try {
//			InputStream is = ClassLoader.getSystemResourceAsStream("data/user.json");
//			contenido = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray usuarios = json.getJSONArray("usuarios");

		for(int i = 0;i<usuarios.length();i++) {
			JSONObject user = usuarios.getJSONObject(i);
			
			String nombre = user.getString("nombre");
			String passwd = user.getString("passwd");
			
			if (usua.equals(nombre) && contra.equals(passwd)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the username exists in the JSON database.
	 * @param usua, username
	 * @return True, if it exists. False, if does not.
	 */
	
	public static boolean lectorUsuarioCrearCuenta(String usua) {
		String contenido = null;
		try {
//			InputStream is = ClassLoader.getSystemResourceAsStream("data/user.json");
//			contenido = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray usuarios = json.getJSONArray("usuarios");

		

		for(int i = 0;i<usuarios.length();i++) {
			JSONObject user = usuarios.getJSONObject(i);
			
			String nombre = user.getString("nombre");
			
			if (usua.equals(nombre)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Reads the username, maximum puntuation and minimum puntuation
	 * @param juego, game
	 * @param usua, username
	 * @return The String with the game, username, and puntuation.
	 */
	
	public static String lectorUsuarioPuntuacion(String juego, String usua) {
		String contenido = null;
		String cad = "";
		try {
//			InputStream is = ClassLoader.getSystemResourceAsStream("data/user.json");
//			contenido = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray usuarios = json.getJSONArray("usuarios");
		
		for(int i = 0 ; i < usuarios.length() ; i++) {
			JSONObject user = usuarios.getJSONObject(i);
			
			String nombre = user.getString("nombre");
			
			if (nombre.equals(usua)) {
				JSONArray juegos = user.getJSONArray("juegos");
				
				for (int j = 0 ; j < juegos.length() ; j++) {
					JSONObject game = juegos.getJSONObject(j);
					
					String g = game.getString("juego");
					
					if (g.equalsIgnoreCase(juego)) {
						cad += "<html>" + usua + ":<br>   " + juego + ": <br>";
						int maxP = game.getInt("puntuacionMaxima");
						int minP = game.getInt("puntuacionMinima");
						cad += "	Puntuacion Máxima: " + maxP + "<br>	 Puntuación Mínima: " + minP + "</html>";
						return cad;
					}
				}
			}
		}
		return cad;
	}
	
	public static String lectorVictorias(String usua, String juego) {
		String contenido = null;
		String cad = "";
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray usuarios = json.getJSONArray("usuarios");
		
		for (int i = 0; i < usuarios.length(); i++) {
			
			JSONObject user = usuarios.getJSONObject(i);
			String nombre = user.getString("nombre");
			
			if (nombre.equals(usua)) {
				JSONArray juegos = user.getJSONArray("juegos");
				
				for (int j = 0; j < juegos.length(); j++) {
					JSONObject games = juegos.getJSONObject(j);
					String game = games.getString("juego");
					
					if (game.equals(juego)) {
						cad += "<br>Victorias: " + games.getInt("victorias") + "<br>"
								+ "Derrotas: " + games.getInt("derrotas") + "<br>";
						return cad;
					}
				}
			}
		}
		return cad;
	}
	
}