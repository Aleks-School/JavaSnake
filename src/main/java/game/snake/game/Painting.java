package game.snake.game;

import game.snake.entities.Entity;
import game.snake.utility.Vector2;
import game.snake.utility.color.Color;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Painting {
    private final Canvas CANVAS;
    private final GraphicsContext PEN;

    public Painting(Canvas canvas) {
        CANVAS = canvas;
        PEN = canvas.getGraphicsContext2D();
    }
    public Painting(Canvas canvas, double size) {
        CANVAS = canvas;
        PEN = canvas.getGraphicsContext2D();
        setSize(size);
    }
    public Painting(Canvas canvas, Vector2 size) {
        CANVAS = canvas;
        PEN = canvas.getGraphicsContext2D();
        setSize(size);
    }

    public void setBackground(Color color) {
        PEN.setFill(javafx.scene.paint.Color.rgb(color.R&255, color.G&255, color.B&255));
        PEN.fillRect(0, 0, CANVAS.getWidth(), CANVAS.getHeight());
    }

    public void drawEntity(Entity entity, Vector2 position) {
        double size = Math.min(CANVAS.getWidth(), CANVAS.getHeight())/Settings.getGridSize();
        Vector2 topleft = position.mul(size);

        PEN.setFill(entity.getPrimaryColor().toPaint());

        int x = (int)topleft.X, y = (int)topleft.Y;

        byte graphicsLevel = Settings.getGraphicsLevel();

        if (graphicsLevel > 1) {
            switch (entity.getPreset()) {
                case 2:
                    PEN.fillOval(x, y, size, size);
                    break;
                default:
                    PEN.fillRect(x+1, y+1, size-2, size-2);
                    break;
            }
        } else if (graphicsLevel == 1) {
            PEN.fillRect(x+1, y+1, size-2, size-2);
        } else {
            PEN.fillRect(x, y, size, size);
        }

    }

    public void eraseEntity(Entity entity, Vector2 position) {
        double size = Math.min(CANVAS.getWidth(), CANVAS.getHeight())/Settings.getGridSize();
        Vector2 topleft = position.mul(size);

        PEN.clearRect((int)topleft.X, (int)topleft.Y, size, size);
    }

    public void drawText(String text) {
        PEN.setTextAlign(TextAlignment.CENTER);
        PEN.setTextBaseline(VPos.CENTER);
        PEN.setFont(new Font("Minecraftia", 32));
        PEN.setFill(Color.WHITE.toPaint());
        PEN.fillText(text, CANVAS.getWidth()/2, CANVAS.getHeight()/2);
    }
    public void drawText(String text, Vector2 position) {
        PEN.setTextAlign(TextAlignment.CENTER);
        PEN.setTextBaseline(VPos.CENTER);
        PEN.setFont(new Font("Minecraftia", 32));
        PEN.setFill(Color.WHITE.toPaint());
        PEN.fillText(text, position.X, position.Y);
    }

    public void setSize(double size) {
        CANVAS.setWidth(size);
        CANVAS.setHeight(size);
    }
    public void setSize(Vector2 size) {
        CANVAS.setWidth(size.X);
        CANVAS.setHeight(size.Y);
    }

    public void clear() {
        PEN.clearRect(0,0, CANVAS.getWidth(), CANVAS.getHeight());
    }

    public void setEffect(Effect effect) {
        CANVAS.setEffect(effect);
    }
}
