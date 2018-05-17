/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Metier {

	private int id_m;
	private String nom_m;


	// GETTERS

	public int getId_m() {
		return id_m;
	}
	public String getNom_m() {
		return nom_m;
	}


	// SETTERS

	public void setId_m(int id_m) {
		this.id_m = id_m;
	}
	public void setNom_m(String nom_m) {
		this.nom_m = nom_m;
	}


	// CONSTRUCTEURS

	public Metier(int id_m, String nom_m) {
		this.id_m = id_m;
		this.nom_m = nom_m;
	}

	public Metier() {
	}


	// AJOUT METIER A BDD

	/**
	 * Cette methode ajoute un metier a la table metier de la BDD
	 */
	public static void defMetier(){
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Metier ? ");
		String metier = scan.nextLine();

		try{
			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT nom_m FROM metier");

			String ok = "Oui";
			while (result.next()) {
				if (metier.equals(result.getString("nom_m"))){
					System.out.println("Le metier existe deja");
					ok = "Non";
				}
			} 

			if (ok == "Oui"){
				try {
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO metier(nom_m) VALUES (?)");
					preparedState.setString(1, metier);

					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("Le metier a ete ajoute a la BDD");
				}

				catch (SQLException e) {
					e.printStackTrace();
				}
			}

			state.close();
			result.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		scan.close();
	}


	// CONVERSION STRING METIER EN METIER METIER

	/**
	 * Cette methode convertit un metier saisi en String en metier sous forme objet Metier
	 * @param metierStr metier sous forme String
	 * @return metier sous forme Metier
	 * @throws SQLException
	 */
	public static Metier loadfromBDD(String metierStr) throws SQLException{

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM metier");

		while (result.next()){
			String nomMetier = result.getString("nom_m");
			if (nomMetier.equals(metierStr)){
				return new Metier(result.getInt("id_m"),result.getString("nom_m"));
			}
		}

		PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO metier(nom_m) VALUES (?) "); // on l'ajoute
		preparedState.setString(1, metierStr);
		preparedState.executeUpdate();
		preparedState.close();

		state.close();
		result.close();
		return Metier.loadfromBDD(metierStr);
	}

}
