package animationDIP;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import assignment1.ImgFrame;

public class SpringDIP {
	
//	public static int springK = 1;
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc01.jpg"; 
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\wcc02.jpg"; 
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		boolean mode = true;
		initIMGMat(path01);
		initBGMat(bg_path, mode);
		initCRTMat(path01, !mode);
		
		
		List<Point> list = new LinkedList<>();
		int size = 10;
		initList(list, size);
		
//		JFrame frame = drawFrame(list);
		JFrame frame = ImgFrame.CFrame();
		
		while(true) {
			double time = 50.0 / 100;
//			p1.running(time);
//			System.out.println(p1);
			running(list, time, size);
//			refresh(frame, list);
			img(path02);
			ImgFrame.setImg(frame, path02);
			try {
//				Thread.sleep(1000/1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
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
		current_mat = new int[HEIGHT][WIDTH][3];
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
	
	public static void initCRTMat(String path, boolean mode) {
		if(mode) {
			current_mat = ImgFile.getMatrix(path);
			return;
		}
		
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = 255;
				current_mat[row][col][1] = 192;
				current_mat[row][col][2] = 203;
			}
		}
	}
	
	public static void initList(List<Point> list, int size) {
		Random ran = new Random();
		for (int row = 0; row < HEIGHT; row = row + size) {
			for (int col = 0; col < WIDTH; col = col + size) {
				double x = row, y = col;
				double v = ran.nextInt(10) + 10 * 2;
				double a = - v/(ran.nextInt(6) + 3);
				int dir = ran.nextInt(2);
//				v = 16;
//				a = -4;
//				dir = 1;
				Point p1 = new Point(x, y, v, a, dir);
				p1.dx = row;
				p1.dy = col;
				list.add(p1);
			}
		}		
	}
	
	public static void running(List<Point> list, double time, int size) {
		int number = 0;
		for (int i = 0; i < list.size(); i++) {
			Point p = list.get(i);
			p.running(time);
			int x = (int)(p.x + 0.5);
			int y = (int)(p.y + 0.5);
			if(inBorder(x, y)) {
				number = number + 1;
			}
			updateIMG(x, y, p.dx, p.dy, size);
		}		
		System.out.println(number);
	}
	
	public static void updateIMG(int x, int y, int dx, int dy, int size) {
		for (int i = -size; i < size; i++) {
			for (int j = -size; j < size; j++) {
				int r1 = j + y;
				int c1 = i + x;
				int r2 = j + dy;
				int c2 = i + dx;
				if(inBorder(r1, c1) && inBorder(r2, c2)) {
					current_mat[r1][c1][0] = img_mat[r2][c2][0];
					current_mat[r1][c1][1] = img_mat[r2][c2][1];
					current_mat[r1][c1][2] = img_mat[r2][c2][2];
				}
			}
		}
		
	}
	
	public static boolean inBorder(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
			return false;
		}
		return true;
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static JFrame drawFrame(List<Point> list) {
		JFrame frame = new JFrame("welcome");
		frame.setSize(800, 800);
		frame.setBackground(Color.WHITE);
		frame.setLocation(600, 200);

		refresh(frame, list);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	public static void refresh(JFrame frame, List<Point> list) {
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
//				g.setColor(Color.RED);
				
				for (int i = 0; i < list.size(); i++) {
					Point ptc = list.get(i);
					g.fillRect((int)ptc.x, (int)ptc.y, 5, 5);
				}
				
			}
		};
		panel.setBounds(0, 0, 800, 800);
		frame.setContentPane(panel);
		frame.repaint();
	}
	
	
}

class Point{
	public double x;
	public double y;
	public double v0;
	public double v;
	
	public Point(double p1, double p2, double sv, double sa, int dir) {
		x = p1;
		y = p2;
		v0 = sv;
		v = sv;
		a = sa;
		direction = dir;
	}
	
	public double m = 1;
	public double distance = 0;
	public int dx;
	public int dy;
	public int direction = 0;
	public double a;
	public double delta = 0;
	
	public void running(double time) {
		
		if(direction == 0) {
			goVertical(time);
		}else {
			goHorizontal(time);
		}
		
		inverseAcc();
	}
	
	public void goVertical(double time) {
		double v1 = v + a;
		delta = (v + v1) * time / 2;
		y = y + delta;
		v = v1;
	}
	
	public void goHorizontal(double time) {
		double v1 = v + a;
		delta = (v + v1) * time / 2;
		x = x + delta;
		v = v1;
	}
	
	public void inverseAcc() {
		if(Math.abs(Math.abs(v) - Math.abs(v0)) < 0.01) {
			a = -a;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return x + ", " + y + " v0: " + v0 + ", v: " + v;
	}
	
}

