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


public class Mariage_eglise {

	private int id_me;
	private String me_date;
	private String me_lieu;
	private Source me_source;
	private Personne pers1;
	private Personne pers2;


	// GETTERS	

	public int getId_me() {
		return id_me;
	}
	public String getMe_date() {
		return me_date;
	}
	public String getMe_lieu() {
		return me_lieu;
	}
	public Source getMe_source() {
		return me_source;
	}
	public Personne getPers1() {
		return pers1;
	}
	public Personne getPers2() {
		return pers2;
	}


	// SETTERS

	public void setId_me(int id_me) {
		this.id_me = id_me;
	}
	public void setMe_date(String me_date) {
		this.me_date = me_date;
	}
	public void setMe_lieu(String me_lieu) {
		this.me_lieu = me_lieu;
	}
	public void setMe_source(Source me_source) {
		this.me_source = me_source;
	}


	// CONSTRUCTEUR

	public Mariage_eglise(String me_date, String me_lieu, Source me_source) {
		this.me_date = me_date;
		this.me_lieu = me_lieu;
		this.me_source = me_source;
	}


	// AJOUT MARIAGE EGLISE

	/**
	 * Cette methode ajoute l'evenement mariage eglise a deux personnes
	 * @param pers1 marie1
	 * @param pers2 marie2
	 * @throws SQLException 
	 */
	public void addMaries(Personne pers1, Personne pers2) throws SQLException {

		this.pers1 = pers1;
		this.pers2 = pers2;

		int id_marie1 = pers1.getId_p();
		int id_marie2 = pers2.getId_p();
		String me_date = this.getMe_date();
		String me_lieu = this.getMe_lieu();
		Source me_source = this.getMe_source();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM mariage_mairie WHERE ((mariage_mairie.id_marie1 = '"+id_marie1+"') AND (mariage_mairie.id_marie2 = '"+id_marie2+"')) OR ((mariage_mairie.id_marie1 = '"+id_marie2+"') AND (mariage_mairie.id_marie2 = '"+id_marie1+"'))");
		result.next();
		int count = result.getInt("rowcount");

		if (count != 0){

			try{
				Statement state2 = Connexion.getInstance().createStatement();
				ResultSet result2 = state.executeQuery("SELECT * FROM mariage_eglise WHERE ((mariage_eglise.id_marie1 = '"+id_marie1+"') AND (mariage_eglise.id_marie2 = '"+id_marie2+"')) OR ((mariage_eglise.id_marie1 = '"+id_marie2+"') AND (mariage_eglise.id_marie2 = '"+id_marie1+"'))");

				if (result2.next()){
					System.out.println("Le mariage existe deja");
				}

				else{
					try{
						PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO mariage_eglise(me_date, me_lieu, me_source, id_marie1, id_marie2) VALUES (?,?,?,?,?) ");
						preparedState.setString(1, me_date);
						preparedState.setString(2, me_lieu);
						preparedState.setString(3, me_source.toString());
						preparedState.setInt(4, id_marie1);
						preparedState.setInt(5, id_marie2);

						preparedState.executeUpdate();
						preparedState.close();
						System.out.println("Le mariage eglise entre "+pers1.getPrenom()+" "+pers1.getNom()+" et "+pers2.getPrenom()+" "+pers2.getNom()+" a ete ajoute");
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				state.close();
				result.close();
				state2.close();
				result2.close();
			}

			catch (SQLException e) {
				e.printStackTrace();	
			}
		}
		else{
			System.out.println("Ajouter d'abord un mariage mairie");
		}
	}


	// OBTENIR ID MARIAGE EGLISE

	/**
	 * Cette methode renvoie l'identifiant du mariage eglise, utile pour la methode addTemoin
	 * @return int identi identifiant du mariage eglise
	 * @throws SQLException
	 */
	public int loadfromBDD() throws SQLException{
		
		int identi = 1;
		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM mariage_eglise");

		while (result.next()){
			String datemar = result.getString("me_date");
			String lieumar = result.getString("me_lieu");
			int id_p1 = result.getInt("id_marie1");


			if ( (datemar.equals(this.me_date)) && (lieumar.equals(this.me_lieu)) &&  ( (id_p1 == this.pers1.getId_p()) || id_p1 == this.pers2.getId_p()) ){
				identi = result.getInt("id_me");
				return identi;
			}
		}
		System.out.println("voici id_me ");
		return identi;
	}


	// CONTROLE AGE TEMOIN

	/**
	 * Cette methode controle si le temoin a plus de 18 ans
	 * @param temoin
	 * @return boolean coherence
	 * @throws SQLException
	 */
	public boolean coherence(Personne temoin) throws SQLException{

		int dateNaissTem_int = Integer.parseInt(temoin.getNaiss_date().substring(0,4));
		int dateMe_int = Integer.parseInt(this.me_date.substring(0, 4));

		boolean coherence = true;
		if (dateNaissTem_int < dateMe_int-18){

			coherence = true;
		}
		else{
			coherence = false;
		}

		return coherence;
	}


	// AJOUT TEMOIN A MARIAGE EGLISE

	/**
	 * Cette methode ajoute une personne temoin au mariage eglise
	 * @param temoin temoin
	 * @throws SQLException
	 */
	public void addTemoin(Personne temoin) throws SQLException {

		int id_me = this.loadfromBDD();
		int id_temoin = temoin.getId_p();		

		if (this.coherence(temoin)==true){
			try{
				Statement state = Connexion.getInstance().createStatement();
				ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM temoin_eglise WHERE id_me = '"+id_me+"'");

				result.next();
				int count = result.getInt("rowcount");
				result.close();

				result = state.executeQuery("SELECT * FROM temoin_eglise WHERE ((temoin_eglise.id_me = '"+id_me+"') AND (temoin_eglise.id_temoin = '"+id_temoin+"')) ");

				if (count<4){
					if (result.next()){
						System.out.println("Ce temoin est deja declare pour ce mariage");
					}
					else{
						try{
							PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO temoin_eglise(id_me, id_temoin) VALUES (?,?) ");
							preparedState.setInt(1, id_me);
							preparedState.setInt(2, id_temoin);

							preparedState.executeUpdate();
							preparedState.close();

							System.out.println(temoin.getPrenom()+" "+temoin.getNom()+" a ete ajoute(e) comme temoin du mariage eglise");
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
					}

					state.close();
					result.close();
				}

				else{
					System.out.println("Il y a deja quatre temoins pour ce mariage");
				}
			}

			catch (SQLException e) {
				e.printStackTrace();	
			}
		}
		else{
			System.out.println("Le temoin n'a pas l'age requis (18 ans)");
		}
	}


	// CHANGE MARIAGE EGLISE

	/**
	 * Cette methode change les donnees relatives au mariage eglise
	 * @param me_date date du mariage
	 * @param me_lieu lieu du mariage
	 * @param me_source source du mariage
	 * @throws SQLException
	 */
	public void changeMariageEglise(String me_date, String me_lieu, Source me_source) throws SQLException {
		
		int id_me = this.getId_me();
		if (this.me_source.ordinal() <= me_source.ordinal() ) {
			this.me_date = me_date;
			this.me_lieu = me_lieu;
			this.me_source = me_source;

			Statement state = Connexion.getInstance().createStatement();
			state.executeUpdate("UPDATE mariage_eglise SET me_date = '"+me_date+"', me_lieu = '"+me_lieu+"', me_source = '"+me_source+"' WHERE (mariage_eglise.id_me = '"+id_me+"')");
			state.close();
		}
	}	

}
