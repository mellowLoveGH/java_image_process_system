package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation015 {
	
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
	
	public static void update(int number, double p1, double p2, double p3) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = 255;
				current_mat[row][col][1] = 192;
				current_mat[row][col][2] = 203;
			}
		}
		//
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				double r = img_mat[row][col][0] * p1;
				double g = img_mat[row][col][1] * p2;
				double b = img_mat[row][col][2] * p3;
				
				int color = (int)(r + g + b + 0.5);
				
				if(color <= number) {
					current_mat[row][col][0] = img_mat[row][col][0];
					current_mat[row][col][1] = img_mat[row][col][1];
					current_mat[row][col][2] = img_mat[row][col][2];
				}
				//
			}
		}
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
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
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		double p1 = 0.299;
		double p2 = 0.587;
		double p3 = 0.114;
		p1 = 1;
		p2 = 1;
		p3 = 1;
		for (int i = 0; i < 256 * 3; i++) {
			update(i, p1, p2, p3);
			img(path02);
			ImgFrame.setImg(frame, path02);
		}
		
	}
	
}
