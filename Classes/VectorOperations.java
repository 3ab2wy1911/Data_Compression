package Classes;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.toBinaryString;
import static java.lang.Math.*;

public class VectorOperations {
    public static void splitImage(int vectorSize, Pixel[][] pixels, List<Vector> vectors) {
        for (int i = 0; i < pixels.length; i += vectorSize) {
            for (int j = 0; j < pixels[i].length; j += vectorSize) {
                Vector vector = new Vector(vectorSize, vectorSize);

                for (int x = 0, pixelX = i; pixelX < i + vectorSize; x++, ++pixelX) {
                    for (int y = 0, pixelY = j; pixelY < j + vectorSize; y++, ++pixelY) {
                        if (pixelX < pixels.length && pixelY < pixels[0].length) {
                            vector.add(pixels[pixelX][pixelY], x, y);
                        }
                    }
                }
                vectors.add(vector);
            }
        }
    }
    public static Vector calcAvg(List<Vector> vectors) {
        if (vectors.isEmpty()){
            return new Vector(0, 0) ;
        }
        Vector vector = new Vector(vectors.get(0).getPixels().length, vectors.get(0).getPixels()[0].length) ;
        for (int i = 0; i < vectors.get(0).getPixels().length; i++) {
            for (int j = 0; j < vectors.get(0).getPixels()[0].length; j++) {
                double redSum = 0, greenSum = 0, blueSum = 0;
                for (Vector temp :
                        vectors) {
                    redSum += temp.getPixels()[i][j].getRed() ;
                    greenSum += temp.getPixels()[i][j].getGreen() ;
                    blueSum += temp.getPixels()[i][j].getBlue() ;
                }
                redSum /= vectors.size() ;
                greenSum /= vectors.size() ;
                blueSum /= vectors.size() ;

                vector.add(new Pixel(redSum, greenSum, blueSum), i, j);
            }
        }

        return vector ;
    }
    private static double calcDistance(Vector vector, Vector avgVector) {
        double distance = 0;
        for (int i = 0; i < vector.getWidth(); i++) {
            for (int j = 0; j < vector.getHeight(); j++) {
                distance += abs(vector.getPixels()[i][j].getAll() - avgVector.getPixels()[i][j].getAll());
            }
        }

        return distance;
    }
    private static double calcDistance(Vector vector, Vector avgVector, boolean leftPart) {
        Vector newVector = new Vector(avgVector.getWidth(), avgVector.getHeight());
        Pixel[][] pixels = avgVector.getPixels() ;

        int increament = 0;
        if (!leftPart){
            increament = 1;
        }

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                newVector.add(new Pixel( pixels[i][j].getRed() + increament,
                        pixels[i][j].getGreen() + increament,
                        pixels[i][j].getBlue() + increament), i, j);
            }
        }

        return calcDistance(vector, newVector) ;
    }
    public static void quantize(int level, List<Vector> vectors, List<CodeBook> codeBooks){
        if (level == 1 || vectors.isEmpty()){
            if (!vectors.isEmpty()){
                codeBooks.add(new CodeBook(calcAvg(vectors))) ;
            }
            return;
        }
        List<Vector> leftVectors = new java.util.Vector<>(), rightVectors = new java.util.Vector<>();
        Vector avgVector = calcAvg(vectors) ;

        for (Vector vector :
                vectors) {
            double leftDistance = calcDistance(vector, avgVector, true) ;
            double rightDistance = calcDistance(vector, avgVector, false) ;

            if (leftDistance >= rightDistance){
                rightVectors.add(vector) ;
            }
            else{
                leftVectors.add(vector) ;
            }
        }

        quantize(level / 2, leftVectors, codeBooks);
        quantize(level / 2, rightVectors, codeBooks);
    }
    public static Map<CodeBook, List<Vector>> getNearestVectors(List<CodeBook> codeBooks, List<Vector> vectors){
        Map<CodeBook, List<Vector>> hashMap = new HashMap<>();

        for (Vector vector: vectors) {
            double minDistance = Double.MAX_VALUE ;
            int index = -1;
            for (int i = 0; i < codeBooks.size(); ++i) {
                double distance = calcDistance(vector, codeBooks.get(i)) ;
                if (distance < minDistance){
                    minDistance = distance ;
                    index = i ;
                }
            }
            List<Vector> temp = hashMap.get(codeBooks.get(index)) ;
            if (temp == null){
                temp = new java.util.Vector<>();
            }
            temp.add(vector) ;
            hashMap.put(codeBooks.get(index),  temp) ;
        }

        return hashMap ;
    }
    public static void optimizeQuantization(List<CodeBook> codeBooks, List<Vector> vectors){
        Map<CodeBook, List<Vector>> hashMap = getNearestVectors(codeBooks, vectors);
        boolean changed ;
        List<CodeBook> updatedCodeBooks = new java.util.Vector<>();

        do {
            changed = false;
            for (Map.Entry<CodeBook, List<Vector>> entity: hashMap.entrySet()) {
                updatedCodeBooks.remove(entity.getKey()) ;
                updatedCodeBooks.add(new CodeBook(calcAvg(entity.getValue()))) ;
            }
            if (codeBooks != updatedCodeBooks) {
                changed = true;
                codeBooks = updatedCodeBooks ;
                hashMap = getNearestVectors(codeBooks, vectors) ;
            }
        }
        while (changed);
    }
    public static int countBits(int codeBooks) {
        int bits = 0;
        int value = 1;

        while (value < codeBooks) {
            value *= 2;
            bits++;
        }

        return bits;
    }
    private static String toBinary(int number, int nBits){
        String binary = toBinaryString(number) ;
        char[] zeros = new char[nBits - binary.length()] ;
        Arrays.fill(zeros, '0');
        return new String(zeros) + binary ;
    }
    public static Map<Vector, CodeBook> generateCodes(Map<CodeBook, List<Vector>> nearestVectors){
        int numberOfBits = countBits(nearestVectors.size()) ;
        Map<Vector, CodeBook> codes = new HashMap<>();
        AtomicInteger counter = new AtomicInteger();

        nearestVectors.forEach((codeBook, vectorList) -> {
            codeBook.setCode(toBinary(counter.getAndIncrement(), numberOfBits)) ;
            vectorList.forEach((vector -> codes.put(vector, codeBook)));
        });

        return codes ;
    }
    public static Pixel[][] getPixels(List<Vector> vectors, Map<Vector, CodeBook> codes, int height, int width){
        Pixel[][] pixels = new Pixel[width][height];
        int vectorSize = vectors.get(0).getHeight() ;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                for (Vector vector: vectors) {
                    CodeBook codeBook = codes.get(vector);
                    Pixel[][] subPixels = codeBook.getPixels();
                    pixels[i][j] = subPixels[i % vectorSize][j % vectorSize] ;
                }
            }
        }

        return pixels ;
    }
}
