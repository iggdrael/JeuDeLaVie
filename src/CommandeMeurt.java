/**
 * Implementation CommandeMeurt de Commande
 */
public class CommandeMeurt extends Commande {
    /**
     * Constructeur de CommandeMeurt
     * @param c la Cellule associée à la Commande
     */
    public CommandeMeurt(Cellule c){
        setCellule(c);
    }

    /**
     * Execute la Commande
     */
    public void executer(){
        getCellule().meurt();
    }
}
