package test;

import org.junit.Test;
import tanks.*;
import test.Game_generation.Game_generationTest;

import static org.junit.jupiter.api.Assertions.*;

// Тест Умного Снаряда
public class SmartProjectileTest {

    @Test
    public void test_shoot_in_free_cell() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();
        tank.setProjectileCell(game.getGameField().getCell(new Point(1,1)));
        tank.shootCell();

        assertEquals(GameState.GAME_IS_ON, game.status());

        // Проверка на убийство крестом, то есть соседи должны быть уничтожены
        assertNull(game.getGameField().getCell(new Point(1, 0)).getGame_object());
    }

    @Test
    public void test_shoot_in_cell_with_headquarter() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();
        tank.setProjectileCell(game.getGameField().getCell(new Point(3,0)));
        tank.shootCell();

        assertEquals(GameState.WINNER_FOUND, game.status());


        assertNull(game.getGameField().getCell(new Point(4, 0)).getGame_object());
        assertNotNull(game.getGameField().getCell(new Point(2, 0)).getGame_object());
    }

    @Test
    public void test_shoot_in_cell_with_tank() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();
        tank.setProjectileCell(game.getGameField().getCell(new Point(4,0)));
        tank.shootCell();

        assertEquals(GameState.WINNER_FOUND, game.status());


        assertNull(game.getGameField().getCell(new Point(3, 0)).getGame_object());
    }

    @Test
    public void test_shoot_in_cell_with_tank_surrounded_walls() throws InterruptedException {
        Game game;
        Tank tank;

        Wall wall = new Wall();
        wall.setDestructibility(true);

        game = new Game(new Game_generationTest());

        game.getGameField().getCell(new Point(4,1)).setGame_object(wall);
        game.getGameField().getCell(new Point(3,1)).setGame_object(wall);
        game.getGameField().getCell(new Point(3,2)).setGame_object(wall);
        game.getGameField().getCell(new Point(2,1)).setGame_object(wall);

        tank = game.activeTank();
        tank.setProjectileCell(game.getGameField().getCell(new Point(3,0)));
        tank.shootCell();

        assertEquals(GameState.WINNER_FOUND, game.status());
    }

    @Test
    public void test_shoot_in_cell_with_tank_surrounded_water() throws InterruptedException {
        Game game;
        Tank tank;

        Water water = new Water();
        water.setDestructibility(false);

        game = new Game(new Game_generationTest());

        game.getGameField().getCell(new Point(4,1)).setGame_object(water);
        game.getGameField().getCell(new Point(3,1)).setGame_object(water);
        game.getGameField().getCell(new Point(2,1)).setGame_object(water);

        tank = game.activeTank();
        tank.setProjectileCell(game.getGameField().getCell(new Point(3,0)));
        tank.shootCell();

        assertEquals(GameState.WINNER_FOUND, game.status());
    }
}
