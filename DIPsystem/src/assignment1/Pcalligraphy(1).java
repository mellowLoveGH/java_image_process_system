package assignment1;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import animationDIP.ImgFile;

public class Pcalligraphy {
	
	public static void main(String[] args) {
		
		String path01 = "";
		String path02 = "";
		
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\reminiscence\\signature1.jpg";
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\reminiscence\\signature01.jpg";
		
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc01.jpg"; 
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc02.jpg"; 
		
		ImgFrame.drawImg(path01);
		extract(path01, path02);
		ImgFrame.drawImg(path02);
	}
	
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	public static int HEIGHT = 800;
	public static int WIDTH = 800;
	
	public static void initIMGMat(String path) {
		img_mat = getMatrix(path);
		HEIGHT = img_mat.length;
		WIDTH = img_mat[0].length;
//		bg_mat = new int[HEIGHT][WIDTH][3];
//		current_mat = new int[HEIGHT][WIDTH][3];
	}
	
	public static int[][][] getMatrix(String path){
		BufferedImage bi1 = null;

		try {
			bi1 = ImageIO.read(new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (bi1 == null)
			throw new NullPointerException("bi1 is null");

		int width = bi1.getWidth();
		int height = bi1.getHeight();

		int[] rgbim1 = new int[width];
		
		int[][][] mat = new int[height][width][3];
		
		for (int row = 0; row < height; row++) {
			bi1.getRGB(0, row, width, 1, rgbim1, 0, width);

			for (int col = 0; col < width; col++) {
				int rgb1 = rgbim1[col];
				int r1 = (rgb1 >> 16) & 255;
				int g1 = (rgb1 >> 8) & 255;
				int b1 = rgb1 & 255;

				mat[row][col][0] = r1;
				mat[row][col][1] = g1;
				mat[row][col][2] = b1;			
			}
		}
		
		return mat;
	}
	
	public static void extract(String path01, String path) {
		initIMGMat(path01);
		
//		for (int row = 0; row < HEIGHT; row++) {
//			for (int col = 0; col < WIDTH; col++) {
//				int r = img_mat[row][col][0];
//				int g = img_mat[row][col][1];
//				int b = img_mat[row][col][2];
//				
//				int sum = r + g + b;
//				if(sum > 200) {
//					r = 255;
//					g = 255;
//					b = 255;
//				}
//				current_mat[row][col][0] = r;
//				current_mat[row][col][1] = g;
//				current_mat[row][col][2] = b;
//			}
//		}
		
//		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}

}
