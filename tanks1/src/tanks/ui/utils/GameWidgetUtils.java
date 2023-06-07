package tanks.ui.utils;

import java.awt.*;

public class GameWidgetUtils {

    public static String colorName(Color color) {
        if (Color.BLUE.equals(color)) {
            return "Синий";
        } else if(Color.GREEN.equals(color)) {
            return "Зелёный";
        }
        return "";
    }
}
