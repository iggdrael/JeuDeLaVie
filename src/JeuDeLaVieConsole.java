/**
 * Observateur affichant le JeuDeLaVie en console
 */
public class JeuDeLaVieConsole implements Observateur {
    /**
     * Le JeuDeLaVie observé
     */
    private final JeuDeLaVie jeu;

    /**
     * Constructeur de la classe JeuDeLaVieUI
     * @param jeu Observable
     */
    public JeuDeLaVieConsole(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.jeu.attacheObservateur(this);
    }

    /**
     * Actualise l'ecran lorsqu'une Cellule change d'état
     */
    @Override
    public void actualise(){
        paint();
    }

    /**
     * Imprime le numéro de la génération courante
     * et le nombre de cellules actuellement en vie
     */
    public void paint(){
        System.out.println(jeu.getNbGenerations() + " - " + jeu.getNbVivantes());
    }
}
