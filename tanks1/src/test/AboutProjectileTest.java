package test;

import org.junit.Test;
import tanks.*;
import test.Game_generation.Game_generationTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AboutProjectileTest {

    @Test
    public void test_shoot_in_my_head() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();

        tank.setProjectile();
        tank.setProjectile();

        tank.shootDir();

        GameObject head = game.getGameField().getCell(new Point(0,3)).getGame_object();

        assertEquals(GameState.WINNER_FOUND, game.status());
        assertNull(head);
    }

    @Test
    public void test_shoot_in_not_my_head() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.NORTH);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.NORTH);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.setProjectile();
        tank.setProjectile();

        tank.shootDir();

        GameObject water = game.getGameField().getCell(new Point(2,0)).getGame_object();
        GameObject head = game.getGameField().getCell(new Point(3,0)).getGame_object();

        assertEquals(GameState.WINNER_FOUND, game.status());
        assertNotNull(water);
        assertNull(head);
    }

    @Test
    public void test_shoot_in_wall() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.NORTH);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.NORTH);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.setProjectile();
        tank.setProjectile();

        tank.shootDir();

        GameObject wall = game.getGameField().getCell(new Point(1,0)).getGame_object();

        assertEquals(GameState.GAME_IS_ON, game.status());
        assertNull(wall);
    }

    @Test
    public void test_shoot_in_wall_perimeter() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.WEST);

        tank = game.activeTank();
        tank.setProjectile();
        tank.setProjectile();

        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_shoot_in_tank() throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.move(Direction.EAST);

        tank = game.activeTank();
        tank.move(Direction.SOUTH);

        tank = game.activeTank();
        tank.setProjectile();
        tank.setProjectile();

        tank.shootDir();

        GameObject tank_enemy = game.getGameField().getCell(new Point(4,2)).getGame_object();

        assertEquals(GameState.WINNER_FOUND, game.status());
        assertNull(tank_enemy);
    }
}
