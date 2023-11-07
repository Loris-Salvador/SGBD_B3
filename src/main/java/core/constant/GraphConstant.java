package core.constant;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GraphConstant {
    public static final int TAUX_RAFRAISHISSEMENT; //milliseconde
    public static final int FROM_TIME;
    public static final int ECHELLE;
    public static final int TAILLE_AXE_X;
    public static final int MULTIPLICATEUR_MAX;

    static {

        Properties prop = new Properties();
        int tauxRafraichissement = -1, fromTime = -1, echelle = -1, tailleAxeX = -1, multiplicateurMax = -1;


        try (FileInputStream fis = new FileInputStream("config.properties")) {

            prop.load(fis);
            tauxRafraichissement = Integer.parseInt(prop.getProperty("TAUX_RAFRAICHISSEMENT"));
            fromTime = Integer.parseInt(prop.getProperty("FROM_TIME"));
            echelle = Integer.parseInt(prop.getProperty("ECHELLE"));
            tailleAxeX = Integer.parseInt(prop.getProperty("TAILLE_ABSCISSE"));
            multiplicateurMax = Integer.parseInt(prop.getProperty("MAX_MULTIPLICATEUR"));

        }
        catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        TAUX_RAFRAISHISSEMENT = tauxRafraichissement;
        FROM_TIME = fromTime;
        ECHELLE = echelle;
        TAILLE_AXE_X = tailleAxeX;
        MULTIPLICATEUR_MAX = multiplicateurMax;
    }
    public static void initGraphConstants(){}
}
