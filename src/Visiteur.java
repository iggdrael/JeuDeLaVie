/**
 * Design Pattern Visiteur
 */
public abstract class Visiteur {
    /**
     * Le JeuDeLaVie à visiter
     */
    private final JeuDeLaVie jeu;

    /**
     * Titre du Visiteur
     */
    private String titre;

    /**
     * Constructeur de Visiteur
     * @param jeu Le Jeu que le Visiteur utilise
     */
    public Visiteur(JeuDeLaVie jeu){
        this.jeu = jeu;
    }

    /**
     * Visite une Cellule vivante
     * Incrémente le nombre de cellules vivantes pour ne pas
     * parcourir plusieurs fois la grille depuis les Observateur
     * @param cellule à visiter
     */
    void visiteCelluleVivante(Cellule cellule){
        jeu.actualiseNbVivantes();
    }

    /**
     * Visite une Cellule morte
     * @param cellule à visiter
     */
    abstract void visiteCelluleMorte(Cellule cellule);

    /**
     * Retourne le JeuDeLaVie utilisé par le Visiteur
     * @return JeuDeLaVie utilisé par le Visiteur
     */
    public JeuDeLaVie getJeu(){
        return jeu;
    }

    /**
     * Permet de récupérer le titre du Visiteur
     * @return titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Permet de fixer le titre du Visiteur
     * @param titre titre à fixer
     */
    public void setTitre(String titre){
        this.titre = titre;
    }

}
