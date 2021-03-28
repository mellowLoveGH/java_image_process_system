package assignment4;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Animation03 {
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower2.jpg";
		int[][][] mat = getMatrix(path01);
		
	}
	
	public static int[][][] matrix;
	
	public static void initMat(String path, int height, int width) {
		int[][][] init = getMatrix(path);
		
		matrix = new int[height][width][3];
		
		
		
		
		
	}
	
	
	
	
	
	public static int[][][] getMatrix(String path01){
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
	
	
}
