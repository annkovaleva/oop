package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tanks.event.TankActionEvent;
import tanks.event.TankActionListener;

import java.util.ArrayList;
import java.util.List;
import tanks.*;

import static org.junit.jupiter.api.Assertions.*;

// Тесты Танка
public class TankTest {

    enum EVENT {TANK_MOVED, TANK_SKIP_STEP}

    private List<EVENT> events = new ArrayList<>();
    private List<EVENT> expectedEvents = new ArrayList<>();

    private class EventsListener implements TankActionListener {

        @Override
        public void tankIsMoved(@NotNull TankActionEvent event) {
            events.add(EVENT.TANK_MOVED);
        }

        @Override
        public void tankIsSkipStep(@NotNull TankActionEvent event) {
            events.add(EVENT.TANK_SKIP_STEP);
        }

        @Override
        public void tankFired(@NotNull TankActionEvent event) {

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

    private Cell cell;
    private Cell neighborCell;
    private final Direction direction = Direction.NORTH;

    private Tank tank;

    @BeforeEach
    public void testSetup() {
        // clean events
        events.clear();
        expectedEvents.clear();

        // create tank
        tank = new Tank();
        tank.setActive(true);
        tank.addTankActionListener(new EventsListener());

        // create field
        cell = new Cell();
        neighborCell = new Cell();
        cell.setNeighbor(neighborCell, direction);
    }

    @Test
    public void test_setActiveAndIsActive() {
        tank.setActive(true);

        assertTrue(tank.isActive());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_emptyCell() {
        assertTrue(Tank.canStayAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_canStayAtPosition_cellWithTank() {
        cell.setGame_object(tank);

        assertFalse(Tank.canStayAtPosition(cell));
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndTankActive() throws InterruptedException {
        cell.setGame_object(tank);

        tank.move(direction);

        expectedEvents.add(EVENT.TANK_MOVED);

        assertEquals(tank, neighborCell.getGame_object());
        assertEquals(neighborCell, tank.getPosition());
        assertNull(cell.getGame_object());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_move_noCellInDirectionAndTankActive() throws InterruptedException {
        neighborCell.setGame_object(tank);

        tank.move(Direction.NORTH);

        assertEquals(neighborCell, tank.getPosition());
        assertEquals(tank, neighborCell.getGame_object());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_move_emptyCellInDirectionAndTankNotActive() throws InterruptedException {
        cell.setGame_object(tank);

        tank.setActive(false);
        tank.move(direction);

        assertEquals(tank, cell.getGame_object());
        assertEquals(cell, tank.getPosition());
        assertNull(neighborCell.getGame_object());
        assertTrue(events.isEmpty());
    }

    @Test
    public void test_skipStep_tankActive() {
        cell.setGame_object(tank);

        tank.skipStep();

        expectedEvents.add(EVENT.TANK_SKIP_STEP);

        assertEquals(cell, tank.getPosition());
        assertEquals(tank, cell.getGame_object());
        assertEquals(expectedEvents, events);
    }

    @Test
    public void test_skipStep_tankNotActive() {
        cell.setGame_object(tank);

        tank.setActive(false);

        tank.skipStep();

        assertEquals(cell, tank.getPosition());
        assertEquals(tank, cell.getGame_object());
        assertTrue(events.isEmpty());
    }

}
