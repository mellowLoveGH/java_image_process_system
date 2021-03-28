package assignment4;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class ImgCut {
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\csy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\csy1.jpg";
		int from = 719;
		int to = 1438;
		ImgFrame.drawImg(path01);
		cutHeight(path01, path02, from, to);
		ImgFrame.drawImg(path02);
//		from = 500;
//		to = 1000;
//		cutWidth(path01, path02, from, to);
	}
	
	public static void cutHeight(String path01, String path02, int from, int to) {

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
		
		int dis = to - from;
		BufferedImage bi3 = new BufferedImage(width, height-dis, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color c = new Color(bi1.getRGB(i, j));
				if(j < from) {
//					System.out.println(row + ", " + col);
					bi3.setRGB(i, j, c.getRGB());
				}else if(j > from && j < to) {
					
				}else {
					bi3.setRGB(i, j-dis, c.getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(bi3, "jpg", new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void cutWidth(String path01, String path02, int from, int to) {

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
		
		int dis = to - from;
		BufferedImage bi3 = new BufferedImage(width-dis, height, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color c = new Color(bi1.getRGB(i, j));
				if(i < from) {
//					System.out.println(row + ", " + col);
					bi3.setRGB(i, j, c.getRGB());
				}else if(i > from && i < to) {
					
				}else {
					bi3.setRGB(i-dis, j, c.getRGB());
				}
			}
		}
		
		try {
			ImageIO.write(bi3, "jpg", new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
