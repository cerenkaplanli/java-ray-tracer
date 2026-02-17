public class Color {
    public final double r, g, b;

    public Color(double r, double g, double b) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
    }

    private double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }

    public Color multiply(double scalar) {
        return new Color(r * scalar, g * scalar, b * scalar);
    }

    public Color multiply(Color other) {
        return new Color(r * other.r, g * other.g, b * other.b);
    }

    public Color add(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    public Color clamp() {
        return new Color(r, g, b);
    }

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
}