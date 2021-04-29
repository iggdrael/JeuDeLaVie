/**
 * Représente une Cellule du jeu de la vie
 * Chaque Cellule est représentée par son état
 * (vivante ou morte)
 */
public class Cellule {
    /**
     * Etat de la cellule : CelluleEtat
     */
    private CelluleEtat etat;
    /**
     * Coordonnées de la Cellule dans la Grille
     */
    private final int x;
    private final int y;

    /**
     * Constructeur de Cellule
     * @param etat initial de la Cellule
     */
    public Cellule(CelluleEtat etat, int x, int y){
        this.etat = etat;
        this.x = x;
        this.y = y;
    }

    /**
     * Permet de connaître le nombre de Cellules voisines en vie
     * @param jeu l'automate
     * @return le nombre de voisines vivantes
     */
    public int nombreVoisinesVivantes(JeuDeLaVie jeu){
        int nbVoisinesVivantes = 0;

        if (jeu.celluleEstVivante(x+1, y+1)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x-1, y-1)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x+1, y-1)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x-1, y+1)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x, y+1)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x, y-1)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x+1, y)) nbVoisinesVivantes++;
        if (jeu.celluleEstVivante(x-1, y)) nbVoisinesVivantes++;

        return nbVoisinesVivantes;
    }

    /**
     * Modifie l'état de la cellule en vivante
     */
    public void vit(){
        this.etat = this.etat.vit();
    }

    /**
     * Modifie l'état de la cellule en morte
     */
    public void meurt(){
        this.etat = this.etat.meurt();
    }

    /**
     * Indique si la Cellule a pour etat vivante
     * @return true si la Cellule est vivante
     * false sinon
     */
    public boolean estVivante(){
        return this.etat.estVivante();
    }

    /**
     * Réalise l'opération du Visiteur sur la Cellule
     * @param visiteur Le Visiteur réalisant l'opération
     */
    public void accepte(Visiteur visiteur){
        this.etat.accepte(visiteur, this);
    }

    public String toString(){
        return this.etat.toString();
    }
}
