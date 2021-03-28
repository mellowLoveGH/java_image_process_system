package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation009{
	
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
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static void colorful(double r, double g, double b) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = (int)(r * img_mat[row][col][0] + 0.5);
				current_mat[row][col][1] = (int)(g * img_mat[row][col][1] + 0.5);
				current_mat[row][col][2] = (int)(b * img_mat[row][col][2] + 0.5);
			}
		}
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		initIMGMat(path01);
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		int number = 10;
		double alpha = 1.0 / number;
		
		for (int i = 0; i < number; i++) {
			for (int j = 0; j < number; j++) {
				for (int j2 = 0; j2 < number; j2++) {
					double r = (i + 1) * alpha;
					double g = (j + 1) * alpha;
					double b = (j2 + 1) * alpha;
					colorful(r, g, b);
					img(path02);
					ImgFrame.setImg(frame, path02);
				}
			}
		}
		
	}
	
}
