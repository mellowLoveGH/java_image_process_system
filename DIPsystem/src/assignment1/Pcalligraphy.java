package assignment1;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Pcalligraphy {
	
	public static void main(String[] args) {
		
		String path01 = "";
		String path02 = "";
		
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\calligraphy\\calligraphy82.jpg";
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\calligraphy\\calligraphy85.jpg";
		
		ImgFrame.drawImg(path01);
		inverseImg(path01, path02);
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
		
		int N = 0;
		
		for (int row = 0; row < height; row++) {
			bi1.getRGB(0, row, width, 1, rgbim1, 0, width);

			for (int col = 0; col < width; col++) {
				int rgb1 = rgbim1[col];
				int r1 = (rgb1 >> 16) & 255;
				int g1 = (rgb1 >> 8) & 255;
				int b1 = rgb1 & 255;

				int r3 = 0;
				int g3 = 0;
				int b3 = 0;
				
				r3 = r1;
				g3 = g1;
				b3 = b1;
				
				/*
				double dis = 0;
				dis = dis + (row - height)*(row - height);
				dis = dis + (col - width)*(col - width);
				dis = Math.sqrt(dis);
				if(dis < 1000) {
					r3 = 255;
					g3 = 255;
					b3 = 255;
				} 
				
				int sum = r1 + g1 + b1;
				if(sum > 200) {
					r3 = 255;
					g3 = 255;
					b3 = 255;
					N++;
				}else {
					r3 = r1;
					g3 = g1;
					b3 = b1;
				}
				
				if(col > 3000 || row > 2000) {
					r3 = 255;
					g3 = 255;
					b3 = 255;
					N++;
				}
				 */
				int sum = r1 + g1 + b1;
				if(sum > 200) {
					
					
					r3 = 255;
					g3 = 192;
					b3 = 203;
					N++;
				}else {
					r3 = 137;
					g3 = 207;
					b3 = 240;			
				}
				rgbim3[col] = (r3 << 16) | (g3 << 8) | b3;
			}

			bi3.setRGB(0, row, width, 1, rgbim3, 0, width);
		}
		
		System.out.println(")))))))))))))))))))" + N);

		try {
			ImageIO.write(bi3, "jpg", new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
