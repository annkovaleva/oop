package tanks;

import java.awt.*;

// Класс Штаб
public class Headquarter extends GameObject {

    private static int LIVE = 1;

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setDestructibility(boolean destructibility) {
        super.setDestructibility(destructibility);
    }

    @Override
    public boolean getDestructibility() {
        return super.getDestructibility();
    }

    @Override
    public Cell getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Cell position) {
        super.setPosition(position);
    }

    private Tank my_tank;

    public Tank getMy_tank() {
        return my_tank;
    }

    public void setMy_tank(Tank my_tank) {
        if(this.my_tank==null) {
            this.my_tank = my_tank;
            my_tank.setMyHeadquarter(this);
        }
    }
}
