package tanks.ui;

import org.jetbrains.annotations.NotNull;
import tanks.*;
import tanks.Point;
import tanks.event.TankActionEvent;
import tanks.event.TankActionListener;
import tanks.ui.cell.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Виджет Поля
public class FieldWidget extends JPanel {

    private final Field field;
    private final WidgetFactory widgetFactory;

    public FieldWidget(@NotNull Field field, @NotNull  WidgetFactory widgetFactory) {
        this.field = field;
        this.widgetFactory = widgetFactory;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fillField();
        subscribeOnTanks();
    }

    private JTextField textFieldX = new JTextField("",SwingConstants.CENTER);

    private JTextField textFieldY = new JTextField("", SwingConstants.CENTER);

    private JButton button = new JButton("Сохранить");

    public void labelEnabled(){
        button.setEnabled(true);
        textFieldX.setEnabled(true);
        textFieldY.setEnabled(true);
    }

    private void fillField() {

        JLabel jlabel = new JLabel("Приятной игры!");
        jlabel.setFont(new Font("Verdana",1,30));
        add(jlabel);
        setBorder(new LineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < field.getHeight(); ++i) {
            JPanel row = createRow(i);
            add(row);
        }

        JLabel label_about = new JLabel("Введите координаты ячейки:", SwingConstants.CENTER);
        label_about.setFont(new Font("Verdana", 1, 20));
        setBorder(new LineBorder(Color.BLACK));
        add(label_about);

        textFieldX.setCaretColor(Color.GREEN);
        textFieldX.setBackground(Color.LIGHT_GRAY);
        textFieldX.setFont(new Font("Verdana", 1, 20));
        add(textFieldX);

        textFieldY.setCaretColor(Color.GREEN);
        textFieldY.setBackground(Color.LIGHT_GRAY);
        textFieldY.setFont(new Font("Verdana", 1, 20));
        add(textFieldY);

        button.setBackground(Color.PINK);
        button.setFont(new Font("Verdana", 1, 20));
        add(button);

        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Verdana", 1, 20));
        setBorder(new LineBorder(Color.BLACK));
        add(label);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = Integer.parseInt(textFieldX.getText());
                    int y = Integer.parseInt(textFieldY.getText());
                    Cell cell = field.getCell(new Point(x,y));
                    field.getTanksOnField().get(0).setProjectileCell(cell);
                    field.getTanksOnField().get(1).setProjectileCell(cell);
                    if(x>=field.getWidth() || y>=field.getHeight() || x<-1 || y<-1){
                        label.setText("Введите первое число до " + field.getWidth() + ", а второе до " + field.getHeight());
                        repaint();
                    }
                    else {
                        label.setText("Вы стреляете в ячейку: " + textFieldX.getText() + ", " + textFieldY.getText());
                        button.setEnabled(false);
                        textFieldX.setEnabled(false);
                        textFieldY.setEnabled(false);
                        repaint();
                    }
                } catch (NumberFormatException a) {
                    label.setText("Введите первое число до " + field.getWidth() + ", а второе до " + field.getHeight());
                    repaint();
                }
            }
        });
    }

    private JPanel createRow(int rowIndex) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for(int i = 0; i < field.getWidth(); ++i) {
            Point point = new Point(i, rowIndex);
            Cell cell = field.getCell(point);
            CellWidget cellWidget = widgetFactory.create(cell);
            cellWidget.setBorder(new LineBorder(Color.black));

            row.add(cellWidget);
        }
        return row;
    }

    private void subscribeOnTanks() {
        List<Tank> tanks = field.getTanksOnField();
        for(Tank tank : tanks) {
            tank.addTankActionListener(new TankController());
        }
    }

    private class TankController implements TankActionListener {

        @Override
        public void tankIsMoved(@NotNull TankActionEvent event) {
            TankWidget tankWidget = widgetFactory.getWidget(event.getTank());
            CellWidget from = widgetFactory.getWidget(event.getFromCell());
            CellWidget to = widgetFactory.getWidget(event.getToCell());
            from.removeItem(tankWidget);
            to.addItem(tankWidget);
            repaint();
        }

        @Override
        public void tankIsSkipStep(@NotNull TankActionEvent event) {
            Tank tank = event.getTank();
            TankWidget tankWidget = widgetFactory.getWidget(tank);
            CellWidget from = widgetFactory.getWidget(event.getTank().getPosition());
            from.removeItem(tankWidget);
            from.addItem(tankWidget);
            repaint();
        }

        @Override
        public void tankFired(@NotNull TankActionEvent event) {
            Tank tank = event.getTank();
            TankWidget tankWidget = widgetFactory.getWidget(tank);
            CellWidget from = widgetFactory.getWidget(event.getTank().getPosition());
            from.removeItem(tankWidget);
            from.addItem(tankWidget);

            repaint();
        }

        @Override
        public void tankFiredSmart(@NotNull TankActionEvent event) {
            labelEnabled();
        }

        @Override
        public void tankDestroyObject(@NotNull TankActionEvent event) {
            GameObject game_object = event.getGame_object();

            if(game_object instanceof Tank tank){
                TankWidget tankWidget = widgetFactory.getWidget(tank);
                CellWidget cell = widgetFactory.getWidget(game_object.getPosition());
                cell.removeItem(tankWidget);
                tankWidget.repaint();
            }

            if(game_object instanceof Headquarter headquarter){
                HeadquarterWidget headquarterWidget = widgetFactory.getWidget(headquarter);
                CellWidget cell = widgetFactory.getWidget(game_object.getPosition());
                cell.removeItem(headquarterWidget);
                headquarterWidget.repaint();
            }

            if(game_object instanceof Wall wall){
                WallWidget wallWidget = widgetFactory.getWidget(wall);
                CellWidget cell = widgetFactory.getWidget(game_object.getPosition());
                cell.removeItem(wallWidget);
                wallWidget.repaint();
            }

            if(game_object instanceof Water water){
                WaterWidget waterWidget = widgetFactory.getWidget(water);
                CellWidget cell = widgetFactory.getWidget(game_object.getPosition());
                cell.removeItem(waterWidget);
                waterWidget.repaint();
            }
        }

        @Override
        public void myProjectileIsMoved(@NotNull TankActionEvent event) throws InterruptedException {
            ProjectileWidget projectileWidget = widgetFactory.create(event.getMyProjectile());
            if (event.getFromCell() != null) {
                CellWidget from = widgetFactory.getWidget(event.getFromCell());
                from.removeItem(projectileWidget);
                repaint();

            }
            if (event.getToCell() != null){
                CellWidget to = widgetFactory.getWidget(event.getToCell());
                to.addItem(projectileWidget);
                repaint();
            }
        }
    }
}
