import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public static Scene parseScene(String filename) throws Exception {
        File file = new File(filename);
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        Element sceneElem = doc.getDocumentElement();
        
        // Output filename
        String outputFilename = sceneElem.getAttribute("output_file");
        
        // Background color
        Element bgColorElem = (Element) sceneElem.getElementsByTagName("background_color").item(0);
        Color backgroundColor = parseColor(bgColorElem);

        // Camera
        Element camElem = (Element) sceneElem.getElementsByTagName("camera").item(0);
        Camera camera = parseCamera(camElem);

        // Ambient light
        Element ambLightElem = (Element) sceneElem.getElementsByTagName("ambient_light").item(0);
        Color ambientLight = parseColor((Element) ambLightElem.getElementsByTagName("color").item(0));

        // Lights
        List<Light> lights = new ArrayList<>();
        
        // Parallel lights
        NodeList parallelLights = sceneElem.getElementsByTagName("parallel_light");
        for (int i = 0; i < parallelLights.getLength(); i++) {
            Element lightElem = (Element) parallelLights.item(i);
            Color color = parseColor((Element) lightElem.getElementsByTagName("color").item(0));
            Vector3 direction = parseVector((Element) lightElem.getElementsByTagName("direction").item(0));
            lights.add(new DirectionalLight(direction, color));
        }
        
        // Point lights
        NodeList pointLights = sceneElem.getElementsByTagName("point_light");
        for (int i = 0; i < pointLights.getLength(); i++) {
            Element lightElem = (Element) pointLights.item(i);
            Color color = parseColor((Element) lightElem.getElementsByTagName("color").item(0));
            Vector3 position = parseVector((Element) lightElem.getElementsByTagName("position").item(0));
            lights.add(new PointLight(position, color));
        }

        // Spheres
        List<Sphere> spheres = new ArrayList<>();
        NodeList sphereNodes = sceneElem.getElementsByTagName("sphere");
        for (int i = 0; i < sphereNodes.getLength(); i++) {
            Element sphereElem = (Element) sphereNodes.item(i);
            
            // Radius
            double radius = Double.parseDouble(sphereElem.getAttribute("radius"));
            
            // Position
            Vector3 position = parseVector((Element) sphereElem.getElementsByTagName("position").item(0));
            
            // Material
            Material material = parseMaterial(sphereElem);
            
            spheres.add(new Sphere(position, radius, material));
        }

        return new Scene(camera, spheres, lights, ambientLight, backgroundColor, outputFilename);
    }

    private static Camera parseCamera(Element camElem) {
        Vector3 position = parseVector((Element) camElem.getElementsByTagName("position").item(0));
        Vector3 lookAt = parseVector((Element) camElem.getElementsByTagName("lookat").item(0));
        Vector3 up = parseVector((Element) camElem.getElementsByTagName("up").item(0));
        
        // FOV
        Element fovElem = (Element) camElem.getElementsByTagName("horizontal_fov").item(0);
        double fov = Double.parseDouble(fovElem.getAttribute("angle"));
        
        // Resolution
        Element resElem = (Element) camElem.getElementsByTagName("resolution").item(0);
        int width = Integer.parseInt(resElem.getAttribute("horizontal"));
        int height = Integer.parseInt(resElem.getAttribute("vertical"));
        
        // Max bounces
        Element bouncesElem = (Element) camElem.getElementsByTagName("max_bounces").item(0);
        int maxBounces = Integer.parseInt(bouncesElem.getAttribute("n"));
        
        return new Camera(position, lookAt, up, fov, width, height, maxBounces);
    }

    private static Vector3 parseVector(Element elem) {
        double x = Double.parseDouble(elem.getAttribute("x"));
        double y = Double.parseDouble(elem.getAttribute("y"));
        double z = Double.parseDouble(elem.getAttribute("z"));
        return new Vector3(x, y, z);
    }

    private static Color parseColor(Element elem) {
        double r = Double.parseDouble(elem.getAttribute("r"));
        double g = Double.parseDouble(elem.getAttribute("g"));
        double b = Double.parseDouble(elem.getAttribute("b"));
        return new Color(r, g, b);
    }

    private static Material parseMaterial(Element sphereElem) {
        Element matElem = (Element) sphereElem.getElementsByTagName("material_solid").item(0);
        
        // Base color
        Color baseColor = parseColor((Element) matElem.getElementsByTagName("color").item(0));
        
        // Phong coefficients
        Element phongElem = (Element) matElem.getElementsByTagName("phong").item(0);
        double ka = Double.parseDouble(phongElem.getAttribute("ka"));
        double kd = Double.parseDouble(phongElem.getAttribute("kd"));
        double ks = Double.parseDouble(phongElem.getAttribute("ks"));
        double exponent = Double.parseDouble(phongElem.getAttribute("exponent"));
        
        // Material properties
        double reflectance = Double.parseDouble(
            ((Element) matElem.getElementsByTagName("reflectance").item(0)).getAttribute("r")
        );
        
        double transmittance = Double.parseDouble(
            ((Element) matElem.getElementsByTagName("transmittance").item(0)).getAttribute("t")
        );
        
        double refractionIndex = Double.parseDouble(
            ((Element) matElem.getElementsByTagName("refraction").item(0)).getAttribute("iof")
        );
        
        return new Material(
            baseColor.multiply(ka), // ambient
            baseColor.multiply(kd), // diffuse
            ks,                     // specular coefficient
            exponent,               // shininess
            reflectance,
            transmittance,
            refractionIndex
        );
    }
}