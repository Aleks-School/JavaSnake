package game.snake.utility.color;

public class Color {
    // -- Attributes -- \\
    /**A component of the color, representing the red, green, or blue channel*/
    public final byte R, G, B;
    /**The opacity of the color*/
    public final double A;

    // -- Values -- \\
    public static final Color BLACK = new Color();
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color ORANGE = new Color(255, 200, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color INDIGO = new Color(75, 0, 130);
    public static final Color VIOLET = new Color(155, 38, 182);
    public static final Color TEAL = new Color(0, 128, 128);

    // -- Constructors -- \\
    /**Creates a new blank {@code Color} object*/
    public Color() {R = 0; G = 0; B = 0; A = 1d;}
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     */
    public Color(byte r, byte g, byte b) {
        R = r;
        G = g;
        B = b;
        A = 1d;
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     */
    public Color(short r, short g, short b) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = 1d;
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     */
    public Color(int r, int g, int b) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = 1d;
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     */
    public Color(long r, long g, long b) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = 1d;
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     */
    public Color(float r, float g, float b) {
        R = (byte)(r * 255);
        G = (byte)(g * 255);
        B = (byte)(b * 255);
        A = 1d;
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     */
    public Color(double r, double g, double b) {
        R = (byte)(r * 255);
        G = (byte)(g * 255);
        B = (byte)(b * 255);
        A = 1d;
    }

    /**Creates a color from rgb values with a transparency modifier
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     * @param alpha the opacity of the color
     */
    public Color(byte r, byte g, byte b, double alpha) {
        R = r;
        G = g;
        B = b;
        A = clamp(alpha, 0d, 1d);
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     * @param alpha the opacity of the color
     */
    public Color(short r, short g, short b, double alpha) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = clamp(alpha, 0d, 1d);
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     * @param alpha the opacity of the color
     */
    public Color(int r, int g, int b, double alpha) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = clamp(alpha, 0d, 1d);
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     * @param alpha the opacity of the color
     */
    public Color(long r, long g, long b, double alpha) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = clamp(alpha, 0d, 1d);
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     * @param alpha the opacity of the color
     */
    public Color(float r, float g, float b, double alpha) {
        R = (byte)(r * 255);
        G = (byte)(g * 255);
        B = (byte)(b * 255);
        A = clamp(alpha, 0d, 1d);
    }
    /**Creates a color from rgb values
     * @param r the red input of the color
     * @param g the green input of the color
     * @param b the blue input of the color
     * @param alpha the opacity of the color
     */
    public Color(double r, double g, double b, double alpha) {
        R = (byte)(r * 255);
        G = (byte)(g * 255);
        B = (byte)(b * 255);
        A = clamp(alpha, 0d, 1d);
    }


    // -- Methods -- \\
    /**Restricts the value range in-between min and max, inclusive
     * @param value the value being clamped
     * @param min the minimum acceptable value
     * @param max the maximum acceptable value
     * @return the value in the range in-between min and max, inclusive
     */
    private static float clamp(float value, float min, float max) {return Math.max(min, Math.min(max, value));}
    /**Restricts the value range in-between min and max, inclusive
     * @param value the value being clamped
     * @param min the minimum acceptable value
     * @param max the maximum acceptable value
     * @return the value in the range in-between min and max, inclusive
     */
    private static double clamp(double value, double min, double max) {return Math.max(min, Math.min(max, value));}

    /**Calculates the value interpolated/extrapolated from a to b by the given alpha
     * @param a the initial value
     * @param b the target value
     * @param alpha the percentage to interpolate/extrapolate by
     * @return the value interpolated/extrapolated from a to b
     */
    private static float lerp(float a, float b, float alpha) {return a + (b - a) * alpha;}
    /**Calculates the value interpolated/extrapolated from a to b by the given alpha
     * @param a the initial value
     * @param b the target value
     * @param alpha the percentage to interpolate/extrapolate by
     * @return the value interpolated/extrapolated from a to b
     */
    private static double lerp(double a, double b, double alpha) {return a + (b - a) * alpha;}

    /**Calculates the alpha from the given value interpolated/extrapolated from a to b
     * @param a the initial value
     * @param b the target value
     * @param value the value to interpolate/extrapolate from
     * @return the alpha interpolated/extrapolated by value from a to b
     */
    private static float inverseLerp(float a, float b, float value) {return (value - a)/(b - a);}
    /**Calculates the alpha from the given value interpolated/extrapolated from a to b
     * @param a the initial value
     * @param b the target value
     * @param value the value to interpolate/extrapolate from
     * @return the alpha interpolated/extrapolated by value from a to b
     */
    private static double inverseLerp(double a, double b, double value) {return (value - a)/(b - a);}

    @Override public String toString() {return String.format((A == 1) ? "%s[%d, %d, %d]" : "%s[%d, %d, %d, %f]", Color.class.getSimpleName(), R&0xFF, G&0xFF, B&0xFF, (A == 1) ? null : A);}
    public boolean equals(Color color) {return R == color.R && G == color.G && B == color.B && A == color.A;}

    /**Calculates the Color interpolated from {@code this} to the {@code target} color by the given alpha
     * @param target the color to interpolate towards
     * @param alpha the fractional amount to interpolate by
     * @return the calculated {@link Color} value
     */
    public Color lerp(Color target, float alpha) {
        alpha = clamp(alpha, 0f, 1f);
        return new Color((int)lerp(R&255, target.R&255, alpha), (int)lerp(G&255, target.G&255, alpha), (int)lerp(B&255, target.B&255, alpha), lerp(A, target.A, alpha));
    }
    /**Calculates the Color interpolated from {@code this} to the {@code target} color by the given alpha
     * @param target the color to interpolate towards
     * @param alpha the fractional amount to interpolate by
     * @return the calculated {@link Color} value
     */
    public Color lerp(Color target, double alpha) {
        alpha = clamp(alpha, 0f, 1d);
        return new Color((int)lerp(R, target.R, alpha), (int)lerp(G, target.G, alpha), (int)lerp(B, target.B, alpha), lerp(A, target.A, alpha));
    }

    public javafx.scene.paint.Color toPaint() {
        return javafx.scene.paint.Color.rgb(R&255, G&255, B&255, A);
    }
}
