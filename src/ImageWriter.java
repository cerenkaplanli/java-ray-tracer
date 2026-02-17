import java.io.FileWriter;
import java.io.IOException;

public class ImageWriter {
    public static void writePPM(Image image, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("P3\n");
            writer.write(image.getWidth() + " " + image.getHeight() + "\n");
            writer.write("255\n");
            
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = image.getPixel(x, y);
                    int r = (int) (color.r * 255);
                    int g = (int) (color.g * 255);
                    int b = (int) (color.b * 255);
                    writer.write(r + " " + g + " " + b + " ");
                }
                writer.write("\n");
            }
        }
    }
}