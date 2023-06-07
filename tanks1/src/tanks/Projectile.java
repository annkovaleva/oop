package tanks;

// Класс Снаряд
public abstract class Projectile {

    // Позиция
    private Cell position;

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public Projectile(Cell position, int range_of_flight) {
        setPosition(position);
        setRange_of_flight(range_of_flight);
    }

    // Дальность полёта
    private int range_of_flight;

    public int getRange_of_flight() {
        return range_of_flight;
    }

    public void setRange_of_flight(int range_of_flight) {
        this.range_of_flight = range_of_flight;
    }

    // Снаряд летит с указанием направления или ячейки
    public void fly(Direction direction){};

    public void fly(Cell cell) throws InterruptedException {};

    // Переместиться
    public void move(Cell oldPosition, Cell newPosition) throws InterruptedException {

    }
}
