import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Two files one for compression and other for decompression.
        String pathCompress = "E:\\PROJECTS\\Data Compression\\Data_Compression\\LZW\\Compression.txt";
        String pathDecompress = "E:\\PROJECTS\\Data Compression\\Data_Compression\\LZW\\Decompression.txt";

        // Making two instances of manager one for compression and other for decompression.
        Manager managerCompress = new Manager (pathCompress);
        Manager managerDecompress = new Manager (pathDecompress);

        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Please enter your choice:
                1. Compression
                2. Decompression""");
        int input = scanner.nextInt();
        if (input == 1){
            // Compression Process.
            managerCompress.readFile();
            Compression compression = new Compression (managerCompress.getText());
            managerDecompress.setText(compression.compress().toString());
            managerDecompress.writeFile();
        }

        else if (input == 2){
            // Decompression Process.
            managerDecompress.readFile();
            Decompression decompression = new Decompression (managerDecompress.getText());
            managerCompress.setText(decompression.decompress());
            managerCompress.writeFile();
        }
        else {
            System.out.println("Wrong Input !!!");
        }
    }
}