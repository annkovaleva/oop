package tanks.ui;

import org.jetbrains.annotations.NotNull;
import tanks.*;
import tanks.ui.cell.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Фабрика виджетов
public class WidgetFactory {

    private final Map<Cell, CellWidget> cells = new HashMap<>();
    private final Map<Tank, TankWidget> tanks = new HashMap<>();
    private final Map<Headquarter, HeadquarterWidget> headquarters = new HashMap<>();
    private final Map<Water, WaterWidget> waters = new HashMap<>();
    private final Map<Wall, WallWidget> walls = new HashMap<>();
    private final Map<Projectile, ProjectileWidget> projectiles = new HashMap<>();
    private final List<Color> usedColors = new ArrayList<>();

    public CellWidget create(@NotNull Cell cell) {
        if(cells.containsKey(cell)) return cells.get(cell);

        CellWidget item = new CellWidget();

        GameObject game_object = cell.getGame_object();

        Projectile projectile = cell.getProjectile();

        if(game_object instanceof Tank _tank) {
            TankWidget tankWidget = create(_tank);
            item.addItem(tankWidget);
        }

        if(game_object instanceof Headquarter _headquarter) {
            HeadquarterWidget headquarterWidget = create(_headquarter);
            item.addItem(headquarterWidget);
        }

        if(game_object instanceof Water _water) {
            WaterWidget waterWidget = create(_water);
            item.addItem(waterWidget);
        }

        if(game_object instanceof Wall _wall) {
            WallWidget wallWidget = create(_wall);
            item.addItem(wallWidget);
        }

        cells.put(cell, item);
        return item;
    }

    public CellWidget getWidget(@NotNull Cell cell) {
        return cells.get(cell);
    }

    public TankWidget create(@NotNull Tank tank) {
        if(tanks.containsKey(tank)) return tanks.get(tank);

        Color color = tank.getColor();
        usedColors.add(color);

        TankWidget item = new TankWidget(tank, color);
        tanks.put(tank, item);
        return item;
    }

    public TankWidget getWidget(@NotNull Tank tank) {
        return tanks.get(tank);
    }

    public HeadquarterWidget create(@NotNull Headquarter headquarter) {
        if(headquarters.containsKey(headquarter)) return headquarters.get(headquarter);

        Color color = headquarter.getColor();
        usedColors.add(color);

        HeadquarterWidget item = new HeadquarterWidget(headquarter, color);
        headquarters.put(headquarter, item);
        return item;
    }

    public HeadquarterWidget getWidget(@NotNull Headquarter headquarter) {
        return headquarters.get(headquarter);
    }

    public WallWidget create(@NotNull Wall wall) {
        if(walls.containsKey(wall)) return walls.get(wall);

        WallWidget item = new WallWidget(wall);
        walls.put(wall, item);
        return item;
    }

    public WallWidget getWidget(@NotNull Wall wall) {
        return walls.get(wall);
    }

    public WaterWidget create(@NotNull Water water) {
        if(waters.containsKey(water)) return waters.get(water);

        WaterWidget item = new WaterWidget(water);
        waters.put(water, item);
        return item;
    }

    public WaterWidget getWidget(@NotNull Water water) {
        return waters.get(water);
    }

    public ProjectileWidget create(@NotNull Projectile projectile) {
        if(projectiles.containsKey(projectile)) return projectiles.get(projectile);

        if(projectile instanceof SmartProjectile smartProjectile) {
            SmartProjectileWidget item = new SmartProjectileWidget(smartProjectile);
            projectiles.put(projectile, item);
            return item;
        }

        if(projectile instanceof SimpleProjectile simpleProjectile) {
            SimpleProjectileWidget item = new SimpleProjectileWidget(simpleProjectile);
            projectiles.put(projectile, item);
            return item;
        }

        return null;
    }

    public ProjectileWidget getWidget(@NotNull Projectile projectile) {
        return projectiles.get(projectile);
    }
}