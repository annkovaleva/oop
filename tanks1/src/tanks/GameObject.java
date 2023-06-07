package tanks;
public abstract class GameObject {

    private boolean destructibility;

    public boolean getDestructibility() {
        return destructibility;
    }

    void setDestructibility(boolean destructibility) {
        this.destructibility = destructibility;
    }

    private Cell position;

    public Cell getPosition() {
        return position;
    }

    void setPosition(Cell position) {
        this.position = position;
    }
}
