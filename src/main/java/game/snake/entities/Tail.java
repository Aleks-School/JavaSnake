package game.snake.entities;

import game.snake.utility.Vector2;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Tail {
    private final Rectangle LINK;
    //private final HashMap<Vector2, > PATH;
    private Vector2 position, direction;

    public Tail(Rectangle link, Vector2 position, Queue<Vector2> path) {
        LINK = link;
        //PATH = path;

        this.position = position;
        this.direction = getDirection();
    }

    private Vector2 getDirection() {
        /*Vector2 nextPosition = PATH.peek();
        System.out.println(nextPosition.sub(position).UNIT);
        return nextPosition.sub(position).UNIT;*/
        return position;
    }

    public Vector2 getPosition() {return position;}
    public void setPosition(Vector2 position) {this.position = position;}

    public Rectangle getLink() {return LINK;}
}
