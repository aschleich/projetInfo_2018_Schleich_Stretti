/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;


public class Sepulture {

	private int id_sep;
	private String sep_date;
	private String sep_lieu;
	private Source sep_source;


	// GETTERS

	public int getId_sep() {
		return id_sep;
	}
	public String getSep_date() {
		return sep_date;
	}
	public String getSep_lieu() {
		return sep_lieu;
	}
	public Source getSep_source() {
		return sep_source;
	}


	// SETTERS

	public void setId_sep(int id_sep) {
		this.id_sep = id_sep;
	}
	public void setSep_date(String sep_date) {
		this.sep_date = sep_date;
	}
	public void setSep_lieu(String sep_lieu) {
		this.sep_lieu = sep_lieu;
	}
	public void setSep_source(Source sep_source) {
		this.sep_source = sep_source;
	}


	// CONSTRUCTEURS

	public Sepulture(int id_sep, String sep_date, String sep_lieu, Source sep_source) {
		this.id_sep = id_sep;
		this.sep_date = sep_date;
		this.sep_lieu = sep_lieu;
		this.sep_source = sep_source;
	}

	public Sepulture(String sep_date, String sep_lieu, Source sep_source) {
		this.sep_date = sep_date;
		this.sep_lieu = sep_lieu;
		this.sep_source = sep_source;
	}

}
