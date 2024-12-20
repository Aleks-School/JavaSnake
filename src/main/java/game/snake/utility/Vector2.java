package game.snake.utility;

import java.util.Objects;

/**A class for performing operations in 2D space
 * @author Aleksandr Stinchcomb
 * @version 1.4
 */
public class Vector2 {
    // -- Static -- \\
    public static final Vector2 ZERO = new Vector2();
    public static final Vector2 ONE = new Vector2(1,1);
    public static final Vector2 X_AXIS = new Vector2(1,0);
    public static final Vector2 Y_AXIS = new Vector2(0,1);

    // -- Attributes -- \\
    public final double X, Y, MAGNITUDE;
    public final Vector2 UNIT;

    // -- Constructors -- \\
    /**Creates a normalized {@link Vector2} (for internal use)
     * @since 1.0
     * @param x the x-component of the vector
     * @param y the y-component of the vector
     * @param magnitude the length of the vector
     * @return a {@link Vector2} with length {@code magnitude}
     */
    private Vector2(double x, double y, double magnitude) {
        this.X = (magnitude > 0) ? (x / magnitude) : 0d;
        this.Y = (magnitude > 0) ? (y / magnitude) : 0d;
        this.MAGNITUDE = magnitude;
        this.UNIT = this;
    }

    public Vector2() {
        this.X = 0d;
        this.Y = 0d;
        this.MAGNITUDE = 0d;
        this.UNIT = this;
    }
    public Vector2(double x) {
        this.X = x;
        this.Y = 0d;
        this.MAGNITUDE = 0d;
        this.UNIT = X_AXIS;
    }
    public Vector2(double x, double y) {
        this.X = x;
        this.Y = y;
        this.MAGNITUDE = Math.sqrt(x*x + y*y);
        this.UNIT = new Vector2(x, y, MAGNITUDE);
    }

    // -- Methods -- \\
    /**Restricts the value range in-between min and max, inclusive
     * @since 1.0
     * @param value the value being clamped
     * @param min the minimum acceptable value
     * @param max the maximum acceptable value
     * @return the value in the range in-between min and max, inclusive
     */
    private static double clamp(double value, double min, double max) {return Math.max(min, Math.min(max, value));}

    /**Calculates the value interpolated/extrapolated from a to b by the given alpha
     * @since 1.0
     * @param a the initial value
     * @param b the target value
     * @param alpha the percentage to interpolate/extrapolate by
     * @return the value interpolated/extrapolated from a to b
     */
    private static double lerp(double a, double b, double alpha) {return a + (b - a) * alpha;}

    /**Calculates the alpha from the given value interpolated/extrapolated from a to b
     * @since 1.0
     * @param a the initial value
     * @param b the target value
     * @param value the value to interpolate/extrapolate from
     * @return the alpha interpolated/extrapolated by value from a to b
     */
    private static double inverseLerp(double a, double b, double value) {return (value - a)/(b - a);}

    @Override public String toString() {return String.format("%s[%g, %g]", Vector2.class.getSimpleName(), X, Y);}
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2 vector)) return false;
        return Double.compare(vector.X, X) == 0 && Double.compare(vector.Y, Y) == 0;
    }
    @Override public int hashCode() {return Objects.hash(X, Y);}

    /**Adds the two vectors
     * @since 1.0
     * @param vector the vector being added to the first
     */
    public Vector2 add(Vector2 vector) {return new Vector2(X+vector.X, Y+vector.Y);}
    /**Adds the vector with a constant
     * @since 1.0
     * @param constant the value to add the vector with
     */
    public Vector2 add(double constant) {return new Vector2(X+constant, Y+constant);}

    /**Subtracts the two vectors
     * @since 1.0
     * @param vector the vector being subtracted from the first
     */
    public Vector2 sub(Vector2 vector) {return new Vector2(X-vector.X, Y-vector.Y);}
    /**Subtracts the vector by a constant
     * @since 1.0
     * @param constant the value to subtract the vector by
     */
    public Vector2 sub(double constant) {return new Vector2(X-constant, Y-constant);}

    /**Multiplies the two vectors
     * @since 1.0
     * @param vector the vector being multiplied by the first
     */
    public Vector2 mul(Vector2 vector) {return new Vector2(X*vector.X, Y*vector.Y);}
    /**Multiplies the vector with a constant
     * @since 1.0
     * @param constant the value to multiply the vector by
     */
    public Vector2 mul(double constant) {return new Vector2(X*constant, Y*constant);}

    /**Divides the two vectors
     * @since 1.0
     * @param vector the vector being divided from the first
     * @throws ArithmeticException if division errors
     */
    public Vector2 div(Vector2 vector) throws ArithmeticException {return new Vector2(Y/vector.Y, Y/vector.Y);}
    /**Divides the vector by a constant
     * @since 1.0
     * @param constant the value to divide the vector by
     * @throws ArithmeticException if division errors
     */
    public Vector2 div(double constant) throws ArithmeticException {return new Vector2(X/constant, Y/constant);}

    /**Modulo the two vectors
     * @since 1.0
     * @param vector the vector first finds the remainder from
     * @throws ArithmeticException if division errors
     */
    public Vector2 mod(Vector2 vector) throws ArithmeticException {return new Vector2(X%vector.X, Y%vector.Y);}
    /**Modulo the vector by a constant
     * @since 1.0
     * @param constant the value to modulo the vector by
     * @throws ArithmeticException if division errors
     */
    public Vector2 mod(double constant) throws ArithmeticException {return new Vector2(X%constant, Y%constant);}

    /**Removes the fractional part of the vector and returns a {@class Vector2} with the integer components
     * @since 1.4
     * @return A {@class Vector2} containing the integer component of the vector
     */
    public Vector2 iPart() {return new Vector2((int)X, (int)Y);}

    /**Removes the integer part of the vector and returns a {@class Vector2} with the fractional component
     * @since 1.4
     * @return A {@class Vector2} with the fractional component of the vector
     */
    public Vector2 fPart() {return new Vector2(X%1, Y%1);}

    /**Calculates the absolute values of the vector coordinates
     * @since 1.0
     * @return a vector with the absolute values of the vector coordinates
     */
    public Vector2 abs() {return new Vector2(Math.abs(X), Math.abs(Y));}

    /**Calculates the distance between the two vectors
     * @since 1.0
     * @param vector the vector to find the distance from the first
     * @return the distance between the two vectors
     */
    public double dist(Vector2 vector) {return sub(vector).MAGNITUDE;}
    /**Calculates the distance between the vector and coordinates
     * @since 1.0
     * @param x the positional x-coordinate
     * @param y the positional y-coordinate
     * @return the distance between the two vectors
     */
    public double dist(double x, double y) {return new Vector2(this.X-X, this.Y-Y).MAGNITUDE;}

    /**Calculates the vector interpolated from this vector to the target vector by the given alpha
     * @since 1.1
     * @param targetVector the target vector to interpolate towards
     * @param alpha the percentage to interpolate by
     * @return the vector interpolated from this vector and the target vector
     */
    public Vector2 lerp(Vector2 targetVector, double alpha) {return new Vector2(lerp(X, targetVector.X, alpha), lerp(Y, targetVector.Y, alpha));}
    /**Calculates the alpha from the initial vector to the target vector by this vector
     * @since 1.1
     * @param initialVector the target to interpolate towards
     * @param targetVector the target to interpolate towards
     * @return the alpha for this vector from the initial vector to the target vector
     */
    public Vector2 inverseLerp(Vector2 initialVector, Vector2 targetVector) {return new Vector2(inverseLerp(initialVector.X, targetVector.X, X), inverseLerp(initialVector.Y, targetVector.Y, Y));}

    /**Calculates the dot product of the two vectors
     * @since 1.1
     * @param vector the other vector to calculate the dot product with
     * @return the dot product of the two vectors
     */
    public double dot(Vector2 vector) {return (X*vector.X + Y*vector.Y);}

    /**Calculates the cross product of the two vectors
     * @since 1.1
     * @param vector the other vector to calculate the cross product with
     * @return the cross product of the two vectors
     */
    public double cross(Vector2 vector) {return (X*vector.Y - vector.X*Y);}

    /**Calculates the angle between the two vectors
     * @since 1.2
     * @param vector the other vector to calculate the angle in-between
     * @return the angle between the two vectors (in radians)
     */
    public double angleBetween(Vector2 vector) {return Math.acos(clamp(dot(vector), -1, 1));}

    /**Calculates the signed angle between the two vectors
     * @since 1.2
     * @param vector the other vector to calculate the signed angle in-between
     * @return the signed angle between the two vectors (in radians)
     */
    public double angleBetweenSigned(Vector2 vector) {return Math.atan2(X*vector.Y - Y*vector.X, X*vector.X + Y*vector.Y);}

    /**Calculates the reflection direction vector
     * @since 1.3
     * @param normal the surface normal to reflect off of
     * @return the direction vector reflected off the surface normal
     */
    public Vector2 reflect(Vector2 normal) {
        double velocityDotProduct = dot(normal);
        Vector2 reflectVector = new Vector2(X-(2 * velocityDotProduct * normal.X), Y-(2 * velocityDotProduct * normal.Y));
        return reflectVector;
    }
}