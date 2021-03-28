package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation007 {
	
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	public static int HEIGHT = 1000;
	public static int WIDTH = 1000;
	
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
				//pink, 255, 192, 203
				bg_mat[row][col][0] = 255;
				bg_mat[row][col][1] = 255;
				bg_mat[row][col][2] = 255;
			}
		}
	}
	
	public static void sample(int row_size, int col_size) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				if(row % row_size == 0 && col % col_size == 0) {
					current_mat[row][col][0] = img_mat[row][col][0];
					current_mat[row][col][1] = img_mat[row][col][1];
					current_mat[row][col][2] = img_mat[row][col][2];
				}else {
					current_mat[row][col][0] = bg_mat[row][col][0];
					current_mat[row][col][1] = bg_mat[row][col][1];
					current_mat[row][col][2] = bg_mat[row][col][2];
				}
			}
			
		}
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower2.jpg";
		
		initIMGMat(path01);
		boolean mode = true;
		initBGMat(bg_path, !mode);
		int row_size = 2;
		int col_size = 2;
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		sample(row_size, col_size);
		img(path02);
		ImgFrame.setImg(frame, path02);
		
	}
	
}
