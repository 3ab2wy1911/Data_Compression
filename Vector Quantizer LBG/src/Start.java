import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Start extends JDialog{
    private JPanel start;
    private JButton compressButton;
    private JButton deCompressButton;
    private final VectorQuantization LBG;
    BufferedImage originalImage, compressedImage;

    //----------------------------------------------------------------------------------------

    public Start(JFrame parent) {
        // Setting the attributes of the panel.
        super(parent);
        setTitle("Vector Quantization");
        setContentPane(start);
        setMinimumSize(new Dimension(820, 420));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);

        this.LBG = new VectorQuantization();

        //__________________________________________________________________________________________________________________

        compressButton.addActionListener(actionEvent -> {
            File file = new File(selectFile().getAbsolutePath());
            try {
                originalImage = ImageIO.read(file);
                compressedImage = LBG.compress(originalImage);
                new PreviewImage(null, originalImage , compressedImage);
                dispose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //__________________________________________________________________________________________________________________


        deCompressButton.addActionListener(actionEvent -> {
            //File file = new File(selectFile().getAbsolutePath());
            // read the file in binary then convert it into the compressed image then decompress it into the
            // original image and save them.
        });
    }

    //__________________________________________________________________________________________________________________

    File selectFile(){
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(".")); //sets current directory

        int response = fileChooser.showOpenDialog(null); //select file to open

        if (response == JFileChooser.APPROVE_OPTION) {
            return new File(fileChooser.getSelectedFile().getAbsolutePath());
        }
        return null;
    }

    //__________________________________________________________________________________________________________________

}
