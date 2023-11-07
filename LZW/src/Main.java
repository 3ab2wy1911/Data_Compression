
public class Main {
    public static void main(String[] args) {
        // Two files one for compression and other for decompression.
        String pathCompress = "E:\\PROJECTS\\Data Compression\\LZW\\Compression.txt";
        String pathDecompress = "E:\\PROJECTS\\Data Compression\\LZW\\Decompression.txt";

        // Making two instances of manager one for compression and other for decompression.
        Manager managerCompress = new Manager (pathCompress);
        Manager managerDecompress = new Manager (pathDecompress);

        // Compression Process.
        managerCompress.readFile();
        Compression compression = new Compression (managerCompress.getText());
        managerDecompress.setText(compression.compress().toString());
        managerDecompress.writeFile();

        // Decompression Process.
        managerDecompress.readFile();
        Decompression decompression = new Decompression (managerDecompress.getText());
        managerCompress.setText(decompression.decompress());
        managerCompress.writeFile();
    }
}