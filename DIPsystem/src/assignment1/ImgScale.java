package assignment1;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgScale {

	public static void main(String[] args) {
		String path01 = "images/img02.jpg";
		String path02 = "images/tester.jpg";
		
		ImgFrame.drawImg(path01);
		scaleImg(path01, path02, 0.6);
		ImgFrame.drawImg(path02);
	}

	public static void scaleImg(String path01, String path02, double scale) {

		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(path01));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		int width = img.getWidth();
		int height = img.getHeight();

		int _width = (int) (scale * width);
		int _height = (int) (scale * height);
		Image image = img.getScaledInstance(_width, _height, Image.SCALE_SMOOTH);

		BufferedImage out = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = (Graphics2D) out.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		
		
		try {
			ImageIO.write(out, "jpg", new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
