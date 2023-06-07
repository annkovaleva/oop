package tanks;

import org.jetbrains.annotations.NotNull;
import tanks.event.*;

import java.awt.*;
import java.util.ArrayList;

// Класс Танк
public class Tank extends GameObject {

    // Жизни танка
    private int lives = 3;

    public boolean getLive() {
        return lives > 0;
    }

    // Ячейка для выстрела
    Cell projectileCell;

    public void setProjectileCell(Cell projectileCell) {
        this.projectileCell = projectileCell;
    }

    public Cell getProjectileCell() {
        return projectileCell;
    }

    // Порядковый номер заряженного снаряда
    private int projectile = 0;

    public void setProjectile() {
        this.projectile++;
        if(this.projectile>2)
        {
            this.projectile=0;
        }
    }

    public int getProjectile() {
        return projectile;
    }

    // Возможность стрельнуть
    private int numberShoot = 3;

    // Цвет команды
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // Направление дула
    private Direction direction = Direction.NORTH;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    // Разрушимость
    @Override
    public void setDestructibility(boolean destructibility) {
        super.setDestructibility(destructibility);
    }

    @Override
    public boolean getDestructibility() {
        return super.getDestructibility();
    }

    // Позиция
    @Override
    public Cell getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Cell position) {
        super.setPosition(position);
    }

    // Мой штаб
    private Headquarter myHeadquarter;

    public Headquarter getMyHeadquarter() {
        return myHeadquarter;
    }

    public void setMyHeadquarter(Headquarter myHeadquarter) {
        if(this.myHeadquarter ==null) {
            this.myHeadquarter = myHeadquarter;
            myHeadquarter.setColor(this.color);
            myHeadquarter.setMy_tank(this);
        }
    }

    // Активность танка
    private boolean isActive;

    public void setActive(boolean value) {
        isActive = value;
    }

    public boolean isActive() {
        return isActive;
    }

    // Перемещение на одну клетку
    public void move(@NotNull Direction direction) throws InterruptedException {
        if(isActive) {
            Cell oldPosition = this.getPosition();
            Cell newPosition = canMove(direction);
            if (newPosition != null) {
                getPosition().takeGame_object();
                newPosition.setGame_object(this);
                fireTankIsMoved(oldPosition, newPosition);
                numberShoot++;
            }
        }
    }

    // Стрелять по направлению
    public void shootDir() throws InterruptedException {
        if(isActive && numberShoot >=3) {
            switch (projectile) {
                case (0) -> {
                    SimpleProjectile projectile = new SimpleProjectile(this.getPosition(), 100);
                    projectile.addProjectileActionListener(new ProjectileObserver());
                    getPosition().setProjectile(projectile);
                    projectile.fly(this.direction);
                }
                case (2) -> {
                    AboutProjectile aboutProjectile = new AboutProjectile(this.getPosition(), 10000);
                    aboutProjectile.addProjectileActionListener(new ProjectileObserver());
                    getPosition().setProjectile(aboutProjectile);
                    aboutProjectile.fly(this.direction);
                }
            }
        }
    }

    // Стрелять в ячейку
    public void shootCell() throws InterruptedException {
        if(isActive && numberShoot >=3 && projectileCell!=null) {
            SmartProjectile projectile = new SmartProjectile(this.getPosition(), 10);
            projectile.addProjectileActionListener(new ProjectileObserver());
            getPosition().setProjectile(projectile);
            projectile.fly(this.projectileCell);
            getPosition().getField().removeProjectile();
            fireTankFiredSmart();
            fireTankFired();
            numberShoot = 0;
        }
    }

    // Пропустить ход
    public void skipStep() {
        if(isActive) {
            fireTankIsSkipStep();
        }
    }

    // Могу ли переместиться в ячейку по направлению
    private Cell canMove(@NotNull Direction direction) {
        Cell result = null;

        Cell neighborCell = getPosition().neighborCell(direction);
        if(neighborCell != null && canStayAtPosition(neighborCell)) {
            result = neighborCell;
        }

        return result;
    }

    // Есть ли в ячейке объект
    public static boolean canStayAtPosition(@NotNull Cell position) {
        return position.getGame_object() == null;
    }

    // events
    private class ProjectileObserver implements ProjectileActionListener{

        @Override
        public void projectileDestroyObject(@NotNull ProjectileActionEvent event) {
            GameObject game_object = event.getGame_object();
            fireTankDestroy(game_object);
        }

        @Override
        public void projectileIsMoved(@NotNull ProjectileActionEvent event) throws InterruptedException {
            fireMyProjectileIsMoved(event.getFromCell(), event.getToCell(), event.getProjectile());
        }

        @Override
        public void projectile(@NotNull ProjectileActionEvent event) {
            getPosition().getField().removeProjectile();
            fireTankFired();
            numberShoot = 0;
        }
    }

    private ArrayList<TankActionListener> tankListListener = new ArrayList<>();

    public void addTankActionListener(TankActionListener listener) {
        tankListListener.add(listener);
    }

    public void removeTankActionListener(TankActionListener listener) {
        tankListListener.remove(listener);
    }

    private void fireTankIsMoved(@NotNull Cell oldPosition, @NotNull Cell newPosition) {
        for(TankActionListener listener: tankListListener) {
            TankActionEvent event = new TankActionEvent(listener);
            event.setTank(this);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.tankIsMoved(event);
        }
    }

    private void fireTankIsSkipStep() {
        for(TankActionListener listener: tankListListener) {
            TankActionEvent event = new TankActionEvent(listener);
            event.setTank(this);
            listener.tankIsSkipStep(event);
        }
    }

    private void fireTankFired(){
        for(TankActionListener listener: tankListListener) {
            TankActionEvent event = new TankActionEvent(listener);
            event.setTank(this);
            listener.tankFired(event);
        }
    }

    private void fireTankFiredSmart(){
        for(TankActionListener listener: tankListListener) {
            TankActionEvent event = new TankActionEvent(listener);
            event.setTank(this);
            listener.tankFiredSmart(event);
        }
    }

    private void fireTankDestroy(GameObject game_object){
        for(TankActionListener listener: tankListListener) {
            TankActionEvent event = new TankActionEvent(listener);
            event.setTank(this);
            event.setGame_object(game_object);
            listener.tankDestroyObject(event);
        }
    }

    private void fireMyProjectileIsMoved(Cell oldPosition, Cell newPosition, Projectile projectile) throws InterruptedException {
        for(TankActionListener listener: tankListListener) {
            TankActionEvent event = new TankActionEvent(listener);
            event.setMyProjectile(projectile);
            event.setFromCell(oldPosition);
            event.setToCell(newPosition);
            listener.myProjectileIsMoved(event);
        }
    }


}
