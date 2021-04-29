/**
 * Représente l'état vivant d'une Cellule
 * Cette Classe est un Singleton
 */
public class CelluleEtatVivant implements CelluleEtat {
    private final static CelluleEtatVivant instance = new CelluleEtatVivant();

    /**
     * Constructeur Singleton de CelluleEtatVivant
     */
    private CelluleEtatVivant() {}

    /**
     * Permet d'obtenir l'instance de CelluleEtatVivant
     * @return instance de CelluleEtatVivant
     */
    public static CelluleEtatVivant getInstance(){
        return instance;
    }

    /**
     * La Cellule est déjà vivante
     * @return la Cellule
     */
    @Override
    public CelluleEtat vit() {
        return this;
    }

    /**
     * Modifie l'état de la cellule en morte
     * @return Instance de CelluleEtatMort
     */
    @Override
    public CelluleEtat meurt(){
        return CelluleEtatMort.getInstance();
    }

    /**
     * Indique si la Cellule a pour etat vivante
     * @return true par défaut
     */
    @Override
    public boolean estVivante(){
        return true;
    }

    /**
     * Réalise l'opération du Visiteur sur la Cellule
     * @param visiteur Le Visiteur réalisant l'opération
     * @param cellule La Cellule à visiter
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleVivante(cellule);
    }

    /**
     * Fonction de debug
     * Affiche la Cellule
     * @return 1 si vivante
     * 0 sinon
     */
    @Override
    public String toString(){
        return "1";
    }
}
