package tanks;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

// Класс Ячейка
public class Cell {

    // Игровой объект
    private GameObject game_object;

    public GameObject getGame_object() { return game_object; }

    public GameObject takeGame_object(){
        game_object.setPosition(null);
        var tmp = game_object;
        game_object = null;
        return tmp;
    }

    // Поле
    private Field field;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void kill() {
        if(this.game_object.getDestructibility()) {
            this.game_object = null;
        }
    }

    public void setGame_object(GameObject game_object) {
        if(getGame_object() != null) throw new IllegalArgumentException("In cell already set game_object");

        game_object.setPosition(this);
        this.game_object = game_object;
    }

    // Снаряд

    private Projectile projectile;

    public Projectile getProjectile() {
        return projectile;
    }

    public Projectile takeProjectile() {
        projectile.setPosition(null);
        var tmp = projectile;
        projectile = null;
        return tmp;
    }

    public void setProjectile(Projectile projectile) {
        if(getProjectile() != null) throw new IllegalArgumentException("In cell already set projectile");

        projectile.setPosition(this);
        this.projectile = projectile;
    }

    // neighbors cell
    private Map<Direction, Cell> neighborCells = new EnumMap<>(Direction.class);

    public Cell neighborCell(@NotNull Direction direction) {
        return neighborCells.get(direction);
    }

    public void setNeighbor(@NotNull Cell cell, @NotNull Direction direction) {
        if (neighborCells.containsKey(direction) && neighborCells.containsValue(cell)) return;
        if (neighborCells.containsKey(direction)) throw new IllegalArgumentException();
        neighborCells.put(direction, cell);
        if (cell.neighborCell(direction.getOppositeDirection()) == null) {
            cell.setNeighbor(this, direction.getOppositeDirection());
        }
    }

    public Direction isNeighbor(Cell cell) {
        if(cell != null) {
            for (var i : neighborCells.entrySet()) {
                if (i.getValue().equals(cell)) return i.getKey();
            }
        }
        return null;
    }

    public ArrayList<Direction> neighbors(){

        ArrayList<Direction> array = new ArrayList<>();
        Direction d1 = isNeighbor(neighborCell(Direction.NORTH));
        if(d1 != null){
            array.add(d1);
        }
        Direction d2 = isNeighbor(neighborCell(Direction.SOUTH));
        if(d2 != null){
            array.add(d2);
        }
        Direction d3 = isNeighbor(neighborCell(Direction.WEST));
        if(d3 != null){
            array.add(d3);
        }
        Direction d4 = isNeighbor(neighborCell(Direction.EAST));
        if(d4 != null){
            array.add(d4);
        }

        return array;
    }
}
