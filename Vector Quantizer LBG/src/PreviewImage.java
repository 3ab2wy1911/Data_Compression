import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PreviewImage extends  JDialog{
    private JLabel firstImage;
    private JLabel secondImage;
    private JButton backButton;
    private JPanel start;

    //----------------------------------------------------------------

    public PreviewImage(JFrame parent, BufferedImage originalImage, BufferedImage compressedImage){

        super(parent);
        setTitle("Image Preview");
        setContentPane(start);
        setMinimumSize(new Dimension(900, 800));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setModal(true);


        // Resize the icon
        int newWidth = 400;
        int newHeight = 600;
        Icon originalIcon = resizeIcon( new javax.swing.ImageIcon(originalImage), newWidth, newHeight);
        Icon compressedIcon = resizeIcon(new javax.swing.ImageIcon(compressedImage), newWidth, newHeight);

        firstImage.setIcon(originalIcon);
        secondImage.setIcon(compressedIcon);

        backButton.addActionListener(actionEvent -> {
            new Start(null);
            dispose();
        });
    }

    //----------------------------------------------------------------
    private static Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

}
