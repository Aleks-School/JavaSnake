package game.snake.entities;

import game.snake.utility.Vector2;
import javafx.scene.image.Image;

public class Player extends Snake {
    /**Creates a default {@link Player} object with a health of 3*/
    public Player() {}

    /**Creates a default {@link Player} object with custom health
     * @param health the health of the snake
     */
    public Player(int health) {super(health); setName("Player");}
    /**Creates a {@link Player} object with a custom appearance and health
     * @param health the health of the player object
     * @param appearance an image of the player
     */
    public Player(int health, Image appearance) {super(health, appearance);}
    /**Creates a {@link Player} object with a custom appearance, health, and size
     * @param health the health of the player object
     * @param appearance an image of the player
     * @param size the size of the player entity
     */
    public Player(int health, Image appearance, Vector2 size) {super(health, appearance, size);}
}
