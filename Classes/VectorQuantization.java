package Classes;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Classes.VectorOperations.*;

public class VectorQuantization {
    public void compress(Image image, int vectorSize){
        Pixel[][] pixels = image.getPixels();
        List<Vector> vectors = new java.util.Vector<>() ;

        splitImage(vectorSize, pixels, vectors);
        List<CodeBook> codeBooks = new java.util.Vector<>() ;
        quantize(vectorSize * vectorSize, vectors, codeBooks);
        optimizeQuantization(codeBooks, vectors) ;

        Map<CodeBook, List<Vector>> nearestVectors = getNearestVectors(codeBooks, vectors) ;
        Map<Vector, CodeBook> codes = generateCodes(nearestVectors) ;
        Pixel[][] compressedPixels = getPixels(vectors, codes, pixels.length, pixels[0].length) ;
        Image compressedImage = new Image() ;
        compressedImage.writeImage(compressedPixels);
        writeCodeBooksToFile(codeBooks);
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

    public void writeCodeBooksToFile(List<CodeBook> codeBooks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("out.bin"))) {
            // Write each code book to the binary file
            for (CodeBook codeBook : codeBooks) {
                oos.writeObject(codeBook);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception or log an error message
        }
    }

}