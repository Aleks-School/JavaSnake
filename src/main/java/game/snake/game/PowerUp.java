package game.snake.game;

import game.snake.utility.color.Color;
import game.snake.utility.color.ColorKeypoint;
import game.snake.utility.color.ColorSequence;

/**Creates a random power up*/
public class PowerUp {
    private String name;
    private float purity;
    private int extraHealth, lifetime;
    private Color color;
    private ColorSequence sequence;

    public static PowerUp SUPER = new PowerUp(0);
    public static PowerUp FREEZE = new PowerUp(1);
    public static PowerUp GHOST = new PowerUp(2);
    public static PowerUp PHANTOM = new PowerUp(3);
    public static PowerUp SUN = new PowerUp(4);

    protected PowerUp(int preset) {
        purity = ((int)(Math.random() * 10) + 1)/10f;

        switch (preset) {
            case 1:
                name = "Frozen";
                extraHealth = Math.max((int)(purity * 2), 1);
                lifetime = Math.min((int)(purity * 10), 4);
                color = Color.BLUE.lerp(new Color(0, 255, 255), (purity - 0.1f)/0.9f);
                sequence = new ColorSequence(new ColorKeypoint(0, new Color(255, 255, 255)), new ColorKeypoint(0.25f, new Color(0, 255, 255)), new ColorKeypoint(1, Color.BLUE));
                break;
            case 2:
                name = "Ghost";
                extraHealth = -1;
                lifetime = Math.min((int)(purity * 12), 6);
                color = new Color(240, 240, 240, 0.75).lerp(new Color(1d, 1d, 1d, 0.25), (purity - 0.1f)/0.9f);
                sequence = new ColorSequence(new Color(244, 244, 244), new Color(80, 80, 80));
                break;
            case 3:
                name = "Phantom";
                extraHealth = Math.max((int)(purity * 10), 1);
                lifetime = Math.min((int)(purity * 12), 6);
                color = Color.INDIGO.lerp(Color.VIOLET, (purity - 0.1f)/0.9f);
                sequence = new ColorSequence(Settings.getPlayerOneColor().get(0).COLOR, Color.INDIGO);
                break;
            case 4:
                name = "Sun";
                extraHealth = Math.max((int)(purity * 20), 1);
                color = new Color(230, 175, 0).lerp(Color.ORANGE, (purity - 0.1f)/0.9f);
                if (Math.random() <= 0.1) {break;} //has a ten-percent chance of being a sun fruit
            default:
                name = "Super";
                extraHealth = (int)(purity * 5) + 1;
                lifetime = 0;
                color = new Color(200, 0, 100).lerp(new Color(200, 0, 200), (purity - 0.1f)/0.9f);
                break;
        }
    }

    public PowerUp() {
        this((int)(Math.random() * 5));
    }

    public String name() {return name;}
    public float purity() {return purity;}
    public int life() {return lifetime;}
    public Color color() {return color;}
    public int getExtraHealth() {return extraHealth;}
    public ColorSequence getSequence() {return sequence;}
}
