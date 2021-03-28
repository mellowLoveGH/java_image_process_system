package assignment3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class ImgGrayscale {
	
	public static void main(String[] args) {
		
		String path01 = "images/img02.jpg";
		String path = "images/tester.jpg";
		
		ImgFrame.drawImg(path01);
		grayImg(path01, path);
		ImgFrame.drawImg(path);
	}
	
	public static void grayImg(String path01, String path) {

		try {
			File input = new File(path01);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

			for (int i = 0; i < height; i++) {

				for (int j = 0; j < width; j++) {

					Color c = new Color(image.getRGB(j, i));
					int red = (int) (c.getRed() * 0.299);
					int green = (int) (c.getGreen() * 0.587);
					int blue = (int) (c.getBlue() * 0.114);
					Color newColor = new Color(red + green + blue,

							red + green + blue, red + green + blue);

					image.setRGB(j, i, newColor.getRGB());
				}
			}

			File ouptut = new File(path);
			ImageIO.write(image, "jpg", ouptut);

		} catch (Exception e) {
		}

	}
	
}
