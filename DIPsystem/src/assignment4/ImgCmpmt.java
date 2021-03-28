package assignment4;

import animationDIP.ImgFile;

public class ImgCmpmt {
	
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
//		current_mat = new int[HEIGHT][WIDTH][3];
	}
	
	public static void cmpmt(int h, int w) {
		int d1 = h - HEIGHT;
		int d2 = w - WIDTH;
		System.out.println(d1);
		System.out.println(d2);
		
		current_mat = new int[h][w][3];
		
		//
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				current_mat[row][col][0] = 255;
				current_mat[row][col][1] = 192;
				current_mat[row][col][2] = 203;
			}
		}
		
		//
		for (int row = d1/2; row < HEIGHT + d1/2; row++) {
			for (int col = d2/2; col < WIDTH + d2/2; col++) {
				current_mat[row][col][0] = img_mat[row - d1/2][col - d2/2][0];
				current_mat[row][col][1] = img_mat[row - d1/2][col - d2/2][1];
				current_mat[row][col][2] = img_mat[row - d1/2][col - d2/2][2];
			}
		}		
		
	}
	
	public static void img(String path, int h, int w) {
		ImgFile.writeImg(current_mat, h, w, path);
	}
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\reminiscence1.jpg";
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\reminiscence2.jpg";
		
		boolean mode = true;
		initIMGMat(path01);
		int h = 810;
		int w = 810;
		cmpmt(h, w);
		
		img(path02, h, w);
		
	}
	
	
}
