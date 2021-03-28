package assignment3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class ImgHistogramUpdate {
	
	public static void main(String[] args) {
		String path01 = "images/img02.jpg";
		String path = "images/tester.jpg";
		
		ImgFrame.drawImg(path01);
		ImgHistogramUpdate.equalisation(path01, path);
		ImgFrame.drawImg(path);
	}
	
	
	public static void equalisation(String path01, String path) {
		
		try {
			File input = new File(path01);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[][] update = updateH(path01, width * height);
			
			for (int i = 0; i < height; i++) {

				for (int j = 0; j < width; j++) {

					Color c = new Color(image.getRGB(j, i));
					int red = update[c.getRed()][0];
					int green = update[c.getGreen()][1];
					int blue = update[c.getBlue()][2];
					
					Color newColor = new Color(red, green, blue);
					image.setRGB(j, i, newColor.getRGB());
				}
			}

			File ouptut = new File(path);
			ImageIO.write(image, "jpg", ouptut);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static int[][] updateH(String path01, int total){
		
		int[][] update = countH(path01);
		double s1 = 0.0, s2 = 0.0, s3 = 0.0;
		int constant = 255;
		int level = 0;
		
		int[][] tmp = new int[256][3];
		
		for (int i = 0; i < update.length; i++) {
//			System.out.print(i + "\t");
			//
			double p = (double)update[i][0] / total;
			s1 += p;
			level = (int)(s1 * constant);
//			System.out.print(p + "\t" + s1 + "\t" + level + "\t");
			tmp[i][0] = level;
			//
			p = (double)update[i][1] / total;
			s2 += p;
			level = (int)(s2 * constant);
//			System.out.print(p + "\t" + s2 + "\t" + level + "\t");
			tmp[i][1] = level;
			//
			p = (double)update[i][2] / total;
			s3 += p;
			level = (int)(s3 * constant);
//			System.out.print(p + "\t" + s3 + "\t" + level);
			tmp[i][2] = level;
//			System.out.println();
		}
		
		return tmp;
	}
	
	
	public static int[][] countH(String path01){
		int[][] count = new int[256][3];
		
		try {
			File input = new File(path01);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					Color c = new Color(image.getRGB(j, i));
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					
					count[red][0]++;
					count[green][1]++;
					count[blue][2]++;
					
				}
			}
			
		} catch (Exception e) {
		}
		
		return count;
	}
	
	
	public static void print(int[][] count) {
		for (int i = 0; i < count.length; i++) {
			System.out.println(i + "\t" + count[i][0] + "\t" + count[i][1] + "\t" + count[i][2]);
		}
	}
	
}
