package animationDIP;

import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation001 {
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower2.jpg";
		
		initBGMat(bg_path);
		initIMGMat(path01);
		
		//
		int size = 20;
		int row = bg_mat.length;
		int col = bg_mat[0].length;
		int iteration = 0;
		int leap = size*2;
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
//		try {
//			Thread.sleep(2*1000);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		for (int i = 0; i < row; i = i + leap) {
			for (int j = 0; j < col; j = j + leap) {
//				System.out.println(current_mat[i][j][0] + ", " + current_mat[i][j][1] + ", " + current_mat[i][j][2]);
				
				String path = path02;
				forward(i, j, size);
//				System.out.println(current_mat[i][j][0] + ", " + current_mat[i][j][1] + ", " + current_mat[i][j][2]);
				
				img(path, row, col);
				ImgFrame.setImg(frame, path);
				
//				backward(i, j, size);
//				System.out.println(current_mat[i][j][0] + ", " + current_mat[i][j][1] + ", " + current_mat[i][j][2]);
				iteration++;
				System.out.println("iteration: " + iteration);
				System.out.println();
			}
		}
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
	
	public static void forward(int row, int col, int size) {
		ImgFile.copyMat(img_mat, current_mat, row, col, size);
	}
	
	public static void backward(int row, int col, int size) {
		ImgFile.copyMat(bg_mat, current_mat, row, col, size);
	}
	
	public static void img(String path, int height, int width) {
		ImgFile.writeImg(current_mat, height, width, path);
	}
	
}
