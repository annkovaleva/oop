package tanks.event;

import org.jetbrains.annotations.NotNull;

public interface FieldActionListener {

    void tankFired(@NotNull FieldActionEvent event);

    void tankDestroyObject(@NotNull FieldActionEvent event);
}
