package tanks.event;

import org.jetbrains.annotations.NotNull;
import tanks.GameObject;

import java.util.EventObject;

public class FieldActionEvent extends EventObject {

    private GameObject gameObject;

    public void setGameObject(@NotNull GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public FieldActionEvent(Object source) {
        super(source);
    }
}
