package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tanks.*;

import static org.junit.jupiter.api.Assertions.*;

// Тесты Ячейки
class CellTest {

    private Cell cell;

    public CellTest() {
    }


    @BeforeEach
    public void testSetup() {

        cell = new Cell();
    }

    @Test
    public void test_setTank_InEmptyCell() {
        Tank tank = new Tank();

        cell.setGame_object(tank);

        assertEquals(tank, cell.getGame_object());
        assertEquals(cell, tank.getPosition());
    }

    @Test
    public void test_takeTank_FromCellWithTank() {
        Tank tank = new Tank();

        cell.setGame_object(tank);

        assertEquals(tank, cell.takeGame_object());
        assertNull(tank.getPosition());
        assertNull(cell.getGame_object());
    }


    @Test
    public void test_setTank_ToCellWithTank() {
        Tank tank = new Tank();
        Tank newTank = new Tank();

        cell.setGame_object(tank);

        assertThrows(IllegalArgumentException.class, () -> cell.setGame_object(newTank));
        assertEquals(tank, cell.getGame_object());
        assertEquals(cell, tank.getPosition());
        assertNull(newTank.getPosition());
    }

    @Test
    public void test_setNeighborCell() {
        Cell neighborCell = new Cell();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);

        assertEquals(neighborCell, cell.neighborCell(direction));
        assertEquals(cell, neighborCell.neighborCell(direction.getOppositeDirection()));
    }


    @Test
    public void test_setNeighborCell_doubleSided() {
        Cell neighborCell = new Cell();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        neighborCell.setNeighbor(cell, direction.getOppositeDirection());
        assertEquals(neighborCell, cell.neighborCell(direction));
        assertEquals(cell, neighborCell.neighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_setNeighborCell_twoTimesInOneDirection() {
        Cell neighborCell = new Cell();
        Cell anotherCell = new Cell();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertThrows(IllegalArgumentException.class, () -> cell.setNeighbor(anotherCell, direction));
        assertEquals(neighborCell, cell.neighborCell(direction));
        assertEquals(cell, neighborCell.neighborCell(direction.getOppositeDirection()));
    }

    @Test
    public void test_isNeighbor_WhenNeighborCellExists() {
        Cell neighborCell = new Cell();
        Direction direction = Direction.NORTH;

        cell.setNeighbor(neighborCell, direction);
        assertEquals(direction, cell.isNeighbor(neighborCell));
    }

    @Test
    public void test_isNeighbor_WhenNeighborCellNotExists() {
        Cell neighborCell = new Cell();

        assertNull(cell.isNeighbor(neighborCell));
    }
}