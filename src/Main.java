import java.util.ArrayList;
import java.util.List;

/**
 * Classe Main du programme du JeuDeLaVie
 */
public class Main {
    /**
     * Fonction principale du programme
     * @param args Arguments main
     */
    public static void main(String[] args) throws InterruptedException {
        int xMax = 150;
        int yMax = 150;

        //Création + initialisation JeuDeLaVie + matrice
        JeuDeLaVie automate = new JeuDeLaVie(xMax, yMax);
        JeuDeLaVie automate2 = new JeuDeLaVie(xMax, yMax);
        JeuDeLaVie automate3 = new JeuDeLaVie(xMax, yMax);

        //Choix règles du jeu : Visiteur
        automate.setVisiteur(new VisiteurClassique(automate));
        automate2.setVisiteur(new VisiteurDayNight(automate2));
        automate3.setVisiteur(new VisiteurHighLife(automate3));

        //Création Liste d'automates
        List<JeuDeLaVie> automates = new ArrayList<>() {{
            add(automate);
            add(automate2);
            add(automate3);
        }};

        //Création liste d'UI des automates
        List<JeuDeLaVieUi> interfaces = new ArrayList<>() {{
            add(new JeuDeLaVieUi(automate));
            add(new JeuDeLaVieUi(automate2));
            add(new JeuDeLaVieUi(automate3));
        }};
        //Ajout Observateur console
        new JeuDeLaVieConsole(automate);

        //Création de la fenêtre gérant les automates
        new InterfaceJouerADieu(automates, interfaces);
    }
}
