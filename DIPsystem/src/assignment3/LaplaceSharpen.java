package assignment3;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class LaplaceSharpen {
	
	public static void main(String[] args) {
		
		String path01 = "images/img02.jpg";
		String path = "images/tester.jpg";
		
		ImgFrame.drawImg(path01);
		laplace(path01, path);
		ImgFrame.drawImg(path);
	}
	
	public static void laplace(String path01, String path) {
		File input = new File(path01);
		BufferedImage src = null;

		try {
			src = ImageIO.read(input);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("cannot read image!");
		}

		// 拉普拉斯算子
		int[] LAPLACE = new int[] { 0, -1, 0, -1, 4, -1, 0, -1, 0 };

		int width = src.getWidth();
		int height = src.getHeight();

		int[] pixels = new int[width * height];
		int[] outPixels = new int[width * height];

		int type = src.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
			src.getRaster().getDataElements(0, 0, width, height, pixels);
		}
		src.getRGB(0, 0, width, height, pixels, 0, width);

		int k0 = 0, k1 = 0, k2 = 0;
		int k3 = 0, k4 = 0, k5 = 0;
		int k6 = 0, k7 = 0, k8 = 0;

		k0 = LAPLACE[0];
		k1 = LAPLACE[1];
		k2 = LAPLACE[2];
		k3 = LAPLACE[3];
		k4 = LAPLACE[4];
		k5 = LAPLACE[5];
		k6 = LAPLACE[6];
		k7 = LAPLACE[7];
		k8 = LAPLACE[8];
		int offset = 0;

		int sr = 0, sg = 0, sb = 0;
		int r = 0, g = 0, b = 0;
		for (int row = 1; row < height - 1; row++) {
			offset = row * width;
			for (int col = 1; col < width - 1; col++) {
				// red
				sr = k0 * ((pixels[offset - width + col - 1] >> 16) & 0xff)
						+ k1 * ((pixels[offset - width + col] >> 16) & 0xff)
						+ k2 * ((pixels[offset - width + col + 1] >> 16) & 0xff)
						+ k3 * ((pixels[offset + col - 1] >> 16) & 0xff) + k4 * ((pixels[offset + col] >> 16) & 0xff)
						+ k5 * ((pixels[offset + col + 1] >> 16) & 0xff)
						+ k6 * ((pixels[offset + width + col - 1] >> 16) & 0xff)
						+ k7 * ((pixels[offset + width + col] >> 16) & 0xff)
						+ k8 * ((pixels[offset + width + col + 1] >> 16) & 0xff);
				// green
				sg = k0 * ((pixels[offset - width + col - 1] >> 8) & 0xff)
						+ k1 * ((pixels[offset - width + col] >> 8) & 0xff)
						+ k2 * ((pixels[offset - width + col + 1] >> 8) & 0xff)
						+ k3 * ((pixels[offset + col - 1] >> 8) & 0xff) + k4 * ((pixels[offset + col] >> 8) & 0xff)
						+ k5 * ((pixels[offset + col + 1] >> 8) & 0xff)
						+ k6 * ((pixels[offset + width + col - 1] >> 8) & 0xff)
						+ k7 * ((pixels[offset + width + col] >> 8) & 0xff)
						+ k8 * ((pixels[offset + width + col + 1] >> 8) & 0xff);
				// blue
				sb = k0 * (pixels[offset - width + col - 1] & 0xff) + k1 * (pixels[offset - width + col] & 0xff)
						+ k2 * (pixels[offset - width + col + 1] & 0xff) + k3 * (pixels[offset + col - 1] & 0xff)
						+ k4 * (pixels[offset + col] & 0xff) + k5 * (pixels[offset + col + 1] & 0xff)
						+ k6 * (pixels[offset + width + col - 1] & 0xff) + k7 * (pixels[offset + width + col] & 0xff)
						+ k8 * (pixels[offset + width + col + 1] & 0xff);
				r = sr;
				g = sg;
				b = sb;
				outPixels[offset + col] = (0xff << 24) | (clamp(r) << 16) | (clamp(g) << 8) | clamp(b);
				sr = 0;
				sg = 0;
				sb = 0;
			}
		}

		BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
			dest.getRaster().setDataElements(0, 0, width, height, outPixels);
		} else {
			dest.setRGB(0, 0, width, height, outPixels, 0, width);
		}

		try {
			ImageIO.write(dest, "jpg", new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}

		System.out.println("laplace edge detection");
	}
	
	private static int clamp(int value) {
		return value > 255 ? 255 : (value < 0 ? 0 : value);
	}
	
}
