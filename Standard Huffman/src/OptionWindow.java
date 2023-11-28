import Classes.Huffman;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
            System.out.println("The compressed text is -> " + compressedText);
            writeBinaryFile(huffmanFrequencies, compressedText);
        });

        //---------------------------------------------------------

        decompressButton.addActionListener(actionEvent -> {
            Map<Character, Integer> huffmanFrequencies = readBinaryFile();
            Huffman huffman = new Huffman(text);
            writeToFile(text);
            System.out.println("The original text is -> " + huffman.decode(huffmanFrequencies));
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
            int c;
            while ((c = din.read()) != -1) {
                compressedText.append((char) c);
            }
            text = compressedText.toString() ;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return huffmanFrequencies;
    }

    //---------------------------------------------------------

    public void writeBinaryFile(Map<Character, Integer> huffmanFrequencies, String compressedText) {
        try {
            FileOutputStream fos = new FileOutputStream("Output.bin");
            DataOutputStream dout = new DataOutputStream(fos);

            for (Map.Entry<Character, Integer> entry : huffmanFrequencies.entrySet()) {
                dout.writeByte((byte) ((char) entry.getKey()));
                dout.writeByte((byte) ((int) entry.getValue()));
            }
            dout.writeByte((byte) '\n');
            dout.writeBytes(compressedText);
            dout.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}