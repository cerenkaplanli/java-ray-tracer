public class DirectionalLight extends Light {
    public final Vector3 direction; // Direction light is pointing toward scene
    
    public DirectionalLight(Vector3 direction, Color color) {
        super(color);
        this.direction = direction.normalize();
    }
    
    @Override
    public Vector3 getDirection(Vector3 point) {
        // For shading: direction from surface to light 
        return direction.multiply(-1);
    }
    
    @Override
    public double getDistance(Vector3 point) {
        return Double.POSITIVE_INFINITY;
    }
}