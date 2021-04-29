/**
 * Représente l'état mort d'une Cellule
 * Cette Classe est un Singleton
 */
public class CelluleEtatMort implements CelluleEtat{
    private final static CelluleEtatMort instance = new CelluleEtatMort();

    /**
     * Constructeur Singleton de CelluleEtatMort
     */
    private CelluleEtatMort() {}

    /**
     * Permet d'obtenir l'instance de CelluleEtatMort
     * @return instance de CelluleEtatMort
     */
    public static CelluleEtatMort getInstance(){
        return instance;
    }

    /**
     * Modifie l'état de la Cellule en vivante
     * @return Instance de CelluleEtatVivant
     */
    @Override
    public CelluleEtat vit() {
        return CelluleEtatVivant.getInstance();
    }

    /**
     * La Cellule est déjà morte
     * @return la Cellule
     */
    @Override
    public CelluleEtat meurt(){
        return this;
    }

    /**
     * Indique si la Cellule a pour etat vivante
     * @return false par défaut
     */
    @Override
    public boolean estVivante(){
        return false;
    }

    /**
     * Réalise l'opération du Visiteur sur la Cellule
     * @param visiteur Le Visiteur réalisant l'opération
     * @param cellule La Cellule à visiter
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule){
        visiteur.visiteCelluleMorte(cellule);
    }

    @Override
    public String toString(){
        return "0";
    }
}
