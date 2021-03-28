package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation011 {
	
	
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
				//255, 192, 203
				bg_mat[row][col][0] = 255;
				bg_mat[row][col][1] = 0;
				bg_mat[row][col][2] = 0;
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
				current_mat[row][col][0] = bg_mat[row][col][0];
				current_mat[row][col][1] = bg_mat[row][col][1];
				current_mat[row][col][2] = bg_mat[row][col][2];
			}
		}
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static void drawEdge(int r, int c, int h, int w, int step) {
		
		int pc = c;
		
		for (int i = r; i < r + h; i = i + step) {
			double dvt = 0;
			int col = c;
			for (int j = c + step; j < c + w; j = j + 1) {
				int c1 = j - step;
				int c2 = j;
				double v = (c2 - col) * (c2 - col);
				v = 0;
				double d = distance(i, c1, i, c2) - v;
				if(d > dvt) {
					dvt = d;
					col = c1;
				}
			}
			int size = 2;
//			if(dvt > 1000) {
				ImgFile.copyMat(bg_mat, current_mat, i, col, size);
//			}
			System.out.println(dvt);
		}
	}
	
	
	public static double distance(int r1, int c1, int r2, int c2){
		double dr = img_mat[r1][c1][0] - img_mat[r2][c2][0];
		double dg = img_mat[r1][c1][1] - img_mat[r2][c2][1];
		double db = img_mat[r1][c1][2] - img_mat[r2][c2][2];
//		dr = dr / 255;
//		dg = dg / 255;
//		db = db / 255;
		
		double d = dr * dr + dg * dg + db * db;
		return d;
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy3.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		boolean mode = true;
		initIMGMat(path01);
		initBGMat(bg_path, !mode);
		initCRTMat(path01, mode);
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, path01);
		
		int r = 320;
		int c = 340;
		int h = 50;
		int w = 30;
		int step = 5;
		for (int i = 0; i < 5; i++) {
			drawEdge(r, c, h, w, step);
			r = r + h;
//			c = c + w;
		}
		
		
		img(path02);
		ImgFrame.setImg(frame, path02);
	}
	
	
	
}
