import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente la simulation de l'automate cellulaire
 */
public class JeuDeLaVie implements Observable {
    //Matrice de Cellule représentant la grille
    private final Cellule[][] grille;

    //Génération courante de l'automate
    // Nombre de Cellules vivantes
    private int nbGenerations, nbVivantes;

    //Nombre de lignes et de colonnes max de la grille
    private final int xMax;
    private final int yMax;

    //Boolean indiquant si l'automate est actuellement en exécution, false par défaut
    private Boolean isRunning;

    //Listes d'observateurs et de commandes pour JeuDeLaVie
    List<Observateur> observateurs;
    List<Commande> commandes;

    //Visiteur utilisé pour le JeuDeLaVie
    private Visiteur visiteur;

    /**
     * Constructeur du JeuDeLaVie
     */
    public JeuDeLaVie(int xMax, int yMax){
        this.xMax = xMax;
        this.yMax = yMax;
        this.isRunning = false;
        this.grille = new Cellule[xMax][yMax];
        this.nbGenerations = 0;
        resetNbVivantes();
        initialiseGrille(50);

        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
    }

    /**
     * Retourne nombre de lignes max de la grille
     * @return xMax
     */
    public int getXMax(){
        return xMax;
    }

    /**
     * Retourne nombre de colonnes max de la grille
     * @return yMax
     */
    public int getYMax(){
        return yMax;
    }

    /**
     * Permet de savoir si l'automate est en cours d'éxecution
     * @return true si l'automate run,
     * false sinon
     */
    public Boolean isRunning(){
        return this.isRunning;
    }

    /**
     * Lance l'automate
     */
    public void run(){
        this.isRunning = true;
    }

    /**
     * Stop l'automate
     */
    public void stop(){
        this.isRunning = false;
    }

    /**
     * Retourne la génération courante de l'automate
     * @return nbGenerations
     */
    public int getNbGenerations(){
        return nbGenerations;
    }

    /**
     * Incrémente le nombre de générations
     */
    public void actualiseNbGeneration(){
        nbGenerations++;
    }

    /**
     * Incrémente le nombre de cellules vivantes
     */
    public void actualiseNbVivantes(){
        nbVivantes++;
    }

    /**
     * Reset le compteur de cellules vivantes
     */
    public void resetNbVivantes() {
        nbVivantes = 0;
    }

    /**
     * Renvoie le nombre de Cellules vivantes de la grille
     * @return nbVivantes
     */
    public int getNbVivantes(){
        return nbVivantes;
    }

    /**
     * Renvoie le Visiteur du JeuDeLaVie
     * @return Visiteur
     */
    public Visiteur getVisiteur(){
        return visiteur;
    }

    /**
     * Attribue au JeuDeLaVie son Visiteur
     * @param visiteur Visiteur de l'automate
     */
    public void setVisiteur(Visiteur visiteur){
        this.visiteur = visiteur;
    }

    /**
     * Attache un Observateur à l'Observable
     * @param o l'Observateur à attacher
     */
    @Override
    public void attacheObservateur(Observateur o){
        this.observateurs.add(o);
    }

    /**
     * Détache un Observateur d'un Observable
     * @param o l'Observateur à détacher
     */
    @Override
    public void detacheObservateur(Observateur o){
        this.observateurs.remove(o);
    }

    /**
     * Notifie tous les Observateur pour actualiser
     */
    @Override
    public void notifieObservateur(){
        observateurs.forEach(Observateur::actualise);
    }

    /**
     * Ajoute une Commande à éxécuter
     * @param c la Commande à ajouter
     */
    public void ajouteCommande(Commande c){
        commandes.add(c);
    }

    /**
     * Distribue le Visiteur
     */
    public void distribueVisiteur(){
        for (int i = 0; i < getXMax(); i++)
            for (int j = 0; j < getYMax(); j++)
                getCellule(i, j).accepte(getVisiteur());
    }

    /**
     * Distribue le Visiteur
     * Execute les Commande
     * Actualise les Observateur
     */
    public void calculerGenerationSuivante(){
        actualiseNbGeneration();
        resetNbVivantes();
        distribueVisiteur();
        executeCommandes();
        notifieObservateur();
    }

    /**
     * Execute toutes les Commandes
     */
    public void executeCommandes(){
        commandes.forEach(Commande::executer);
    }

    /**
     * Initialise la grille
     * Une Cellule a {densite}% de chances d'être vivante
     * @param densite densite de la grille
     * */
    public void initialiseGrille(double densite) {
        this.nbGenerations = 0;
        for (int i = 0; i < xMax; i++)
            for (int j = 0; j < yMax; j++)
                grille[i][j] = (Math.random() > (densite / 100)) ?
                        new Cellule(CelluleEtatMort.getInstance(), i, j) :
                        new Cellule(CelluleEtatVivant.getInstance(), i, j);
    }

    /**
     * Initialise un pattern sur la Grille
     * @param path le chemin du pattern
     */
    public void initialisePattern(String path){
        //Clear la grille
        for (int i = 0; i < xMax; i++)
            for (int j = 0; j < yMax; j++)
                grille[i][j] = new Cellule(CelluleEtatMort.getInstance(), i, j);
        //Reset nbGenerations à 0
        this.nbGenerations = 0;

        String patternString = null;
        //Lecture du Pattern > transformation en String
        try {
            patternString = Files.readString(Path.of("resources/patterns/" + path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Split de la String
        //Ligne 0 : taille du pattern
        //Ligne 1 : structure du pattern
        String[] lines = patternString != null ? patternString.split("\n") : new String[0];
        String[] maxXY = lines[0].split(" ");

        int maxX = Integer.parseInt(maxXY[0]);
        int maxY = Integer.parseInt(maxXY[2]);

        //x et y de départ calculés pour que le pattern soit au centre de la grille
        int x = getXMax() / 2 - maxX / 2, y = getYMax() / 2 - maxY / 2, num = -1;
        /*
         *  Parcourt de chaque char de la structure du pattern
         *  [0-9]*  -> nombre d'opérations à effectuer
         *  'o'     -> opération ajouter cellule vivante
         *  'b'     -> opération ajouter cellule morte (ici ne rien faire puisque la grille est initilialisée)
         *  '$'     -> opération saut de ligne
         */
        for (char c : lines[1].toCharArray()) {
            switch (c) {
                case 'o' -> {
                    if (num == -1) num = 1;
                    for (int i = 0; i < num; i++)
                        grille[x][y] = new Cellule(CelluleEtatVivant.getInstance(), x++, y);
                    num = -1;
                }
                case 'b' -> {
                    if (num == -1) num = 1;
                    for (int i = 0; i < num; x++, i++) ;
                    num = -1;
                }
                case '$' -> {
                    if (num == -1) num = 1;
                    for (int i = 0; i < num; y++, i++) ;
                    //reset de x au centre de la grille
                    x = getXMax() / 2 - maxX / 2;
                    num = -1;
                }
                //Création du nombre d'opérations à effectuer
                default -> num = (num == -1) ? (c - '0') : (num * 10 + c - '0');
            }
        }
        //Actualise la grille
        notifieObservateur();
    }

    /**
     * Permet d'obtenir une Cellule dans la grille
     * en donnant ses coordonnées dans la matrice
     * @param x x lignes de la Cellule
     * @param y y colonnes de la Cellule
     * @return Cellule trouvée
     */
    public Cellule getCellule(int x, int y){
        return this.grille[x][y];
    }

    /**
     * Permet de savoir si des coordonnées ne sont pas hors grille
     * @param x nb lignes
     * @param y nb colonnes
     * @return true si les coordonnées sont valides
     * false sinon
     */
    public boolean coordsValides(int x, int y){
        return x >= 0 && x < getXMax() && y >= 0 && y < getYMax();
    }

    /**
     * Permet de savoir si une Cellule
     * de la grille est vivante
     * @param x nb lignes
     * @param y nb colonnes
     * @return true si vivante, false sinon
     */
    public boolean celluleEstVivante(int x, int y){
        return (coordsValides(x, y) &&
                getCellule(x, y).estVivante());
    }

    /**
     * Permet d'afficher la Grille actuelle
     * @return la String à afficher
     */
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < getXMax(); i++) {
            for (int j = 0; j < getYMax(); j++)
                str.append(getCellule(i, j).toString());
            str.append("\n");
        }
        return str.toString();
    }
}
