package game.snake.entities;

import game.snake.game.PowerUp;
import game.snake.utility.Vector2;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public class Food extends Entity {
    private PowerUp powerUp;
    private Effect effect;
    private Shape fruit;

    /**Creates a default {@link Food} object with a health of 1*/
    public Food() {super("Food", (short)2);}
    /**Creates a {@link Food} object with custom health
     * @param health the health of the food object
     */
    public Food(int health) {super("Food", (short)2, health);}
    /**Creates a {@link Food} object with a custom appearance and health
     * @param health the health of the food object
     * @param appearance an image of the food
     */
    public Food(int health, Image appearance) {
        this(health);
        setAppearance(appearance);
    }
    /**Creates a {@link Food} object with a custom appearance, health, and size
     * @param health the health of the food object
     * @param appearance an image of the food
     * @param size the size of the food entity
     */
    public Food(int health, Image appearance, Vector2 size) {
        super("Food", (short)2, health, size);
        setAppearance(appearance);
    }

    public PowerUp getPower() {return powerUp;}
    public void setPower(PowerUp power) {
        powerUp = power;

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);
        bloom.setInput(new Glow((powerUp != null) ? powerUp.purity() : 0.1));

        GaussianBlur blur = new GaussianBlur((powerUp != null) ? Math.max(powerUp.purity()*5, 1) : 1);
        blur.setInput(bloom);

        effect = blur;
    }

    public Effect getEffect() {return effect;}

    public Shape getFruit() {return fruit;}

    public void setFruit(Shape fruit) {this.fruit = fruit;}
}
