package animationDIP;

import java.util.Random;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation014 {
	
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	public static int HEIGHT = 800;
	public static int WIDTH = 800;
	
	
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
	
	public static void initCRTMat(String path, boolean mode) {
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
	
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	
	public static void magnify(int r, int c, int w, int h, double size, String path) {
		
		double s = 1.0;
		while(s < size) {
			magnify(r, c, w, h, s);
			s = s + 0.2;
			img(path);
			
		}		
	}
	
	public static void magnify(int r, int c, int w, int h, double size) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = img_mat[row][col][0];
				current_mat[row][col][1] = img_mat[row][col][1];
				current_mat[row][col][2] = img_mat[row][col][2];
			}
		}
		
		int cx = r + w/2;
		int cy = c + h/2;
		
		int right = - (int)(w/2 * size);
		int left = (int)(w/2 * size);
		int top = - (int)(h/2 * size);
		int bottom = (int)(h/2 * size);
		
		//
		for (int row = right; row < left; row++) {
			for (int col = top; col < bottom; col++) {
				int r1 = row + cx;
				int c1 = col + cy;
				int r2 = cx + (int)(row/size + 0.5);
				int c2 = cy + (int)(col/size + 0.5);
				if(inBorder(r1, c1) && inBorder(r2, c2)) {
					current_mat[r1][c1][0] = img_mat[r2][c2][0];
					current_mat[r1][c1][1] = img_mat[r2][c2][1];
					current_mat[r1][c1][2] = img_mat[r2][c2][2];
				}
			}
		}
		
	}
	
	public static boolean inBorder(int r, int c) {
		if(r < 0 || r >= HEIGHT || c < 0 || c >= WIDTH) {
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc01.jpg"; 
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc02.jpg"; 
		
		boolean mode = true;
		initIMGMat(path01);
		initBGMat(bg_path, mode);
		
		double size = 1.5;
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, path01);
		
		int r = 100;
		int c = 300;
		int w = 100;
		int h = 100;
		Random ran = new Random();
		
		for (int i = 0; i < 100; i++) {
			int row = ran.nextInt(200) + r;
			int col = ran.nextInt(100) + c;
			size = ran.nextDouble() * 2 + 1;
			
			w = ran.nextInt(6) + 2;
			w = w * 50;
			h = w;
			
			//
			double s = 1.0;
			while(s < size) {
				magnify(row, col, w, h, s);
				s = s + 0.1;
				img(path02);
				ImgFrame.setImg(frame, path02);
			}	
//			magnify(row, col, w, h, size);			
		}
		
		
	}
	
	
	
	
	
	
	
	
}
