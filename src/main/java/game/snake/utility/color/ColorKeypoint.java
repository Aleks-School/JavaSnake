package game.snake.utility.color;

/**A class containing data for color time position*/
public class ColorKeypoint implements Comparable<ColorKeypoint> {
    // -- Attributes -- \\
    public final Color COLOR;
    public final float TIME;

    // -- Constructors -- \\
    /**Creates a new keypoint with {@code Color.BLACK} at time zero*/
    public ColorKeypoint() {
        TIME = 0f;
        COLOR = Color.BLACK;
    }

    /**Creates a new keypoint at time zero for the given color
     * @param color the color at time zero
     */
    public ColorKeypoint(Color color) {
        TIME = 0f;
        COLOR = color;
    }

    /**Creates a new keypoint at the specified time for the given color
     * @param time a {@code float} value in-between 0 and 1, inclusive, representing the color's position in a color sequence
     * @param color the color at the specified time
     */
    public ColorKeypoint(float time, Color color) {
        TIME = time;
        COLOR = color;
    }

    public static int compareTo(ColorKeypoint a, ColorKeypoint b) {return Float.compare(a.TIME, b.TIME);}

    // -- Methods -- \\
    @Override public String toString() {return String.format("%s[Time: %f, Color: %s]", getClass().getSimpleName(), TIME, COLOR.toString());}
    @Override public int compareTo(ColorKeypoint k) {return Float.compare(TIME, k.TIME);}

    public boolean isEqual(ColorKeypoint keypoint) {return (TIME == keypoint.TIME) && COLOR.equals(keypoint.COLOR);}

    public float getTIME() {return TIME;}
}