import Classes.Huffman;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.JPanel;

import static java.lang.System.in;

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
        setMinimumSize(new Dimension(500, 100));
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
        label.setFont(new Font("Comic Sans",Font.BOLD,18));
        label.setIconTextGap(-15);

        compressButton.setFont(new Font("Comic Sans",Font.BOLD,18));
        compressButton.setIconTextGap(-15);
        compressButton.setForeground(Color.white);
        compressButton.setBackground(Color.DARK_GRAY);
        compressButton.setBorder(BorderFactory.createEtchedBorder());
        compressButton.addActionListener(this);


        decompressButton.setFont(new Font("Comic Sans",Font.BOLD,18));
        decompressButton.setIconTextGap(-15);
        decompressButton.setForeground(Color.white);
        decompressButton.setBackground(Color.DARK_GRAY);
        decompressButton.setBorder(BorderFactory.createEtchedBorder());
        decompressButton.addActionListener(this);


        //--------------------------------------------------

        compressButton.addActionListener(actionEvent -> {
            Map<Character, Integer> huffmanFrequencies = new HashMap<>();
            readFile();
            Huffman huffman = new Huffman(text);
            String compressedText = huffman.encode(huffmanFrequencies) ;
            System.out.println(compressedText);
            writeBinaryFile(huffmanFrequencies, compressedText);
        });

        //---------------------------------------------------------

        decompressButton.addActionListener(actionEvent -> {
            Map<Character, Integer> huffmanFrequencies = readBinaryFile();
            Huffman huffman = new Huffman(text);
            writeToFile(text);
            System.out.println(huffman.decode(huffmanFrequencies));
            writeToFile(huffman.decode(huffmanFrequencies)) ;
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
            System.out.println(e.getMessage()); // Handle the exception
        }
    }

    //---------------------------------------------------------

    public void writeToFile(String content){
        if (content == null) {
            System.out.println("Error Occurred during writing !!!");
            return;
        }

        // Writing the content to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println(e.getMessage()); // Handle the exception as needed (print, log, or throw)
        }
    }

    //---------------------------------------------------------

    private Map<Character, Integer> readBinaryFile(){
        Map<Character, Integer> huffmanFrequencies = new HashMap<>();

        try (FileInputStream fis = new FileInputStream("Output.bin")) {
            DataInputStream din = new DataInputStream(fis);

            while (din.available() > 0) {
                char character = (char) din.readByte();
                int frequency = din.readByte();

                // Add the character and frequency to the map
                huffmanFrequencies.put(character, frequency);

                // Skip the newline character
                din.readByte();
            }

            // Read the compressed text
            StringBuilder compressedText = new StringBuilder();
            int c;
            while ((c = din.read()) != -1) {
                compressedText.append((char) c);
            }
            System.out.println(huffmanFrequencies);
            text = compressedText.toString() ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return huffmanFrequencies;
    }

    //---------------------------------------------------------

    public void writeBinaryFile(Map<Character, Integer> huffmanFrequencies, String compressedText) {
        try {
            FileOutputStream fos = new FileOutputStream("Output.bin");
            DataOutputStream dout = new DataOutputStream(fos);

            for (Map.Entry<Character, Integer> entry : huffmanFrequencies.entrySet()) {
                dout.writeChar(entry.getKey());
                dout.writeInt(entry.getValue());
            }
            dout.writeBytes(compressedText);
            dout.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}