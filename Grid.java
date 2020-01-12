import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class Grid extends JPanel {
    private static final long serialVersionUID = 1L;
    private int r;
    private int c;
    private int xOffset;
    private int yOffset;
    private Image[] imgs;
    private Color bgColor;

    public Grid(int columns, int rows, int max_width) {
        this.r = rows;
        this.c = columns;
        setSize(max_width, max_width);
        this.xOffset = 1;
        this.yOffset = 1;
        this.bgColor = Color.white;
    }

    public Grid(int columns, int rows, int max_width, int xOffset, int yOffset) {
        this.r = rows;
        this.c = columns;
        setSize(max_width, max_width);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.bgColor = Color.white;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (imgs != null && imgs.length > 0) {
            setBackground(this.bgColor);
            final int pictureWidth = getPictureWidth();
            int k = 0;
            for (int i = 1; i <= r; i++) {
                int yRes = yOffset + (i - 1) * (2 * yOffset + pictureWidth);
                for (int j = 1; j <= c; j++) {
                    if (k + 1 > imgs.length) break;
                    int xRes = xOffset + (j - 1) * (2 * xOffset + pictureWidth);
                    g.drawImage(imgs[k], xRes, yRes, null);
                    k++;
                }
            }
        } else {
            setBackground(Color.red);
            g.drawLine(0, 0, getWidth(), getHeight());
            g.drawLine(0, getHeight(), getWidth(), 0);
        }
    }

    public void setFieldBorder(int sides, int top_bottom) {
        this.xOffset = sides;
        this.yOffset = top_bottom;
    }

    public void setImageList(Image[] images) {
        this.imgs = images;
    }

    public int getPictureWidth() {
        return ((int) Math.floor(getWidth() / c)) - xOffset * 2;
    }

    public void setBgColor(Color c) {
        this.bgColor = c;
    }

}