import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Insert extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField ortField = new JTextField();
    private JLabel ortLabel = new JLabel();
    private JLabel BildBtnLabel = new JLabel();
    private JLabel schenkerLabel = new JLabel();
    private JLabel notizenLabel = new JLabel();
    private JCheckBox geschenk = new JCheckBox();
    private JTextField schenker = new JTextField();
    private JButton addBtn = new JButton();
    private JButton addBildBtn = new JButton();
    private JTextField ID = new JTextField();
    private JFileChooser fileChooser = new JFileChooser();
    private Font writeFont;
    private JTextField notizen = new JTextField();
    private JLabel IDLabel = new JLabel();
    private Grid grid;
    private static String current_path;

    public Insert() {
        setBounds(100, 100, 500, 500);
        setBackground(Color.white);
        setTitle("Hinzufuegen");
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        writeFont = new Font("Arial", 0, 18);
        grid = new Grid(1, 1, 300, 0, 0);
        if (current_path == null || current_path.equals("")) current_path = System.getProperty("user.dir");

        ortField.setBounds(40, 100, 150, 40);
        ortField.setFont(writeFont);
        ortField.setVisible(true);
        ortField.setEditable(true);
        ortLabel.setText("Wo komme ich her?");
        ortLabel.setBounds(ortField.getX(), ortField.getY() - ortField.getHeight() / 2, ortField.getWidth(),
                ortField.getHeight() / 2);
        ortLabel.setVisible(true);

        ID.setBounds(ortField.getX(), ortField.getY() - 65, 50, 40);
        ID.setFont(writeFont);
        ID.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {textUeberpruefen();}
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });
        IDLabel.setBounds(ID.getX(), ID.getY() - 25, 50, 30);
        IDLabel.setText("ID:");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Bilder", "png", "jpg", "jpeg"));
        fileChooser.setApproveButtonText("Auswaehlen");

        addBildBtn.setBounds(ortField.getX(), ortField.getY() + 65, 60, 30);
        addBildBtn.setText("Bild");
        addBildBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBildHinzufuegenClicked();
            }
        });
        BildBtnLabel.setBounds(addBildBtn.getX(), addBildBtn.getY() - 20, addBildBtn.getWidth() * 2, 20);
        BildBtnLabel.setText("Wie sehe ich aus?");

        addBtn.setBounds(getWidth() - 200, getHeight() - 100, 180, 55);
        addBtn.setVisible(true);
        addBtn.setText("Hinzufuegen");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAddClicked();
            }
        });

        geschenk.setBounds(addBildBtn.getX() - 5, addBildBtn.getY() + 30, 150, 60);
        geschenk.setText("Bin ich ein Geschenk?");
        geschenk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                geschenkClicked();
            }
        });
        schenker.setBounds(geschenk.getX() + 5, geschenk.getY() + 80, 150, 40);
        schenker.setVisible(false);
        schenker.setFont(writeFont);
        schenkerLabel.setBounds(schenker.getX(), schenker.getY() - 30, schenker.getWidth(), 30);
        schenkerLabel.setText("Und von wem?");
        schenkerLabel.setVisible(false);

        notizen.setBounds(geschenk.getX() + 5, geschenk.getY() + 160, getWidth() - 2 * (geschenk.getX() + 5), 40);
        notizen.setFont(writeFont);
        notizenLabel.setBounds(notizen.getX(), notizen.getY() - 30, 70, 30);
        notizenLabel.setText("Notizen:");

        grid.setBounds(200, 0, 300, 300);
        grid.setBackground(Color.white);

        add(ortLabel);
        add(ID);
        add(IDLabel);
        add(ortField);
        add(BildBtnLabel);
        add(addBildBtn);
        add(geschenk);
        add(schenker);
        add(schenkerLabel);
        add(notizen);
        add(notizenLabel);
        add(grid);
        add(addBtn);
    }

    protected void textUeberpruefen() {
        String al = "abcdefghijklmnopqrstuvwxyzäöü,.-#+´ß^°!§$%&/()=?`*'_:;<>/*-+";
        String s = ID.getText();
        boolean ok = true;
        for (int i = 0; i < al.length(); i++) {
            if (s.contains(al.charAt(i) + "") || s.contains((al.charAt(i) + "").toUpperCase())) {
                ok = false;
                break;
            }
        }
        if (!ok) {
            ID.setText("");
            JOptionPane.showMessageDialog(ID, "Nur Zahlen! Ansonsten explodiert das Programm :O", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
        }
    }

    protected void geschenkClicked() {
        schenker.setVisible(geschenk.isSelected());
        schenkerLabel.setVisible(geschenk.isSelected());
    }

    protected void btnBildHinzufuegenClicked() {
        fileChooser.setCurrentDirectory(new File(current_path));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            current_path = fileChooser.getCurrentDirectory().getPath();
            File auswahl = fileChooser.getSelectedFile();
            try {
                Image[] imglist = {ImageIO.read(auswahl).getScaledInstance(grid.getPictureWidth(), grid.getPictureWidth(), 4)};
                grid.setImageList(imglist);
                grid.repaint();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fehler beim Bild malen");
            }
        }
    }

    protected void btnAddClicked() {
        if (ID.getText().equalsIgnoreCase("") || ID.getText().equalsIgnoreCase(" ")) {
            JOptionPane.showMessageDialog(addBtn, "ID muss angegeben werden!", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ortField.getText().equalsIgnoreCase("") || ortField.getText().equalsIgnoreCase(" ")) {
            JOptionPane.showMessageDialog(addBtn, "Ort muss angegeben werden!", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fileChooser.getSelectedFile() == null) {
            JOptionPane.showMessageDialog(addBtn, "Du musst ein Bild angeben!", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (geschenk.isSelected() && (schenker.getText().equalsIgnoreCase("") || schenker.getText().equalsIgnoreCase(" "))) {
            JOptionPane.showMessageDialog(addBtn, "Von wem hast du es bekommen? Oder ist es doch kein Geschenk?", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (notizen.getText().equalsIgnoreCase("") || notizen.getText().equalsIgnoreCase(" ")) {
            notizen.setText(" ");
        }
        String data = "";
        data += ID.getText() + "___";
        data += fileChooser.getSelectedFile().getPath() + "___";
        data += ortField.getText() + "___";
        if (geschenk.isSelected()) data += schenker.getText() + "___";
        data += notizen.getText();
        boolean result = Speicher.addData(data);
        if (!result) {
            if (Speicher.getData(Integer.parseInt(ID.getText())) != null) JOptionPane.showMessageDialog(addBtn, "Diese ID ist schon vergeben!", "Hinweis", JOptionPane.ERROR_MESSAGE);
            System.out.println("Es gab ein Problem beim Speichern!");
        } else {
            this.dispose();
        }
    }
    
}