import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Interface permettant de simuler différents automates cellulaires
 */
public class InterfaceJouerADieu extends JFrame{
    //List d'automates
    private final List<JeuDeLaVie> automates;
    private final List<JLabel> labelsTitres;
    //Vitesse d'éxecution des automates
    private int speed;
    /**
     * Constructeur De l'interface
     * @param interfaces UI Observateur des automates
     */
    public InterfaceJouerADieu(List<JeuDeLaVie> automates, List<JeuDeLaVieUi> interfaces) throws InterruptedException {
        this.automates = automates;
        this.labelsTitres = new ArrayList<>();

        //Création de l'interface
        this.setTitle("Les Thanatonautes");
        this.setResizable(false);
        //Récupération des données de l'écran pour créer une interface maximisée mais fenêtrée
        //setUndecorated(true) pour fullscreen
        GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();
        DisplayMode displayMode = screen.getDisplayMode();
        this.setSize(displayMode.getWidth(), displayMode.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-------------------------------------------------
        //Création des icon des boutons reset/éxécuter/arrêter/avancer
        ImageIcon resetIcon = new ImageIcon("resources/resetIcon.png");
        ImageIcon playIcon = new ImageIcon("resources/playIcon.png");
        ImageIcon stopIcon = new ImageIcon("resources/stopIcon.png");
        ImageIcon nextIcon = new ImageIcon("resources/nextIcon.png");

        //Création div principale contenant les panels des automates
        //et leurs boutons + options
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        //Panel contenant les automates
        JPanel automatesContainer = new JPanel();
        //Panel contenant barre défilantes et options
        JPanel optionsContainer = new JPanel();
        //Panel courant à ajouter dans automatesContainer
        JPanel automatePan;

        //Création de l'image de couverture
        JLabel coverLabel = new JLabel(new ImageIcon("resources/cover.png"), JLabel.CENTER);
        coverLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mainContainer.add(coverLabel);
        mainContainer.add(Box.createRigidArea(new Dimension(0, 20)));


        //--------------Création des panels des automates----------------
        //Ajout de chaque automate sur le container
        for (int i = 0; i < this.automates.size(); i++) {
            //Création du panel de l'automate
            automatePan = new JPanel();
            automatePan.setLayout(new BoxLayout(automatePan, BoxLayout.Y_AXIS));

            //Ajout titre du Visiteur
            JLabel titre = new JLabel(automates.get(i).getVisiteur().getTitre() + " : " +
                    automates.get(i).getNbGenerations(), JLabel.CENTER);
            titre.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            this.labelsTitres.add(titre);
            automatePan.add(this.labelsTitres.get(i));

            //Ajout du JeuDeLaVieUi au panel de l'automate
            automatePan.add(interfaces.get(i));

            //Création d'un panel pour les boutons sur l'automate
            JPanel panButtons = new JPanel();

            //Création des boutons
            int finalI = i;
            JButton playButton = new JButton(playIcon);
            //Ajout listener playButton
            playButton.addActionListener(e -> this.automates.get(finalI).run());
            JButton stopButton = new JButton(stopIcon);
            //Ajout listener stopButton
            stopButton.addActionListener(e -> this.automates.get(finalI).stop());
            JButton nextButton = new JButton(nextIcon);
            //Ajout listener nextButton
            nextButton.addActionListener(e -> {
                JeuDeLaVie automate = this.automates.get(finalI);
                automate.calculerGenerationSuivante();
                this.labelsTitres.get(finalI).setText(automate.getVisiteur().getTitre() + " : " +
                        automate.getNbGenerations());
            });

            //Ajout des boutons au panel des boutons
            panButtons.add(playButton);
            panButtons.add(stopButton);
            panButtons.add(nextButton);

            //Ajout d'un contour
            automatePan.setBorder(new LineBorder(Color.black, 2, false));

            //Ajout du panel des boutons au panel de l'automate
            automatePan.add(panButtons);

            //Ajout du panel à la div principale
            automatesContainer.add(automatePan);
        }

        //-----------Création des options-----------
        //Input densité des grilles
        JTextField densiteTextField = new JTextField();
        final double[] dens = new double[1];
        dens[0] = 50;
        densiteTextField.setText(" Densité [0-100] ");
        densiteTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                densiteTextField.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                dens[0] = (densiteTextField.getText().matches("-?\\d+(\\.\\d+)?")) ?
                        Double.parseDouble(densiteTextField.getText()) :
                        50.0;
                densiteTextField.setText(" Densité [0-100] ");
            }
        });

        //Bouton permettant de reset la grille
        JButton resetButton = new JButton(resetIcon);
        resetButton.addActionListener(e -> {
            JeuDeLaVie automate;
            for (int i = 0; i < this.automates.size(); i++) {
                automate = this.automates.get(i);

                automate.initialiseGrille(dens[0]);
                this.labelsTitres.get(i).setText(automate.getVisiteur().getTitre() + " : " +
                        automate.getNbGenerations());
                automate.notifieObservateur();
            }
        });

        optionsContainer.add(densiteTextField);

        //Bouton permettant de lancer tous les automates
        JButton playAllButton = new JButton(playIcon);
        //Ajout listener playAllButton
        playAllButton.addActionListener(e -> {
            for (JeuDeLaVie automate : this.automates) automate.run();
        });

        //Bouton permettant d'arrêter tous les automates
        JButton stopAllButton = new JButton(stopIcon);
        //Ajout listener playAllButton
        stopAllButton.addActionListener(e -> {
            for (JeuDeLaVie automate : this.automates) automate.stop();
        });

        //Bouton permettant de faire avancer tous les automates
        JButton nextAllButton = new JButton(nextIcon);
        //Ajout listener nextButton
        nextAllButton.addActionListener(e -> {
            JeuDeLaVie automate;
            for (int i = 0; i < this.automates.size(); i++) {
                automate = automates.get(i);
                automate.calculerGenerationSuivante();
                this.labelsTitres.get(i).setText(automate.getVisiteur().getTitre() + " : " +
                        automate.getNbGenerations());
            }
        });

        //Ajout des boutons aux options
        optionsContainer.add(resetButton);
        optionsContainer.add(playAllButton);
        optionsContainer.add(stopAllButton);
        optionsContainer.add(nextAllButton);

        //Création d'un slider pour modifier la vitesse d'éxécution
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL,
                200, 3000, 1500);
        speedSlider.setInverted(true);

        setSpeed(speedSlider.getValue());

        //Ajout d'un listener sur le slider pour modifier la vitesse d'éxecution
        speedSlider.addChangeListener(e -> setSpeed(speedSlider.getValue()) );

        //Ajout du slider au panel options
        optionsContainer.add(speedSlider);

        //Ajout liste des patterns
        List<String> patternArray = new ArrayList<>();
        for (File file : Objects.requireNonNull(new File("resources/patterns/").listFiles())) {
            if (file.isFile()) {
                patternArray.add(file.getName());
            }
        }
        //Création de la JList
        JList patternList = new JList(patternArray.toArray());

        patternList.addListSelectionListener(e -> {
            JeuDeLaVie automate;
            for (int i = 0; i < this.automates.size(); i++) {
                automate = this.automates.get(i);
                automate.initialisePattern(patternList.getSelectedValue().toString());
                this.labelsTitres.get(i).setText(automate.getVisiteur().getTitre() + " : " +
                        automate.getNbGenerations());
            }
        });
        optionsContainer.add(patternList);

        //Ajout des panels des automates à la div principale
        mainContainer.add(automatesContainer);

        //Ajout du panel des options à la div principale
        mainContainer.add(optionsContainer);

        //La div principale est fixée sur la fenêtre
        this.setContentPane(mainContainer);
        //Affichage du contenu
        this.setVisible(true);

        //Lancement de la boucle d'éxécution des automates
        run();
    }

    /**
     * Retourne l'automate coresspondant à l'index
     * dans la liste des automates
     * @param i index de l'automate
     * @return automate souhaité
     */
    private JeuDeLaVie getAutomate(int i){
        return this.automates.get(i);
    }

    /**
     * Permet de modifier la vitesse d'éxecution des automates
     * @param speed vitesse d'éxecution
     */
    private void setSpeed(int speed){
        this.speed = speed;
    }

    /**
     * Permet d'obtenir la vitesse d'éxecution des automates
     * @return speed
     */
    private int getSpeed(){
        return this.speed;
    }
    /**
     * Lance une boucle infinie actualisant les automates
     * s'ils sont en cours d'éxécution
     * @throws InterruptedException Thread.sleep
     */
    private void run() throws InterruptedException {
        JeuDeLaVie automate;
        while (true){
            for (int i = 0; i < this.automates.size(); i++) {
                automate = getAutomate(i);
                if (automate.isRunning()) {
                    automate.calculerGenerationSuivante();
                    this.labelsTitres.get(i).setText(automate.getVisiteur().getTitre() + " : " +
                            automate.getNbGenerations());
                }
            }
            Thread.sleep(getSpeed());
        }
    }
}
