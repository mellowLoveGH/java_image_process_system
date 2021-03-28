package assignment1;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class JPGImg {
	
	public static void main(String[] args) {
		
		String path01 = "images/mf.jpg";
		String path02 = "images/tester.bmp";
		
		readJPG(path01);
		jpgTObmp(path01, path02);
		ImgFrame.drawImg(path02);
		
	}
	
	public static void readJPG(String path) {
		ImgFrame.drawImg(path);
	}
	
	public static void jpgTObmp(String path01, String path02) {
		try {
			BufferedImage jpg = ImageIO.read(new File(path01));
			ImageIO.write(jpg, "BMP", new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
