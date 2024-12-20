module game.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;

    requires java.desktop;

    opens game.snake.ui.util to javafx.fxml;
    opens game.snake.controllers to javafx.fxml;
    opens game.snake.game to javafx.fxml;
    opens game.snake to javafx.fxml;

    exports game.snake;
}