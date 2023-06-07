package tanks.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface GameActionListener extends EventListener {

    void tankIsMoved(@NotNull GameActionEvent event);

    void tankIsSkipStep(@NotNull GameActionEvent event);

    void tankFired(@NotNull GameActionEvent event);

    void gameStatusChanged(@NotNull GameActionEvent event);
}
