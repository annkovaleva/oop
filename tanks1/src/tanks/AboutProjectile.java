package tanks;

import org.jetbrains.annotations.NotNull;
import tanks.event.ProjectileActionEvent;
import tanks.event.ProjectileActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AboutProjectile extends Projectile{

    private Cell position;

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    private int range_of_flight;

    public int getRange_of_flight() {
        return range_of_flight;
    }

    public void setRange_of_flight(int range_of_flight) {
        this.range_of_flight = range_of_flight;
    }

    public AboutProjectile(Cell position, int range_of_flight) {
        super(position, range_of_flight);
    }

    public void fly(Direction direction) throws InterruptedException {

        boolean flag = true;

        Cell newPosition = canMove(Direction.NORTH);

        flag = projectileFlight(2, newPosition, true, Direction.NORTH);

        newPosition = canMove(Direction.EAST);

        flag = projectileFlight(2, newPosition,flag, Direction.EAST);

        newPosition = canMove(Direction.SOUTH);

        flag = projectileFlight(5, newPosition,flag, Direction.SOUTH);

        newPosition = canMove(Direction.WEST);

        flag = projectileFlight(5, newPosition,flag, Direction.WEST);

        newPosition = canMove(Direction.NORTH);

        flag = projectileFlight(5, newPosition,flag, Direction.NORTH);

        newPosition = canMove(Direction.EAST);

        flag = projectileFlight(2, newPosition,flag, Direction.EAST);
    }

    private boolean projectileFlight(int a, Cell newPosition, boolean flag, Direction dir) throws InterruptedException {

        GameObject game_object;

        for (int i = 0; i<a; i++){
            if(flag) {
                if (newPosition != null) {
                    move(getPosition(), newPosition);
                    game_object = newPosition.getGame_object();
                    if (game_object != null && game_object.getDestructibility()) {
                        newPosition.kill();
                        fireProjectileDestroyObject(game_object);
                        fireProjectile();
                        return false;
                    }
                } else {
                    fireProjectile();
                    return false;
                }
                newPosition = canMove(dir);
            }
        }
        return flag;
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
