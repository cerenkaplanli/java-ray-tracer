public class PointLight extends Light {
    public final Vector3 position;
    
    public PointLight(Vector3 position, Color color) {
        super(color);
        this.position = position;
    }
    
    @Override
    public Vector3 getDirection(Vector3 point) {
        // Direction from surface to light
        return position.subtract(point).normalize();
    }
    
    @Override
    public double getDistance(Vector3 point) {
        return position.subtract(point).length();
    }
}