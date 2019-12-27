import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

class GUI extends JFrame {
    // Variables
    private static final long serialVersionUID = 1L;
    private JButton button1 = new JButton();
    private static int px_width;
    private static int px_height;
    private Grid g;
    private Image[] imglist;

    // Methods
    public static void main(String[] args) {
        px_width = 1200;
        px_height = 900;
        new GUI();
    }

    public GUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        this.setVisible(true);
        this.setBounds(200, 100, px_width, px_height);
        button1.setBounds(px_width - 300, px_height - 100, 250, 40);
        button1.setVisible(true);
        button1.setText("Bilder laden und anzoigen");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClicked();
            }
        });

        add(button1);
        this.setTitle("Schneekugelbibliothek :)");
        g = new Grid(3, 2, 700, 4, 4);
        g.setBounds(10, 10, g.getWidth(), g.getHeight());
        add(g);

        Speicher.load();
        Image[] oldList = Speicher.getPicturesUnscaled();
        imglist = new Image[oldList.length];

        for (int i = 0; i < imglist.length; i++) {
            Image im = oldList[i].getScaledInstance(g.getPictureWidth(), g.getPictureWidth(), 4);
            imglist[i] = im;
        }
    }

    public void btnClicked() {
        g.setImageList(imglist);
        g.repaint();
        new Insert();
    }

}