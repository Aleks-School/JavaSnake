package game.snake.game;

import game.snake.utility.Vector2;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class World {
    private double paneSize;
    private Vector2 previousSize = Vector2.ZERO;

    private final AnchorPane PANE;
    private final ObservableList<Node> CHILDREN;

    private boolean clipChildren = false;

    public World(AnchorPane pane) {
        PANE = pane;
        CHILDREN = pane.getChildren();

        previousSize = new Vector2(PANE.getWidth(), PANE.getHeight());

        Platform.runLater(() -> setClipChildren(clipChildren));
    }
    public World(AnchorPane pane, double size) {
        this(pane);
        setSize(size);
    }
    public World(AnchorPane pane, Vector2 size) {
        this(pane);
        setSize(size);
    }

    public boolean contains(Node node) {return CHILDREN.contains(node);}

    public void add(Node node) {if (!CHILDREN.contains(node)) {Platform.runLater(() -> CHILDREN.add(node));}}
    public void add(Node node, Vector2 position) {
        add(node);
        Vector2 topLeft = position.mul(paneSize);

        if (node instanceof Rectangle) {
            ((Rectangle)node).setX(topLeft.X);
            ((Rectangle)node).setY(topLeft.Y);
        } else if (node instanceof Circle) {
            double radius = (paneSize / 2);
            ((Circle)node).setCenterX(topLeft.X+radius);
            ((Circle)node).setCenterY(topLeft.Y+radius);
        }
    }

    public boolean getClipChildren() {return clipChildren;}
    public void setClipChildren(boolean clip) {
        clipChildren = clip;
        PANE.setClip(clipChildren ? PANE.getParent() : null);
    }

    public void remove(Node node) {Platform.runLater(() -> CHILDREN.remove(node));}
    public void remove(int x, int y) {

    }

    public void clear() {CHILDREN.clear();}

    public void setSize(double size) {PANE.setPrefSize(size, size);}
    public void setSize(Vector2 size) {PANE.setPrefSize(size.X, size.Y);}

    public double getPaneSize() {
        Vector2 paneSize = new Vector2(PANE.getWidth(), PANE.getHeight());

        if (!previousSize.equals(paneSize)) {
            this.paneSize = Math.min(paneSize.X, paneSize.Y)/Settings.getGridSize();
            previousSize = paneSize;
        }
        return this.paneSize;
    }

    public Effect getEffect() {return PANE.getEffect();}
    public void setEffect(Effect effect) {PANE.setEffect(effect);}
}