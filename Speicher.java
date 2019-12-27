import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Image;

class Speicher {
    private static Image[] pictures_Unscaled;
    private static int count;
    private static String[] paths;

    public static void load() {
        File folder = new File("bilder");
        if (!folder.exists())
            folder.mkdir();
        count = folder.listFiles().length;
        pictures_Unscaled = new Image[count];
        paths = new String[count];
        int i = 0;
        for (File f : folder.listFiles()) {
            try {
                pictures_Unscaled[i] = ImageIO.read(f);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Dieses Bild konnte nicht geladen werden. Pfad: '" + f.getPath() + "'");
            }
            paths[i] = f.getPath();
            i++;
        }
    }

    public static Image[] getPicturesUnscaled() {
        return pictures_Unscaled;
    }

    public static String[] getPathList() {
        return paths;
    }
}