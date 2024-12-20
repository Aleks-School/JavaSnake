package game.snake.entities;

import game.snake.utility.Vector2;
import game.snake.utility.color.Color;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Entity {
    private String name = "Entity";
    private int health = 1; // the health of the entity
    private Vector2 position = Vector2.ZERO, direction = Vector2.Y_AXIS.mul(-1), size = new Vector2(1, 1); // the position of the entity, the forward-direction of the entity, the size of the entity (in cells)
    private Image appearance; // an image of the entity
    private short preset = -1; // a short value corresponding to a constructed image instructions preset
    private ArrayList<Color> colors = new ArrayList<>();

    public Entity(String name, short preset) {
        this.name = name;
        this.preset = preset;
    }
    public Entity(String name, short preset, int health) {
        this.name = name;
        this.preset = preset;
        this.health = health;
    }
    public Entity(String name, short preset, int health, Vector2 size) {
        this.name = name;
        this.preset = preset;
        this.health = health;
        this.size = size;
    }

    /**Returns the entity type*/
    public String getName() {return name;}
    /**Sets the entity type
     * @param name the type of entity
     */
    protected void setName(String name) {this.name = name;}

    /**Returns the health of the entity*/
    public int getHealth() {return health;}
    /**Sets the health of the entity
     * @param health the health of the entity
     */
    public void setHealth(int health) {this.health = Math.max(health, 0);}

    public Vector2 getPosition() {return position;}
    public void setPosition(Vector2 position) {this.position = position;}

    public Vector2 getDirection() {return direction;}

    public void setDirection(Vector2 direction) {this.direction = direction;}

    /**Sets the size of the entity to {@code size} cells in the x and y directions*/
    public Vector2 getSize() {return size;}
    /**Sets the span of the entity to {@code size} cells in the x and y directions
     * @param size the size of the entity (in cells)
     */
    public void setSize(Vector2 size) {this.size = new Vector2(Math.max(size.X, 0), Math.max(size.Y, 0));}

    /**Sets the appearance of the entity to an image file
     * @param image the image object to set the appearance to
     */
    public void setAppearance(Image image) {
        this.appearance = image;
        if (preset != -1) {preset = -1;}
    }

    /**Sets the primary color of the entity
     * @param primary the primary color of the entity
     */
    public void setColors(Color primary) {
        if (colors.size() > 0) {
            colors.set(0, primary);
        } else {
            colors.add(primary);
        }
    }
    /**Sets the primary and additional colors of the entity
     * @param primary the primary color of the entity
     * @param colors the additional colors of the entity
     */
    public void setColors(Color primary, Color... colors) {
        setColors(primary);
        for (int i=0; i<colors.length; i++) {
            if (i+1 < this.colors.size()) {
                this.colors.set(i+1, colors[i]);
            } else {
                this.colors.add(colors[i]);
            }
        }
    }

    public Color getPrimaryColor() {return colors.get(0);}
    public Color[] getColors() {return colors.toArray(new Color[colors.size()]);}

    /**Returns the preset value of the entity appearance
     * @return a short value indicating the index of the preset
     */
    public short getPreset() {return preset;}

    /**Sets the appearance preset
     * @param preset the index of the appearance preset to use
     */
    protected void setPreset(short preset) {this.preset = (short)Math.max(preset, -1);}

    @Override public String toString() {return String.format("%s[health: %d, preset: %d, size: %s]", name, health, preset, size);}
}