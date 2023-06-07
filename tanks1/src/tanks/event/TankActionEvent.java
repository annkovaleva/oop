package tanks.event;

import org.jetbrains.annotations.NotNull;
import tanks.*;

import java.util.EventObject;

public class TankActionEvent extends EventObject {

    private Tank tank;
    private GameObject game_object;
    private Projectile myProjectile;

    public void setMyProjectile(Projectile myProjectile) {
        this.myProjectile = myProjectile;
    }

    public Projectile getMyProjectile() {
        return myProjectile;
    }

    public void setGame_object(GameObject game_object) {
        this.game_object = game_object;
    }

    public GameObject getGame_object() {
        return game_object;
    }

    private Cell fromCell;
    private Cell toCell;

    public void setFromCell(Cell fromCell) {
        this.fromCell = fromCell;
    }

    public Cell getFromCell() {
        return fromCell;
    }

    public void setToCell(Cell toCell) {
        this.toCell = toCell;
    }

    public Cell getToCell() {
        return toCell;
    }

    public void setTank(@NotNull Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    public TankActionEvent(Object source) {
        super(source);
    }

}
