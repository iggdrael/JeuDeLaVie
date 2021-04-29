/**
 * Visiteur Classique de JeuDeLaVie
 */
public class VisiteurClassique extends Visiteur {
    /**
     * Constructeur de Visiteur
     * @param jeu JeuDeLaVie utilisé par le Visiteur
     */
    public VisiteurClassique(JeuDeLaVie jeu) {
        super(jeu);
        this.setTitre("Classique");
    }

    /**
     * Visite une Cellule vivante
     * Une Cellule vivante doit mourir si elle
     * a moins de 2 ou plus de 3 voisines
     * @param cellule à visiter
     */
    @Override
    void visiteCelluleVivante(Cellule cellule){
        super.visiteCelluleVivante(cellule);
        int nbVoisines = cellule.nombreVoisinesVivantes(getJeu());
        if (nbVoisines < 2 || nbVoisines > 3)
            getJeu().ajouteCommande(new CommandeMeurt(cellule));
    }

    /**
     * Visite une Cellule morte
     * Une Cellule morte doit vivre si elle
     * a 3 voisines exactement
     * @param cellule à visiter
     */
    @Override
    void visiteCelluleMorte(Cellule cellule){
        if (cellule.nombreVoisinesVivantes(getJeu()) == 3)
            getJeu().ajouteCommande(new CommandeVit(cellule));
    }
}
