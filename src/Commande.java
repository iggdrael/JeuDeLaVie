/**
 * Design Pattern Commande pour file d'actions sur Cellules
 */
public abstract class Commande {
    /**
     * Cellule sur laquelle executer la Commande
     */
    private Cellule cellule;

    /**
     * Execute la Commande
     */
    public abstract void executer();

    /**
     * Retourne la Cellule de la Commande
     * @return cellule
     */
    public Cellule getCellule(){
        return cellule;
    }

    /**
     * Attribue la Cellule à la Commande
     * @param cellule à attribuer
     */
    public void setCellule(Cellule cellule){
        this.cellule = cellule;
    }
}
