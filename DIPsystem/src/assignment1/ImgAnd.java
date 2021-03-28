package assignment1;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgAnd {
	
	public static void main(String[] args) {
		
		String path01 = "images/img01.jpg";
		String path02 = "images/img02.jpg";
		String path03 = "images/tester.jpg";
		
		//display image01
		ImgFrame.drawImg(path01);
		//display image02
		ImgFrame.drawImg(path02);		
		//merge the two images
		andImg(path01, path02, path03);		
		//display the merged image
		ImgFrame.drawImg(path03);
	}
	
	public static void andImg(String path01, String path02, String path) {

		BufferedImage bi1 = null;
		BufferedImage bi2 = null;

		try {
			bi1 = ImageIO.read(new File(path01));
			bi2 = ImageIO.read(new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (bi1 == null)
			throw new NullPointerException("bi1 is null");

		if (bi2 == null)
			throw new NullPointerException("bi2 is null");

		int width = bi1.getWidth();
		if (width != bi2.getWidth())
			throw new IllegalArgumentException("widths not equal");

		int height = bi1.getHeight();
		if (height != bi2.getHeight())
			throw new IllegalArgumentException("heights not equal");

		BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgbim1 = new int[width];
		int[] rgbim2 = new int[width];
		int[] rgbim3 = new int[width];

		for (int row = 0; row < height; row++) {
			bi1.getRGB(0, row, width, 1, rgbim1, 0, width);
			bi2.getRGB(0, row, width, 1, rgbim2, 0, width);

			for (int col = 0; col < width; col++) {
				int rgb1 = rgbim1[col];
				int r1 = (rgb1 >> 16) & 255;
				int g1 = (rgb1 >> 8) & 255;
				int b1 = rgb1 & 255;

				int rgb2 = rgbim2[col];
				int r2 = (rgb2 >> 16) & 255;
				int g2 = (rgb2 >> 8) & 255;
				int b2 = rgb2 & 255;

				int r3 = (smaller(r1, r2)) % 255;
				int g3 = (smaller(g1, g2)) % 255;
				int b3 = (smaller(b1, b2)) % 255;

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
	
	public static int smaller(int a, int b) {
		
		if(b < a) {
			return b;
		}
		
		return a; 
	}
	
	
}
