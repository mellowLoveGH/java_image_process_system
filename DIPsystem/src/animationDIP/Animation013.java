package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation013 {
	
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
		current_mat = ImgFile.getMatrix(path);
		
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
	
	public static void filter(int size) {
		for (int row = size; row < HEIGHT - size; row++) {
			for (int col = size; col < WIDTH - size; col++) {
				int[] rgb = mean(row, col, img_mat, size);
				current_mat[row][col][0] = rgb[0];
				current_mat[row][col][1] = rgb[1];
				current_mat[row][col][2] = rgb[2];
			}
		}		
	}
	
	public static int[] mean(int r, int c, int[][][] mat, int size) {
		int[] rgb = new int[3];
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		//
		for (int row = -size; row < size; row++) {
			for (int col = -size; col < size; col++) {
				rgb[0] = rgb[0] + mat[row + r][col + c][0];
				rgb[1] = rgb[1] + mat[row + r][col + c][1];
				rgb[2] = rgb[2] + mat[row + r][col + c][2];
			}
		}
		int number = (size * 2) * (size * 2);
		rgb[0] = rgb[0] / number;
		rgb[1] = rgb[1] / number;
		rgb[2] = rgb[2] / number;
		
		return rgb;
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
		ImgFrame.setImg(frame, path01);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		int size = 2;
		System.out.println("filter 1");
		filter(size);
		JFrame frame1 = ImgFrame.CFrame();
		img(path02);
		System.out.println("filter 2");
		ImgFrame.setImg(frame1, path02);
	}
	
}
