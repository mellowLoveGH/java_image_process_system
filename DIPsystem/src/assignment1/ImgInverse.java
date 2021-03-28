package assignment1;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgInverse {

	public static void main(String[] args) {

		String path01 = "images/img02.jpg";
		String path02 = "images/tester.jpg";

		inverseImg(path01, path02);
		// display original image
		ImgFrame.drawImg(path01);
		// display the inverse image
		ImgFrame.drawImg(path02);

	}

	public static void inverseImg(String path01, String path) {

		BufferedImage bi1 = null;

		try {
			bi1 = ImageIO.read(new File(path01));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (bi1 == null)
			throw new NullPointerException("bi1 is null");

		int width = bi1.getWidth();
		int height = bi1.getHeight();

		BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgbim1 = new int[width];
		int[] rgbim3 = new int[width];

		for (int row = 0; row < height; row++) {
			bi1.getRGB(0, row, width, 1, rgbim1, 0, width);

			for (int col = 0; col < width; col++) {
				int rgb1 = rgbim1[col];
				int r1 = (rgb1 >> 16) & 255;
				int g1 = (rgb1 >> 8) & 255;
				int b1 = rgb1 & 255;

				int r3 = (255 - r1) % 255;
				int g3 = (255 - g1) % 255;
				int b3 = (255 - b1) % 255;

				rgbim3[col] = (r3 << 16) | (g3 << 8) | b3;
			}

			bi3.setRGB(0, row, width, 1, rgbim3, 0, width);
		}

		try {
			ImageIO.write(bi3, "jpg", new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
