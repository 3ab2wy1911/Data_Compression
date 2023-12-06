import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JPanel;


public class MainWindow extends JDialog implements ActionListener {
    private JPanel panel;
    private JButton selectFileButton;
    private JLabel label;

    //    int operation;
    private File file = null;

    //----------------------------------------------------------------

    public MainWindow(JFrame parent) {


        super(parent);
        setTitle("Huffman Adaptive Data Compression");
        setContentPane(panel);
        setMinimumSize(new Dimension(300, 150));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);
        setSize(400,200);


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        selectFileButton.addActionListener(this);


    }

    //----------------------------------------------------------------

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == selectFileButton) {

            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File(".")); //sets current directory

            int response = fileChooser.showOpenDialog(null); //select file to open

            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        new OptionWindow(null,file);
        dispose();
    }
//----------------------------------------------------------------
}