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


public class Mariage_mairie {

	private int id_mm;
	private String mm_date;
	private String mm_lieu;
	private Source mm_source;
	private Personne pers1;
	private Personne pers2;


	// GETTERS

	public int getId_mm() {
		return id_mm;
	}
	public String getMm_date() {
		return mm_date;
	}
	public String getMm_lieu() {
		return mm_lieu;
	}
	public Source getMm_source() {
		return mm_source;
	}
	public Personne getPers1() {
		return pers1;
	}
	public Personne getPers2() {
		return pers2;
	}


	// SETTERS

	public void setId_mm(int id_mm) {
		this.id_mm = id_mm;
	}
	public void setMm_date(String mm_date) {
		this.mm_date = mm_date;
	}
	public void setMm_lieu(String mm_lieu) {
		this.mm_lieu = mm_lieu;
	}
	public void setMm_source(Source mm_source) {
		this.mm_source = mm_source;
	}


	// CONSTRUCTEUR

	public Mariage_mairie(String mm_date, String mm_lieu, Source mm_source) {
		this.mm_date = mm_date;
		this.mm_lieu = mm_lieu;
		this.mm_source = mm_source;
	}


	// AJOUT MARIAGE MAIRIE

	/**
	 * Cette methode ajoute l'evenement mariage mairie a deux personnes
	 * @param pers1 marie1
	 * @param pers2 marie2
	 */

	public void addMaries(Personne pers1, Personne pers2) {

		int id_marie1 = pers1.getId_p();
		int id_marie2 = pers2.getId_p();
		String mm_date = this.getMm_date();
		String mm_lieu = this.getMm_lieu();
		Source mm_source = this.getMm_source();

		this.pers1 = pers1;
		this.pers2 = pers2;

		try{
			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM mariage_mairie WHERE ((mariage_mairie.id_marie1 = '"+id_marie1+"') AND (mariage_mairie.id_marie2 = '"+id_marie2+"')) OR ((mariage_mairie.id_marie1 = '"+id_marie2+"') AND (mariage_mairie.id_marie2 = '"+id_marie1+"'))");

			if (result.next()){
				System.out.println("Le mariage existe deja");
			}
			else{
				try{
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO mariage_mairie(mm_date, mm_lieu, mm_source, id_marie1, id_marie2) VALUES (?,?,?,?,?) ");
					preparedState.setString(1, mm_date);
					preparedState.setString(2, mm_lieu);
					preparedState.setString(3, mm_source.toString());
					preparedState.setInt(4, id_marie1);
					preparedState.setInt(5, id_marie2);

					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("Le mariage mairie entre "+pers1.getPrenom()+" "+pers1.getNom()+" et "+pers2.getPrenom()+" "+pers2.getNom()+" a ete ajoute");
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
	}


	// OBTENIR IDENTIFIANT MARIAGE MAIRIE

	/**
	 * Cette methode renvoie l'identifiant du mariage
	 * @return int id_mm identifiant du mariage
	 * @throws SQLException
	 */
	public int loadfromBDD() throws SQLException{

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM mariage_mairie");

		while (result.next()){
			String datemar = result.getString("mm_date");
			String lieumar = result.getString("mm_lieu");
			int id_p1 = result.getInt("id_marie1");
			if (datemar.equals(this.mm_date)
					&& lieumar.equals(this.mm_lieu)
					&& ((this.pers1 != null && id_p1 == this.pers1.getId_p()) || (this.pers2 != null && id_p1 == this.pers2.getId_p())))
			{
				id_mm = result.getInt("id_mm");
			}
		}
		return id_mm;
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
		int dateMm_int = Integer.parseInt(this.mm_date.substring(0, 4));

		boolean coherence = true;
		if (dateNaissTem_int < dateMm_int-18){
			coherence = true;
		}
		else{
			coherence = false;
		}

		return coherence;
	}


	// AJOUT TEMOIN A MARIAGE MAIRIE

	/**
	 * Cette methode ajoute un temoin a un mariage mairie
	 * @param temoin
	 * @throws SQLException
	 */
	public void addTemoin(Personne temoin) throws SQLException {

		int id_mm = this.loadfromBDD();
		int id_temoin = temoin.getId_p();


		if (this.coherence(temoin)==true){
			try{
				Statement state = Connexion.getInstance().createStatement();
				ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM temoin_mairie WHERE id_mm = '"+id_mm+"'");
				result.next();
				int count = result.getInt("rowcount");
				result.close();

				result = state.executeQuery("SELECT * FROM temoin_mairie WHERE ((temoin_mairie.id_mm = '"+id_mm+"') AND (temoin_mairie.id_temoin = '"+id_temoin+"')) ");

				if (count<2){
					if (result.next()){
						System.out.println("Ce temoin est deja declare pour ce mariage");
					}
					else{
						try{
							PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO temoin_mairie(id_mm, id_temoin) VALUES (?,?) ");
							preparedState.setInt(1, id_mm);
							preparedState.setInt(2, id_temoin);
							preparedState.executeUpdate();
							preparedState.close();

							System.out.println(temoin.getPrenom()+" "+temoin.getNom()+" a ete ajoute(e) comme temoin du mariage mairie");
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
					}

					state.close();
					result.close();
				}

				else{
					System.out.println("Il y a deja deux temoins pour ce mariage");
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


	// CHANGE MARIAGE MAIRIE

	/**
	 * Cette methode change les donnees relatives au mariage mairie
	 * @param mm_date date du mariage
	 * @param mm_lieu lieu du mariage
	 * @param mm_source source du mariage
	 * @throws SQLException
	 */
	public void changeMariageMairie(String mm_date, String mm_lieu, Source mm_source) throws SQLException {
		
		int id_mm = this.getId_mm();
		if (this.mm_source.ordinal() <= mm_source.ordinal() ) {
			this.mm_date= mm_date;
			this.mm_lieu = mm_lieu;
			this.mm_source = mm_source;

			Statement state = Connexion.getInstance().createStatement();
			state.executeUpdate("UPDATE mariage_mairie SET mm_date = '"+mm_date+"', mm_lieu = '"+mm_lieu+"', mm_source = '"+mm_source+"' WHERE (mariage_mairie.id_mm = '"+id_mm+"')");
			state.close();
		}
	}

}
