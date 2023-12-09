package Classes;

import java.util.Arrays;

public class Vector {
    int width ;
    int height ;
    Pixel[][] pixels ;

    public Vector(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new Pixel[width][height] ;
    }
    public Vector(int width, int height, Pixel[][] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void add(Pixel pixel, int pixelX, int pixelY) {
        pixels[pixelX][pixelY] = pixel ;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Vector{\n");
        for (Pixel[] row : pixels) {
            result.append("\t\tPixels{\n");
            for (Pixel pixel : row) {
                result.append("\t\t\t").append(pixel).append("\n");
            }
            result.append("\t\t}\n");
        }
        result.append("\t}");
        return result.toString();
    }

    public Pixel[][] getPixels() {
        return pixels ;
    }
}
