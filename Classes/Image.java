package Classes;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

public class Image {
    private Pixel[][] pixels ;
    private BufferedImage image ;

    /**
     *<pre>
     *Method {@code readImage} to read the image and create 2D array {@code pixels}
     *</pre>
     * @param path <strong style="color:'white'"> represent the path of the image</strong>
     */
    public void readImage(String path){
        try {
            image = ImageIO.read(new File(path));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream() ;
            ImageIO.write(image, "jpg", byteArray) ;
            pixels = new Pixel[image.getHeight()][image.getWidth()] ;

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    Color color = new Color(image.getRGB(i, j), true) ;
                    pixels[j][i] = new Pixel(color.getRed(),
                            color.getGreen(),
                            color.getBlue());
                }
            }
        }
        catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }

    /**
     *<pre>
     *Method {@code createBufferedImage} creates the buffered image
     *from the pixels array{@code pixels}
     *</pre>
     * @param pixels <strong style="color:'white'"> represent the pixels of the image</strong>
     * @return <pre>{@code BufferedImage}</pre> <strong style="color:'white'"> represent the Buffered Image</strong>
     */
    public BufferedImage createBufferedImage(Pixel[][] pixels){
        BufferedImage bufferedImage = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB) ;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                int pixel = ((int)pixels[i][j].getRed() << 16) | ((int)pixels[i][j].getGreen() << 8) | ((int)pixels[i][j].getBlue()) ;
                bufferedImage.setRGB(i, j, pixel);
            }
        }

        return bufferedImage ;
    }

    /**
     *<pre>
     *Method {@code writeImage} writes the image
     *and save it in {@code project directory}
     *</pre>
     * @param pixels <strong style="color:'white'"> represent the pixels of the image</strong>
     */
    public void writeImage(Pixel[][] pixels){
        try {
            BufferedImage bufferedImage = createBufferedImage(pixels) ;
            File image = new File(System.getProperty("user.dir") + "\\output.jpg") ;
            ImageIO.write(bufferedImage, "jpg", image) ;
        }
        catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }
    /**
     <pre>
     *Method {@code toString} to convert the pixels 2D array to string and return it
     *</pre>
     * @return String <strong style="color:'white'"> represent the string of the pixels' array</strong>
     */
    @Override
    public String toString() {
        return "Image{" +
                "pixels=" + Arrays.deepToString(pixels) +
                '}';
    }

    public Pixel[][] getPixels() {
        return pixels ;
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }
}