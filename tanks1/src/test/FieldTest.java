package test;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tanks.*;
import tanks.event.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// Тесты Поля
class FieldTest {

    private int eventCount = 0;

    class FieldObserver implements FieldActionListener {

        @Override
        public void tankDestroyObject(@NotNull FieldActionEvent event) {
            eventCount += 1;
        }

        @Override
        public void tankFired(@NotNull FieldActionEvent event) {

        }
    }

    private Field field;

    @BeforeEach
    public void testSetup() {
        eventCount = 0;
        field = new Field(2, 2);
        field.addFieldActionListener(new FieldObserver());
    }

    @Test
    public void test_create_withCorrectParams() {
        Cell cell_0_0 = field.getCell(new Point(0, 0));
        Cell cell_0_1 = field.getCell(new Point(1, 0));
        Cell cell_1_0 = field.getCell(new Point(0, 1));
        Cell cell_1_1 = field.getCell(new Point(1, 1));

        assertEquals(Direction.SOUTH, cell_0_0.isNeighbor(cell_1_0));
        assertEquals(Direction.SOUTH, cell_0_1.isNeighbor(cell_1_1));
        assertEquals(Direction.NORTH, cell_1_1.isNeighbor(cell_0_1));
        assertEquals(Direction.NORTH, cell_1_0.isNeighbor(cell_0_0));
        assertEquals(Direction.EAST, cell_0_0.isNeighbor(cell_0_1));
        assertEquals(Direction.EAST, cell_1_0.isNeighbor(cell_1_1));
        assertEquals(Direction.WEST, cell_0_1.isNeighbor(cell_0_0));
        assertEquals(Direction.WEST, cell_1_1.isNeighbor(cell_1_0));
    }

    @Test
    public void test_create_withNegativeWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(-1, 1));
    }

    @Test
    public void test_create_withZeroWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Field(0, 1));
    }

    @Test
    public void test_create_withNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, -1));
    }

    @Test
    public void test_create_withZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Field(1, 0));
    }

    @Test
    public void test_getTanksOnField_empty() {
        assertTrue(field.getTanksOnField().isEmpty());
    }

    @Test
    public void test_getTanksOnField_oneTank() {
        Tank tank = new Tank();
        field.getCell(new Point(0, 0)).setGame_object(tank);

        assertTrue(field.getTanksOnField().contains(tank));
        assertEquals(1, field.getTanksOnField().size());
    }

    @Test
    public void test_getTanksOnField_severalTanks() {
        Tank robot = new Tank();
        Tank anotherTank = new Tank();
        field.getCell(new Point(0, 0)).setGame_object(robot);
        field.getCell(new Point(1, 0)).setGame_object(anotherTank);

        assertTrue(field.getTanksOnField().containsAll(Arrays.asList(robot, anotherTank)));
        assertEquals(2, field.getTanksOnField().size());
    }
}