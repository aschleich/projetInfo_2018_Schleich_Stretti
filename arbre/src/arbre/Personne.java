/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingUtilities;


public class Personne {

	public static ArrayList<Personne> listpers = new ArrayList <Personne>();

	private int id_p;
	private String prenom;
	private String nom;
	private Sexe sexe;
	private String naiss_date;
	private String naiss_lieu;
	private String deces_date;
	private String deces_lieu;
	private Source naiss_source;
	private Source deces_source;



	// GETTERS

	public int getId_p() {
		return id_p;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getNom() {
		return nom;
	}
	public Sexe getSexe() {
		return sexe;
	}
	public String getNaiss_date() {
		return naiss_date;
	}
	public String getNaiss_lieu() {
		return naiss_lieu;
	}
	public String getDeces_date() {
		return deces_date;
	}
	public String getDeces_lieu() {
		return deces_lieu;
	}
	public Source getNaiss_source() {
		return naiss_source;
	}
	public Source getDeces_source() {
		return deces_source;
	}

	public String toString() {
		return (this.id_p +" "+ this.prenom +" "+ this.nom+" " + this.sexe);
	}


	// SETTERS

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}
	public void setNaiss_date(String naiss_date) {
		this.naiss_date = naiss_date;
	}
	public void setNaiss_lieu(String naiss_lieu) {
		this.naiss_lieu = naiss_lieu;
	}
	public void setDeces_date(String deces_date) {
		this.deces_date = deces_date;
	}
	public void setDeces_lieu(String deces_lieu) {
		this.deces_lieu = deces_lieu;
	}
	public void setNaiss_source(Source naiss_source) {
		this.naiss_source = naiss_source;
	}
	public void setDeces_source(Source deces_source) {
		this.deces_source = deces_source;
	}


	// CONSTRUCTEURS

	public Personne(int id_p, String prenom, String nom, Sexe sexe, String naiss_date, String naiss_lieu,
			String deces_date, String deces_lieu, Source naiss_source, Source deces_source) {
		this.id_p = id_p;
		this.prenom = prenom;
		this.nom = nom;
		this.sexe = sexe;
		this.naiss_date = naiss_date;
		this.naiss_lieu = naiss_lieu;
		this.deces_date = deces_date;
		this.deces_lieu = deces_lieu;
		this.naiss_source = naiss_source;
		this.deces_source = deces_source;
	}

	public Personne(String prenom, String nom) {
		this.prenom = prenom;
		this.nom = nom;
	}

	public Personne(int id_p, String prenom, String nom) {
		this.id_p = id_p;
		this.prenom = prenom;
		this.nom = nom;
	}

	public Personne(int id_p, String prenom, String nom, Sexe sexe) {
		this.id_p = id_p;
		this.prenom = prenom;
		this.nom = nom;
		this.sexe = sexe;
	}


	// CHARGER PERSONNES DE LA BDD

	/**
	 * Cette methode charge les personnes de la BDD dans une liste et de les afficher dans la console 
	 */
	public static void appelPers() {
		
		try{
			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM personne");
			ResultSetMetaData resultMeta = result.getMetaData();

			for (int i=1 ; i<=resultMeta.getColumnCount();i++){
				String value = String.format("%8s",resultMeta.getColumnName(i).toUpperCase());
				System.out.print("\t" + value +"\t |");
			}
			System.out.println("\n**************************************************************************************************************************************************************************************************************************************************");

			while (result.next()){
				for (int i =1; i<= resultMeta.getColumnCount();i++){
					if (result.getObject(i)!=null){
						String value = String.format("%8s", result.getObject(i).toString());
						System.out.format("\t" + value +"\t |");
					}
					else {
						String value = String.format("%8s", " ");
						System.out.format("\t" + value +"\t |");
					}		
				}
				System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

				Integer id_p = result.getInt("id_p");
				String prenom = result.getString("prenom");
				String nom = result.getString("nom");
				String sexeStr = result.getString("sexe");
				String naiss_date = result.getString("naiss_date");
				String naiss_lieu = result.getString("naiss_lieu");
				String deces_date = result.getString("deces_date");
				String deces_lieu = result.getString("deces_lieu");
				String naiss_sourceStr= result.getString("naiss_source");
				String deces_sourceStr = result.getString("deces_source");

				if ((naiss_date == null) && (null==naiss_date)){
					naiss_date = "";
				}
				if ((naiss_lieu == null)&& (null==naiss_lieu)) {
					naiss_lieu = "";
				}
				if ((deces_date == null) && (null==deces_date)) {
					deces_date = "";
				}
				if ((deces_lieu == null) && (null==deces_lieu)) {
					deces_lieu = "";
				}
				if ((naiss_sourceStr == null) && (null==naiss_sourceStr)) {
					naiss_sourceStr = "";
				}
				if ((deces_sourceStr == null)&& (null==deces_sourceStr)) {
					deces_sourceStr = "";
				}

				Sexe sexe = Sexe.valueOf(sexeStr);
				Source naiss_source = Source.valueOf(naiss_sourceStr);
				Source deces_source = Source.valueOf(deces_sourceStr);

				Personne personne = new Personne (id_p, prenom, nom, sexe, naiss_date, naiss_lieu, deces_date, deces_lieu, naiss_source, deces_source);
				listpers.add(personne);
			}
			result.close();
			state.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cette methode cree une instance de Personne a partir de la BDD
	 * @param id de la personne de la BDD a afficher dans la console avec appelPers()
	 * @return Personne personne
	 */
	public static Personne choixPersonne(int id ) {
		
		return(listpers.get(id-1));
	}


	// ************************* AJOUTER PERSONNE *******************************************************

	// AJOUT D'UNE PERSONNE

	/**
	 * Cette methode ajoute une personne dans la BDD
	 */
	public static void addPersonne() {

		Scanner scan = new Scanner(System.in);
		System.out.println("Prenom ? ");
		String  prenom= scan.nextLine();
		System.out.println("Nom ? ");
		String  nom= scan.nextLine();
		System.out.println("Sexe ? ");
		String sexe = scan.nextLine();

		try{
			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM personne ");

			String ok = "Oui";
			while (result.next()) {
				if (prenom.equals(result.getString("prenom")) && (nom.equals(result.getString("nom")))){
					System.out.println("La personne existe deja");
					ok = "Non";
				}
			} 
			if (ok == "Oui"){								
				try{
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO personne(prenom, nom, sexe, naiss_source, deces_source) VALUES (?,?,?,?,?) ");
					preparedState.setString(1, prenom);
					preparedState.setString(2, nom);
					preparedState.setString(3, sexe);
					preparedState.setString(4, "na");
					preparedState.setString(5, "na");
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("La personne a ete ajoutee");
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


	// ************************* AJOUTER ATTRIBUTS *******************************************************

	// AJOUT NAISSANCE

	/**
	 * Cette methode ajoute les donnees relatives a la naissance d'une personne
	 * @param naiss_date nouvelle date
	 * @param naiss_lieu nouveau lieu
	 * @param naiss_source nouvelle source
	 * @throws SQLException
	 */
	public void addNaissance(String naiss_date, String naiss_lieu, Source naiss_source) throws SQLException {
		
		int id_p = this.getId_p();

		if (this.naiss_source.ordinal() <= naiss_source.ordinal() ) {
			this.naiss_date = naiss_date;
			this.naiss_lieu = naiss_lieu;
			this.naiss_source = naiss_source;

			try{
				Statement state = Connexion.getInstance().createStatement();
				ResultSet result = state.executeQuery("SELECT naiss_date, naiss_lieu, naiss_source FROM personne WHERE (personne.id_p = '"+id_p+"') ");

				if (result.next()){
					System.out.println("Les attributs existent deja");
				}
				else{
					try{
						PreparedStatement preparedState = Connexion.getInstance().prepareStatement("UPDATE personne(naiss_date, naiss_lieu, naiss_source) VALUES (?,?,?) WHERE (personne.id_p = '"+id_p+"')");
						preparedState.setString(1, naiss_date);
						preparedState.setString(2, naiss_lieu);
						preparedState.setObject(3, naiss_source);
						preparedState.executeUpdate();
						preparedState.close();
						System.out.println("Les attributs de naissance ont ete ajoutes");
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
	}


	// COHERENCE AGE DECES

	/**
	 * Cette methode controle l'age de deces de la personne
	 * @param deces_date date de deces de la personne
	 * @return boolean coherence
	 */
	public boolean coherenceDeces(String deces_date){

		int dateNaiss_int = Integer.parseInt(this.getNaiss_date().substring(0,4));
		int dateDeces_int = Integer.parseInt(this.getDeces_date().substring(0, 4));

		boolean coherence = true;
		if (dateDeces_int < dateNaiss_int + 100){
			coherence = true;
		}
		else{
			coherence = false;
		}
		return coherence;
	}

	// AJOUT DECES
	/**
	 * Cette methode ajoute les donnees relatives au deces d'une personne
	 * @param deces_date nouvelle date
	 * @param deces_lieu nouveau lieu
	 * @param deces_source nouvelle source
	 * @throws SQLException 
	 */
	public void addDeces(String deces_date, String deces_lieu, Source deces_source) throws SQLException {
		
		int id_p = this.getId_p();

		if (this.deces_source.ordinal() <= deces_source.ordinal() ) {
			this.deces_date = deces_date;
			this.deces_lieu = deces_lieu;
			this.deces_source = deces_source;

			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT deces_date, deces_lieu, deces_source FROM personne WHERE (personne.id_p = '"+id_p+"') ");

			if (coherenceDeces(deces_date) == true){
				try{
					if (result.next()){
						System.out.println("Les attributs existent deja");
					}
					else{
						try{
							PreparedStatement preparedState = Connexion.getInstance().prepareStatement("UPDATE personne(deces_date, deces_lieu, deces_source) VALUES (?,?,?) WHERE (personne.id_p = '"+id_p+"')");
							preparedState.setString(1, deces_date);
							preparedState.setString(2, deces_lieu);
							preparedState.setObject(3, deces_source);
							preparedState.executeUpdate();
							preparedState.close();
							System.out.println("Les attributs de deces ont ete ajoutes");
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
			else{
				Scanner scan = new Scanner(System.in);
				System.out.println("La personne est decedee a plus de 100 ans, etes-vous sur ? ");
				String reponse = scan.nextLine();
				if (reponse.equals("oui")){
					state.executeUpdate("UPDATE personne SET deces_lieu='"+deces_lieu+"', deces_date = '"+deces_date+"' , deces_source = '"+deces_source+"' WHERE (personne.id_p = '"+id_p+"') ");
					state.close();
					System.out.println("Les attributs de deces ont ete ajoutes");
				}
				else{
					System.out.println("Les attributs de deces n'ont pas ete ajoutes");
				}
				scan.close();
			}
		}

	}


	// ************************* AJOUTER RELATIONS *******************************************************

	// AJOUT RELATION FRERE SOEUR

	/**
	 * Cette methode ajoute une relation frere/soeur entre deux personnes
	 * @param pers2 le frere ou la soeur a ajouter
	 */
	public void addFrere(Personne pers2){

		int id_pers1= this.getId_p();
		int id_pers2 = pers2.getId_p();

		try{
			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM frere_soeur WHERE (((frere_soeur.id_pers1 = '"+id_pers1+"') AND (frere_soeur.id_pers2 = '"+id_pers2+"')) OR ((frere_soeur.id_pers1 = '"+id_pers2+"') AND (frere_soeur.id_pers2 = '"+id_pers1+"'))) ");
			if (result.next()){
				System.out.println("La relation existe deja");
			}
			else{
				try{
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO frere_soeur(id_pers1, id_pers2) VALUES (?,?) ");
					preparedState.setInt(1, id_pers1);
					preparedState.setInt(2, id_pers2);
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println(pers2.prenom+" "+pers2.nom+" a ete ajoute(e) comme frere(soeur) de "+this.prenom+" "+this.nom);
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


	// COHERENCE ENFANT/PARENT

	/**
	 * Cette methode verifie la coherence de l'age entre un enfant et son parent
	 * @param pers l'enfant a ajouter
	 */
	public boolean coherence(Personne pers) throws SQLException{

		int dateNaissEnf_int = Integer.parseInt(pers.getNaiss_date().substring(0,4));
		int dateNaissPar_int = Integer.parseInt(this.getNaiss_date().substring(0, 4));

		boolean coherence = true;
		if (dateNaissEnf_int > dateNaissPar_int+18){
			if (dateNaissEnf_int<dateNaissPar_int+50){
				coherence = true;
			}
			else{
				coherence = false;
			}
		}
		return coherence;
	}


	//AJOUT AUTOMATIQUE FRERE/SOEUR SI MEME PARENT

	/**
	 * Cette methode ajoute automatiquement la relation frere/soeur entre deux personnes ayant un parent commun
	 * @param parent le parent en commun
	 * @throws SQLException
	 */
	public void ajoutFrere(Personne parent) throws SQLException {
		
		int id_enf=this.id_p;

		for (Personne enf : parent.trouveEnfant()) {
			int id_enf2 = enf.getId_p();
			if (id_enf2 != id_enf){
				Statement state = Connexion.getInstance().createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM frere_soeur WHERE (    ((frere_soeur.id_pers1 = '"+id_enf+"') AND (frere_soeur.id_pers2 = '"+id_enf2+"'))      OR  ((frere_soeur.id_pers1 = '"+id_enf2+"') AND (frere_soeur.id_pers2 = '"+id_enf+"'))        )");
				if (!result.next()){
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO frere_soeur (id_pers1, id_pers2) VALUES (?,?) ");
					preparedState.setInt(1, id_enf);
					preparedState.setInt(2, id_enf2);
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println(this.prenom+" "+this.nom+" et "+enf.prenom+" "+enf.nom+" sont donc freres et soeurs car ils ont un parent en commun ("+parent.prenom+" "+parent.nom+")");
				}

			}
		}
	}


	// AJOUT RELATION PARENT

	/**
	 * Cette methode ajoute un parent a une personne
	 * @param parent
	 * @throws SQLException 
	 */
	public void addParent(Personne parent) throws SQLException{

		int id_enfant= this.getId_p();
		int id_parent = parent.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM enfant_parent WHERE (enfant_parent.id_enfant = '"+id_enfant+"')");

		int count = result.getInt("rowcount");

		if (count < 2){
			if (this.coherence(parent)==true){
				try{
					Statement state2 = Connexion.getInstance().createStatement();
					ResultSet result2 = state2.executeQuery("SELECT * FROM enfant_parent WHERE ((enfant_parent.id_enfant = '"+id_enfant+"') AND (enfant_parent.id_parent = '"+id_parent+"')) ");
					if (result2.next()){
						System.out.println("La relation existe deja");
					}
					else{
						try{
							PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO enfant_parent(id_enfant, id_parent) VALUES (?,?) ");
							preparedState.setInt(1, id_enfant);
							preparedState.setInt(2, id_parent);
							preparedState.executeUpdate();
							preparedState.close();
							System.out.println(parent.prenom+" "+parent.nom+" a ete ajoute(e) comme parent de "+this.prenom+" "+this.nom);
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
					}
					state2.close();
					result2.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				Scanner scan = new Scanner(System.in);
				System.out.println("Il y a plus de 50 ans d'ecart, etes-vous sur ? ");
				String reponse = scan.nextLine();
				if (reponse.equals("oui")){
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO enfant_parent(id_enfant, id_parent) VALUES (?,?) ");
					preparedState.setInt(1, id_enfant);
					preparedState.setInt(2, id_parent);
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("La relation a ete ajoutee");
				}
				else{
					System.out.println("La relation n'a pas ete ajoutee");
				}
				scan.close();
			}
		}
		else{
			System.out.println(this.prenom+" "+this.nom+" a deja deux parents");
		}

		this.ajoutFrere(parent);

	}


	//AJOUT RELATION ENFANT

	/**
	 * Cette methode ajoute un enfant a une personne
	 * @param enfant
	 * @throws SQLException 
	 */
	public void addEnfant(Personne enfant) throws SQLException{
		
		enfant.addParent(this);
	}


	// COHERENCE CONJOINTS

	/**
	 * Cette methode verifie la coherence de l'age entre deux conjoints
	 * @param pers
	 * @return boolean coherence
	 * @throws SQLException
	 */
	public boolean coherenceConjoint(Personne pers) throws SQLException{

		int dateNaissC1_int = Integer.parseInt(this.getNaiss_date().substring(0,4));
		int dateNaissC2_int = Integer.parseInt(pers.getNaiss_date().substring(0, 4));

		boolean coherence = true;
		if (dateNaissC2_int < dateNaissC1_int+30){
			if (dateNaissC2_int > dateNaissC1_int-30){

				coherence = true;
			}
			else{
				coherence = false;
			}
		}
		return coherence;
	}


	// AJOUT RELATION CONJOINT

	/**
	 * Cette methode ajoute une relation conjoint entre deux personnes
	 * @param pers2 le conjoint a ajouter
	 * @throws SQLException 
	 */
	public void addConjoint(Personne pers2) throws SQLException{

		int id_conjoint1= this.getId_p();
		int id_conjoint2 = pers2.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM conjoint WHERE ((conjoint.id_conjoint1 = '"+id_conjoint1+"') AND (conjoint.id_conjoint2 = '"+id_conjoint2+"')) OR ((conjoint.id_conjoint1 = '"+id_conjoint2+"') AND (conjoint.id_conjoint2 = '"+id_conjoint1+"'))");

		if (this.coherenceConjoint(pers2)==true){
			try{
				if (result.next()){
					System.out.println("Le conjoint declare est deja conjoint de la personne");
				}
				else{
					try{
						PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO conjoint(id_conjoint1, id_conjoint2) VALUES (?,?) ");
						preparedState.setInt(1, id_conjoint1);
						preparedState.setInt(2, id_conjoint2);
						preparedState.executeUpdate();
						preparedState.close();
						System.out.println(pers2.prenom+" "+pers2.nom+" a ete ajoute(e) comme conjoint(e) de "+this.prenom+" "+this.nom);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				//}

				/*state2.close();
				result2.close();*/
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			if (result.next()){
				System.out.println("Le conjoint declare est deja conjoint de la personne");

			}
			else{
				Scanner scan = new Scanner(System.in);
				System.out.println("Il y a plus de 30 ans d'ecart, etes-vous sur ? ");
				String reponse = scan.nextLine();
				if (reponse.equals("oui")){
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO conjoint(id_conjoint1, id_conjoint2) VALUES (?,?) ");
					preparedState.setInt(1, id_conjoint1);
					preparedState.setInt(2, id_conjoint2);
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("La relation a ete ajoutee");
				}
				else{
					System.out.println("La relation n'a pas ete ajoutee");
				}
				scan.close();

			}
		}
		state.close();
		result.close();
	}


	// AJOUT JOB

	/**
	 * Cette methode ajoute un metier a une personne
	 * @param smetier sous forme String smetier
	 * @throws SQLException
	 */
	public void addJob(String smetier) throws SQLException {

		Metier metier = Metier.loadfromBDD(smetier);
		int id_m = metier.getId_m();

		try{
			Statement state2 = Connexion.getInstance().createStatement();
			ResultSet result2 = state2.executeQuery("SELECT * FROM job WHERE (job.id_m = '"+id_m+"'  AND job.id_p = '"+id_p+"') ");

			if (result2.next()){
				System.out.println("Ce metier est deja declare pour "+this.prenom+" "+this.nom);
			}
			else {
				try{
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO job(id_m, id_p) VALUES (?,?) ");
					preparedState.setInt(1, id_m);
					preparedState.setInt(2, id_p);
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("Le metier "+smetier+" a ete ajoute pour "+this.prenom+" "+this.nom);
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			state2.close();
			result2.close();		
		}
		catch (SQLException e) {
			e.printStackTrace();	
		}
	}


	// AJOUT BAPTEME

	/**
	 * Cette methode ajoute l'evenement bapteme a une personne
	 * @param bapteme cree dans la classe Bapteme
	 * @param parrain
	 */
	public void addBapteme(Bapteme bapteme, Personne parrain) {

		int id_p = this.getId_p();
		String bapt_date = bapteme.getBapt_date();
		String bapt_lieu = bapteme.getBapt_lieu();
		Source bapt_source = bapteme.getBapt_source();
		int id_parrain = parrain.getId_p();

		int dateBapt_int = Integer.parseInt(bapteme.getBapt_date().substring(0,4));
		int dateNaiss_int = Integer.parseInt(this.getNaiss_date().substring(0,4));

		if (dateBapt_int > dateNaiss_int){

			try{
				Statement state = Connexion.getInstance().createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM bapteme WHERE ((bapteme.id_p = '"+id_p+"') )");
				if (result.next()){
					System.out.println("Cette personne est deja baptisee");
				}
				else{
					try{
						PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO bapteme(id_p,bapt_date, bapt_lieu, bapt_source, id_parrain) VALUES (?,?,?,?,?) ");
						preparedState.setInt(1, id_p);
						preparedState.setString(2, bapt_date);
						preparedState.setString(3, bapt_lieu);
						preparedState.setString(4, bapt_source.toString());
						preparedState.setInt(5, id_parrain);
						preparedState.executeUpdate();
						preparedState.close();
						System.out.println("Le bapteme et le parrain/marraine "+parrain.prenom+" "+parrain.nom+" ont ete ajoutes pour "+this.prenom+" "+this.nom);
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
		else{
			System.out.println("La date du bapteme est anterieure a la date de naissance");
		}
	}	


	// AJOUT SEPULTURE

	/**
	 * Cette methode ajoute l'evenement sepulture a une personne
	 * @param sepulture creee dans la classe Sepulture
	 */
	public void addSepulture(Sepulture sepulture) {

		int id_p = this.getId_p();
		String sep_date = sepulture.getSep_date();
		String sep_lieu = sepulture.getSep_lieu();
		Source sep_source = sepulture.getSep_source();

		try{
			Statement state = Connexion.getInstance().createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM sepulture WHERE ((sepulture.id_p = '"+id_p+"') )");
			if (result.next()){
				System.out.println("Cette personne a deja une sepulture");
			}
			else{
				try{
					PreparedStatement preparedState = Connexion.getInstance().prepareStatement("INSERT INTO sepulture(id_p,sep_date, sep_lieu, sep_source) VALUES (?,?,?,?) ");
					preparedState.setInt(1, id_p);
					preparedState.setString(2, sep_date);
					preparedState.setString(3, sep_lieu);
					preparedState.setString(4, sep_source.toString());
					preparedState.executeUpdate();
					preparedState.close();
					System.out.println("La sepulture a ete ajoutee pour "+this.prenom+" "+this.nom);
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



	// ************************* CHANGER ATTRIBUTS *******************************************************

	// CHANGER PRENOM
	/**
	 * Cette methode change le prenom d'une personne
	 * @param prenom nouveau prenom
	 * @throws SQLException 
	 */
	public void changePrenom(String prenom) throws SQLException {
		
		int id_p = this.getId_p();
		this.prenom = prenom;

		Statement state = Connexion.getInstance().createStatement();
		state.executeUpdate("UPDATE personne SET prenom='"+prenom+"' WHERE (personne.id_p = '"+id_p+"') ");
		state.close();
		System.out.println("Le prenom a ete modifie");
	}


	// CHANGER NOM

	/**
	 * Cette methode change le nom d'une personne
	 * @param nom nouveau nom
	 * @throws SQLException 		
	 */
	public void changeNom(String nom) throws SQLException {
		
		int id_p = this.getId_p();
		this.nom = nom;

		Statement state = Connexion.getInstance().createStatement();
		state.executeUpdate("UPDATE personne SET nom='"+nom+"' WHERE (personne.id_p = '"+id_p+"') ");
		state.close();
		System.out.println("Le nom a ete modifie");
	}


	// CHANGER NAISSANCE

	/**
	 * Cette methode change les donnees relatives a la naissance d'une personne
	 * @param naiss_date nouvelle date
	 * @param naiss_lieu nouveau lieu
	 * @param naiss_source nouvelle source
	 * @throws SQLException 
	 */
	public void changeNaissance(String naiss_date, String naiss_lieu, Source naiss_source) throws SQLException {
		
		int id_p = this.getId_p();

		if (this.naiss_source.ordinal() <= naiss_source.ordinal() ) {
			this.naiss_date = naiss_date;
			this.naiss_lieu = naiss_lieu;
			this.naiss_source = naiss_source;

			Statement state = Connexion.getInstance().createStatement();
			state.executeUpdate("UPDATE personne SET naiss_lieu='"+naiss_lieu+"', naiss_date = '"+naiss_date+"' , naiss_source = '"+naiss_source+"' WHERE (personne.id_p = '"+id_p+"') ");
			state.close();
			System.out.println("Les attributs de naissance ont ete modifies");
		}
		else{
			System.out.println("La source est moins fiable que la source initiale");
		}
	}


	// CHANGER DECES

	/**
	 * Cette methode change les donnees relatives au deces d'une personne
	 * @param deces_date nouvelle date
	 * @param deces_lieu nouveau lieu
	 * @param deces_source nouvelle source
	 * @throws SQLException 
	 */
	public void changeDeces(String deces_date, String deces_lieu, Source deces_source) throws SQLException {
		
		int id_p = this.getId_p();

		if (this.deces_source.ordinal() <= deces_source.ordinal() ) {
			this.deces_date = deces_date;
			this.deces_lieu = deces_lieu;
			this.deces_source = deces_source;

			Statement state = Connexion.getInstance().createStatement();
			state.executeUpdate("UPDATE personne SET deces_lieu='"+deces_lieu+"', deces_date = '"+deces_date+"' , deces_source = '"+deces_source+"' WHERE (personne.id_p = '"+id_p+"') ");
			state.close();
			System.out.println("Les attributs de deces ont ete modifies");
		}
		else{
			System.out.println("La source est moins fiable que la source initiale");
		}
	}



	// ************************* CHANGER RELATIONS *******************************************************

	// CHANGER CONJOINT

	/**
	 * Cette methode change le conjoint d'une personne
	 * @param pers2 nouveau conjoint
	 * @throws SQLException
	 */
	public void changeConjoint(Personne pers2) throws SQLException {
		
		int id_conjoint1 = this.getId_p();
		int id_conjoint2 = pers2.id_p;

		Statement state = Connexion.getInstance().createStatement();

		if (this.coherenceConjoint(pers2)==true){
			state.executeUpdate("UPDATE conjoint SET id_conjoint1 = '"+id_conjoint1+"', id_conjoint2 = '"+id_conjoint2+"' WHERE (conjoint.id_conjoint1 = '"+id_conjoint1+"' OR id_conjoint2 = '"+id_conjoint1+"')");

		}
		else{
			Scanner scan = new Scanner(System.in);
			System.out.println("Il y a plus de 30 ans d'ecart, etes-vous sur ? ");
			String reponse = scan.nextLine();
			if (reponse.equals("oui")){
				state.executeUpdate("UPDATE conjoint SET id_conjoint1 = '"+id_conjoint1+"', id_conjoint2 = '"+id_conjoint2+"' WHERE (conjoint.id_conjoint1 = '"+id_conjoint1+"' OR id_conjoint2 = '"+id_conjoint1+"')");

				System.out.println("La relation a ete ajoutee");
			}
			else{
				System.out.println("La relation n'a pas ete ajoutee");
			}

			scan.close();
		}
		state.close();
	}


	// CHANGER BAPTEME

	/**
	 * Cette methode change les donnees relatives au bapteme ainsi que le parrain d'une personne en verifiant la source
	 * @param bapteme
	 * @param parrain
	 * @throws SQLException
	 */
	public void changeBapteme(Bapteme bapteme, Personne parrain) throws SQLException {
		
		int id_p = this.getId_p();
		String bapt_date = bapteme.getBapt_date();
		String bapt_lieu = bapteme.getBapt_lieu();
		Source bapt_source = bapteme.getBapt_source();
		int id_parrain = parrain.getId_p();

		int dateBapt_int = Integer.parseInt(bapteme.getBapt_date().substring(0,4));
		int dateNaiss_int = Integer.parseInt(this.getNaiss_date().substring(0,4));

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM bapteme WHERE (bapteme.id_p = '"+id_p+"')");
		result.next();
		int count = result.getInt("rowcount");

		if (count != 0){
			if (dateNaiss_int < dateBapt_int){
				if (bapteme.getBapt_source().ordinal() <= bapt_source.ordinal() ) {
					bapt_date = bapteme.getBapt_date();
					bapt_lieu = bapteme.getBapt_lieu();
					bapt_source = bapteme.getBapt_source();

					Statement state2 = Connexion.getInstance().createStatement();
					state2.executeUpdate("UPDATE bapteme SET bapt_date = '"+bapt_date+"', bapt_lieu = '"+bapt_lieu+"', bapt_source = '"+bapt_source+"', id_parrain = '"+id_parrain+"' WHERE (bapteme.id_p = '"+id_p+"')");
					state2.close();
					System.out.println("Le bapteme de "+this.prenom+" "+this.nom+" a ete modifie");

					state2.close();
				}

				else{
					System.out.println("La source est moins fiable que la source initiale");
				}
			}
			else{
				System.out.println("La date de bapteme est anterieure a la date de naissance");
			}
		}
		else{
			System.out.println(this.prenom+" "+this.nom+" n'a pas de bapteme, modification impossible");
		}
		state.close();
		result.close();
	}


	// CHANGER SEPULTURE

	/**
	 * Cette methode change les donnees relatives a la sepulture d'une personne en verifiant la source
	 * @param sepulture
	 * @throws SQLException
	 */
	public void changeSepulture(Sepulture sepulture) throws SQLException {
		
		int id_p = this.getId_p();
		String sep_date = sepulture.getSep_date();
		String sep_lieu = sepulture.getSep_lieu();
		Source sep_source = sepulture.getSep_source();

		int dateSep_int = Integer.parseInt(sepulture.getSep_date().substring(0,4));
		int dateDeces_int = Integer.parseInt(this.getDeces_date().substring(0,4));

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM sepulture WHERE (sepulture.id_p = '"+id_p+"')");
		result.next();
		int count = result.getInt("rowcount");

		if (count != 0){
			if (dateDeces_int < dateSep_int){

				if (sepulture.getSep_source().ordinal() <= sep_source.ordinal() ) {
					sep_date = sepulture.getSep_date();
					sep_lieu = sepulture.getSep_lieu();
					sep_source = sepulture.getSep_source();

					Statement state2 = Connexion.getInstance().createStatement();
					state2.executeUpdate("UPDATE sepulture SET sep_date = '"+sep_date+"', sep_lieu = '"+sep_lieu+"', sep_source = '"+sep_source+"' WHERE (sepulture.id_p = '"+id_p+"')");
					state2.close();
					System.out.println("La sepulture de "+this.prenom+" "+this.nom+" a ete modifiee");
					state2.close();
				}
				else{
					System.out.println("La source est moins fiable que la source initiale");
				}
			}
			else{
				System.out.println("La date de sepulture est anterieure a la date de deces");
			}
		}
		else{
			System.out.println(this.prenom+" "+this.nom+" n'a pas de sepulture, modification impossible");
		}
		state.close();
		result.close();
	}


	// ************************* SUPPRIMER RELATIONS *******************************************************

	// SUPPRIMER CONJOINT

	/**
	 * Cette methode supprime le conjoint d'une personne
	 * @throws SQLException
	 */
	public void suppConjoint() throws SQLException {
		
		int id_conjoint1 = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		state.executeUpdate("DELETE FROM conjoint WHERE (conjoint.id_conjoint1 = '"+id_conjoint1+"' OR conjoint.id_conjoint2 = '"+id_conjoint1+"')");
		state.close();
		System.out.println("Le conjoint de "+this.prenom+" "+this.nom+" a ete supprime");
	}


	// ************************* OBTENIR RELATIONS *******************************************************

	// OBTENIR ENFANTS

	/**
	 * Cette methode renvoie les enfants d'une personne
	 * @return String prenom et nom des enfants
	 * @throws SQLException
	 */
	public String getEnfant() throws SQLException{
		
		int id_p = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM enfant_parent, personne WHERE personne.id_p = enfant_parent.id_enfant AND enfant_parent.id_parent = '"+id_p+"'");

		int count = result.getInt("rowcount");
		String resultat = "";

		if (count > 0){

			Statement state2 = Connexion.getInstance().createStatement();
			ResultSet result2 = state2.executeQuery("SELECT prenom, nom FROM enfant_parent, personne WHERE personne.id_p = enfant_parent.id_enfant AND id_parent = '"+id_p+"'");

			while (result2.next()){
				resultat += " "+result2.getString("prenom") +" "+ result2.getString("nom")+" ;";
			}
			resultat = "Les enfants de "+this.prenom+" "+this.nom+" sont :"+resultat;

			state2.close();
			result2.close();
		}

		else{
			resultat += this.prenom+" "+this.nom+" n'a pas d'enfant";
		}

		result.close();
		state.close();

		return resultat;
	}


	// OBTENIR PARENT

	/**
	 * Cette methode renvoie les parents d'une personne
	 * @return String prenom et nom des parents
	 * @throws SQLException
	 */
	public String getParent() throws SQLException{
		
		int id_p = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT COUNT(*) AS rowcount FROM enfant_parent, personne WHERE personne.id_p = enfant_parent.id_parent AND id_enfant = '"+id_p+"'");

		String resultat = "";
		int count = result.getInt("rowcount");

		if (count > 0){
			Statement state2 = Connexion.getInstance().createStatement();
			ResultSet result2 = state2.executeQuery("SELECT prenom, nom FROM enfant_parent, personne WHERE personne.id_p = enfant_parent.id_parent AND id_enfant = '"+id_p+"'");
			
			while (result2.next()){
				resultat += " "+result2.getString("prenom") +" "+ result2.getString("nom") +" ;";
			}
			resultat = "Les parents de "+this.prenom+" "+this.nom+" sont :"+resultat;
			state2.close();
			result2.close();
		}
		else{
			resultat = "Les parents de "+this.prenom+" "+this.nom+" ne sont pas renseignes";
		}
		
		result.close();
		state.close();
		return resultat;
	}


	// OBTENIR CONJOINT

	/**
	 * Cette methode renvoie le conjoint d'une personne
	 * @return String prenom, nom du conjoint
	 * @throws SQLException
	 */
	public String getConjoint() throws SQLException{
		
		int id_p = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT prenom, nom, COUNT(*) AS rowcount FROM conjoint, personne WHERE ((personne.id_p = id_conjoint2 AND id_conjoint1 = '"+id_p+"') OR (personne.id_p = id_conjoint1 AND id_conjoint2 = '"+id_p+"'))");

		int count = result.getInt("rowcount");
		String resultat = "";

		if (count > 0){
			resultat += result.getString("prenom")+" "+result.getString("nom");
			resultat = ("Le conjoint de "+ this.getPrenom()+" "+ this.getNom() +" est : "+ resultat);
		}
		if (count == 0){
			resultat += this.prenom + " " +this.nom +" n'a pas de conjoint";
		}
		return resultat;
	}

	// OBTENIR METIER
	/**
	 * Cette methode renvoie le(s) metier(s) d'une personne
	 * @return String nom du metier
	 * @throws SQLException
	 */
	public String getMetier() throws SQLException{
		
		int id_p = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT nom_m, COUNT(*) AS rowcount FROM metier, job, personne WHERE job.id_m = metier.id_m AND personne.id_p = job.id_p AND job.id_p = '"+id_p+"'");

		int count = result.getInt("rowcount");
		String resultat ="";

		if (count > 0){
			resultat+= " "+(result.getString("nom_m"));
			resultat = "Le(s) metier(s) de "+this.getPrenom()+" "+this.getNom()+": "+resultat;
		}
		if (count == 0){
			resultat += this.prenom + " " +this.nom +" n'a pas de metier";
		}

		return resultat;
	}


	// OBTENIR MARIAGE MAIRIE

	/**
	 * Cette methode renvoie les donnees relatives au(x) mariage(s) mairie d'une personne
	 * @return String date et lieu du(des) mariage(s) mairie de la personne		 
	 * @throws SQLException
	 */
	public String getMariageMairie() throws SQLException {
		
		int id_p = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT mm_date, mm_lieu, prenom, nom FROM mariage_mairie, personne, temoin_mairie WHERE ((id_marie1 = '"+id_p+"' OR id_marie2 = '"+id_p+"')) AND (temoin_mairie.id_mm = mariage_mairie.id_mm) AND (personne.id_p = id_temoin) GROUP BY mm_date");
		Statement state3 = Connexion.getInstance().createStatement();
		ResultSet result3 = state3.executeQuery("SELECT COUNT(*) AS rowcount FROM mariage_mairie, personne WHERE (id_marie1 = '"+id_p+"' OR id_marie2 = '"+id_p+"')");

		String resultat = "";
		String temoin = "";
		int count = result3.getInt("rowcount");

		if (count != 0) {
			Statement state2 = Connexion.getInstance().createStatement();
			ResultSet result2 = state2.executeQuery("SELECT prenom, nom FROM personne, mariage_mairie, temoin_mairie WHERE ((id_marie1 = '"+id_p+"' OR id_marie2 = '"+id_p+"') AND (temoin_mairie.id_mm = mariage_mairie.id_mm) AND (personne.id_p = id_temoin))");

			while (result.next()){
				resultat += result.getString("mm_date") +" "+ result.getString("mm_lieu") +", ";
				while (result2.next()) {
					temoin += result2.getString("prenom") +" "+ result2.getString("nom")+"; ";
				}
			}
			resultat = "Mariage(s) mairie de "+this.getPrenom()+" "+this.getNom()+" : "+resultat+" Temoin(s) : "+temoin;
			state2.close();
			result2.close();
		}
		else {
			resultat = this.getPrenom()+" "+this.getNom()+" n'a pas de mariage mairie";
		}
		result.close();
		result3.close();
		state3.close();
		state.close();

		return resultat;
	}


	// OBTENIR MARIAGE EGLISE

	/**
	 * Cette methode renvoie les donnees relatives au(x) mariage(s) eglise d'une personne
	 * @return String date et lieu du(des) mariage(s) eglise , String nom et prenom du(des) temoin(s)
	 * @throws SQLException
	 */
	public String getMariageEglise() throws SQLException {
		
		int id_p = this.getId_p();

		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT me_date, me_lieu, prenom, nom FROM mariage_eglise, personne, temoin_eglise WHERE ((id_marie1 = '"+id_p+"' OR id_marie2 = '"+id_p+"')) AND (temoin_eglise.id_me = mariage_eglise.id_me) AND (personne.id_p = id_temoin) GROUP BY me_date");
		Statement state3 = Connexion.getInstance().createStatement();
		ResultSet result3 = state3.executeQuery("SELECT COUNT(*) AS rowcount FROM mariage_eglise, personne WHERE (id_marie1 = '"+id_p+"' OR id_marie2 = '"+id_p+"')");

		String resultat = "";
		String temoin = "";
		int count = result3.getInt("rowcount");

		if (count != 0) {
			Statement state2 = Connexion.getInstance().createStatement();
			ResultSet result2 = state2.executeQuery("SELECT prenom, nom FROM personne, mariage_eglise, temoin_eglise WHERE ((id_marie1 = '"+id_p+"' OR id_marie2 = '"+id_p+"') AND (temoin_eglise.id_me = mariage_eglise.id_me) AND (personne.id_p = id_temoin))");

			while (result.next()){
				resultat += result.getString("me_date") +" "+ result.getString("me_lieu") +", ";
				while (result2.next()) {
					temoin += result2.getString("prenom") +" "+ result2.getString("nom")+"; ";
				}
			}
			resultat = "Mariage(s) eglise de "+this.getPrenom() +" "+this.getNom() +" : "+ resultat+" Temoin(s) : "+temoin;
			state2.close();
			result2.close();
		}
		else {
			resultat = this.getPrenom()+" "+this.getNom()+" n'a pas de mariage eglise";
		}
		result.close();
		result3.close();
		state3.close();
		state.close();

		return (resultat);
	}

	// OBTENIR BAPTEME

	/**
	 * Cette methode renvoie les donnees relatives au bapteme d'une personne
	 * @return Sting date et lieu du bapteme, String nom et prenom du parrain/marraine
	 * @throws SQLException
	 */
	public String getBapteme() throws SQLException {
		
		int id_p = this.getId_p();

		String resultat="";
		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT bapt_date, bapt_lieu, prenom, nom FROM bapteme, personne WHERE (bapteme.id_p = '"+id_p+"' AND personne.id_p = id_parrain)");
		
		
		if (result.next()){
			resultat += result.getString("bapt_date") +" "+ result.getString("bapt_lieu") +", Parrain/Marraine : "+ result.getString("prenom") +" "+ result.getString("nom");
			resultat = "Bapteme de "+this.getPrenom() +" "+this.getNom() +" : "+ resultat;
		}
		else{
			resultat = this.getPrenom() +" "+this.getNom() +" n'a pas de bapteme";
		}
		result.close();
		state.close();

		return resultat;
	}


	// OBTENIR SEPULTURE

	/**
	 * Cette methode renvoie les donnees relatives a la sepulture d'une personne
	 * @return String date et lieu de la sepulture
	 * @throws SQLException
	 */
	public String getSepulture() throws SQLException {
		
		int id_p = this.getId_p();

		String resultat="";
		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT sep_date, sep_lieu FROM sepulture WHERE sepulture.id_p = '"+id_p+"'");

		if (result.next()){
			resultat += result.getString("sep_date") +" "+ result.getString("sep_lieu");
			resultat = "Sepulture de "+this.getPrenom() +" "+this.getNom() +" : "+ resultat;
		}
		else{
			resultat = this.getPrenom() +" "+this.getNom() +" n'a pas de sepulture";
		}
		result.close();
		state.close();
		return resultat;
	}


	// OBTENIR ENFANT D'UNE PERSONNE

	/**
	 * Cette methode renvoie les enfants sous forme de List de Personne
	 * @return ArrayList listEnfant la liste des enfants d'une personne
	 * @throws SQLException
	 */
	public ArrayList<Personne> trouveEnfant () throws SQLException {
		
		int id_p = this.getId_p();
		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT id_p, prenom, nom, sexe FROM enfant_parent, personne WHERE personne.id_p = enfant_parent.id_enfant AND id_parent = '"+id_p+"' ");
		ArrayList<Personne> listEnfant = new ArrayList<Personne>();

		while (result.next()) {
			Personne enf = new Personne(result.getInt("id_p"),result.getString("prenom"),result.getString( "nom"),Sexe.valueOf(result.getString("sexe")));
			listEnfant.add(enf);
		}
		result.close();
		state.close();

		return listEnfant;
	}


	// OBTENIR PARENTS D'UNE PERSONNE

	/**
	 * Cette methode renvoie les parents sous forme de List de Personne
	 * @return ArrayList listParent
	 * @throws SQLException
	 */
	public ArrayList<Personne> trouveParent() throws SQLException {
		
		int id_p = this.getId_p();
		Statement state = Connexion.getInstance().createStatement();
		ResultSet result = state.executeQuery("SELECT id_p, prenom, nom, sexe FROM enfant_parent, personne WHERE personne.id_p = enfant_parent.id_parent AND id_enfant = '"+id_p+"' ");

		ArrayList<Personne> listParent = new ArrayList<Personne>();

		while (result.next()) {
			Personne par = new Personne(result.getInt("id_p"),result.getString("prenom"),result.getString( "nom"),Sexe.valueOf(result.getString("sexe")));
			listParent.add(par);
		}
		result.close();
		state.close();

		return listParent;
	}


	// ************************* AFFICHAGE ARBRE *******************************************************

	/**
	 * Cette methode affiche les descendants de la personne sous forme d'arbre
	 */
	public void arbreDescendance(){
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Tree(Personne.this,"descendants");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Cette methode affiche les ascendants de la personne sous forme d'arbre
	 */
	public void arbreAscendance() {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Tree(Personne.this,"ascendants");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
