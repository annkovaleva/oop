package tanks.ui.cell;

import tanks.Wall;
import tanks.Water;
import tanks.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WallWidget extends CellItemWidget{
    private final Wall wall;

    public WallWidget(Wall wall) {
        this.wall = wall;
        setPreferredSize(new Dimension(120,120));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("wall.png"));
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
