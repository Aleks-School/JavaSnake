package game.snake.controllers;

import game.snake.ui.exceptions.NullPageException;
import game.snake.ui.util.Controller;
import game.snake.ui.util.Page;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Size extends Controller {
    private static final Glow glow = new Glow(1);
    private static final DropShadow shadow = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 0d, 0d, 0d, 0d);

    @Override public void initialize() {
        shadow.setWidth(255d);
        shadow.setHeight(0d);

        glow.setInput(shadow);
    }

    @FXML private void setGlow(MouseEvent event) {
        Button button = (Button)event.getSource();
        button.setEffect(glow);

        event.consume();
    }

    @FXML private void setNormal(MouseEvent event) {
        Button button = (Button)event.getSource();
        button.setEffect(shadow);

        event.consume();
    }

    @FXML private void onPressed(ActionEvent event) throws NullPageException {
        ObservableMap<String,String> properties = (ObservableMap)((Button)event.getSource()).getProperties();

        game.snake.game.Settings.setGridSize(Integer.parseInt(properties.get("gridSize")));

        page().load(new Page("Snake"));
        event.consume();
    }
}