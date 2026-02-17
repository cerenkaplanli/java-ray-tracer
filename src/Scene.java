import java.util.List;

public class Scene {
    public final Camera camera;
    public final List<Sphere> spheres;
    public final List<Light> lights;
    public final Color ambientLight;
    public final Color backgroundColor;
    public final String outputFilename;
    
    public Scene(Camera camera, List<Sphere> spheres, List<Light> lights, 
                Color ambientLight, Color backgroundColor, String outputFilename) {
        this.camera = camera;
        this.spheres = spheres;
        this.lights = lights;
        this.ambientLight = ambientLight;
        this.backgroundColor = backgroundColor;
        this.outputFilename = outputFilename;
    }
}