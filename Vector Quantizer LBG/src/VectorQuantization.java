import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VectorQuantization {

    private int height, width;


    //----------------------------------------------------------------

    public BufferedImage compress(BufferedImage image) throws IOException {

        this.height = image.getHeight();
        this.width = image.getWidth();

        double[][] pixels = new double [height][width];

//        for (int i = 0; i < height; i++){
//            for (int j = 0; j < width; j++){
//                int rgb = image.getRGB(i, j);
//                int r = (rgb & 0x00ff0000) >> 16;
//                int g = (rgb & 0x0000ff00) >> 8;
//                int b = (rgb & 0x000000ff);
//                pixels[i][j] = Math.max(Math.max(r, g), b);
//            }
//        }



        this.height = pixels.length;
        this.width = pixels[0].length;
        ImageIO.write(new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR), "png", new File("out.png"));
        return ImageIO.read(new File("out.png"));
    }

}
