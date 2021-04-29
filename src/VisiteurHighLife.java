/**
 * Visiteur HighLife de JeuDeLaVie
 */
public class VisiteurHighLife extends Visiteur {
    /**
     * Constructeur de Visiteur
     * @param jeu JeuDeLaVie utilisé par le Visiteur
     */
    public VisiteurHighLife(JeuDeLaVie jeu) {
        super(jeu);
        this.setTitre("HighLife");
    }

    /**
     * Visite une Cellule vivante
     * Une Cellule vivante doit mourir si elle
     * n'a pas 2 ou 3 voisines
     * @param cellule à visiter
     */
    @Override
    void visiteCelluleVivante(Cellule cellule){
        //Incrémente nbCellulesVivantes
        super.visiteCelluleVivante(cellule);
        int nbVoisines = cellule.nombreVoisinesVivantes(getJeu());
        if (!(nbVoisines == 2 || nbVoisines == 3))
            getJeu().ajouteCommande(new CommandeMeurt(cellule));
    }

    /**
     * Visite une Cellule morte
     * Une Cellule morte doit vivre si elle
     * a 3 ou 6 voisines
     * @param cellule à visiter
     */
    @Override
    void visiteCelluleMorte(Cellule cellule){
        int nbVoisines = cellule.nombreVoisinesVivantes(getJeu());
        if (nbVoisines == 3 || nbVoisines == 6)
            getJeu().ajouteCommande(new CommandeVit(cellule));
    }
}
