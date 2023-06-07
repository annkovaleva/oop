package tanks.ui.cell;

import tanks.Headquarter;
import tanks.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Виджет Штаба
public class HeadquarterWidget extends CellItemWidget{


    private final Headquarter headquarter;

    private final Color color;

    public HeadquarterWidget(Headquarter headquarter, Color color) {
        this.headquarter = headquarter;
        this.color = color;
        setPreferredSize(new Dimension(120,120));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getHeadquarterImageFileByColor(color));
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

    private static File getHeadquarterImageFileByColor(Color headquarterColor) {
        File file = null;
        if (headquarterColor == Color.BLUE)  {
            file =  new File("head_blue.png");
        }
        if (headquarterColor == Color.GREEN) {
            file = new File("head_green.png");
        }
        return file;
    }
}
