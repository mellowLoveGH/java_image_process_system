package animationDIP;

import java.util.Random;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation005 {
	
	public static int HEIGHT = 1000;
	public static int WIDTH = 1000;
	
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	
	public static void initIMGMat(String path) {
		img_mat = ImgFile.getMatrix(path);
		HEIGHT = img_mat.length;
		WIDTH = img_mat[0].length;
		bg_mat = new int[HEIGHT][WIDTH][3];
		current_mat = new int[HEIGHT][WIDTH][3];
	}
	
	public static void initBGMat(String path, boolean mode) {
		if(mode) {
			bg_mat = ImgFile.getMatrix(path);
			return;
		}
		
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				bg_mat[row][col][0] = 255;
				bg_mat[row][col][1] = 192;
				bg_mat[row][col][2] = 203;
			}
		}
	}
	
	public static void initMat(String path, boolean mode) {
		if(mode) {
			current_mat = ImgFile.getMatrix(path);
			return;
		}
		
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = 255;
				current_mat[row][col][1] = 192;
				current_mat[row][col][2] = 203;
			}
		}
	}
	
	public static int[][] twoToOne(int[][][] mat) {
		int[][] arr = new int[mat.length * mat[0].length][3];
		int count = 0;
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				arr[count][0] = mat[i][j][0];
				arr[count][1] = mat[i][j][1];
				arr[count][2] = mat[i][j][2];
				count++;
			}
		}
		return arr;
	}
	
	public static void oneToTwo(int[][] arr, int row, int col){
//		int[][][] mat = new int[row][col][3];
		int count = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				current_mat[i][j][0] = arr[count][0];
				current_mat[i][j][1] = arr[count][1];
				current_mat[i][j][2] = arr[count][2];
				count++;
			}
		}			
	}
	
	public static void forward(int[][] arr, int[][] exchange, int number) {
		Random ran = new Random();
		int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
		r1 = ran.nextInt(current_mat.length);
		c1 = ran.nextInt(current_mat[0].length);
		r2 = ran.nextInt(current_mat.length);
		c2 = ran.nextInt(current_mat[0].length);
		int i1 = r1 * current_mat.length + c1;
		int i2 = r2 * current_mat.length + c2;
		exchange[number][0] = i1;
		exchange[number][1] = i2;
		
		swap(arr, r1, c1, r2, c2);
	}
	
	public static void backward(int[][] arr, int[][] exchange, int number) {
		int i1 = exchange[number][0];
		int i2 = exchange[number][1];
		int r1 = i1 % current_mat.length;
		int c1 = i1 - r1 * current_mat.length;
		int r2 = i2 % current_mat.length;
		int c2 = i2 - r2 * current_mat.length;
		swap(arr, r1, c1, r2, c2);
	}
	
	public static void swap(int[][] arr, int r1, int c1, int r2, int c2) {
		int i1 = r1 * current_mat.length + c1;
		int i2 = r2 * current_mat.length + c2;
		int r = arr[i1][0];
		int g = arr[i1][1];
		int b = arr[i1][2];
		arr[i1][0] = arr[i2][0];
		arr[i1][1] = arr[i2][1];
		arr[i1][2] = arr[i2][2];
		arr[i2][0] = r;
		arr[i2][1] = g;
		arr[i2][2] = b;
	}
	
	public static void img(String path, int height, int width) {
		ImgFile.writeImg(current_mat, height, width, path);
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		initIMGMat(path01);
		initMat(path01, true);
		
		//
		int row = img_mat.length;
		int col = img_mat[0].length;
		img(path02, row, col);
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, path02);
		int number = 600 * 600;
		int[][] exchange = new int[number][2];
		int[][] arr = twoToOne(current_mat);
		
		//
//		int count = 0;
		for (int i = 0; i < number; i++) {
			forward(arr, exchange, i);
			
			if(i % 1000 * 6 == 0) {
				oneToTwo(arr, row, col);
				img(path02, row, col);
				ImgFrame.setImg(frame, path02);
				System.out.println("forward: " + i);
			}
		}
		
		for (int i = number - 1; i >= 0; i--) {
			backward(arr, exchange, i);
			if(i % 1000 * 6 == 0) {
				oneToTwo(arr, row, col);
				img(path02, row, col);
				ImgFrame.setImg(frame, path02);
				System.out.println("backward: " + i);
			}
		}
		
	}
	
	
}
