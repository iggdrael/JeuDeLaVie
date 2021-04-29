/**
 * Interface représentant l'état d'une Cellule
 * => vivante ou morte
 */
public interface CelluleEtat {
    /**
     * Modifie l'état de la cellule en vivante
     * @return CelluleEtat
     */
    CelluleEtat vit();

    /**
     * Modifie l'état de la cellule en morte
     * @return CelluleEtat
     */
    CelluleEtat meurt();

    /**
     * Indique si la Cellule a pour etat vivante
     * @return true si la Cellule est vivante
     * false sinon
     */
    boolean estVivante();

    /**
     * Réalise l'opération du Visiteur sur la Cellule
     * @param visiteur Le Visiteur réalisant l'opération
     * @param cellule La Cellule à visiter
     */
    void accepte(Visiteur visiteur, Cellule cellule);

    /**
     * Fonction de debug
     * Affiche la Cellule
     * @return 1 si vivante
     * 0 sinon
     */
    String toString();
}
