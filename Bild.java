import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Bild extends JPanel {
    private static final long serialVersionUID = 1L;
    public String bild_path;

    public static Image getScaledImage(String path, int pictureWidth) {
        try {
            return ImageIO.read(new File(path)).getScaledInstance(pictureWidth, pictureWidth, 4);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}