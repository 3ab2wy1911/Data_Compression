import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JPanel;
public class OptionWindow extends JDialog implements ActionListener {
    private JButton compressButton;
    private JButton decompressButton;
    private JPanel panel;
    private JLabel label;
    private JLabel chooseLabel;
//    private JLabel chooseLabel;

    //    int operation;
    private File file = null;
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
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);

        if (file == null) {
            dispose();
        }
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

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // do el7agat bta3t el compress.
            }
        });

        //---------------------------------------------------------
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // do el7agat bta3t el decompress.
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}