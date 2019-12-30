import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Edit extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField ortField = new JTextField();
    private JLabel ortLabel = new JLabel();
    private JLabel BildBtnLabel = new JLabel();
    private JLabel schenkerLabel = new JLabel();
    private JLabel notizenLabel = new JLabel();
    private JCheckBox geschenk = new JCheckBox();
    private JTextField schenker = new JTextField();
    private JButton saveBtn = new JButton();
    private JButton deleteBtn = new JButton();
    private JButton addBildBtn = new JButton();
    private JTextField ID = new JTextField();
    private JFileChooser fileChooser = new JFileChooser();
    private Font writeFont;
    private JTextField notizen = new JTextField();
    private JLabel IDLabel = new JLabel();
    private Grid grid;

    public Edit() {
        setBounds(100, 100, 500, 500);
        setBackground(Color.white);
        setTitle("Bearbeiten");
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        writeFont = new Font("Arial", 0, 18);
        grid = new Grid(1, 1, 300, 0, 0);

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
        ID.setEditable(false);
        IDLabel.setBounds(ID.getX(), ID.getY() - 25, 50, 30);
        IDLabel.setText("ID:");

        fileChooser.setFileFilter(new FileNameExtensionFilter("Bilder", "png", "jpg", "jpeg"));
        fileChooser.setApproveButtonText("Auswählen");

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

        saveBtn.setBounds(getWidth() - 200, getHeight() - 100, 180, 55);
        saveBtn.setVisible(true);
        saveBtn.setText("Speichern");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSpeichernClicked();
            }
        });
        deleteBtn.setBounds(saveBtn.getX() - 200, getHeight() - 100, 180, 55);
        deleteBtn.setVisible(true);
        deleteBtn.setText("Loeschen");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnDeleteClicked();
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
        add(saveBtn);
        add(deleteBtn);

        updateFields();
    }

    protected void geschenkClicked() {
        schenker.setVisible(geschenk.isSelected());
        schenkerLabel.setVisible(geschenk.isSelected());
    }

    private void updateFields() {
        if (Speicher.getIdList() == null) {
            this.dispose();
            JOptionPane.showMessageDialog(null, "Es sind aktuell noch keine Schneekugeln hinzugefuegt worden.", "Hinweis", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object input = JOptionPane.showInputDialog(null, "Waehle die ID der Schneekugel aus, die du bearbeiten moechtest.", "Bearbeiten", JOptionPane.INFORMATION_MESSAGE, null, Speicher.getIdList(), Speicher.getIdList()[0]);
        try {
            int ID = Integer.parseInt(input.toString());
            String[] data = Speicher.getData(ID).split("___");
            String bild_path = data[1];
            String ort = data[2];
            String notizen = data[data.length - 1];
            String schenker = "";
            if (data.length == 5) schenker = data[3];
            fileChooser.setCurrentDirectory(new File(bild_path).getParentFile());
            fileChooser.setSelectedFile(new File(bild_path));
            Image[] imglist = {ImageIO.read(new File(bild_path)).getScaledInstance(grid.getPictureWidth(), grid.getPictureWidth(), 4)};
            grid.setImageList(imglist);
            grid.repaint();
            this.ID.setText(ID + "");
            this.ortField.setText(ort);
            this.notizen.setText(notizen);
            if (!schenker.equalsIgnoreCase("")) {
                geschenk.setSelected(true);
                geschenkClicked();
                this.schenker.setText(schenker);
            }
        } catch (NumberFormatException | IOException | NullPointerException ex) {
            updateFields();
        }
    }

    protected void btnBildHinzufuegenClicked() {
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
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

    protected void btnSpeichernClicked() {
        if (ortField.getText().equalsIgnoreCase("") || ortField.getText().equalsIgnoreCase(" ")) {
            JOptionPane.showMessageDialog(saveBtn, "Ort muss angegeben werden!", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (fileChooser.getSelectedFile() == null) {
            JOptionPane.showMessageDialog(saveBtn, "Du musst ein Bild angeben!", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (geschenk.isSelected() && (schenker.getText().equalsIgnoreCase("") || schenker.getText().equalsIgnoreCase(" "))) {
            JOptionPane.showMessageDialog(saveBtn, "Von wem hast du es bekommen? Oder ist es doch kein Geschenk?", "Wichtiger Hinweis!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (notizen.getText().equalsIgnoreCase("")) {
            notizen.setText(" ");
        }
        String data = "";
        data += ID.getText() + "___";
        data += fileChooser.getSelectedFile().getPath() + "___";
        data += ortField.getText() + "___";
        if (geschenk.isSelected()) data += schenker.getText() + "___";
        data += notizen.getText();
        boolean result = Speicher.editData(data);
        if (!result) {
            System.out.println("Es gab ein Problem beim Speichern!");
        } else {
            this.dispose();
        }
    }

    protected void btnDeleteClicked() {
        int result = JOptionPane.showConfirmDialog(deleteBtn, "Moechtest du die Schneekugel wirklich loeschen?", "Loeschen", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Speicher.removeData(Integer.parseInt(ID.getText()));
            this.dispose();
        }
    }

}