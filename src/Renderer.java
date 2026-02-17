public class Renderer {
    private static final double BIAS = 1e-4;
    private static final int MAX_DEPTH = 5;

    public static Image renderScene(Scene scene) {
        Camera camera = scene.camera;
        int width = camera.width;
        int height = camera.height;
        Image image = new Image(width, height);

        // Create camera coordinate system
        Vector3 forward = camera.lookAt.subtract(camera.position).normalize();
        Vector3 right = forward.cross(camera.up.normalize()).normalize();
        Vector3 up = right.cross(forward).normalize();

        double aspectRatio = (double) width / height;
        double fovRad = Math.toRadians(camera.fov);
        double scale = Math.tan(fovRad / 2);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Calculate pixel center
                double u = (x + 0.5) / width;
                double v = (y + 0.5) / height;

                // Map to [-1, 1] range with aspect ratio
                double xCam = (2 * u - 1) * aspectRatio * scale;
                double yCam = (1 - 2 * v) * scale;  // Flip y-axis

                // Calculate ray direction
                Vector3 rayDir = right.multiply(xCam)
                                     .add(up.multiply(yCam))
                                     .add(forward)
                                     .normalize();

                Ray ray = new Ray(camera.position, rayDir);
                Color color = traceRay(ray, scene, 0);
                image.setPixel(x, y, color);
            }
        }

        return image;
    }

    private static Color traceRay(Ray ray, Scene scene, int depth) {
        if (depth > MAX_DEPTH) {
            return Color.BLACK;
        }

        HitRecord hit = findClosestHit(ray, scene);
        if (hit == null) {
            return scene.backgroundColor;
        }
        
        return calculateShading(hit, scene);
    }

    private static HitRecord findClosestHit(Ray ray, Scene scene) {
        HitRecord closestHit = null;
        double closestT = Double.POSITIVE_INFINITY;

        for (Sphere sphere : scene.spheres) {
            Double t = sphere.intersect(ray, BIAS, closestT);
            if (t != null && t < closestT) {
                closestT = t;
                Vector3 point = ray.at(t);
                Vector3 normal = sphere.normalAt(point);
                closestHit = new HitRecord(point, normal, sphere.material, t, sphere, ray.direction);
            }
        }

        return closestHit;
    }

    private static Color calculateShading(HitRecord hit, Scene scene) {
        Material material = hit.material;
        Color result = material.ambient.multiply(scene.ambientLight);
        Vector3 viewDir = hit.rayDirection.multiply(-1).normalize();  // Towards camera

        for (Light light : scene.lights) {
            // Get direction TO light
            Vector3 toLight = light.getDirection(hit.point);
            double lightDistance = light.getDistance(hit.point);

            // Shadow ray setup - start from hit point with bias
            Vector3 shadowOrigin = hit.point.add(hit.normal.multiply(BIAS));
            Ray shadowRay = new Ray(shadowOrigin, toLight, BIAS, lightDistance);
            
            // Check for obstacles between surface and light
            boolean inShadow = false;
            for (Sphere sphere : scene.spheres) {
                // Skip self-intersection
                if (sphere == hit.sphere) continue;
                
                Double t = sphere.intersect(shadowRay, BIAS, lightDistance);
                if (t != null) {
                    inShadow = true;
                    break;
                }
            }
            
            if (inShadow) {
                continue; // Skip this light source
            }

            // Diffuse calculation (Lambertian)
            double NdotL = Math.max(0, hit.normal.dot(toLight));
            Color diffuse = material.diffuse.multiply(light.color).multiply(NdotL);

            // Specular calculation (Phong)
            Vector3 reflectDir = reflect(toLight.multiply(-1), hit.normal).normalize();
            double RdotV = Math.max(0, reflectDir.dot(viewDir));
            double specFactor = Math.pow(RdotV, material.shininess);
            Color specular = light.color.multiply(material.specularCoefficient * specFactor);

            result = result.add(diffuse).add(specular);
        }

        return result.clamp();
    }
    
    private static Vector3 reflect(Vector3 incident, Vector3 normal) {
        // incident points toward surface, normal points out
        return incident.subtract(normal.multiply(2 * incident.dot(normal)));
    }
}