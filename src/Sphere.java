public class Sphere {
    public final Vector3 center;
    public final double radius;
    public final Material material;
    
    public Sphere(Vector3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }
    
    public Double intersect(Ray ray, double tMin, double tMax) {
        Vector3 oc = ray.origin.subtract(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2.0 * oc.dot(ray.direction);
        double c = oc.dot(oc) - radius * radius;
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) return null;

        double sqrtDisc = Math.sqrt(discriminant);
        double t1 = (-b - sqrtDisc) / (2.0 * a);
        double t2 = (-b + sqrtDisc) / (2.0 * a);

        // Find the smallest valid t in range
        if (t1 > tMin && t1 < tMax) return t1;
        if (t2 > tMin && t2 < tMax) return t2;
        return null;
    }

    public Vector3 normalAt(Vector3 point) {
        return point.subtract(center).normalize();
    }
}