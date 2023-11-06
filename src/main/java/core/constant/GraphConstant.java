package core.constant;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GraphConstant {
    public static int TAUX_RAFRAISHISSEMENT; //milliseconde
    public static final int FROM_TIME;
    public static final int ECHELLE;
    public static final int TAILLE_AXE_X;

    static {

        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de l'application", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        TAUX_RAFRAISHISSEMENT = Integer.parseInt(prop.getProperty("TAUX_RAFRAICHISSEMENT"));
        FROM_TIME = Integer.parseInt(prop.getProperty("FROM_TIME"));
        ECHELLE = Integer.parseInt(prop.getProperty("ECHELLE"));
        TAILLE_AXE_X = Integer.parseInt(prop.getProperty("TAILLE_ABSCISSE"));
    }
    public static void initGraphConstants(){}
}
