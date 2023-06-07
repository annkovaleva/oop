package tanks.event;

import org.jetbrains.annotations.NotNull;
import tanks.Cell;
import tanks.GameObject;
import tanks.Projectile;

import java.util.EventObject;

public class ProjectileActionEvent extends EventObject {

    private Projectile projectile;

    private GameObject game_object;

    public void setGame_object(GameObject game_object) {
        this.game_object = game_object;
    }

    public GameObject getGame_object() {
        return game_object;
    }

    public void setProjectile(@NotNull Projectile projectile) {
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return projectile;
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

    public ProjectileActionEvent(Object source) {
        super(source);
    }
}
