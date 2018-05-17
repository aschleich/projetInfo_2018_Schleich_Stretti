/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	private final String DB_URL = "jdbc:sqlite:arbre";
	private static Connection connection;

	
	// CONNEXION ENTRE LA BDD ET LE CODE
	
	/**
	 * Cette methode etablit une connexion entre la BDD et le code source
	 * @throws ClassNotFoundException
	 */
	private Connexion() throws ClassNotFoundException{
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(DB_URL);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Cette methode etablit une connexion entre la BDD et le code source
	 * @return connection connexion
	 */
	public static Connection getInstance(){
		if (connection == null){
			try {
				new Connexion();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}

}
