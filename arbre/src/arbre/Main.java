/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;

import java.sql.SQLException;


public class Main {

	public static void main(String[] args) throws SQLException {

		Personne.appelPers();

		Personne pers2= Personne.choixPersonne(2);
		
		pers2.arbreAscendance();
		pers2.arbreDescendance();
		
		// Ecrire votre commande ici
	
	}
	
}

