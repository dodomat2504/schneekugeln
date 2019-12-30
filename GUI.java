import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

class GUI extends JFrame {
    // Variables
    private static final long serialVersionUID = 1L;
    private JButton debugButton = new JButton();
    private JButton addButton = new JButton();
    private JButton editButton = new JButton();
    private static int px_width;
    private static int px_height;

    // Methods
    public static void main(String[] args) {
        px_width = 300;
        px_height = 500;
        new GUI();
    }

    public GUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(200, 100, px_width, px_height);
        this.setTitle("Schneekugelbibliothek :)");

        debugButton.setBounds(20, 20, 250, 40);
        debugButton.setVisible(true);
        debugButton.setText("DEBUG-Ausgabe: Data-String");
        debugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClicked();
            }
        });

        addButton.setBounds(debugButton.getX(), debugButton.getY() + 60, 250, 40);
        addButton.setVisible(true);
        addButton.setText("Hinzufuegen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnInsertClicked();
            }
        });

        editButton.setBounds(addButton.getX(), addButton.getY() + 60, 250, 40);
        editButton.setVisible(true);
        editButton.setText("Bearbeiten");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEditClicked();
            }
        });

        add(debugButton);
        add(addButton);
        add(editButton);
        try {
            Speicher.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    protected void btnClicked() {
        System.out.println(Speicher.getData());
    }

    protected void btnInsertClicked() {
        new Insert();
    }

    protected void btnEditClicked() {
        new Edit();
    }

}