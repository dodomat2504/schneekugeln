import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Insert extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField ortField = new JTextField();
    private JLabel label = new JLabel();
    private JCheckBox geschenk = new JCheckBox();
    private JTextField schenker = new JTextField();
    private JButton addBtn = new JButton();
    private JTextField ID = new JTextField();

    public Insert() {
        setBounds(100, 100, 600, 400);
        setBackground(Color.white);
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ortField.setBounds(50, 70, 200, 50);
        ortField.setVisible(true);
        ortField.setEditable(true);
        label.setText("Wo komme ich her?");
        label.setBounds(5, 0, ortField.getWidth() - 50, ortField.getHeight() / 2);
        label.setVisible(true);
        ortField.add(label);

        add(ortField);
    }
    
}