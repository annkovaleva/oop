package tanks.ui.cell;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class CellItemWidget extends JPanel {

    public CellItemWidget() {
        setOpaque(false);
    }

    protected abstract BufferedImage getImage();

    public abstract CellWidget.Layer getLayer();

    protected abstract Dimension getDimension();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getImage(), 0, -5, null);
    }
}
