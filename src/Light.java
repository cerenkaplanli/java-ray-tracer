public abstract class Light {
    public final Color color;
    
    public Light(Color color) {
        this.color = color;
    }
    
    // Returns direction from surface to light
    public abstract Vector3 getDirection(Vector3 point);
    public abstract double getDistance(Vector3 point);
}