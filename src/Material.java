public class Material {
    public final Color ambient;
    public final Color diffuse;
    public final double specularCoefficient;
    public final double shininess;
    public final double reflectance;
    public final double transmittance;
    public final double refractionIndex;
    
    public Material(Color ambient, Color diffuse, double specularCoefficient, 
                   double shininess, double reflectance, double transmittance, 
                   double refractionIndex) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specularCoefficient = specularCoefficient;
        this.shininess = shininess;
        this.reflectance = reflectance;
        this.transmittance = transmittance;
        this.refractionIndex = refractionIndex;
    }
    
    public static final Material DEFAULT = new Material(
        new Color(0.1, 0.1, 0.1), 
        new Color(0.9, 0.9, 0.9),
        0.8, // ks
        32.0, // shininess
        0.0, // reflectance
        0.0, // transmittance
        1.0  // refractionIndex
    );
}