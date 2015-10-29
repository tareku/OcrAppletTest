package ocr.AppletView;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.UIManager.LookAndFeelInfo;
import ocr.AppletModel.OcrModel;
import ocr.AppletModel.OcrModel;

public class Ocr extends JApplet implements ActionListener {

    OcrModel ocrModel;
    public JButton browse;
    public JLabel result;
    public JProgressBar bar;
    public JLabel image;

    @Override
    public void init() {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setLayout(null);
        setSize(700, 500);
        browse = new JButton("Browse");
        browse.addActionListener(this);
        browse.setBounds(10, 20, 100, 50);
        result = new JLabel();
        result.setVisible(false);
        result.setBounds(10, 150, 1000, 50);
        bar = new JProgressBar();
        bar.setBounds(10, 100, 300, 20);
        bar.setStringPainted(true);

        add(bar);
        add(result);
        add(browse);
    }

//    BufferedImage createResizedCopy(Image originalImage,
//            int scaledWidth, int scaledHeight,
//            boolean preserveAlpha) {
//        System.out.println("resizing...");
//        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
//        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
//        Graphics2D g = scaledBI.createGraphics();
//        if (preserveAlpha) {
//            g.setComposite(AlphaComposite.Src);
//        }
//        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
//        g.dispose();
//        return scaledBI;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browse) {
            bar.setIndeterminate(true);
            ocrModel = new OcrModel();
            //return image absolute path
            String img = ocrModel.getPassport();
//            try {
//                BufferedImage pic = ImageIO.read(new File(img));
//                pic = createResizedCopy(pic, 100, 100, false);
//                image = new JLabel(new ImageIcon(pic));
//                pic.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
//                image.setBounds(10, 200, 500, 500);
//                add(image);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
            //Return binarize image path 
            String convertedimg = ocrModel.ConvertImage(img);
            //Extract data string
            String r = ocrModel.dataExtract(convertedimg);
            result.setText(r);
            result.setVisible(true);
            bar.setValue(100);
            bar.setIndeterminate(false);
            //Delete binarize image
            ocrModel.deleteConvertedImg(convertedimg);
        }
    }

}
