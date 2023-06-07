package tanks.generation_field;

import tanks.Field;

import java.awt.*;

public abstract class Game_generation {

    protected Field field;

    public Field buildField() {

        field = new Field(fieldWidth(), fieldHeight());

        addTanks_and_Headquarter();
        addWalls();
        addWater();

        return field;
    }

    protected abstract int fieldHeight();

    protected abstract int fieldWidth();

    protected abstract void addTanks_and_Headquarter();

    protected abstract void addWalls();

    protected abstract void addWater();
}
