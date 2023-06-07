package tanks;

import org.jetbrains.annotations.NotNull;
import tanks.event.*;

import java.util.*;
import java.util.List;

// Класс Поле
public class Field{

    private Map<Point, Cell> cells = new HashMap<>();

    public ArrayList<Cell> cells_ = new ArrayList<>();

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Field(int width, int height) {
        if(width <= 0) throw new IllegalArgumentException("Field width must be more than 0");
        if(height <= 0) throw new IllegalArgumentException("Field height must be more than 0");

        this.width = width;
        this.height = height;

        setupField();
    }

    private void setupField() {
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                Point p = new Point(x, y);
                Cell cell = new Cell();
                cell.setField(this);
                if(x > 0) getCell(new Point((p.getX() - 1), p.getY())).setNeighbor(cell, Direction.EAST);
                if(y > 0) getCell(new Point( p.getX(), (p.getY() - 1))).setNeighbor(cell, Direction.SOUTH);
                cells.put(p, cell);
                cells_.add(cell);
            }
        }
    }

    public Cell getCell(@NotNull Point point) {
        return cells.get(point);
    }

    public List<Tank> getTanksOnField() {
        List<Tank> tanks = new ArrayList<>();
        for(var i : cells.entrySet()) {
            GameObject tank = i.getValue().getGame_object();
            if(tank instanceof Tank tank_) {
                tanks.add(tank_);
            }
        }
        return tanks;
    }

    public List<Headquarter> getHeadquartersOnField() {
        List<Headquarter> headquarters = new ArrayList<>();
        for(var i : cells.entrySet()) {
            GameObject headquarter = i.getValue().getGame_object();
            if(headquarter instanceof Headquarter headquarter_) {
                headquarters.add(headquarter_);
            }
        }
        return headquarters;
    }

    public void removeProjectile(){
        for(var i : cells.entrySet()) {
            Projectile projectile = i.getValue().getProjectile();
            if(projectile != null) {
                projectile.getPosition().takeProjectile();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return width == field.width &&
                height == field.height &&
                Objects.equals(cells, field.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells, width, height);
    }

    @Override
    public String toString() {
        return "Field{" +
                "cells=" + cells +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    //events

    private ArrayList<FieldActionListener> fieldListListener = new ArrayList<>();

    public void addFieldActionListener(FieldActionListener listener) {
        fieldListListener.add(listener);
    }

    public void removeFieldCellActionListener(FieldActionListener listener) {
        fieldListListener.remove(listener);
    }

    private void fireTankDestroyObject(GameObject game_object) {
        for(FieldActionListener listener: fieldListListener) {
            FieldActionEvent event = new FieldActionEvent(listener);
            event.setGameObject(game_object);
            listener.tankFired(event);
        }
    }
}
