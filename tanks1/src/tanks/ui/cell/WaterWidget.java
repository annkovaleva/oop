package tanks.ui.cell;

import tanks.Headquarter;
import tanks.Water;
import tanks.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WaterWidget extends CellItemWidget{

    private final Water water;

    public WaterWidget(Water water) {
        this.water = water;
        setPreferredSize(new Dimension(120,120));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("water.png"));
            image = ImageUtils.resizeImage(image, 120, 120);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.BOTTOM;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(120, 120);
    }
}
