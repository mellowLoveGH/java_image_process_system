package animationDIP;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgFile {
	
	public static void main(String[] args) {
		
	}
	
	public static void copyMat(int[][][] from, int[][][] to, int row, int col, int r2, int c2, int size) {
		
		
	}
	
	//
	public static void copyMat(int[][][] from, int[][][] to, int row, int col, int size) {
		int row_start = row - size;
		int row_end = row + size;
		if(row_start < 0)
			row_start = 0;
		if(row_end > from.length) 
			row_end = from.length;
		
		int col_start = col - size;
		int col_end = col + size;
		if(col_start < 0)
			col_start = 0;
		if(col_end > from[0].length) 
			col_end = from[0].length;
		
		//
		for (int i = row_start; i < row_end; i++) {
			for (int j = col_start; j < col_end; j++) {
				to[i][j][0] = from[i][j][0];
				to[i][j][1] = from[i][j][1];
				to[i][j][2] = from[i][j][2];
			}
		}
	}
	
	public static void writeImg(int[][][] mat, int height, int width, String path) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgbim = new int[width];
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int r3 = mat[row][col][0];
				int g3 = mat[row][col][1];
				int b3 = mat[row][col][2];
				
				rgbim[col] = (r3 << 16) | (g3 << 8) | b3;
			}
			bi.setRGB(0, row, width, 1, rgbim, 0, width);
		}
		
		try {
			ImageIO.write(bi, "jpg", new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
		
	//convert image to two dimensional matrix
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
	
}
