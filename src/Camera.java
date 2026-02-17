public class Camera {
    public final Vector3 position;
    public final Vector3 lookAt;
    public final Vector3 up;
    public final double fov;
    public final int width;
    public final int height;
    public final int maxBounces;

    public Camera(Vector3 position, Vector3 lookAt, Vector3 up, 
                 double fov, int width, int height, int maxBounces) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.maxBounces = maxBounces;
    }
}