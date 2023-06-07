package tanks.ui.cell;

import tanks.Projectile;
import tanks.Water;
import tanks.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Класс виджет Снаряда
public abstract class ProjectileWidget extends CellItemWidget{


    @Override
    protected BufferedImage getImage() {
        return null;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return null;
    }

    @Override
    protected Dimension getDimension() {
        return null;
    }

    private static File getProjectileImageFile() {
        return null;
    }

}
