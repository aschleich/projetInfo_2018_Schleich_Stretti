/**
 * Projet informatique simulant un arbre genealogique
 * @author Schleich Anouk
 * @author Stretti Marie
 * @date mai 2018
 */


package arbre;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;


public class Tree extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private JLabel selectedLabel;


	// AJOUT NOEUD

	/**
	 * Cette methode ajoute un noeud parent au niveau de la personne
	 * @param pers
	 * @param parentNode noeud parent
	 * @throws SQLException
	 */
	public void ajoutNoeud(Personne pers, DefaultMutableTreeNode parentNode, String choix) throws SQLException {

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(pers.getPrenom() + " " +pers.getNom()+ " "+"("+pers.getSexe() +")");
		parentNode.add(node);

		if (choix.equals("ascendants")){
			for (Personne enfant : pers.trouveParent()) {
				this.ajoutNoeud(enfant, node, choix);
			}
		}

		if (choix.equals("descendants")){
			for (Personne enfant : pers.trouveEnfant()) {
				this.ajoutNoeud(enfant, node, choix);
			}
		}

	}

	/**
	 * Cette methode affiche la fiche identite de la personne quand on double-clique dessus dans l'arbre
	 * @param me clic
	 */
	void doMouseClicked(MouseEvent me) {
		if(me.getClickCount() == 2){

			String ttp = tree.getLastSelectedPathComponent().toString();

			System.out.println("Voici la fiche identite de " + ttp +" :" );
			System.out.println();
			ttp = ttp.replaceAll(" ", "");

			for (Personne pers : Personne.listpers) {
				String toutname = (pers.getPrenom()+pers.getNom()+"("+pers.getSexe()+")");
				if (toutname.equals(ttp)){
					System.out.println("Prenom            : "+pers.getPrenom());
					System.out.println("Nom               : "+pers.getNom());
					System.out.println("Sexe              : "+pers.getSexe());
					System.out.println("Date de naissance : "+pers.getNaiss_date());
					System.out.println("Lieu de naissance : "+pers.getNaiss_lieu());
					System.out.println("Date de deces     : "+pers.getDeces_date());
					System.out.println("Lieu de deces     : "+pers.getDeces_lieu());
					System.out.println();
					System.out.println("Identifiant       : "+pers.getId_p());
					System.out.println("Source naissance  : "+pers.getNaiss_source());
					System.out.println("Source deces      : "+pers.getDeces_source());
					System.out.println();

					try {
						System.out.println(pers.getEnfant());
					} catch (SQLException e) {
						e.printStackTrace();
					}


					try {
						System.out.println(pers.getParent());
					} catch (SQLException e) {
						e.printStackTrace();
					}


					try {
						System.out.println(pers.getConjoint());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					try {
						System.out.println(pers.getMetier());
					} catch (SQLException e) {
						e.printStackTrace();
					}  

					try {
						System.out.println(pers.getMariageMairie());
					} catch (SQLException e) {
						e.printStackTrace();
					}

					try {
						System.out.println(pers.getMariageEglise());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						System.out.println(pers.getBapteme());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						System.out.println(pers.getSepulture());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("\n");
				}

			}
		}
	}


	// ARBRE

	/**
	 * Cree l'arbre genealogique de la personne entree
	 * @param pers
	 * @throws SQLException
	 */
	public Tree(Personne pers, String choix) throws SQLException {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		ajoutNoeud(pers, root, choix);

		tree = new JTree(root);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				doMouseClicked(me);
			}
		});

		ImageIcon imageIcon = new ImageIcon(Tree.class.getResource("Homme.png"));
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();       
		renderer.setLeafIcon(imageIcon);
		renderer.setClosedIcon(imageIcon);
		renderer.setOpenIcon(imageIcon);

		tree.setCellRenderer(renderer);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		add(new JScrollPane(tree));

		selectedLabel = new JLabel();
		add(selectedLabel, BorderLayout.SOUTH);
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				selectedLabel.setText(selectedNode.getUserObject().toString());
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Arbre des "+choix);       
		this.setSize(400, 400);
		this.setVisible(true);
	}

}
