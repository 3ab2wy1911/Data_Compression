import Classes.Huffman;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import javax.swing.JPanel;

public class OptionWindow extends JDialog implements ActionListener {
    private JButton compressButton;
    private JButton decompressButton;
    private JPanel panel;
    private JLabel label;
    private final File file;
    private String text;

    public OptionWindow(JFrame parent, File file) {


        super(parent);
        setTitle("Huffman Adaptive Data Compression");
        setContentPane(panel);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        this.file = file;
        this.text = "";
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);

        if (file == null) {
            dispose();
        }

        assert file != null;
        label.setText("File Name : " + file.getName());

        //--------------------------------------------------

        compressButton.addActionListener(actionEvent -> {
            Map<Character, Integer> huffmanFrequencies = new HashMap<>();
            readFile();

            Huffman huffman = new Huffman(text);
            String compressedText = huffman.encode(huffmanFrequencies) ;
            JOptionPane.showMessageDialog(OptionWindow.this,
                    "The compressed text is -> " + compressedText,
                    "Compression done Successfully!!!",
                    JOptionPane.INFORMATION_MESSAGE);
            writeBinaryFile(huffmanFrequencies, compressedText);

            new MainWindow(null);
            dispose();
        });

        //---------------------------------------------------------

        decompressButton.addActionListener(actionEvent -> {
            Map<Character, Integer> huffmanFrequencies = readBinaryFile();
            Huffman huffman = new Huffman(text);

            String decompressedText = huffman.decode(huffmanFrequencies);
            JOptionPane.showMessageDialog(OptionWindow.this,
                    "The decompressed text is -> " + decompressedText,
                    "Decompression done Successfully!!!",
                    JOptionPane.INFORMATION_MESSAGE);
            writeToFile(decompressedText) ;

            new MainWindow(null);
            dispose();
        });
    }

    //---------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    //---------------------------------------------------------

    public void readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text = text.concat(line);
            }
        } catch (IOException e) {
            // Handle the exception
            JOptionPane.showMessageDialog(OptionWindow.this,
                    e.getMessage(),
                    "Error at reading file !!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //---------------------------------------------------------

    public void writeToFile(String content){
        if (content == null) {
            JOptionPane.showMessageDialog(OptionWindow.this,
                    "There are no data to write",
                    "Error at writing in file !!!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Writing the content to a file
        try{
            if (!Files.exists(Paths.get(System.getProperty("user.dir") + "Output.txt"))){
                Files.createFile(Paths.get(System.getProperty("user.dir") + "Output.txt")) ;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt")) ;
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(OptionWindow.this,
                    e.getMessage(),
                    "Error at writing in file !!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //---------------------------------------------------------

    private Map<Character, Integer> readBinaryFile(){
        Map<Character, Integer> huffmanFrequencies = new HashMap<>();

        try (FileInputStream fis = new FileInputStream("Output.bin")) {
            DataInputStream din = new DataInputStream(fis);

            while (din.available() > 0) {
                char character = (char)din.readByte();
                if (character == '\n') {
                    break;
                }

                int frequency = din.readByte();

                // Add the character and frequency to the map
                huffmanFrequencies.put(character, frequency);
            }

            // Read the compressed text
            StringBuilder compressedText = new StringBuilder();
            int bit;
            while ((bit = din.read()) != -1) {
                compressedText.append((char) bit);
            }
            text = compressedText.toString() ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(OptionWindow.this,
                    e.getMessage(),
                    "Error at reading binary file !!!",
                    JOptionPane.ERROR_MESSAGE);
        }

        return huffmanFrequencies;
    }

    //---------------------------------------------------------

    public void writeBinaryFile(Map<Character, Integer> huffmanFrequencies, String compressedText) {
        try {
            if (!Files.exists(Paths.get(System.getProperty("user.dir") + "Output.bin"))){
                Files.createFile(Paths.get(System.getProperty("user.dir") + "Output.bin")) ;
            }
            FileOutputStream fileOutputStream = new FileOutputStream("Output.bin");
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

            for (Map.Entry<Character, Integer> entry : huffmanFrequencies.entrySet()) {
                dataOutputStream.writeByte((byte) ((char) entry.getKey()));
                dataOutputStream.writeByte((byte) ((int) entry.getValue()));
            }
            dataOutputStream.writeByte((byte) '\n');
            dataOutputStream.writeBytes(compressedText);
            dataOutputStream.close();
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(OptionWindow.this,
                    e.getMessage(),
                    "Error at writing in binary file !!!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}