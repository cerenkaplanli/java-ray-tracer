public class Ray {
    public final Vector3 origin;
    public final Vector3 direction;
    public final double minT;
    public final double maxT;

    public Ray(Vector3 origin, Vector3 direction) {
        this(origin, direction, 1e-4, Double.POSITIVE_INFINITY);
    }
    
    public Ray(Vector3 origin, Vector3 direction, double minT, double maxT) {
        this.origin = origin;
        this.direction = direction.normalize();
        this.minT = minT;
        this.maxT = maxT;
    }

    public Vector3 at(double t) {
        return origin.add(direction.multiply(t));
    }
}