import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * Représente l'interface graphique du JeuDeLaVie
 */
public class JeuDeLaVieUi extends JPanel implements Observateur {
    /**
     * Le JeuDeLaVie observé
     */
    private final JeuDeLaVie jeu;

    /**
     * Constructeur de la classe JeuDeLaVieUI
     * @param jeu Observable
     */
    public JeuDeLaVieUi(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.jeu.attacheObservateur(this);
        //Active le doubleBuffering pour éviter le scintillement de l'image
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(452,452));
        this.setBorder(new MatteBorder(2,0,2,0, Color.BLACK));
    }

    /**
     * Actualise l'ecran lorsqu'une Cellule change d'état
     */
    @Override
    public void actualise(){
        repaint();
    }

    /**
     * Imprime le contenu du JeuDeLaVie observé sur l'écran
     * @param g Graphics à paint
     */
    public void paint(Graphics g){
        super.paint(g);
        for (int x = 0; x < jeu.getXMax(); x++)
            for (int y = 0; y < jeu.getYMax(); y++)
                if (jeu.getCellule(x, y).estVivante())
                    g.fillOval(x * 3, y * 3, 3, 3);
    }
}
