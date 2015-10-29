package ocr.AppletModel;

import java.io.File;
import javax.swing.JFileChooser;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import ocr.AppletView.Ocr;

public class OcrModel {

    String filename;

    //return image absolute path
    public String getPassport(Ocr ocrView) {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showDialog(null, null);
            File f = chooser.getSelectedFile();
            filename = f.getAbsolutePath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return filename;
    }

    //Return binarize image path 
    public String ConvertImage(Ocr ocrView, String filename) {

        OtsuBinarize ob = new OtsuBinarize();
        String bin = null;
        try {
            bin = ob.BinarizeImage(filename);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bin;
    }

    //Extract data string
    public void dataExtract(Ocr ocrView, String filename) {
        File imageFile = new File(filename);
        Tesseract instance;
        instance = Tesseract.getInstance();
        try {
            instance.setDatapath("..\\OCR\\tessdata");
            String result = instance.doOCR(imageFile);
            String data = result.substring(result.indexOf("P<"), result.length());
            ocrView.result.setText(data);
            ocrView.result.setVisible(true);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    
    //Delete binarize image
    public void deleteConvertedImg(Ocr ocrView, String filename) {
        try {
            File img = new File(filename);
            boolean b = img.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
