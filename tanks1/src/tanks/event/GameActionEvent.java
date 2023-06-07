package tanks.event;

import org.jetbrains.annotations.NotNull;
import tanks.GameState;
import tanks.Tank;

import java.util.EventObject;

public class GameActionEvent extends EventObject {

    private Tank tank;

    public void setTank(@NotNull Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    private GameState status;

    public GameState getStatus() {
        return status;
    }

    public void setStatus(GameState status) {
        this.status = status;
    }

    public GameActionEvent(Object source) {
        super(source);
    }

}
