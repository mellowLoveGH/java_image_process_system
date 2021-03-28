package assignment1;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgMirror {
	
	public static void main(String[] args) {
		
		String path01 = "images/img02.jpg";
		String path02 = "images/tester.jpg";
		String path03 = "images/tester1.jpg";
		
		ImgFrame.drawImg(path01);
		horizontal(path01, path02);
		ImgFrame.drawImg(path02);
		vertical(path01, path03);
		ImgFrame.drawImg(path03);
	}
	
	public static void horizontal(String path01, String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path01));
			int width = image.getWidth(null);
		    int height = image.getHeight(null);

		    int x0 = width - 1;   // point to rotate about
		    
		    //
		    WritableRaster inRaster = image.getRaster();
		    BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    WritableRaster outRaster = pic2.getRaster();
		    int[] pixel = new int[3];

		    // rotation
		    for (int x = 0; x < width; x++) {
		        for (int y = 0; y < height; y++) {
		            int a = x0 - x;
		            int b = y;
		            
		            outRaster.setPixel(x, y, inRaster.getPixel(a, b, pixel));
		        }
		    }
		    ImageIO.write(pic2, "jpg", new File(path));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void vertical(String path01, String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path01));
			int width = image.getWidth(null);
		    int height = image.getHeight(null);

		    int y0 = height - 1;     // center of image
		    
		    //
		    WritableRaster inRaster = image.getRaster();
		    BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    WritableRaster outRaster = pic2.getRaster();
		    int[] pixel = new int[3];

		    // rotation
		    for (int x = 0; x < width; x++) {
		        for (int y = 0; y < height; y++) {
		            int a = x;
		            int b = y0 - y;
		            
		            outRaster.setPixel(x, y, inRaster.getPixel(a, b, pixel));
		        }
		    }
		    ImageIO.write(pic2, "jpg", new File(path));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
