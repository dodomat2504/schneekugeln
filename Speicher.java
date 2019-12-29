import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

class Speicher {
    private static Image[] pictures_Unscaled;
    //private static int count;
    private static HashMap<Integer, String> bilder;
    private static HashMap<Integer, String> ort;
    private static HashMap<Integer, String> schenker;
    private static HashMap<Integer, String> notizen;

    public static void load() throws IOException {
        /*
        File folder = new File("bilder");
        if (!folder.exists())
            folder.mkdir();
        count = folder.listFiles().length;
        pictures_Unscaled = new Image[count];
        int k = 0;
        for (File f : folder.listFiles()) {
            try {
                pictures_Unscaled[k] = ImageIO.read(f);
            } catch (IOException e) {
                System.out.println("Dieses Bild konnte nicht geladen werden. Pfad: '" + f.getPath() + "'");
            }
            k++;
        }
        */

        File db = new File("db.txt");
        if (!db.exists()) db.createNewFile();
        FileReader fr = new FileReader(db);
        BufferedReader br = new BufferedReader(fr);
        String[] data_arr = br.readLine().split(";");
        br.close();
        for (int i = 0; i < data_arr.length; i++) {
            String[] sub_arr = data_arr[i].split("___");
            int ID = Integer.parseInt(sub_arr[0]);
            bilder.put(ID, sub_arr[1]);
            ort.put(ID, sub_arr[2]);
            if (sub_arr.length == 3) {
                notizen.put(ID, sub_arr[2]);
            } else if (sub_arr.length == 4) {
                schenker.put(ID, sub_arr[2]);
                notizen.put(ID, sub_arr[3]);
            }
        }
    }

    private static void save() throws IOException {
        File db = new File("db.txt");
        if (db.exists()) db.delete();
        db.createNewFile();
        FileWriter fw = new FileWriter(db);
        BufferedWriter bw = new BufferedWriter(fw);
        int i = 1;
        int eintraege = bilder.keySet().size();
        String data = "";
        for (int ID : bilder.keySet()) {
            String sub_data = ID + "___";
            sub_data += bilder.get(ID) + "___";
            sub_data += ort.get(ID) + "___";
            if (schenker.containsKey(ID)) {
                sub_data += schenker.get(ID) + "___";
            }
            sub_data += notizen.get(ID);
            data += sub_data;
            if (i != eintraege) data += ";";
            i++;
        }
        bw.write(data, 0, data.length());
        bw.close();
    }

    public static Image[] getPicturesUnscaled() {
        return pictures_Unscaled;
    }

    public static boolean addData(String data) {
        String[] sub_arr = data.split("___");
        int ID = Integer.parseInt(sub_arr[0]);
        if (bilder.containsKey(ID)) return false;
        bilder.put(ID, sub_arr[1]);
        ort.put(ID, sub_arr[2]);
        if (sub_arr.length == 3) {
            notizen.put(ID, sub_arr[2]);
        } else if (sub_arr.length == 4) {
            schenker.put(ID, sub_arr[2]);
            notizen.put(ID, sub_arr[3]);
        }
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean removeData(int ID) {
        if (!bilder.containsKey(ID)) return false;
        bilder.remove(ID);
        ort.remove(ID);
        if (schenker.containsKey(ID)) schenker.remove(ID);
        notizen.remove(ID);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean editData(String data) {
        String[] arr = data.split("___");
        int ID = Integer.parseInt(arr[0]);
        if (!bilder.containsKey(ID)) return false;
        removeData(ID);
        addData(data);
        return true;
    }
}