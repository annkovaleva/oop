package tanks.ui.cell;

import tanks.SmartProjectile;
import tanks.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Класс виджета Умного Снаряда
public class SmartProjectileWidget extends ProjectileWidget {

    private final SmartProjectile projectile;

    public SmartProjectileWidget(SmartProjectile projectile) {
        this.projectile = projectile;
        setPreferredSize(new Dimension(120,120));
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("smart_projectile.png"));
            image = ImageUtils.resizeImage(image, 60, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public CellWidget.Layer getLayer() {
        return CellWidget.Layer.TOP;
    }

    @Override
    protected Dimension getDimension() {
        return new Dimension(60, 60);
    }
}
