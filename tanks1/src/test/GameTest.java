package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tanks.*;
import tanks.event.*;
import test.Game_generation.Game_generationTest;
import utils.Pare;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Тесты Игры
public class GameTest {
    private Game game;

    private enum Event {TANK_MOVED, TANK_SKIP_STEP, TANK_FIRED}

    private List<Pare<Event, Tank>> events = new ArrayList<>();
    private List<Pare<Event, Tank>> expectedEvents = new ArrayList<>();

    class EventListener implements GameActionListener {

        @Override
        public void tankIsMoved(@NotNull GameActionEvent event) {
            events.add(new Pare<>(Event.TANK_MOVED, event.getTank()));
        }

        @Override
        public void tankIsSkipStep(@NotNull GameActionEvent event) {
            events.add(new Pare<>(Event.TANK_SKIP_STEP, event.getTank()));
        }

        @Override
        public void tankFired(@NotNull GameActionEvent event) {
            events.add(new Pare<>(Event.TANK_FIRED, event.getTank()));
        }

        @Override
        public void gameStatusChanged(@NotNull GameActionEvent event) {

        }
    }

    @BeforeEach
    public void testSetup() {
        events.clear();
        expectedEvents.clear();

        game = new Game(new Game_generationTest());
        game.addGameActionListener(new EventListener());
    }

    @Test
    public void test_finishGame() {
        game.finish();

        assertEquals(GameState.GAME_FINISHED_AHEAD_OF_SCHEDULE, game.status());
    }

    @Test
    public void test_tankMoved_success() throws InterruptedException {
        Tank tank = game.activeTank();
        expectedEvents.add(new Pare<>(Event.TANK_MOVED, tank));

        game.activeTank().move(Direction.EAST);

        assertNotEquals(tank, game.activeTank());
        assertFalse(tank.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_tankMoved_incorrectDirection() throws InterruptedException {
        Tank tank = game.activeTank();
        game.activeTank().move(Direction.WEST);

        assertEquals(tank, game.activeTank());
        assertTrue(tank.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_tankSkipStep() {
        Tank tank = game.activeTank();
        expectedEvents.add(new Pare<>(Event.TANK_SKIP_STEP, tank));

        game.activeTank().skipStep();

        assertNotEquals(tank, game.activeTank());
        assertFalse(tank.isActive());
        assertEquals(expectedEvents, events);
        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_winnerFound() throws InterruptedException {
        Tank tank = game.activeTank();

        game.activeTank().move(Direction.EAST);
        expectedEvents.add(new Pare<>(Event.TANK_MOVED, tank));

        tank = game.activeTank();

        game.activeTank().move(Direction.WEST);

        tank = game.activeTank();

        game.activeTank().setDirection(Direction.WEST);
        game.activeTank().shootDir();

        assertEquals(expectedEvents, events);
        assertTrue(tank.isActive());
        assertNotEquals(tank, game.winner());
    }
}
