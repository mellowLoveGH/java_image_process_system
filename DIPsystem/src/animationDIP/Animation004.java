package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation004 {
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower2.jpg";
		
		initBGMat(bg_path);
		initIMGMat(path01);
		int row = bg_mat.length;
		int col = bg_mat[0].length;
		
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		double ratio = 0;
		double speed = 0.003;
		for (int i = 0; i < 10 * 36; i++) {
			if(ratio < 1) {
				ratio = ratio + speed;
				imgAnd(ratio);
				img(path02, row, col);
				ImgFrame.setImg(frame, path02);
			}
			if(i % 10 * 2 == 1) {
				speed = speed + 0.001;
			}
		}
		ImgFrame.setImg(frame, path01);
	}
	
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	
	public static void initBGMat(String path) {
		bg_mat = ImgFile.getMatrix(path);
		current_mat = ImgFile.getMatrix(path);
	}
	
	public static void initIMGMat(String path) {
		img_mat = ImgFile.getMatrix(path);
	}
	
	public static void img(String path, int height, int width) {
		ImgFile.writeImg(current_mat, height, width, path);
	}
	
	public static void imgAnd(double ratio) {
		for (int i = 0; i < current_mat.length; i++) {
			for (int j = 0; j < current_mat[0].length; j++) {
				current_mat[i][j][0] = (int)(bg_mat[i][j][0] * (1 - ratio) + 
						img_mat[i][j][0] * ratio);
				current_mat[i][j][1] = (int)(bg_mat[i][j][1] * (1 - ratio) + 
						img_mat[i][j][1] * ratio);
				current_mat[i][j][2] = (int)(bg_mat[i][j][2] * (1 - ratio) + 
						img_mat[i][j][2] * ratio);
			}
		}
	}
	
}
