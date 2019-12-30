import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

class GUI extends JFrame {
    // Variables
    private static final long serialVersionUID = 1L;
    private JButton button1 = new JButton();
    private JButton button2 = new JButton();
    private static int px_width;
    private static int px_height;

    // Methods
    public static void main(String[] args) {
        px_width = 1200;
        px_height = 900;
        new GUI();
    }

    public GUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(200, 100, px_width, px_height);
        this.setTitle("Schneekugelbibliothek :)");

        button1.setBounds(px_width - 300, px_height - 100, 250, 40);
        button1.setVisible(true);
        button1.setText("DEBUG-Ausgabe: Data-String");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClicked();
            }
        });

        button2.setBounds(px_width - 300, px_height - 160, 250, 40);
        button2.setVisible(true);
        button2.setText("Hinzufuegen");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnInsertClicked();
            }
        });

        add(button1);
        add(button2);
        try {
            Speicher.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void btnClicked() {
        System.out.println(Speicher.getData());
    }

    public void btnInsertClicked() {
        new Insert();
    }

}