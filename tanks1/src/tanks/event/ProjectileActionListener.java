package tanks.event;

import org.jetbrains.annotations.NotNull;

import java.util.EventListener;

public interface ProjectileActionListener extends EventListener {

    void projectileDestroyObject(@NotNull ProjectileActionEvent event);

    void projectileIsMoved(@NotNull ProjectileActionEvent event) throws InterruptedException;

    void projectile(@NotNull ProjectileActionEvent event);
}
