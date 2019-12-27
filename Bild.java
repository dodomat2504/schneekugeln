import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Bild extends JPanel {
    private static final long serialVersionUID = 1L;
    //private Image bf;
    public String bild_path;

    /*
    public Bild(String path) {
        super();
        bild_path = path;
        setLayout(null);
        setSize(16*30, 9*30);
        bf = null;
        try {bf = ImageIO.read(getClass().getResource(path));bf=bf.getScaledInstance(getWidth(), getHeight(), 4);} catch (IOException e) {
            bf = null;
        }
    }
    
    public Bild(String path, int pictureWidth) {
        super();
        bild_path = path;
        setLayout(null);
        setSize(pictureWidth, pictureWidth);
        bf = null;
        try {bf = ImageIO.read(getClass().getResource(path));bf=bf.getScaledInstance(getWidth(), getHeight(), 4);} catch (IOException e) {
            bf = null;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (bf != null) g.drawImage(bf, 0, 0, this);
    }
    */
    public static Image getScaledImage(String path, int pictureWidth) {
        try {
            return ImageIO.read(new File(path)).getScaledInstance(pictureWidth, pictureWidth, 4);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}