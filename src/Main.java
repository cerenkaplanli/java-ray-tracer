import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int width = 640;
        int height = 480;

        // T1: Output valid black image
        generateBlackImage(width, height);

        // T2: Output image with spheres
        generateSpheresImage(width, height);

        // T3/T4: XML rendering 
        if (args.length >= 1) {
            try {
                Scene scene = XMLParser.parseScene(args[0]);
                Image image = Renderer.renderScene(scene); // Use built-in renderer
                
                String outputName = args[0].substring(0, args[0].lastIndexOf('.')) + ".ppm";
                ImageWriter.writePPM(image, outputName);
                System.out.println("T3/T4: Rendered scene to " + outputName);
            } catch (Exception e) {
                System.err.println("Error processing XML scene: " + e.getMessage());
            }
        }
    }

    private static void generateBlackImage(int width, int height) {
        Image image = new Image(width, height); // Already black by default
        try {
            ImageWriter.writePPM(image, "black_image.ppm");
            System.out.println("T1: Created black_image.ppm");
        } catch (Exception e) {
            System.err.println("Error writing black image: " + e.getMessage());
        }
    }

    private static void generateSpheresImage(int width, int height) {
        // Create camera with parameters
        Camera camera = new Camera(
            new Vector3(0, 0, 5),   
            new Vector3(0, 0, 0),   
            new Vector3(0, 1, 0),   
            60,                      
            width,                 
            height,                 
            3                         
        );

        // Create materials for spheres
        Material redMat = createMaterial(new Color(1, 0, 0));
        Material greenMat = createMaterial(new Color(0, 1, 0));
        Material blueMat = createMaterial(new Color(0, 0, 1));
        Material yellowMat = createMaterial(new Color(1, 1, 0));

        // Create spheres WITH materials
        List<Sphere> spheres = new ArrayList<>();
        spheres.add(new Sphere(new Vector3(-1.5, 0, -3), 0.5, redMat));
        spheres.add(new Sphere(new Vector3(0, 0, -3), 0.5, greenMat));
        spheres.add(new Sphere(new Vector3(1.5, 0, -3), 0.5, blueMat));
        spheres.add(new Sphere(new Vector3(0, -1.5, -3), 0.5, yellowMat));

        // Create lights
        List<Light> lights = new ArrayList<>();
        lights.add(new PointLight(new Vector3(2, 3, 5), new Color(1, 1, 1)));
        lights.add(new DirectionalLight(new Vector3(-1, -1, -1), new Color(0.2, 0.2, 0.2)));

        // Create scene
        Scene scene = new Scene(
            camera,
            spheres,
            lights,
            new Color(0.1, 0.1, 0.1), // ambient light
            new Color(0, 0, 0),  // background color
            "spheres.png" // output filename
        );

        // Render using built-in Renderer
        Image image = Renderer.renderScene(scene);
        
        try {
            ImageWriter.writePPM(image, "spheres.ppm");
            System.out.println("T2: Created spheres.ppm with 4 spheres");
        } catch (Exception e) {
            System.err.println("Error writing spheres image: " + e.getMessage());
        }
    }

    // Helper to create simple materials
    private static Material createMaterial(Color baseColor) {
        return new Material(
            baseColor.multiply(0.1), // ambient = 10% of base
            baseColor.multiply(0.9), // diffuse = 90% of base
            0.8, // specular coefficient (ks)
            50, // shininess
            0.0, // reflectance
            0.0, // transmittance
            1.0 // refraction index
        );
    }
}