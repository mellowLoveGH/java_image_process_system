package animationDIP;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import assignment1.ImgFrame;

public class Animation006 {
	
	public static int HEIGHT = 800;
	public static int WIDTH = 800;
//	public static int SIZE = 10;
	public static int iteration = 0;
	public static int[][] pos;
	
	public static void initPosition(int size) {
		int len = (HEIGHT / size) * (WIDTH / size);
		pos = new int[len][2];
		
		//
		int count = 0;
		for (int i = HEIGHT / size - 1; i >= 0; i--) {
			for (int j = 0; j < WIDTH / size; j++) {
				pos[count][0] = j * size;
				pos[count][1] = i * size;
				count++;
			}
		}
		//
		Random ran = new Random();
		for (int i = 0; i < len; i++) {
			int i1 = ran.nextInt(len);
			int i2 = ran.nextInt(len);
			
			int row = pos[i1][0];
			int col = pos[i1][1];
			
			pos[i1][0] = pos[i2][0];
			pos[i1][1] = pos[i2][1];
			pos[i2][0] = row;
			pos[i2][1] = col;
		}		
	}
	
	
	public static Particle initPtc(double x, double y, int dx, int dy, int sxv, int syv, int axv, int ayv, int xstep, int ystep) {
		Particle ptc = new Particle(x, y);
		ptc.dx = dx;
		ptc.dy = dy;
		ptc.xstep = xstep;
		ptc.ystep = ystep;
		ptc.xmove = (double)(ptc.dx - ptc.x) / ptc.xstep;
		ptc.ymove = (double)(ptc.dy - ptc.y) / ptc.ystep;
		Velocity v = new Velocity(sxv, syv);
		Velocity a = new Velocity(axv, ayv);
		ptc.setSpeed(v);
		ptc.setAcc(a);
		return ptc;
	}
	
	public static void running(List<Particle> list, int add) {
		int number = 0;
		
		for (int i = 0; i < list.size(); i++) {
			Particle ptc = list.get(i);
			if(ptc.state) {
				number++;
				running(ptc);
			}
		}
		
		emitter(list, add);
	}
	
	public static void emitter(List<Particle> list, int number) {
		Random ran = new Random();
		for (int i = 0; i < number; i++) {
			int x = 0, y = 0;
			int[] p = getPos();
			
			if(i % 2 == 0) {
				x = ran.nextInt(WIDTH/3);
				y = 0;
			}else {
				x = 0;
				y = ran.nextInt(WIDTH/3);
			}
			
			if(p == null) {
				System.out.println("no more points");
				break;
			}
			
			int dx = p[0], dy = p[1];
			int sxv = 0, syv = 0;
			int axv = 0, ayv = 0;
			int xstep = stepNum(Math.abs(dx - x)), ystep = stepNum(Math.abs(dy -y));
			Particle ptc = initPtc(x, y, dx, dy, sxv, syv, axv, ayv, xstep, ystep);
			
			list.add(ptc);
		}		
	}
	
	public static int[] getPos() {
		if(iteration >= pos.length) {
			return null;
		}
		
		int[] p = pos[iteration];
		iteration++;
		return p;
	}
	
	public static int stepNum(int dis) {
		if(dis <= 10) {
			return 1;
		}else if(dis < 300 && dis > 10) {
			return dis / 10;
		}
		
		return 30;
	}
	
	
	public static void running(Particle ptc) {
		if(!ptc.state) {
			return;
		}
//		ptc.displacement(time);		
//		if(ptc.x > 800 - 30 || ptc.x < 30) {
//			ptc.v.xv = -ptc.v.xv;
//		}		
//		if(ptc.y > 800 - 30 || ptc.y < 30) {
//			ptc.v.yv = -ptc.v.yv;
//		}
		int x = (int)(ptc.x + 0.5);
		int y = (int)(ptc.y + 0.5);
		if(x == ptc.dx && y == ptc.dy) {
			ptc.state = false;
			ptc.x = ptc.dx;
			ptc.y = ptc.dy;
		}
		Random ran = new Random();
		if(ptc.state) {		
			if(ran.nextInt(10) % 2 == 0) {
				move1(ptc);
			}else {
				move2(ptc);
			}
		}
	}
	
	public static void move1(Particle ptc) {
		int x = (int)(ptc.x + 0.5);
		int y = (int)(ptc.y + 0.5);
		
		if(x != ptc.dx) {
			ptc.x = ptc.x + ptc.xmove;
		}else {
			ptc.y = ptc.y + ptc.ymove;
		}
	}
	
	public static void move2(Particle ptc) {
		int x = (int)(ptc.x + 0.5);
		int y = (int)(ptc.y + 0.5);
		
		if(y != ptc.dy) {
			ptc.y = ptc.y + ptc.ymove;
		}else {
			ptc.x = ptc.x + ptc.xmove;
		}
	}
	
	
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
				bg_mat[row][col][0] = 255;
				bg_mat[row][col][1] = 192;
				bg_mat[row][col][2] = 203;
			}
		}
	}
	
	public static void updateMat(List<Particle> list) {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				current_mat[row][col][0] = bg_mat[row][col][0];
				current_mat[row][col][1] = bg_mat[row][col][1];
				current_mat[row][col][2] = bg_mat[row][col][2];
			}
		}
		//
		for (int i = 0; i < list.size(); i++) {
			Particle ptc = list.get(i);
			int x1 = (int)ptc.x;
			int y1 = (int)ptc.y;
			int x2 = ptc.dx;
			int y2 = ptc.dy;
			
			int size = 5;
			if(x1 > size && x1 + size < HEIGHT
					&& y1 > size && y1 + size < WIDTH
					&& x2 > size && x2 + size < HEIGHT
					&& y2 > size && y2 + size < WIDTH) {
				
				copyMat(x1, y1, x2, y2, size);
				
			}else {
				current_mat[x1][y1][0] = img_mat[x2][y2][0];
				current_mat[x1][y1][1] = img_mat[x2][y2][1];
				current_mat[x1][y1][2] = img_mat[x2][y2][2];
			}
		}		
	}
	
	public static void copyMat(int x1, int y1, int x2, int y2, int size) {
		for (int i = -size; i < size; i++) {
			for (int j = -size; j < size; j++) {
				int r1 = j + y1;
				int c1 = i + x1;
				int r2 = j + y2;
				int c2 = i + x2;				
				
				current_mat[r1][c1][0] = img_mat[r2][c2][0];
				current_mat[r1][c1][1] = img_mat[r2][c2][1];
				current_mat[r1][c1][2] = img_mat[r2][c2][2];
			}
		}
	}
	
	public static void img(String path) {
		ImgFile.writeImg(current_mat, HEIGHT, WIDTH, path);
	}
	
	public static void refresh(JFrame frame, List<Particle> list) {
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.RED);
				
				for (int i = 0; i < list.size(); i++) {
					Particle ptc = list.get(i);
					g.fillRect((int)ptc.x, (int)ptc.y, 10, 10);
				}
				
			}
		};
		panel.setBounds(0, 0, 800, 800);
		frame.setContentPane(panel);
		frame.repaint();
	}
	
	public static JFrame drawFrame(List<Particle> list) {
		JFrame frame = new JFrame("welcome");
		frame.setSize(800, 800);
		frame.setBackground(Color.WHITE);
		frame.setLocation(600, 200);

		refresh(frame, list);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	public static void main(String[] args) {
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";
		
		boolean mode = true;
		initIMGMat(path01);
		initBGMat(bg_path, mode);
		int size = 10;
		initPosition(size);
		List<Particle> list = new LinkedList<>();
		emitter(list, 10);
		
//		JFrame frame = drawFrame(list);
		
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		int add = 20;
		while(true) {
//			
			running(list, add);
			add++;
			updateMat(list);
			String path = path02;
			img(path);
			ImgFrame.setImg(frame, path);
//			int time = 30;
//			try {
//				Thread.sleep(time);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			refresh(frame, list);
		}
		
	}
	
	
	
}

