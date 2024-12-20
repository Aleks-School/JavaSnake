package game.snake.utility;


public abstract class Utility {
    /**
     * Takes a number {@code value} and limits it to range of {@code min} to {@code max}
     * @return an int at or in-between the values of {@code min} and {@code max}
     */
    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
    /**
     * Takes a number {@code value} and limits it to range of {@code min} to {@code max}
     * @return a long at or in-between the values of {@code min} and {@code max}
     */
    public static long clamp(long value, long min, long max) {
        return Math.min(Math.max(value, min), max);
    }
    /**
     * Takes a number {@code value} and limits it to range of {@code min} to {@code max}
     * @return a float at or in-between the values of {@code min} and {@code max}
     */
    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }
    /**
     * Takes a number {@code value} and limits it to range of {@code min} to {@code max}
     * @return a double at or in-between the values of {@code min} and {@code max}
     */
    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }
}
