package game.snake.game;

import game.snake.utility.Vector2;
import game.snake.utility.color.Color;
import game.snake.utility.color.ColorSequence;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Settings {
    public static final Duration FRAME_RATE = new Duration(50); // 1/0.05 = 20 frames a sec
    public static final int MAX_CELLS = (int)Math.min(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
    public static final Vector2 START_POS = new Vector2(1, 1);
    public static final Vector2 START_DIRECTION = Vector2.X_AXIS;

    public static byte graphicsLevel = 3;
    private static double powerUpChance = 0.15;
    private static Color backgroundColor = Color.BLACK;
    private static boolean singlePlayer = true; // if the game is single player or two player; if the grid has edges
    private static int gridSize = 10; // how many cells there should be on each side
    private static int startSpeed = 5; // the starting speed of the snake (in cells/second)
    private static float speedProgress = 0.25f; // how much speed is gained per food eaten
    private static ColorSequence playerOneColor = new ColorSequence(new Color(0, 150,0), new Color(0, 100, 0));
    private static ColorSequence playerTwoColor = new ColorSequence(new Color(0, 0, 150));

    public static byte getGraphicsLevel() {return graphicsLevel;}
    public static Color getBackgroundColor() {return backgroundColor;}
    public static boolean getSinglePlayer() {return singlePlayer;}
    public static int getGridSize() {return gridSize;}
    public static int getStartSpeed() {return startSpeed;}
    public static float getSpeedProgress() {return speedProgress;}
    public static double getPowerUpChance() {return powerUpChance;}
    public static ColorSequence getPlayerOneColor() {return playerOneColor;}
    public static ColorSequence getPlayerTwoColor() {return playerTwoColor;}

    public static void setGraphicsLevel(byte level) {graphicsLevel = level;}
    public static void setBackgroundColor(Color color) {backgroundColor = color;}
    public static void setSinglePlayer(boolean singlePlayer) {Settings.singlePlayer = singlePlayer;}
    public static void setGridSize(int size) {gridSize = size;}
    public static void setStartSpeed(int speed) {startSpeed = speed;}
    public static void setSpeedProgress(float progress) {speedProgress = progress;}
    public static void setPowerUpChance(double chance) {powerUpChance = chance;}
    public static void setPlayerOneColor(ColorSequence sequence) {playerOneColor = sequence;}
    public static void setPlayerTwoColor(ColorSequence sequence) {playerTwoColor = sequence;}
}
