package tanks;

import org.jetbrains.annotations.NotNull;
import tanks.event.GameActionEvent;
import tanks.event.GameActionListener;
import tanks.generation_field.simpleField;
import tanks.ui.FieldWidget;
import tanks.ui.WidgetFactory;

import javax.swing.*;
import java.awt.*;

import tanks.ui.utils.GameWidgetUtils;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    static class GamePanel extends JFrame {

        private Game game;
        private WidgetFactory widgetFactory;

        public GamePanel() throws HeadlessException {
            setVisible(true);

            widgetFactory = new WidgetFactory();
            game = new tanks.Game(new simpleField());

            game.addGameActionListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.add(new FieldWidget(game.getGameField(), widgetFactory));

            widgetFactory.getWidget(game.activeTank()).requestFocus();

            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private final class GameController implements GameActionListener {

            @Override
            public void tankIsMoved(@NotNull GameActionEvent event) {

            }

            @Override
            public void tankIsSkipStep(@NotNull GameActionEvent event) {

            }

            @Override
            public void tankFired(@NotNull GameActionEvent event) {

            }

            @Override
            public void gameStatusChanged(@NotNull GameActionEvent event) {
                GameState status = event.getStatus();
                switch (status) {
                    case WINNER_FOUND:
                        JOptionPane.showMessageDialog(GamePanel.this, "Выиграл танк: " +
                                GameWidgetUtils.colorName(widgetFactory.getWidget(game.winner()).getColor()));
                        break;
                    case GAME_FINISHED_AHEAD_OF_SCHEDULE:
                        JOptionPane.showMessageDialog(GamePanel.this, "Игра завершена досрочно");
                        break;
                }
            }
        }
    }
}