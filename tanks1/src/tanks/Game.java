package tanks;

import org.jetbrains.annotations.NotNull;
import tanks.event.*;
import tanks.generation_field.Game_generation;

import java.util.ArrayList;
import java.util.List;

// Класс Игра
public class Game {

    private GameState gameStatus;
    private Tank activeTank;
    private Tank winner;
    private Field gameField;
    private Game_generation generation;

    public Game(Game_generation generation) {
        this.generation = generation;

        initGame();
    }

    private void initGame() {
        setStatus(GameState.GAME_IS_ON);

        buildField();

        for(var i : gameField.getTanksOnField()) {
            i.addTankActionListener(new TankObserver());
        }

        passMoveNextTank();
    }

    public void finish() {
        setStatus(GameState.GAME_FINISHED_AHEAD_OF_SCHEDULE);
        setActiveTank(null);
    }

    public GameState status() {
        return gameStatus;
    }

    private void setStatus(GameState status) {
        if(gameStatus != status) {
            gameStatus = status;
            fireGameStatusIsChanged(gameStatus);
        }
    }

    public GameState getGameStatus() {
        return gameStatus;
    }

    public Tank winner() {
        return winner;
    }

    public Tank activeTank() {
        return activeTank;
    }

    public Field getGameField() {
        return gameField;
    }

    private void passMoveNextTank() {
        if(gameStatus != GameState.GAME_IS_ON) {
            setActiveTank(null);
            return;
        }

        List<Tank> tanksOnField = gameField.getTanksOnField();

        if(tanksOnField.size() == 1 ) {
            Tank tank = tanksOnField.get(0);
            if(tank.getLive()) setActiveTank(tank);
        } else if (tanksOnField.size() == 2) {
            Tank firstTank = tanksOnField.get(0);
            Tank secondTank = tanksOnField.get(1);
            if(!firstTank.isActive() && firstTank.getLive()) {
                setActiveTank(firstTank);
            } else if (!secondTank.isActive() && secondTank.getLive()){
                setActiveTank(secondTank);
            }
        }
    }


    private GameState determineOutcomeGame() {
        GameState result = GameState.GAME_IS_ON;

        List<Tank> getTanksOnField = gameField.getTanksOnField();
        List<Headquarter> getHeadquartersOnField = gameField.getHeadquartersOnField();

        if(getTanksOnField.size()==1) {
            setWinner(getTanksOnField.get(0));
            result = GameState.WINNER_FOUND;
        }

        if(getHeadquartersOnField.size()==1) {
            setWinner(getHeadquartersOnField.get(0).getMy_tank());
            result = GameState.WINNER_FOUND;
        }

        return result;
    }

    private void updateGameState() {
        GameState status = determineOutcomeGame();
        setStatus(status);
        if(status == GameState.GAME_IS_ON) {
            passMoveNextTank();
        } else {
            setActiveTank(null);
        }
    }

    private void buildField() {
        gameField = generation.buildField();
    }

    private void setWinner(@NotNull Tank tank) {

        winner = tank;

        setActiveTank(null);
    }

    private void setActiveTank(Tank tank) {

        if (activeTank != null) activeTank.setActive(false);

        activeTank = tank;

        if(tank != null ) tank.setActive(true);
    }

    // events
    private class TankObserver implements TankActionListener {

        @Override
        public void tankIsMoved(@NotNull TankActionEvent event) {
            fireTankIsMoved(event.getTank());
            if(event.getTank().getLive()){
                updateGameState();
            }
        }

        @Override
        public void tankIsSkipStep(@NotNull TankActionEvent event) {
            fireTankIsSkipStep(event.getTank());
            updateGameState();
        }

        @Override
        public void tankFired(@NotNull TankActionEvent event) {
            fireTankFired(event.getTank());
            updateGameState();
        }

        @Override
        public void tankFiredSmart(@NotNull TankActionEvent event) {

        }

        @Override
        public void tankDestroyObject(@NotNull TankActionEvent event) {

        }

        @Override
        public void myProjectileIsMoved(@NotNull TankActionEvent event) {

        }
    }

    private ArrayList<GameActionListener> gameActionListeners = new ArrayList<>();

    public void addGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void removeGameActionListener(@NotNull GameActionListener listener) {
        gameActionListeners.remove(listener);
    }

    private void fireTankIsMoved(@NotNull Tank tank) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setTank(tank);
            listener.tankIsMoved(event);
        }
    }

    private void fireTankIsSkipStep(@NotNull Tank tank) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setTank(tank);
            listener.tankIsSkipStep(event);
        }
    }

    private void fireTankFired(@NotNull Tank tank) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setTank(tank);
            listener.tankFired(event);
        }
    }

    private void fireGameStatusIsChanged(@NotNull GameState status) {
        for(GameActionListener listener: gameActionListeners) {
            GameActionEvent event = new GameActionEvent(listener);
            event.setStatus(status);
            listener.gameStatusChanged(event);
        }
    }
}
