import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class VectorQuantization {

    public void compress(Image image, int vectorSize){
        Pixel[][] pixels = image.getPixels();
        List<Vector> vectors = new java.util.Vector<>() ;

        VectorOperations.splitImage(vectorSize, pixels, vectors);
        List<CodeBook> codeBooks = new java.util.Vector<>() ;
        VectorOperations.quantize(vectorSize * vectorSize, vectors, codeBooks);
        VectorOperations.optimizeQuantization(codeBooks, vectors) ;

        Map<CodeBook, List<Vector>> nearestVectors = VectorOperations.getNearestVectors(codeBooks, vectors) ;
        Map<Vector, CodeBook> codes = VectorOperations.generateCodes(nearestVectors) ;
        Pixel[][] compressedPixels = VectorOperations.getPixels(vectors, codes, pixels.length, pixels[0].length) ;
        Image compressedImage = new Image() ;

        ArrayList<int[][]> intPixels = convertPixelsToInt(compressedPixels);
        writeImage(intPixels, compressedPixels.length, compressedPixels[0].length, 2);
//        writeCompressedPixelsToBin(intPixels);
//        writeCodeBooksToBin(codeBooks);
    }
    private void writeCodeBooksToFile(List<CodeBook> codeBooks) {
        String fileName = "codeBooks.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (CodeBook codeBook : codeBooks) {
                writer.write(codeBook.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage constructImage(File file){

        List<CodeBook> codeBooks = readCodeBooksFromFile();

        return null;
    }

    public List<CodeBook> readCodeBooksFromFile() {
        List<CodeBook> codeBooks = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("out.bin"))) {
            // Read each code book from the binary file
            while (true) {
                try {
                    CodeBook codeBook = (CodeBook) ois.readObject();
                    codeBooks.add(codeBook);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception or log an error message
        }

        return codeBooks;
    }

//    public void writeCodeBooksToFile(List<CodeBook> codeBooks) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("out.bin"))) {
//            // Write each code book to the binary file
//            for (CodeBook codeBook : codeBooks) {
//                oos.writeObject(codeBook);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the exception or log an error message
//        }
//    }

    public static void writeImage(ArrayList<int[][]> imagePixels, int width, int height, int vectorSize) {
        BufferedImage image = new BufferedImage(width * vectorSize, height * vectorSize, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = i * width + j;

                if (index < imagePixels.size()) {
                    int[][] sourceBlock = imagePixels.get(index);

                    for (int m = 0; m < vectorSize; m++) {
                        for (int n = 0; n < vectorSize; n++) {
                            int rgb = sourceBlock[m][n];
                            image.setRGB(j * vectorSize + n, i * vectorSize + m, rgb);
                        }
                    }
                }
            }
        }

        try {
            ImageIO.write(image, "png", new File("outputImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void writePixelsToFile(String fileName, Pixel[][] pixels) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Pixel[] row : pixels) {
                for (Pixel pixel : row) {
                    writer.write(pixel.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<int[][]> convertPixelsToInt(Pixel[][] pixels) {
        ArrayList<int[][]> result = new ArrayList<>();

        for (Pixel[] row : pixels) {
            int[][] intRow = new int[row.length][3];

            for (int i = 0; i < row.length; i++) {
                intRow[i][0] = (int) row[i].getRed();
                intRow[i][1] = (int) row[i].getGreen();
                intRow[i][2] = (int) row[i].getBlue();
            }

            result.add(intRow);
        }

        return result;
    }

    public void writeCompressedPixelsToBin(ArrayList<int[][]> imagePixels) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("compressedPixels.bin"))) {
            oos.writeObject(imagePixels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCodeBooksToBin(List<CodeBook> codeBooks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("codeBooks.bin"))) {
            oos.writeObject(codeBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}