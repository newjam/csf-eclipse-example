import book.FileChooser;
import book.Picture;
import book.Pixel;

/**
 * DoubleRed.java is written in 
 * Object-oriented style
 */

public class DoubleRed { 
  Picture pic;
  
  public DoubleRed(Picture p) {
    pic = p;
  }
  
  public void scaleRed(double scale) {
    Pixel[] pixArray = pic.getPixels();
    for (int p=0; p<pixArray.length; p++){
      pixArray[p].setRed((int)(pixArray[p].getRed() * scale));
    }
  }
  
  public static void main(String[] args) {
    String mediaPath = "media-source";
    FileChooser.setMediaPath(mediaPath);
    Picture pic = new Picture(FileChooser.pickAFile());
    DoubleRed redPic = new DoubleRed(pic);
    redPic.scaleRed(2.0);
    pic.show();
  }
  
}