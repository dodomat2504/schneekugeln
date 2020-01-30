import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

class Speicher {
    private static HashMap<Integer, String> bilder = new HashMap<Integer, String>();
    private static HashMap<Integer, String> ort = new HashMap<Integer, String>();
    private static HashMap<Integer, String> schenker = new HashMap<Integer, String>();
    private static HashMap<Integer, String> notizen = new HashMap<Integer, String>();

    public static void load() throws IOException {
        File db = new File("db.txt");
        if (!db.exists()) {
            db.createNewFile();
            return;
        }
        FileReader fr = new FileReader(db);
        BufferedReader br = new BufferedReader(fr);
        String readline = "";
        String[] data_arr;
        if ((readline = br.readLine()) == null) {
            br.close();
            return;
        }
        br.close();
        data_arr = readline.split(";");
        for (int i = 0; i < data_arr.length; i++) {
            String[] sub_arr = data_arr[i].split("___");
            int ID = Integer.parseInt(sub_arr[0]);
            bilder.put(ID, sub_arr[1]);
            ort.put(ID, sub_arr[2]);
            if (sub_arr.length == 4) {
                notizen.put(ID, sub_arr[3]);
            } else if (sub_arr.length == 5) {
                schenker.put(ID, sub_arr[3]);
                notizen.put(ID, sub_arr[4]);
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

    public static String getData() {
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
        return data;
    }

    public static Object[] getIdList() {
        if (bilder.keySet().size() == 0) return null;
        Object[] list = new Object[bilder.keySet().size()];
        int i = 0;
        for (int key : bilder.keySet()) {
            list[i] = key;
            i++;
        }
        return sort(list);
    }

    private static boolean isSorted(Object[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (Integer.parseInt(arr[i-1].toString()) > Integer.parseInt(arr[i].toString())) return false;
        }
        return true;
    }

    private static Object[] sort(Object[] arr) {
        while (!isSorted(arr)){
            for (int i = 1; i < arr.length; i++) {
                if (Integer.parseInt(arr[i-1].toString()) > Integer.parseInt(arr[i].toString())) {
                    Object zw = arr[i];
                    arr[i] = arr[i-1];
                    arr[i-1] = zw;
                }
            }   
        }
        return arr;
    }

    public static String getData(int ID) {
        if (!bilder.containsKey(ID)) return null;
        String data = "";
        data += ID + "___";
        data += bilder.get(ID) + "___";
        data += ort.get(ID) + "___";
        if (schenker.containsKey(ID)) {
            data += schenker.get(ID) + "___";
        }
        data += notizen.get(ID);
        return data;
    }

    public static HashMap<Integer, String> getBildPaths() {
        return bilder;
    }

    public static boolean addData(String data) {
        String[] sub_arr = data.split("___");
        int ID = Integer.parseInt(sub_arr[0]);
        if (bilder.containsKey(ID)) return false;
        bilder.put(ID, sub_arr[1]);
        ort.put(ID, sub_arr[2]);
        if (sub_arr.length == 4) {
            notizen.put(ID, sub_arr[3]);
        } else if (sub_arr.length == 5) {
            schenker.put(ID, sub_arr[3]);
            notizen.put(ID, sub_arr[4]);
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