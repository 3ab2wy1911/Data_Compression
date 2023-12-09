package Classes;
import java.util.Arrays;
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
    }

    public void decompress(){

    }
}