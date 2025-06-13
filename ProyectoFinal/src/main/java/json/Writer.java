package json;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class Writer {

	public Writer() {

	}

	/**
	 * Create a another object user in the JSON with the correspondent games.
	 * 
	 * @param usua, username
	 * @param passwd, password
	 */

	public static void crearCuentaUsuario(String usua, String passwd) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray usuarios = json.getJSONArray("usuarios");

		JSONObject nuevoUsuario = new JSONObject();
		nuevoUsuario.put("nombre", usua);
		nuevoUsuario.put("passwd", passwd);

		JSONArray juegos = new JSONArray();

		JSONObject juego1 = new JSONObject();
		juego1.put("juego", "Hundir la Flota");
		juego1.put("puntuacionMaxima", 0);
		juego1.put("puntuacionMinima", 0);

		JSONObject juego2 = new JSONObject();
		juego2.put("juego", "wordle");
		juego2.put("puntuacionMaxima", 0);
		juego2.put("puntuacionMinima", 0);

		JSONObject juego3 = new JSONObject();
		juego3.put("juego", "4 en raya");
		juego3.put("victorias", 0);
		juego3.put("derrotas", 0);

		juegos.put(juego1);
		juegos.put(juego2);
		juegos.put(juego3);

		nuevoUsuario.put("juegos", juegos);

		usuarios.put(nuevoUsuario);

		try (FileWriter file = new FileWriter("data/user.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overwrite the puntuation if it is more than the maximum putuation or less
	 * than the minimum.
	 * 
	 * @param usua, username.
	 * @param punt, actual putuation of the player in the game.
	 * @param juego, game.
	 */

	public static void escribirPuntuacion(String usua, int punt, String juego) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray users = json.getJSONArray("usuarios");

		for (int i = 0; i < users.length(); i++) {
			JSONObject usuario = users.getJSONObject(i);
			if (usuario.getString("nombre").equals(usua)) {
				JSONArray games = usuario.getJSONArray("juegos");

				for (int j = 0; j < games.length(); j++) {
					JSONObject game = games.getJSONObject(j);

					if (game.getString("juego").equals(juego) && game.getInt("puntuacionMaxima") < punt) {
						game.put("puntuacionMaxima", punt);
						if (game.getInt("puntuacionMinima") == 0) {
							game.put("puntuacionMinima", punt);
						}
					} else if (game.getString("juego").equals(juego) && game.getInt("puntuacionMinima") > punt) {
						game.put("puntuacionMinima", punt);
					}
				}
			}
		}

		try (FileWriter file = new FileWriter("data/user.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To overwrite the wins of the correspondent players.
	 * 
	 * @param usua, username
	 * @param juego, game
	 * @param ganador, winner
	 */

	public static void ganador4Raya(String usua, String juego, String ganador, String perdedor) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray users = json.getJSONArray("usuarios");

		for (int i = 0; i < users.length(); i++) {
			JSONObject usuario = users.getJSONObject(i);
			if (usuario.getString("nombre").equals(usua)) {
				JSONArray games = usuario.getJSONArray("juegos");

				for (int j = 0; j < games.length(); j++) {
					JSONObject game = games.getJSONObject(j);

					if (game.getString("juego").equals(juego)) {
						game.put("victorias", game.getInt("victorias") + 1);
					}
				}
			} else if (usuario.getString("nombre").equals(perdedor)) {
				JSONArray games = usuario.getJSONArray("juegos");

				for (int j = 0; j < games.length(); j++) {
					JSONObject game = games.getJSONObject(j);

					if (game.getString("juego").equals(juego)) {
						game.put("derrotas", game.getInt("derrotas") + 1);
					}
				}
			}
		}

		try (FileWriter file = new FileWriter("data/user.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To overwrite the wins of the correspondent player, for just one player.
	 * 
	 * @param usua
	 * @param juego
	 * @param win
	 */

	public static void ganador4RayaJ1(String usua, String juego, boolean win) {
		String contenido = null;
		try {
			contenido = new String(Files.readAllBytes(Paths.get("data/user.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject(contenido);
		JSONArray users = json.getJSONArray("usuarios");

		for (int i = 0; i < users.length(); i++) {
			JSONObject usuario = users.getJSONObject(i);
			String nom = usuario.getString("nombre");

			if (nom.equals(usua)) {
				JSONArray games = usuario.getJSONArray("juegos");

				for (int j = 0; j < games.length(); j++) {
					JSONObject game = games.getJSONObject(j);

					if (game.getString("juego").equals(juego)) {
						if (win) {
							game.put("victorias", game.getInt("victorias") + 1);
						} else {
							game.put("derrotas", game.getInt("derrotas") + 1);
						}
					}
				}
			}
		}

		try (FileWriter file = new FileWriter("data/user.json")) {
			file.write(json.toString(4));
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
