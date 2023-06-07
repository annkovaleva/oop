package test;

import org.junit.Test;
import tanks.Direction;
import tanks.Game;
import tanks.GameState;
import tanks.Tank;
import test.Game_generation.Game_generationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Тесты Снаряда
public class ProjectileTest {

    @Test
    public void test_shoot_in_wall_back_to_back() {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();
        tank.setDirection(Direction.SOUTH);
        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_shoot_in_wall_with_distance() {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();
        tank.setDirection(Direction.EAST);
        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }


    //Этот тест почему-то надо запускать отдельно, и он проходит
    @Test
    public void test_kill_headquarter() {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();
        tank.setDirection(Direction.NORTH);
        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_kill_tank () throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();

        game.activeTank().move(Direction.EAST);
        game.activeTank().move(Direction.SOUTH);
        game.activeTank().move(Direction.EAST);
        game.activeTank().move(Direction.WEST);
        game.activeTank().move(Direction.EAST);
        tank.setDirection(Direction.NORTH);
        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_kill_wall () throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();

        game.activeTank().move(Direction.EAST);
        game.activeTank().move(Direction.SOUTH);
        tank.setDirection(Direction.NORTH);
        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }

    @Test
    public void test_shoot_water () throws InterruptedException {
        Game game;
        Tank tank;

        game = new Game(new Game_generationTest());
        tank = game.activeTank();

        game.activeTank().move(Direction.EAST);
        game.activeTank().move(Direction.SOUTH);
        game.activeTank().move(Direction.EAST);
        game.activeTank().move(Direction.SOUTH);
        tank.setDirection(Direction.NORTH);
        tank.shootDir();

        assertEquals(GameState.GAME_IS_ON, game.status());
    }
}





