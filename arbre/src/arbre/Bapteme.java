/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;


public class Bapteme {

	private int id_bapt;
	private String bapt_date;
	private String bapt_lieu;
	private Source bapt_source;


	// GETTERS

	public int getId_bapt() {
		return id_bapt;
	}
	public String getBapt_date() {
		return bapt_date;
	}
	public String getBapt_lieu() {
		return bapt_lieu;
	}
	public Source getBapt_source() {
		return bapt_source;
	}


	// SETTERS

	public void setId_bapt(int id_bapt) {
		this.id_bapt = id_bapt;
	}
	public void setBapt_date(String bapt_date) {
		this.bapt_date = bapt_date;
	}
	public void setBapt_lieu(String bapt_lieu) {
		this.bapt_lieu = bapt_lieu;
	}
	public void setBapt_source(Source bapt_source) {
		this.bapt_source = bapt_source;
	}


	// CONSTRUCTEURS 

	public Bapteme(int id_bapt, String bapt_date, String bapt_lieu, Source bapt_source) {
		this.id_bapt = id_bapt;
		this.bapt_date = bapt_date;
		this.bapt_lieu = bapt_lieu;
		this.bapt_source = bapt_source;
	}

	public Bapteme(String bapt_date, String bapt_lieu, Source bapt_source) {
		this.bapt_date = bapt_date;
		this.bapt_lieu = bapt_lieu;
		this.bapt_source = bapt_source;
	}

}
