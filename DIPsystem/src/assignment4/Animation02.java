package assignment4;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation02 {
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower2.jpg";
		
		int[][][] mat = getMatrix(path01);
		int width = mat[0].length;
		int height = mat.length;
		initMat(bg_path, height, width);
		
		int number = 10 * 6;
		int size = 2;
		
		int iteration = 160;
		int leap = 1;
		boolean mode = true;
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		
		try {
			Thread.sleep(2*1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		for (int i = 0; i < iteration; i = i + leap) {
			
			if(i % 50 == 0) {
				size = size + 3;
				number = number + 30;
				System.out.println(")))))))))))))))))))))\tspeed up");
			}
			
			addPoint(height, width, mat, number, size, mode);
//			addPoint01(height, width, mat, size);
			String path = path02;//+ (i+1) + ".jpg";
			animation(path);
			
			ImgFrame.setImg(frame, path);
			
			System.out.println("iteration: " + (i+1));
		}
		
		mode = false;
		size = 2;
		number = 10 * 6;
		for (int i = 0; i < iteration; i = i + leap) {			
			if(i % 50 == 0) {
				size = size + 3;
				number = number + 30;
				System.out.println(")))))))))))))))))))))\tspeed up");
			}
			
			addPoint(height, width, mat, number, size, mode);
//			addPoint01(height, width, mat, size);
			String path = path02;//+ (i+1) + ".jpg";
			animation(path);			
			ImgFrame.setImg(frame, path);			
			System.out.println("iteration: " + (i+1));
		}
		
		mode = true;
		size = 2;
		number = 10 * 6;
		for (int i = 0; i < iteration; i = i + leap) {			
			if(i % 50 == 0) {
				size = size + 3;
				number = number + 30;
				System.out.println(")))))))))))))))))))))\tspeed up");
			}
			
//			addPoint(height, width, mat, number, size, mode);
			addPoint01(height, width, mat, size, mode);
			String path = path02;//+ (i+1) + ".jpg";
			animation(path);			
			ImgFrame.setImg(frame, path);			
			System.out.println("iteration: " + (i+1));
		}
		
		ImgFrame.setImg(frame, path01);
	}
	
	public static int[][][] matrix;
	public static int[][][] bg_mat;
	public static int NUMBER = 1;
	public static int[][] update;
	
	public static void initBG(String path) {
		bg_mat = getMatrix(path);		
	}
	
	public static void initMat(String path, int height, int width) {
		initBG(path);
		
		matrix = new int[height][width][3];
		update = new int[height][width];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				matrix[row][col][0] = bg_mat[row][col][0];
				matrix[row][col][1] = bg_mat[row][col][1];
				matrix[row][col][2] = bg_mat[row][col][2];
				
				update[row][col] = 0;
			}
		}	
	}
	
	public static void initMat(int height, int width) {		
		matrix = new int[height][width][3];
		update = new int[height][width];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				matrix[row][col][0] = 255;
				matrix[row][col][1] = 192;
				matrix[row][col][2] = 203;
				
				update[row][col] = 0;
			}
		}		
	}
	
	
	public static void animation(String path02) {		

		int width = matrix[0].length;
		int height = matrix.length;

		BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgbim3 = new int[width];
		
		//
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int r3 = 0;
				int g3 = 0;
				int b3 = 0;
				
				r3 = 255;
				g3 = 255;
				b3 = 255;
				r3 = matrix[row][col][0];
				g3 = matrix[row][col][1];
				b3 = matrix[row][col][2];
				
				rgbim3[col] = (r3 << 16) | (g3 << 8) | b3;
			}
			bi3.setRGB(0, row, width, 1, rgbim3, 0, width);
		}
		
		try {
			ImageIO.write(bi3, "jpg", new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}		
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
	
	static int increment = 1;
	public static void addPoint01(int height, int width, int[][][] mat, int size, boolean mode){
		
//		int xr = NUMBER * 1;
//		int yr = NUMBER * 1;
//		for (int i = 0; i < xr; i++) {			
//			for (int j = -yr; j < yr; j++) {
//				int row = height/2 - j;
//				int col = width/2 + i;
//				int col1 = width/2 - i;
//				if(row >= 0 && row < height && col >= 0 && col < width) {
//					if(update[row][col] == 1) {
//						
//					}else {
//						boolean flag = function(i, j, NUMBER);
//						if(flag) {
//							size = 2;
//							copyMat(mat, row, col, size, mode);
//							copyMat(mat, row, col1, size, mode);	
//						}
//					}
//				}						
//			}			
//		}
//		NUMBER = NUMBER + 20 * increment;
//		increment++;
		
		for (int i = 0; i <= 360; i = i + 2) {
			double radian = i * 2 * Math.PI / 360;
			int x = (int) (NUMBER * (2 * Math.cos(radian) - Math.cos(2 * radian)));
			int y = (int) (NUMBER * (2 * Math.sin(radian) - Math.sin(2 * radian)));
			
			int offset = 100;
			int rx = -y;
			int ry = x + offset;
			
			int row = height/2 - ry;
			int col = width/2 + rx;
			
			if(row >= 0 && row < height && col >= 0 && col < width) {
				copyMat(mat, row, col, size, mode);
			}
		}
		NUMBER = NUMBER + 1;
		increment++;		
	}
	
	public static void addPoint(int height, int width, int[][][] mat, int number, int size, boolean mode) {
		for (int i = 0; i < number; i++) {
			int h = (int) (Math.random() * height);
			int w = (int) (Math.random() * width);
			copyMat(mat, h, w, size, mode);
		}
	}
	
	
	
	public static void copyMat(int[][][] mat, int row, int col, int size, boolean mode) {		
		if(row < size || col < size || row > matrix.length - size || col > matrix[0].length - size) {
			if(mode) {
				matrix[row][col][0] = mat[row][col][0];
				matrix[row][col][1] = mat[row][col][1];
				matrix[row][col][2] = mat[row][col][2];
				
				update[row][col] = 1;
			}else {
				matrix[row][col][0] = bg_mat[row][col][0];
				matrix[row][col][1] = bg_mat[row][col][1];
				matrix[row][col][2] = bg_mat[row][col][2];
				
				update[row][col] = 0;
			}
			
			
			return;
		}
		
		for (int i = row - size; i < row + size; i++) {			
			for (int j = col - size; j < col + size; j++) {
				if(mode) {
					matrix[i][j][0] = mat[i][j][0];
					matrix[i][j][1] = mat[i][j][1];
					matrix[i][j][2] = mat[i][j][2];
					
					update[i][j] = 1;
				}else {
//					matrix[i][j][0] = 255;
//					matrix[i][j][1] = 192;
//					matrix[i][j][2] = 203;
					matrix[i][j][0] = bg_mat[i][j][0];
					matrix[i][j][1] = bg_mat[i][j][1];
					matrix[i][j][2] = bg_mat[i][j][2];
					
					update[i][j] = 0;
				}
				
			}			
		}		
	}
	
	
	public static boolean function(double x, double y, double c) {	
		double p1 = x * x;
		double p2 = Math.pow(p1, (double)1/3);
		double p3 = p1 + (y - p2) * (y - p2);
		if(p3 > c) {
			return false;
		}
		
//		double p1 = 5 * x * x;
//		double p2 = 5 * y * y;
//		double p3 = p1 + p2 - 6 * x * y;
//		if(p3 > 128 * c) {
//			return false;
//		}
		
//		double p1 = x * x;
//		double p2 = Math.sqrt(Math.abs(x));
//		double p3 = (1.25 * y - p2) * (1.25 * y - p2) + p1;
//		if(p3 > c) {
//			return false;
//		}
		return true;
	}
	
}
