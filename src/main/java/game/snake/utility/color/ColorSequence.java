package game.snake.utility.color;

import game.snake.utility.Utility;

import java.util.*;
import java.util.stream.Collectors;

public class ColorSequence extends ArrayList<ColorKeypoint> {
    public ColorSequence() {}
    public ColorSequence(Color color) {this(color, color);}
    public ColorSequence(Color origin, Color target) {addAll(new ColorKeypoint(origin), new ColorKeypoint(1f, target));}
    public ColorSequence(Color... colors) {
        if (colors.length == 0) {return;} else if (colors.length == 1) {addAll(new ColorKeypoint(colors[0]), new ColorKeypoint(1f, colors[0])); return;}
        float max = (colors.length - 1);

        for (int i=0; i<colors.length; i++) {add(new ColorKeypoint(i/max, colors[i]));}
    }

    public ColorSequence(ColorKeypoint... keypoints) {
        List<ColorKeypoint> keypointList = Arrays.stream(keypoints).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ColorKeypoint::getTIME))), ArrayList::new));
        for (ColorKeypoint keypoint : keypointList) {super.add(keypoint);}
    }

    @Override public String toString() {
        HashSet<Color> colors = new HashSet<Color>();
        for (ColorKeypoint keypoint : this) {if (!colors.contains(keypoint)) {colors.add(keypoint.COLOR);}}

        return String.format("%s[keypoints: %d, colors: %d]", getClass().getSimpleName(), size(), colors.size());
    }

    @Override public boolean add(ColorKeypoint keypoint) {
        if (contains(keypoint)) {return false;}
        boolean changed = super.add(keypoint);

        if (changed) {Collections.sort(this);}
        return changed;
    }

    /**Returns {@code True} if {@code this} contains one or more of the keypoints in {@code keypoints}, such that<br>
     * for any element pair {@code e1}, {@code e2}: {@code e1.equals(e2)}
     * @param keypoints the keypoints in the collection
     * @return {@code True} if the array was changed due to this method call
     */
    public boolean containsAny(Collection<ColorKeypoint> keypoints) {
        for (ColorKeypoint keypoint : keypoints) {
            if (contains(keypoint)) {return true;}
        }
        return false;
    }

    public boolean equals(ColorSequence sequence) {
        final int SIZE = size();
        if (SIZE != sequence.size()) {return false;}

        for (int i=0; i<SIZE; i++) {if (!get(i).equals(sequence.get(i))) {return false;}} //unfortunately, cannot use containsAll(), as it doesn't check in the specified order
        return true;
    }

    /**Adds the given keypoints to the sequence if possible
     * @param k the keypoints to add to the sequence, if possible
     * @return {@code True} if the sequence was changed as a result of this method call
     */
    public boolean addAll(ColorKeypoint... k) {
        List<ColorKeypoint> keypoints = Arrays.asList(k);

        for (ColorKeypoint keypoint : keypoints) {if (contains(keypoint) || keypoints.indexOf(keypoint) != keypoints.lastIndexOf(keypoint)) {return false;}}
        addAll(keypoints);
        Collections.sort(this);
        return true;
    }

    public Color[] getColors() {
        Color[] colors = new Color[size()];

        for (int i=0; i<size(); i++) {colors[i] = this.get(i).COLOR;}
        return colors;
    }

    public Color getColorAtTime(float alpha) {
        alpha = Utility.clamp(alpha, 0, 1);
        if (size() == 2 && get(0).equals(get(1))) {return get(0).COLOR;} else if (alpha == 0f) {return get(0).COLOR;} else if (alpha == 1f) {return get(size()-1).COLOR;}

        int length = size()-1;
        for (int i=0; i<length; i++) { // step through sequential keypoint pairs
            ColorKeypoint currentKeypoint = get(i);
            ColorKeypoint nextKeypoint = get(i+1);

            if (alpha >= currentKeypoint.TIME && alpha < nextKeypoint.TIME) {
                alpha = (alpha - currentKeypoint.TIME) / (nextKeypoint.TIME - currentKeypoint.TIME); //find the alpha between the points from current alpha (Inverse Lerp)
                return currentKeypoint.COLOR.lerp(nextKeypoint.COLOR, alpha);
            }
        }

        return get(size()-1).COLOR;
    }
}
