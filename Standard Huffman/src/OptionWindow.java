import Classes.Huffman;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.Vector;
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
        readFile();
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
            Huffman huffman = new Huffman(text.replace(" ", ""));
            System.out.println(huffman.encode());
            writeBinaryFile(toBinary(huffman.encode()));
        });

        //---------------------------------------------------------
        decompressButton.addActionListener(actionEvent -> {
            // do el7agat bta3t el decompress.
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
            e.printStackTrace(); // Handle the exception
        }
    }

    //---------------------------------------------------------
    public List<Byte> toBinary(String txt) {
        int length = txt.length();
        List<Byte> bytes = new Vector<>();

        for (int i = 0; i < length; i += 8) {
            String byteString ;
            if (i + 8 < length){
                byteString = txt.substring(i, i + 8);
            }
            else{
                byteString = txt.substring(i) ;
            }

            byte b = (byte) Integer.parseInt(byteString, 2);
            bytes.add(b);
        }
        return bytes;
    }

    //---------------------------------------------------------

    public void writeBinaryFile(List<Byte> binaryString ){
        byte[] binaryArray = new byte[binaryString.size()];
        for (int i = 0; i < binaryString.size(); i++) {
            binaryArray[i] = binaryString.get(i) ;
        }

        try {
            // Convert binary string to bytes

            // Write binary data to a file
            try (FileOutputStream fos = new FileOutputStream("Output")) {
                fos.write(binaryArray);
                System.out.println("Binary data has been successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}