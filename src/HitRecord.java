public class HitRecord {
    public final Vector3 point;
    public final Vector3 normal;
    public final Material material;
    public final double t;
    public final Sphere sphere;
    public final Vector3 rayDirection;

    public HitRecord(Vector3 point, Vector3 normal, Material material, 
                    double t, Sphere sphere, Vector3 rayDirection) {
        this.point = point;
        this.normal = normal;
        this.material = material;
        this.t = t;
        this.sphere = sphere;
        this.rayDirection = rayDirection;
    }
}