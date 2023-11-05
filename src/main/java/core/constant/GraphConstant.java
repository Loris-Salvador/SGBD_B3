package core.constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GraphConstant {
    public static final int TAUX_RAFRAISHISSEMENT; //milliseconde
    public static final int FROM_TIME;
    public static final int ECHELLE;
    public static final int TAILLE_AXE_X;

    static {
        Properties prop = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TAUX_RAFRAISHISSEMENT = Integer.parseInt(prop.getProperty("TAUX_RAFRAICHISSEMENT"));
        FROM_TIME = Integer.parseInt(prop.getProperty("T-"));
        ECHELLE = Integer.parseInt(prop.getProperty("ECHELLE"));
        TAILLE_AXE_X = Integer.parseInt(prop.getProperty("TAILLE_ABSCISSE"));
    }
}
