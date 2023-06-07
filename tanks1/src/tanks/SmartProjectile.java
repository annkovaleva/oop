package tanks;

import tanks.event.ProjectileActionEvent;
import tanks.event.ProjectileActionListener;

import javax.swing.*;
import java.util.ArrayList;

// Умный снаряд
public class SmartProjectile extends Projectile{

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

    public SmartProjectile(Cell position, int range_of_flight) {
        super(position, range_of_flight);
        setPosition(position);
        setRange_of_flight(range_of_flight);
    }

    Timer t;

    public void fly(Cell cell) throws InterruptedException {

        WaveNavigator navigator = new WaveNavigator(cell.getField());
        ArrayList <Cell> array = navigator.findPath(getPosition(), cell);


        /*ActionListener performer = new ActionListener() {
            int a = 0;
            @Override
            public void actionPerformed(ActionEvent e) {

                if(t.isRunning() && a<array.size()-1 && getRange_of_flight()>0){
                    try {
                        move(array.get(a), array.get(a + 1));
                        System.out.println("a");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    range_of_flight--;
                    a++;
                }
                else {
                    kill_cross();
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
        t.start();*/



        for(int i = 0; i<array.size()-1; i++) {
            if (getRange_of_flight() > 0) {

                move(array.get(i), array.get(i+1));
                range_of_flight--;
            }
        }

        if(array.size()!=0) {
            kill_cross();
        }
    }

    private void kill_cross() {
        if(getPosition().getGame_object() != null && getPosition().getGame_object().getDestructibility()) {
            GameObject game_object = getPosition().getGame_object();
            getPosition().kill();
            fireProjectileDestroyObject(game_object);
        }

        if(getPosition().neighborCell(Direction.SOUTH) != null){
            if(getPosition().neighborCell(Direction.SOUTH).getGame_object() != null && getPosition().neighborCell(Direction.SOUTH).getGame_object().getDestructibility()) {
                GameObject game_object = getPosition().getGame_object();
                getPosition().neighborCell(Direction.SOUTH).kill();
                fireProjectileDestroyObject(game_object);
            }
        }
        if(getPosition().neighborCell(Direction.NORTH) != null){
            if(getPosition().neighborCell(Direction.NORTH).getGame_object() != null && getPosition().neighborCell(Direction.NORTH).getGame_object().getDestructibility()) {
                GameObject game_object = getPosition().getGame_object();
                getPosition().neighborCell(Direction.NORTH).kill();
                fireProjectileDestroyObject(game_object);
            }
        }
        if(getPosition().neighborCell(Direction.WEST) != null){
            if(getPosition().neighborCell(Direction.WEST).getGame_object() != null && getPosition().neighborCell(Direction.WEST).getGame_object().getDestructibility()) {
                GameObject game_object = getPosition().getGame_object();
                getPosition().neighborCell(Direction.WEST).kill();
                fireProjectileDestroyObject(game_object);
            }
        }
        if(getPosition().neighborCell(Direction.EAST) != null){
            if(getPosition().neighborCell(Direction.EAST).getGame_object() != null && getPosition().neighborCell(Direction.EAST).getGame_object().getDestructibility()) {
                GameObject game_object = getPosition().getGame_object();
                getPosition().neighborCell(Direction.EAST).kill();
                fireProjectileDestroyObject(game_object);
            }
        }
    }

    public void move(Cell oldPosition, Cell newPosition) throws InterruptedException {

        newPosition.setProjectile(this);
        oldPosition.takeProjectile();
        this.setPosition(newPosition);
        fireProjectileIsMoved(oldPosition,newPosition);
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
