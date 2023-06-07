package tanks.ui.cell;

import org.jetbrains.annotations.NotNull;
import tanks.Cell;
import tanks.Direction;
import tanks.Tank;
import tanks.ui.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Виджет Танка
public class TankWidget extends CellItemWidget {

    private final Tank tank;
    private final Color color;

    public TankWidget(Tank tank, Color color) {
        super();
        this.tank = tank;
        this.color = color;
        setFocusable(true);
        setPreferredSize(new Dimension(120,120));
        addKeyListener(new KeyController());
    }

    @Override
    protected BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getTankFileByColor(color, tank.isActive()));
            image = switch (tank.getDirection()) {
                case SOUTH -> rotate(image, 1.5708);
                case WEST -> rotate(image, 3.14159);
                case NORTH -> rotate(image, 4.71239);
                default -> image;
            };
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

    public Color getColor() {
        return color;
    }

    private static File getTankFileByColor(Color tankColor, boolean active) {
        File file = null;
        if (tankColor == Color.BLUE)  {
            if(active) {
                file = new File("tank_blue_a.png");
            }
            else {
                file = new File("tank_blue.png");
            }
        }
        if (tankColor == Color.GREEN) {
            if(active){
                file = new File("tank_green_a.png");
            }
            else {
                file = new File("tank_green.png");
            }
        }
        return file;
    }

    public static BufferedImage rotate(BufferedImage image, double angle) {
        BufferedImage bufImg = toBufferedImage(image);
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = bufImg.getWidth(), h = bufImg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(bufImg, null);
        g.dispose();
        return result;
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage buff = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buff.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return buff;
    }

    private class KeyController implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int keyCode = ke.getKeyCode();

            directionByKeyCode(keyCode);
            try {
                moveAction(keyCode);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            skipStepAction(keyCode);
            try {
                fired(keyCode);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            skipProjectile(keyCode);
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        private void skipStepAction(@NotNull int keyCode) {
            if(keyCode == KeyEvent.VK_Z) {
                tank.skipStep();
                System.out.println(color + " skip step");
            }
        }

        private void moveAction(@NotNull int keyCode) throws InterruptedException {
            if(keyCode == KeyEvent.VK_SPACE) {
                Direction direction = tank.getDirection();
                System.out.println(color + " go to " + direction);
                if (direction != null && tank.isActive()) {
                    tank.move(direction);
                }
            }
        }

        private void fired(@NotNull int keyCode) throws InterruptedException {
            if(keyCode == KeyEvent.VK_F){
                Direction direction = tank.getDirection();
                System.out.println(color + " fire to " + direction);
                if (direction != null && tank.isActive()) {
                    if(tank.getProjectile()==0){
                        tank.shootDir();
                    }
                    if(tank.getProjectile()==1){
                        tank.shootCell();
                    }
                    if(tank.getProjectile()==2){
                        tank.shootDir();
                    }
                }
            }
        }

        private void directionByKeyCode(@NotNull int keyCode) {
            switch (keyCode) {
                case KeyEvent.VK_W:
                    tank.setDirection(Direction.NORTH);
                    break;
                case KeyEvent.VK_S:
                    tank.setDirection(Direction.SOUTH);
                    break;
                case KeyEvent.VK_A:
                    tank.setDirection(Direction.WEST);
                    break;
                case KeyEvent.VK_D:
                    tank.setDirection(Direction.EAST);
                    break;
            }
        }

        private int a = 0;
        private JLabel label = new JLabel("Снаряд: простой");

        private void skipProjectile(@NotNull int keyCode){

            if(a==0) {
                label.setFont(new Font("Verdana", 1, 10));
                add(label);
                a++;
            }

            if(keyCode == KeyEvent.VK_X){
                tank.setProjectile();

                if(tank.getProjectile()==1) {
                    label.setText("Снаряд: умный");
                }
                if(tank.getProjectile()==0) {
                    label.setText("Снаряд: простой");
                }
                if(tank.getProjectile()==2) {
                    label.setText("Снаряд: окружной");
                }
            }
        }
    }

}
