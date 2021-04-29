/**
 * Implementation CommandeVit de Commande
 */
public class CommandeVit extends Commande {
    /**
     * Constructeur de CommandeVit
     * @param c la Cellule associée à la Commande
     */
    public CommandeVit(Cellule c){
        setCellule(c);
    }

    /**
     * Execute la Commande
     */
    public void executer(){
        getCellule().vit();
    }
}
