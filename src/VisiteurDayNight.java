/**
 * Visiteur Day&Night de JeuDeLaVie
 */
public class VisiteurDayNight extends Visiteur {
    /**
     * Constructeur de Visiteur
     * @param jeu JeuDeLaVie utilisé par le Visiteur
     */
    public VisiteurDayNight(JeuDeLaVie jeu) {
        super(jeu);
        this.setTitre("Day&Night");
    }

    /**
     * Visite une Cellule vivante
     * Une Cellule vivante doit mourir si elle
     * n'a pas 3, 4, 6, 7 ou 8 voisines
     * @param cellule à visiter
     */
    @Override
    void visiteCelluleVivante(Cellule cellule){
        super.visiteCelluleVivante(cellule);
        int nbVoisines = cellule.nombreVoisinesVivantes(getJeu());
        if (!(nbVoisines == 3 || nbVoisines == 4 || nbVoisines == 6 ||
                nbVoisines == 7 || nbVoisines == 8))
            getJeu().ajouteCommande(new CommandeMeurt(cellule));
    }

    /**
     * Visite une Cellule morte
     * Une Cellule morte doit vivre si elle
     * a 3, 6, 7 ou 8 voisines
     * @param cellule à visiter
     */
    @Override
    void visiteCelluleMorte(Cellule cellule){
        int nbVoisines = cellule.nombreVoisinesVivantes(getJeu());
        if (nbVoisines == 3 || nbVoisines == 6 ||
            nbVoisines == 7 || nbVoisines == 8)
            getJeu().ajouteCommande(new CommandeVit(cellule));
    }
}
