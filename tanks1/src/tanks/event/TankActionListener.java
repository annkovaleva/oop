package tanks.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface TankActionListener extends EventListener {

    void tankIsMoved(@NotNull TankActionEvent event);

    void tankIsSkipStep(@NotNull TankActionEvent event);

    void tankFired(@NotNull TankActionEvent event);

    void tankFiredSmart(@NotNull TankActionEvent event);

    void tankDestroyObject(@NotNull TankActionEvent event);

    void myProjectileIsMoved(@NotNull TankActionEvent event) throws InterruptedException;
}
