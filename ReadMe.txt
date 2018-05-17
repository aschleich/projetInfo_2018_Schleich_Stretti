README ARBRE GENEALOGIQUE

Pour exécuter le code il faut avoir installé SQLite sur le poste. 
Si vous ne l'avez pas, veuillez l'installer : http://sqlitebrowser.org/
Vérifiez que vous avez importé le fichier arbre (contenant la BDD) dans le workspace.


Il faut d'abord importer les personnes de la BDD dans Java en faisant :
	Personne.appelPers();


On crée ensuite des personnes en faisant des instances de Personne :
	Soit 1. avec le numéro correspondant à l'identifiant, qui est affiché dans la console :
		Personne pers1 = Personne.choixPersonne(1);
		Personne pers2 = Personne.choixPersonne(2);
		Personne pers3 = Personne.choixPersonne(3);
		etc
	Soit 2. en utilisant un constructeur :
		Personne pers1 = Personne("Maxime", "Curie");
	Soit 3. en utilisant la méthode addPersonne qui permet de entrer le prénom, nom et sexe dans la console:
		Personne.addPersonne();


Pour obtenir des informations sur la personne on utilise les méthodes suivantes :
	- getPrenom()
	- getNom()
	- getSexe()
	- getNaiss_date()
	- getNaiss_lieu()
	- getDeces_date()
	- getDeces_lieu
	
	exemples :
		System.out.println(pers1.getPrenom();
		System.out.println(pers1.getNaiss_lieu():


On peut ajouter à la personne pers1 des données concernant sa naissance et son décès.
	Les méthodes sont implémentées de cette façon :
	addNaissance(String naiss_date, String naiss_lieu, Source naiss_source);
	addDeces(String deces_date, String deces_lieu, Source deces_source);

	exemple :
	pers1.addNaissance("1990-05-22", "Paris", Source.acte_officiel);


Pour modifier des attributs on fait de même avec les fonctions suivantes :
	- changeNom(String nom)
	- changeNaissance(String naiss_date, String naiss_lieu, Source naiss_source)
	- changeDeces(String deces_date, String deces_lieu, Source deces_source)

	exemples : 
		pers1.changeNom("Dupont");
		pers1.changeNaissance("1990-05-22", "Paris", Source.acte_officiel);
		pers1.changeDeces("1990-05-22", "Paris", Source.acte_officiel);

		
Pour ajouter des relations on applique les fonctions suivantes à la personne:
	- addFrere(Personne pers2)
	- addParent(Personne parent)
	- addEnfant(Personne enfant)
	- addConjoint(Personne pers2)
	- addJob(String smetier)
	- addBapteme(Bapteme bapteme, Personne parrain)
	- addSepulture(Sepulture sepulture)

	- Pour ajouter un frère appelé pers2 à une personne nommée pers1 :
		pers1.addFrere(pers2);
		C'est le même principe pour le parent, l'enfant et le conjoint.
	- Pour ajouter un métier à la personne :
		pers1.addJob("Pompier");
	- Pour ajouter un baptême ou une sépulture à la personne on doit d'abord créer un baptême ou une sépulture.
		Les constructeurs sont :
		Bapteme(String bapt_date, String bapt_lieu, Source bapt_source);
		Sepulture(String sep_date, String sep_lieu, Source sep_source);
		exemple :
		Bapteme bapt = new Bapteme("2000-09-21","Marseille",Source.document);
		Sepulture sep = new Sepulture("2000-09-21","Marseille",Source.document);

		Ensuite on applique la méthode à la personne.
		
		exemples :
		pers1.addBapteme(bapt, pers2); pers2 est donc le parrain du baptême
		pers1.addSepulture(sep);
	
	
Pour créer un mariage, on utilise le constructeur Mariage_mairie ou Mariage_eglise :
	Mariage_mairie(String mm_date, String mm_lieu, Source mm_source)
	Mariage_eglise(String me_date, String me_lieu, Source me_source)
	On crée ensuite une instance.
	
	exemple :
	Mariage_mairie mariage = new Mariage_mairie("2000-09-21","Marseille",Source.archive);
	

Pour ajouter des mariés (pers1,pers2) à un mariage, on utilise la méthode :
	addMaries(Personne pers1, Personne pers2)
	On applique cette méthode au mariage créé.
	
	exemple :
		mariage.addMaries(pers1, pers2);
	
Pour ajouter un témoin (pers2) au mariage crée on utilise la méthode :
		addTemoin(Personne temoin)
		On applique cette méthode au mariage créé.
		
		exemple :
			mariage.addTemoin(pers2);
	
	
Pour modifier des relations, on fait de même avec les méthodes suivantes :
	- changeConjoint(Personne pers2)
	- changeBapteme(Bapteme bapteme, Personne parrain)
	- changeSepulture(Sepulture sepulture)

	exemples :
		pers1.changeConjoint(pers2);
		pers1.changeBapteme(bapt,pers2);
		pers1.changeSepulture(sep);


On peut supprimer le conjoint de la personne avec la méthode 
	suppConjoint()
	
	exemple :
		pers1.suppConjoint();


Pour afficher les relations on utilise les méthodes suivantes :
	- getEnfant()
	- getParent()
	- getConjoint()
	- getMetier()
	- getMariageMairie()
	- getMariageEglise()
	- getBapteme()
	- getSepulture()
	
	exemples :
		pers1.getEnfant();
		pers1.getMariageMairie();
		etc


Pour afficher l'arbre généalogique il y a deux méthodes :
	- arbreDescendance() qui affiche les descendants de la personne
	- arbreAscendance() qui affiche les ascendants de la personne
	
	exemples :
		pers1.arbreDescendance();
		pers1.arbreAscendance();


Pour obtenir la fiche identité de la personne, il suffit de double-cliquer sur son nom dans l'arbre.

