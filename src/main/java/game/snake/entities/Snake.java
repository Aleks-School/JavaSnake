package game.snake.entities;

import game.snake.game.PowerUp;
import game.snake.game.Settings;
import game.snake.utility.Vector2;
import game.snake.utility.color.Color;
import game.snake.utility.color.ColorKeypoint;
import game.snake.utility.color.ColorSequence;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import java.util.*;

public class Snake extends Entity {
    private int eaten = 0;
    private float speed = Settings.getStartSpeed();
    private Vector2 lastPosition, lastDirection;
    private Queue<Vector2> directions = new LinkedList<>();
    private Queue<Pair<Vector2, Vector2>> path = new LinkedList<>();
    private ArrayList<Vector2> ghostedCells = new ArrayList<>();
    private ArrayList<Rectangle> links = new ArrayList<>();
    private HashMap<Vector2, Rectangle> corners = new HashMap<>();
    private ColorSequence sequence = new ColorSequence(getColors());
    private ColorSequence powerSequence;

    private boolean moved = false;
    private PowerUp powerUp;
    private long eatTime;

    /**Creates a default {@link Snake} object with a health of 3*/
    public Snake() {
        super("Snake", (short)1, 3);
        super.setPosition(Settings.START_POS);
        super.setDirection(Settings.START_DIRECTION);

        lastDirection = super.getDirection();
        lastPosition = super.getPosition();
        path.add(new Pair<>(Settings.START_POS, Settings.START_DIRECTION));
    }

    /**Creates a default {@link Snake} object with custom health
     * @param health the health of the snake
     */
    public Snake(int health) {super("Snake", (short)1, health);}
    /**Creates a {@link Snake} object with a custom appearance and health
     * @param health the health of the snake object
     * @param appearance an image of the snake
     */
    public Snake(int health, Image appearance) {
        this(health);
        setAppearance(appearance);
    }
    /**Creates a {@link Snake} object with a custom appearance, health, and size
     * @param health the health of the food object
     * @param appearance an image of the food
     * @param size the size of the food entity
     */
    public Snake(int health, Image appearance, Vector2 size) {
        super("Snake", (short)2, health, size);
        setAppearance(appearance);
    }

    private void updateColor() {
        //Iterator<Pair<Vector2,Vector2>> iterator = path.iterator();
        int size = links.size();
        float max = (size - 1);

        //Vector2 lastDirection = (path.size() == size) ? path.peek().getValue() : null;
        ColorSequence seq = (powerSequence != null) ? powerSequence : sequence;

        for (int i=0; i<size; i++) {
            /*if (lastDirection != null) {
                Pair<Vector2,Vector2> linkLocation = iterator.next();

                Vector2 position = linkLocation.getKey();
                Vector2 direction = linkLocation.getValue().UNIT;

                if (!direction.equals(lastDirection)) {
                    Rectangle corner = corners.get(position);
                    if (corner != null) {corner.setFill(links.get(Math.max(i, 0)).getFill());}
                }
                lastDirection = direction;
            }*/

            links.get(i).setFill(seq.getColorAtTime((max-i)/max).toPaint());
        }
    }

    @Override public void setColors(Color primary) {
        super.setColors(primary);
        sequence = new ColorSequence(getColors());
        updateColor();
    }
    @Override public void setColors(Color primary, Color... colors) {
        super.setColors(primary, colors);
        sequence = new ColorSequence(getColors());
        updateColor();
    }
    public void setColors(ColorSequence sequence) {
        Color[] colors = sequence.getColors();
        super.setColors(colors[0], Arrays.copyOfRange(colors, 1, colors.length));
        this.sequence = sequence;
        updateColor();
    }

    public void render(double paneSize) {
        if (links.size() == 0) {return;}

        int size = Math.min(path.size(), links.size()), max = (size - 1);
        Iterator<Pair<Vector2, Vector2>> iterator = path.iterator();

        Pair<Vector2,Vector2> endLocation = path.peek();
        Vector2 lastDirection = endLocation.getValue().UNIT;
        Vector2 endPosition = endLocation.getKey();

        if (corners.containsKey(endPosition)) {
            Rectangle corner = corners.get(endPosition);

            Platform.runLater(() -> ((AnchorPane)corner.getParent()).getChildren().remove(corner));
            corners.remove(endPosition);
        }

        for (int i=0; i<size; i++) {
            if (iterator.hasNext()) {
                Pair<Vector2, Vector2> linkLocation = iterator.next();

                Vector2 position = linkLocation.getKey();
                Vector2 absolutePosition = position.mul(paneSize);
                Vector2 direction = linkLocation.getValue().UNIT;

                Rectangle link = links.get(i);
                int offset = (int)((paneSize - link.getWidth()) / 2);
                double rotation = Math.toDegrees(Math.atan2(direction.Y, direction.X));

                switch (Settings.getGraphicsLevel()) {
                    case 3:
                        int minSize = (int)(paneSize * 0.5);
                        int height = (int)(minSize + (paneSize - minSize) * (i/(double)max));
                        int heightOffset = (i == max) ? 0 : (int)((paneSize - height) / 2);

                        link.setHeight(height-(offset*2));

                        /*if (!lastDirection.equals(direction)) {
                            System.out.println("Pos: "+position+" Direction: "+direction);
                            for (Vector2 vector : corners.keySet()) {
                                System.out.println(vector);
                            }
                            System.out.println();
                        }*/
                        if (!direction.equals(lastDirection) && corners.containsKey(position.add(direction))) {
                            Rectangle next = links.get(Math.max(i-1, 0));
                            Rectangle corner = corners.get(position.add(direction));

                            corner.setX(next.getX());
                            corner.setY(next.getY());
                            corner.setWidth(next.getWidth());
                            corner.setHeight(next.getHeight());
                            corner.setRotate(rotation);
                            corner.setFill(next.getFill());

                            if (i == 1) {links.get(0).setRotate(rotation);}

                            ObservableList<Node> children = ((AnchorPane)link.getParent()).getChildren();
                            if (!children.contains(corner)) {Platform.runLater(() -> children.add(corner));}
                        }

                        link.setX((int)absolutePosition.X+offset);
                        link.setY((int)absolutePosition.Y+offset+heightOffset);
                        break;
                    case 2:
                    case 1:
                        link.setX((int)absolutePosition.X+offset);
                        link.setY((int)absolutePosition.Y+offset);
                        break;
                    default:
                        link.setX((int)absolutePosition.X);
                        link.setY((int)absolutePosition.Y);
                }

                link.setRotate(rotation);
                lastDirection = direction;
            } else {break;}
        }
    }

    /**Returns the amount of items eaten
     * @return the amount of items eaten
     */
    public int getEaten() {return eaten;}

    /**Eats the given {@link Food} item and adds its health
     * @param food the food object to eat
     */
    public void eat(Food food) {
        setHealth(getHealth() + food.getHealth());
        speed = Settings.getStartSpeed() + (Settings.getSpeedProgress() * ++eaten);

        if (food.getPower() != null) {
            PowerUp foodPower = food.getPower();
            String effectName = (powerUp != null) ? powerUp.name() : foodPower.name();

            if (effectName.equals(foodPower.name()) && foodPower.life() > 0) {
                powerUp = foodPower;
                eatTime = new Date().getTime();
            }
        } else {eatTime = new Date().getTime();}

        if (powerUp != null && powerUp.getSequence() != null) {
            powerSequence = powerUp.getSequence();
            updateColor();
        }
    }

    public void expunge(Vector2 cell) {
        setHealth(Math.max(getHealth() - 2, 3));
        if (!isGhosted(cell)) {ghostedCells.add(cell);}
    }

    public void renew(Vector2 cell) {
        if (isGhosted(cell)) {ghostedCells.remove(cell);}
    }

    public boolean isGhosted(Vector2 cell) {
        return ghostedCells.contains(cell);
    }

    @Override public Vector2 getDirection() {
        if (moved && !directions.isEmpty()) {
            super.setDirection(directions.remove());
            moved = false;
        }

        return super.getDirection();
    }
    @Override public void setDirection(Vector2 direction) {
        if (lastDirection.equals(direction)) {return;}
        lastDirection = direction;

        if (moved) {
            moved = false;

            if (!directions.isEmpty()) {
                super.setDirection(directions.remove());
                directions.add(direction);
            } else {
                super.setDirection(direction);
            }
        } else {
            directions.add(direction);
        }
    }
    public void setDirection(Vector2 direction, Rectangle link) {
        Vector2 position = super.getPosition().iPart();
        System.out.println("POSITION: "+position);

        setDirection(direction);
        corners.put(position, link);
    }

    public Vector2 getLastDirection() {return lastDirection;}

    @Override public void setPosition(Vector2 position) {
        if (lastPosition.equals(position)) {return;}

        Vector2 currentPosition = getPosition();
        boolean changed = !currentPosition.iPart().equals(lastPosition.iPart());

        if (changed) {path.add(new Pair<>(position.iPart(), getDirection()));} //TODO: it isn't detecting direction changed because the snake is waiting for position change before

        moved = (moved || changed);
        lastPosition = currentPosition;
        super.setPosition(position);
    }

    public int getSpeed() {return isFrozen() ? Math.min((int)speed/2, 10) : (int)speed;}

    public int getTailSize() {return links.size();}
    public Rectangle shrinkTail() {
        Rectangle link = links.remove(links.size()-1);

        updateColor();
        return link;
    }
    public void growTail(Rectangle link) {
        links.add(link);
        updateColor();
    }
    public String getEffectName() {return isAffected() ? powerUp.name() : "None";}
    public int getEffectTime() {return isAffected() ? powerUp.life() - (int)getFastTime() : 0;}
    public boolean isAffected() {return (powerUp != null);}
    public boolean isActive() {return isAffected() && getFastTime() >= powerUp.life();}
    public boolean isFrozen() {return isAffected() && powerUp.name().equals("Frozen");}
    public boolean isIntangible() {return isAffected() && powerUp.name().equals("Ghost");}
    public boolean canLoop() {return isAffected() && powerUp.name().equals("Phantom");}
    public void nullify() {
        powerUp = null;
        powerSequence = null;
        updateColor();
    }
    public double getFastTime() {return (new Date().getTime() - eatTime)/1000d;}
    public Queue<Pair<Vector2, Vector2>> getPath() {return path;}
}
