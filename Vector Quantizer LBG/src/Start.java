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
    private Image image;
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


        this.image = new Image();
        this.LBG = new VectorQuantization();

        //__________________________________________________________________________________________________________________

        compressButton.addActionListener(actionEvent -> {
            String path =selectFile().getAbsolutePath();
            File file = new File(path);
            try {
                originalImage = ImageIO.read(file);
                compressedImage = ImageIO.read(new File("outputImage.png"));
                image.readImage(path);
                LBG.compress(image,2);    // what is the vector size ??
//                compressedImage = LBG.writeImage(image);
                // Select the image then compress it then write it and pass the 2 images .


            } catch (IOException e) {
                JOptionPane.showMessageDialog(Start.this,
                        e,
                        "Error!!!",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            new PreviewImage(null, originalImage, compressedImage);
            dispose();
        });

        //__________________________________________________________________________________________________________________


        deCompressButton.addActionListener(actionEvent -> {
            File file = new File(selectFile().getAbsolutePath());
//            compressedImage = LBG.writeFile(LBG.ReadFile(file););
            // read the file in binary then convert it into the  image then pass the 2 images.
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
