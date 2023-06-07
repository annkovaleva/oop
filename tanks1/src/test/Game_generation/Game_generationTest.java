package test.Game_generation;

import tanks.*;
import tanks.generation_field.Game_generation;

public class Game_generationTest extends Game_generation {

    @Override
    protected int fieldHeight() {
        return 5;
    }

    @Override
    protected int fieldWidth() {
        return 5;
    }

    @Override
    protected void addTanks_and_Headquarter(){

        Tank firstTank = new Tank();
        firstTank.setDestructibility(true);
        Tank secondTank = new Tank();
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
        wall.setDestructibility(true);

        field.getCell(new Point(1,0)).setGame_object(wall);
    }

    @Override
    protected void addWater(){
        Water water = new Water();
        water.setDestructibility(false);

        field.getCell(new Point(2,0)).setGame_object(water);
    }
}
