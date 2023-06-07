package tanks.ui.cell;

import tanks.ui.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// Виджет Ячейки
public class CellWidget extends JPanel {

    public enum Layer {
        TOP,
        BOTTOM
    }

    private Map<Layer, CellItemWidget> items = new HashMap();

    private static final int CELL_SIZE = 120;

    public CellWidget() {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(ImageUtils.BACKGROUND_COLOR);
        setSize(new Dimension(120,120));
    }

    public void addItem(CellItemWidget item) {
        if(items.size() > 2) throw new IllegalArgumentException();
        int index = -1;

        if (items.containsKey(Layer.TOP)) {
            index = 0;
        }

        items.put(item.getLayer(), item);
        add(item, index);
    }

    public void removeItem(CellItemWidget item) {
        if (items.containsValue(item)) {
            int index = 0;

            if(item.getLayer() == Layer.TOP) {
                if(items.containsKey(Layer.BOTTOM)) {
                    index = 1;
                }
            }

            remove(index);
            items.remove(item.getLayer());
            repaint();
        }
    }
}
