package tanks;

import org.jetbrains.annotations.NotNull;
import tanks.event.ProjectileActionEvent;
import tanks.event.ProjectileActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Простой снаряд
public class SimpleProjectile extends Projectile{

    private Cell position;

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public SimpleProjectile(Cell position, int range_of_flight) {
        super(position, range_of_flight);
        setPosition(position);
        setRange_of_flight(range_of_flight);
    }

    private int range_of_flight;

    public int getRange_of_flight() {
        return range_of_flight;
    }

    public void setRange_of_flight(int range_of_flight) {
        this.range_of_flight = range_of_flight;
    }

    Timer t;

    public void fly(Direction direction) {

        final Cell[] newPosition = {canMove(direction)};
        final GameObject[] game_object = {null};

        ActionListener performer = new ActionListener() {
            final int a = 0;
            @Override
            public void actionPerformed(ActionEvent e) {

                if (newPosition[0] != null && t.isRunning()) {
                    try {
                        move(getPosition(), newPosition[0]);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    game_object[0] = newPosition[0].getGame_object();
                    if (game_object[0] != null && game_object[0].getDestructibility()) {
                        newPosition[0].kill();
                        fireProjectileDestroyObject(game_object[0]);
                        t.stop();
                        try {
                            fireProjectile();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    newPosition[0] = canMove(direction);
                    System.out.println("a");
                }
                else {
                    t.stop();
                    try {
                        fireProjectile();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };

        t = new Timer(2000, performer);
        t.addActionListener(performer);
        t.start();
    }

    public void move(Cell oldPosition, Cell newPosition) throws InterruptedException {

        newPosition.setProjectile(this);
        oldPosition.takeProjectile();
        this.setPosition(newPosition);
        fireProjectileIsMoved(oldPosition,newPosition);
    }

    private Cell canMove(@NotNull Direction direction) {
        Cell result = null;
        if(getPosition()!=null){
            Cell neighborCell = getPosition().neighborCell(direction);

            if(neighborCell != null) {
                result = neighborCell;
            }
        }

        return result;
    }

    // events
    private ArrayList<ProjectileActionListener> projectileListListener = new ArrayList<>();

    public void addProjectileActionListener(ProjectileActionListener listener) {
        projectileListListener.add(listener);
    }

    public void removeProjectileActionListener(ProjectileActionListener listener) {
        projectileListListener.remove(listener);
    }

    private void fireProjectileDestroyObject(GameObject game_object) {
        for(ProjectileActionListener listener: projectileListListener) {
            ProjectileActionEvent event = new ProjectileActionEvent(listener);
            event.setProjectile(this);
            event.setGame_object(game_object);
            listener.projectileDestroyObject(event);
        }
    }

    private void fireProjectileIsMoved(Cell oldPosition, Cell newPosition) throws InterruptedException {
        for(ProjectileActionListener listener: projectileListListener) {
            ProjectileActionEvent event = new ProjectileActionEvent(listener);
            event.setProjectile(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.projectileIsMoved(event);
        }
    }

    private void fireProjectile() throws InterruptedException {
        for(ProjectileActionListener listener: projectileListListener) {
            ProjectileActionEvent event = new ProjectileActionEvent(listener);
            event.setProjectile(this);
            listener.projectile(event);
        }
    }
}
