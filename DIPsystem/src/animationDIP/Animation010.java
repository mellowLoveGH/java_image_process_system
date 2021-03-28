package animationDIP;

import java.util.Random;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation010 {
	
	public static int HEIGHT = 800;
	public static int WIDTH = 800;
	public static int SIZE = 3;	
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	public static int[][][] cpl_mat;
	public static int[][] update;
	
	public static void initIMGMat(String path) {
		img_mat = ImgFile.getMatrix(path);
		HEIGHT = img_mat.length;
		WIDTH = img_mat[0].length;
		bg_mat = new int[HEIGHT][WIDTH][3];
		current_mat = new int[HEIGHT][WIDTH][3];
		cpl_mat = ImgFile.getMatrix(path);
		//
		
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
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static double distance(int r1, int c1, int r2, int c2) {
		double dx = (double)(r1 - r2);
		double dy = (double)(c1 - c2);
		dx = dx / HEIGHT;
		dy = dy / WIDTH;
		double d1 = dx * dx + dy * dy;
		
		double r = img_mat[r1][c1][0] - img_mat[r2][c2][0];
		double g = img_mat[r1][c1][1] - img_mat[r2][c2][1];
		double b = img_mat[r1][c1][2] - img_mat[r2][c2][2];
		
		int weight = 255;
		r = r / 255 * weight;
		g = g / 255 * weight;
		b = b / 255 * weight;
		double d2 = r * r + g * g + b * b;
		
		return d1 + d2;
	}
	
	public static void initPoint(int r, int c) {
		update = new int[HEIGHT][WIDTH];
		
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
//				current_mat[row][col][0] = 255;
//				current_mat[row][col][1] = 255;
//				current_mat[row][col][2] = 255;
				current_mat[row][col][0] = bg_mat[row][col][0];
				current_mat[row][col][1] = bg_mat[row][col][1];
				current_mat[row][col][2] = bg_mat[row][col][2];
				
				update[row][col] = 0;
			}
		}
//		current_mat[r][c][0] = img_mat[r][c][0];
//		current_mat[r][c][1] = img_mat[r][c][1];
//		current_mat[r][c][2] = img_mat[r][c][2];
		ImgFile.copyMat(img_mat, current_mat, r, c, SIZE);
		update[r][c] = 1;
	}
	
	
	public static void flip(int r1, int c1, int loop, int sx, int sy, int ex, int ey) {
		for (int i = 0; i < loop; i++) {
			flip(r1, c1, sx, sy, ex, ey);
		}
	}
	
	public static void flip(int r1, int c1, int sx, int sy, int ex, int ey) {
		double dis = Integer.MAX_VALUE;
		int r2 = r1, c2 = c1;
		for (int row = sx; row < ex; row = row + SIZE) {
			for (int col = sy; col < ey; col = col + SIZE) {
				if(update[row][col] == 0) {
					double d = distance(r1, c1, row, col);
					if(dis > d) {
						dis = d;
						r2 = row;
						c2 = col;
					}
				}
				
			}
		}
		//
		update[r2][c2] = 1;
		
		ImgFile.copyMat(img_mat, current_mat, r2, c2, SIZE);
		ImgFile.copyMat(bg_mat, cpl_mat, r2, c2, SIZE);
	}
	
	public static void complement(int sx, int sy, int ex, int ey, String path) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				cpl_mat[row][col][0] = 255;
				cpl_mat[row][col][1] = 192;
				cpl_mat[row][col][2] = 203;
			}
		}
		
		
		for (int row = sx; row < ex; row = row + SIZE) {
			for (int col = sy; col < ey; col = col + SIZE) {
				if(update[row][col] == 0) {
					ImgFile.copyMat(img_mat, cpl_mat, row, col, SIZE);
				}else {
//					System.out.println(")))))))))))))))))))))");
				}
			}
		}
		
		ImgFile.writeImg(cpl_mat, HEIGHT, WIDTH, path);
	}
	
	public static void imgCpl(String path) {
		ImgFile.writeImg(cpl_mat, HEIGHT, WIDTH, path);
	}
	
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String path03 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy2.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc01.jpg"; 
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc02.jpg"; 
		
		initIMGMat(path01);
		initBGMat(bg_path, !true);
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, path01);
		JFrame frame1 = ImgFrame.CFrame();
		
		try {
			Thread.sleep(1000 * 6);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int r = 300;
		int c = 250;
		int sx = 0;
		int sy = 0;
		int ex = 810;
		int ey = 810;
		initPoint(r, c);
		for (int i = 0; i < 300; i++) {
			flip(r, c, (i+1) * 10, sx, sy, ex, ey);
			img(path02);
			ImgFrame.setImg(frame, path02);
//			complement(sx, sy, ex, ey, path03);
			imgCpl(path03);
			ImgFrame.setImg(frame1, path03);
			System.out.println("iteration: " + (i+1));
		}
		System.out.println("iteration: ");
	}
	
}
