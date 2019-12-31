import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Galerie extends JFrame {
    private static final long serialVersionUID = 1L;
    private Grid g;
    private JButton backButton = new JButton();
    private JButton forwardButton = new JButton();
    private JButton gotoButton = new JButton();
    private JTextField tf = new JTextField();
    private Font writeFont;

    public Galerie(int site) {
        final int sidelength = 900;
        final int xOffset = 10;
        final int yOffset = 10;
        writeFont = new Font("Arial", 0, 18);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Galerie - Seite " + site);
        this.setBackground(new Color(255, 255, 255, 255));
        this.setBounds(400, 20, sidelength + 15, sidelength + 38 + 60);
        this.setVisible(true);

        if (Speicher.getIdList() == null) {
            this.dispose();
            JOptionPane.showMessageDialog(null, "Es sind noch keine Bilder vorhanden.", "Hinweis",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        final int pic_count = Speicher.getIdList().length;
        if (pic_count - (site - 1) * 9 <= 0) {
            this.dispose();
            JOptionPane.showMessageDialog(null, "Das hier sollte nicht passieren...", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        final int pic_count_this_site = pic_count - (site - 1) * 9 > 9 ? 9 : pic_count - (site - 1) * 9;
        final int pic_count_next_site = pic_count - (site - 1) * 9 - pic_count_this_site;
        int r = 1;
        int c = 1;
        boolean col = true;
        while (r * c < pic_count_this_site) {
            if (col) {
                c++;
                col = !col;
            } else {
                r++;
                col = !col;
            }
        }
        g = new Grid(c, r, sidelength, xOffset, yOffset);
        g.setBounds(0, 0, sidelength, sidelength);
        Image[] imglist = new Image[pic_count_this_site];
        int i = 0;
        int index = 0;
        for (int ID : Speicher.getBildPaths().keySet()) {
            if (i >= (site - 1) * 9 && i <= (site * 9 - 1)) {
                imglist[index] = Bild.getScaledImage(Speicher.getBildPaths().get(ID), g.getPictureWidth());
                index++;
            }
            i++;
        }
        g.setImageList(imglist);
        g.setOpaque(false);
        g.repaint();

        int column = 1;
        int row = 1;
        Object[] obj_arr = Speicher.getIdList();
        for (i = 0; i < obj_arr.length; i++) {
            if (i >= (site - 1) * 9 && i <= (site * 9 - 1)) {
                Object obj = obj_arr[i];
                JTextArea ta = new JTextArea();
                int ID = Integer.parseInt(obj.toString());
                String[] data = Speicher.getData(ID).split("___");
                String ort = data[2] + "\n";
                String schenker = data.length == 5 ? "Geschenkt von: \n" + data[3] + "\n" : "";
                String notiz = data[data.length - 1];

                int xRes = xOffset + (column - 1) * (2 * xOffset + g.getPictureWidth());
                int yRes = yOffset + (row - 1) * (2 * yOffset + g.getPictureWidth());

                ta.setBounds(xRes, yRes, g.getPictureWidth(), g.getPictureWidth());
                ta.setVisible(true);
                ta.setEditable(false);
                ta.setBackground(new Color(0, 0, 0, 200));
                ta.setForeground(Color.white);
                ta.setFont(writeFont);
                ta.setOpaque(false);
                ta.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ta.setText("ID: " + ID + "\nOrt: " + ort + schenker + "Notiz: " + notiz);
                        ta.setOpaque(true);
                        System.out.println("Mouse entered");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ta.setText("");
                        ta.setOpaque(false);
                        System.out.println("Mouse exited");
                    }
                });

                add(ta);

                column++;
                if (column > c) {
                    row++;
                    column = 1;
                }
                if (row > r) {
                    break;
                }
            }
        }

        forwardButton.setBounds(getWidth() - 180, sidelength + 10, 150, 40);
        forwardButton.setText("Naechste Seite");
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSite(site + 1);
            }
        });

        backButton.setBounds(30, sidelength + 10, 150, 40);
        backButton.setText("Vorherige Seite");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSite(site - 1);
            }
        });

        tf.setBounds(getWidth() / 2 + 35, sidelength + 15, 40, 30);
        tf.setVisible(true);

        gotoButton.setBounds(getWidth() / 2 - 80, sidelength + 10, 100, 40);
        gotoButton.setText("Seite:");
        gotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gotoSite();
            }
        });

        add(g);
        if (pic_count_next_site > 0) {
            add(forwardButton);
        }
        if (site - 1 > 0) {
            add(backButton);
        }
        add(gotoButton);
        add(tf);
    }

    protected void openSite(int site) {
        new Galerie(site);
        this.dispose();
    }

    protected void gotoSite() {
        try {
            int site = Integer.parseInt(tf.getText());
            if (Speicher.getIdList().length - (site - 1) * 9 > 0) {
                openSite(site);
            } else {
                JOptionPane.showMessageDialog(null, "Auf dieser Seite sind keine Bilder vorhanden!", "Hinweis", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Nur Zahlen eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

}