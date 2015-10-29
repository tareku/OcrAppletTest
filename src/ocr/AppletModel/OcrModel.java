package ocr.AppletModel;

import java.io.File;
import javax.swing.JFileChooser;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OcrModel {

    String filename;

    //return image absolute path
    public String getPassport() {
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
    public String ConvertImage(String filename) {

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
    public String dataExtract(String filename) {
        File imageFile = new File(filename);
        String result = null, data = null;
        Tesseract instance;
        instance = Tesseract.getInstance();
        try {
            instance.setDatapath("..\\OCR\\tessdata");
            result = instance.doOCR(imageFile);
            data = result.substring(result.indexOf("P<"), result.length());           
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return data;
    }

    
    //Delete binarize image
    public void deleteConvertedImg(String filename) {
        try {
            File img = new File(filename);
            boolean b = img.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
