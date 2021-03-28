package assignment4;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import assignment1.ImgFrame;

public class Animation01 {
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\csy1.jpg";
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy2.jpg";
		
		int[][][] mat = getMatrix(path01);
		int width = mat[0].length;
		int height = mat.length;
		
		Set<Integer> rowset = new TreeSet<Integer>();
		Set<Integer> colset = new TreeSet<Integer>();
		System.out.println(height);
		System.out.println(width);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		JFrame frame = ImgFrame.CFrame();
		
		int leap = 1;
		for (int i = 0; i < 100; i = i + leap) {
			addPoint(rowset, height);
			addPoint(colset, width);
			
			animation(mat, path02, rowset, colset);
			String path = path02;
			ImgFrame.setImg(frame, path);
			
			System.out.println("iteration: " + i);
		}
		
	}
	
	
	
	public static void animation(int[][][] mat, String path02, Set<Integer> rowset, Set<Integer> colset) {		

		int width = mat[0].length;
		int height = mat.length;

		BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgbim3 = new int[width];
		
//		Iterator<Integer> rit = rowset.iterator();
//		Iterator<Integer> cit = colset.iterator();
//		while (rit.hasNext()) {
//		    int r = rit.next();
//		    
//		    while(cit.hasNext()) {
//		    	int c = cit.next();
//		    	
//		    	
//		    }
//		}    
		
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int r3 = 0;
				int g3 = 0;
				int b3 = 0;
				
				if(rowset.contains(row) && colset.contains(col)) {
					r3 = mat[row][col][0];
					g3 = mat[row][col][1];
					b3 = mat[row][col][2];
				}else {
					r3 = 255;
					g3 = 255;
					b3 = 255;
				}
				
				rgbim3[col] = (r3 << 16) | (g3 << 8) | b3;
			}
			bi3.setRGB(0, row, width, 1, rgbim3, 0, width);
		}
		
		try {
			ImageIO.write(bi3, "jpg", new File(path02));
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
	public static int[][][] getMatrix(String path01){
		BufferedImage bi1 = null;

		try {
			bi1 = ImageIO.read(new File(path01));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (bi1 == null)
			throw new NullPointerException("bi1 is null");

		int width = bi1.getWidth();
		int height = bi1.getHeight();

		int[] rgbim1 = new int[width];
		
		int[][][] mat = new int[height][width][3];
		
		for (int row = 0; row < height; row++) {
			bi1.getRGB(0, row, width, 1, rgbim1, 0, width);

			for (int col = 0; col < width; col++) {
				int rgb1 = rgbim1[col];
				int r1 = (rgb1 >> 16) & 255;
				int g1 = (rgb1 >> 8) & 255;
				int b1 = rgb1 & 255;

				mat[row][col][0] = r1;
				mat[row][col][1] = g1;
				mat[row][col][2] = b1;			
			}
		}
		
		return mat;
	}
	
	public static void addPoint(Set<Integer> set, int range) {
		int num = 0;
		for (int i = 0; i < 10 * 5; i++) {
			num = (int) (Math.random() * range);
			set.add(num);
		}
	}
	
}
