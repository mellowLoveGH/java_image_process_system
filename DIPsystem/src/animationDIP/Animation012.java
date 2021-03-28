package animationDIP;

import java.util.Random;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation012 {
	
	public static int HEIGHT = 800;
	public static int WIDTH = 800;
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
	
	
	public static void update(double h, double phi) {
		
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = 255;
				current_mat[row][col][1] = 192;
				current_mat[row][col][2] = 203;
			}
			
		}
		
		
		Random ran = new Random();
		
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				int y = (int) (function(row, h, phi) + 0.5);
//				System.out.println(y);
				int c = col - y;
				
//				c = col - ran.nextInt(100);
				if(c >= 0 && c <= WIDTH) {
					current_mat[c][row][0] = img_mat[col][row][0];
					current_mat[c][row][1] = img_mat[col][row][1];
					current_mat[c][row][2] = img_mat[col][row][2];
				}
				
			}
		}
		
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static double function(double x, double h, double phi) {
		double y = 0;
		phi = phi / Math.PI;
		
		x = x / WIDTH;
		x = x * Math.PI * 1;
		y = Math.sin(x + phi) * h;
		y = Math.abs(y);
		return y;
	}
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc01.jpg"; 
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		boolean mode = true;
		initIMGMat(path01);
		initBGMat(bg_path, mode);
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, path01);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(2024%253);
		double h = 100;
		for (int i = 0; i < 200; i++) {
			double phi = (i + 1) * 0.5;
			update(h, phi);
			img(path02);
			ImgFrame.setImg(frame, path02);
		}
		
	}
	
}
