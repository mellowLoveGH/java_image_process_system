package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation008 {
	
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
				//255, 192, 203
				bg_mat[row][col][0] = 255;
				bg_mat[row][col][1] = 192;
				bg_mat[row][col][2] = 203;
			}
		}
	}
	
	public static void initMat() {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = 255;
				current_mat[row][col][1] = 255;
				current_mat[row][col][2] = 255;
			}
		}
	}
	
	public static void approach(int time) {
		int i = 0;
		while(i < time) {
			for (int row = 0; row < HEIGHT; row++) {
				for (int col = 0; col < WIDTH; col++) {
					
					if(current_mat[row][col][0] != img_mat[row][col][0]) {
						current_mat[row][col][0]--;
					}
					if(current_mat[row][col][1] != img_mat[row][col][1]) {
						current_mat[row][col][1]--;
					}
					if(current_mat[row][col][2] != img_mat[row][col][2]) {
						current_mat[row][col][2]--;
					}
				}
			}
			
			i++;
		}
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		boolean mode = true;
		initIMGMat(path01);
		initBGMat(bg_path, mode);
		initMat();
		JFrame frame = ImgFrame.CFrame();
		int time = 1;
		for (int i = 0; i < 255; i++) {
			img(path02);
			approach(time);
			ImgFrame.setImg(frame, path02);
			System.out.println("iteration: " + (i+1));
		}
	}
	
}
