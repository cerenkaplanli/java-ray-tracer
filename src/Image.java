public class Image {
    private final int width;
    private final int height;
    private final Color[][] pixels;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Color[width][height];
        initializeBlack();
    }

    private void initializeBlack() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = Color.BLACK;
            }
        }
    }

    public void setPixel(int x, int y, Color color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[x][y] = color;
        }
    }

    public Color getPixel(int x, int y) {
        return pixels[x][y];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}