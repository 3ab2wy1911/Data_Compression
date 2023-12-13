
public class Pixel {
    double red;
    double green;
    double blue;

    public Pixel(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return "Pixel{ red = " + red + ", green = " + green + ", blue = " + blue + " }";
    }

    public double getAll() {
        return red + blue + green;
    }
}
