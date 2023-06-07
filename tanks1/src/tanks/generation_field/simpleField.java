package tanks.generation_field;

import tanks.*;
import tanks.Point;

import java.awt.*;

public class simpleField extends Game_generation{

    private static final int FIELD_HEIGHT = 5;
    private static final int FIELD_WIDTH = 5;


    @Override
    protected int fieldHeight() {
        return FIELD_HEIGHT;
    }

    @Override
    protected int fieldWidth() {
        return FIELD_WIDTH;
    }

    @Override
    protected void addTanks_and_Headquarter(){

        Tank firstTank = new Tank();
        Tank secondTank = new Tank();

        firstTank.setColor(Color.GREEN);
        firstTank.setDestructibility(true);
        secondTank.setColor(Color.BLUE);
        secondTank.setDestructibility(true);

        Headquarter firstHeadquarter = new Headquarter();
        firstHeadquarter.setDestructibility(true);
        Headquarter secondHeadquarter = new Headquarter();
        secondHeadquarter.setDestructibility(true);

        firstTank.setMyHeadquarter(firstHeadquarter);
        secondTank.setMyHeadquarter(secondHeadquarter);

        field.getCell(new Point(0,4)).setGame_object(firstTank);
        field.getCell(new Point(4,0)).setGame_object(secondTank);

        field.getCell(new Point(0,3)).setGame_object(firstHeadquarter);
        field.getCell(new Point(3,0)).setGame_object(secondHeadquarter);

    }

    @Override
    protected void addWalls() {
        Wall wall = new Wall();

        field.getCell(new Point(0,1)).setGame_object(wall);
        wall.setDestructibility(true);
    }

    @Override
    protected void addWater(){
        Water water = new Water();

        field.getCell(new Point(0,0)).setGame_object(water);
        water.setDestructibility(false);
    }
}
