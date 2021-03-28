package animationDIP;

import java.util.Random;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class PickUpPiece {
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		
		initIMGMat(path01);
		int row = img_mat.length;
		int col = img_mat[0].length;
		img(path02, row, col);
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, path02);
		
		//
		int count = 2;
		
		//
		int[][] arr = twoToOne(current_mat);
		int number = 20 * 15;
		int size = 17;
		int[][] exchange = new int[number][2];
		for (int i = 0; i < number; i++) {
			forward(arr, exchange, i, size);
			
			if(i % count == 0) {
				oneToTwo(arr, row, col);
				img(path02, row, col);
				ImgFrame.setImg(frame, path02);
			}
			if(i % 20 == 0) {
				count++;
			}
			
			System.out.println("forward: " + i);
		}
		
		
		System.out.println();
		count = 2;
		for (int i = number - 1; i >= 0; i--) {
			backward(arr, exchange, i, size);
			if(i % count == 0) {
				oneToTwo(arr, row, col);
				img(path02, row, col);
				ImgFrame.setImg(frame, path02);
			}
			if(i % 20 == 0) {
				count++;
			}
			System.out.println("backward: " + i);
		}
	}
	
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	
	public static void initIMGMat(String path) {
		img_mat = ImgFile.getMatrix(path);
		current_mat = ImgFile.getMatrix(path);
	}
	
	public static void img(String path, int height, int width) {
		ImgFile.writeImg(current_mat, height, width, path);
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
	
	public static void forward(int[][] arr, int[][] exchange, int number, int size) {
		Random ran = new Random();
		int r1 = 0, c1 = 0, r2 = 0, c2 = 0;
		int dis = (r1 - r2) * (r1 - r2) + (c1 - c2) * (c1 - c2);
		while(dis < 2 * size * size) {
			r1 = ran.nextInt(current_mat.length - 2 * size) + size;
			c1 = ran.nextInt(current_mat[0].length - 2 * size) + size;
			r2 = ran.nextInt(current_mat.length - 2 * size) + size;
			c2 = ran.nextInt(current_mat[0].length - 2 * size) + size;
			dis = (r1 - r2) * (r1 - r2) + (c1 - c2) * (c1 - c2);
		}
		
		//
		int i1 = r1 * current_mat.length + c1;
		int i2 = r2 * current_mat.length + c2;
		exchange[number][0] = i1;
		exchange[number][1] = i2;
		
		move(arr, r1, c1, r2, c2, size);
		
//		int rs = (r2 - r1)/size;
//		int cs = (c2 - c1)/size;
//		int rdir = 1;
//		if(rs < 0) {
//			rdir = -1;
//		}
//		int cdir = 1;
//		if(cs < 0) {
//			cdir = -1;
//		}
//		double descent = Math.abs((double)cs / rs);
//		
//		if(Math.abs(rs) == 1) {
//			forward(arr, r1, c1, r2, c2, size);
//			return;
//		}
		
		
	}
	
	public static void move(int[][] arr, int r1, int c1, int r2, int c2, int size) {
		
		swap(arr, r1, c1, r2, c2, size);
		
		
	}
	
	
	public static void swap(int[][] arr, int r1, int c1, int r2, int c2, int size){
		
		for (int i = -size; i < size; i++) {
			for (int j = -size; j < size; j++) {
				int i1 = (i + r1) * current_mat.length + (j + c1);
				int i2 = (i + r2) * current_mat.length + (j + c2);
				
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
		}
	}
	
	public static void backward(int[][] arr, int[][] exchange, int number, int size) {
		int i1 = exchange[number][0];
		int i2 = exchange[number][1];
		int r1 = i1 % current_mat.length;
		int c1 = i1 - r1 * current_mat.length;
		int r2 = i2 % current_mat.length;
		int c2 = i2 - r2 * current_mat.length;
		
		move(arr, r1, c1, r2, c2, size);
		
	}
	
	
	
	public static int[][] forward(int[][] arr, int number) {
		int[][] exchange = new int[number][2];
		
		Random ran = new Random();
		for (int i = 0; i < number; i++) {
			int i1 = ran.nextInt(arr.length);
			int i2 = ran.nextInt(arr.length);
			//
			int r = arr[i1][0];
			int g = arr[i1][1];
			int b = arr[i1][2];
			arr[i1][0] = arr[i2][0];
			arr[i1][1] = arr[i2][1];
			arr[i1][2] = arr[i2][2];
			arr[i2][0] = r;
			arr[i2][1] = g;
			arr[i2][2] = b;
			
			exchange[i][0] = i1;
			exchange[i][1] = i2;
		}
		
		return exchange;
	}
	
	public static void backward(int[][] arr, int[][] exchange) {
		for (int i = exchange.length - 1; i >= 0; i--) {
			int i1 = exchange[i][0];
			int i2 = exchange[i][1];
			
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
	}
	
	
}
