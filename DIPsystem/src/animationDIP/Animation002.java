package animationDIP;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import assignment1.ImgFrame;

public class Animation002 {
	
	public static void main(String[] args) {
		
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower2.jpg";
		
		boolean mode = true;
		orginal_x = 390;
		orginal_y = 600;
		initIMGMat(path01);
		initBGMat(bg_path, mode);
		initMat(bg_path, mode);
		//
		initList();
		int size = 6;
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		
		
		while(true) {
			
			int time = 30;
//			try {
//				Thread.sleep((long)time);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
			
			running((double)time/1000);
//			refresh(frame);
			String path = path02;
			updateMat(bg_path, size, !mode);
			img(path, HEIGHT, WIDTH);
			ImgFrame.setImg(frame, path);
			backward(size);
			
		}
		
		
	}
	
	public static List<Particle> list;
	public static int HEIGHT = 1000;
	public static int WIDTH = 1000;
	public static int orginal_x = 600;
	public static int orginal_y = 600;
	
	public static void initList() {
		list = new LinkedList<>();
		int number = 120;
		emitter(number);
	}
	
	public static void emitter(int number){
		
		Random ran = new Random();
		for (int i = 0; i < number; i++) {
			
			Particle ptc = new Particle(orginal_x, orginal_y);
			//starting speed
			int sxv = ran.nextInt(60);
			if(i % 2 == 0) {
				sxv = -sxv;
			}
			
			int syv = -100;
			Velocity v = new Velocity(sxv, syv);
			ptc.setSpeed(v);
			
			sxv = ran.nextInt(3) + 1;
			if(i % 2 == 0) {
				sxv = -sxv;
			}
			
			syv = (ran.nextInt(30) + 0);
			Velocity a = new Velocity(sxv, syv);
			ptc.setAcc(a);
			
			list.add(ptc);
		}
	}
	
	public static void running(double time) {
		System.out.print(list.size() + ", ");
		
		List<Particle> updateList = new LinkedList<>();
		
		for (int i = 0; i < list.size(); i++) {
			
			Particle ptc = list.get(i);
			
			if(ptc.state) {
				ptc.displacement(time);
				updateList.add(ptc);
			}else {
				
			}
			
			if(ptc.x > WIDTH || ptc.y > HEIGHT ||
					ptc.x < 0 || ptc.y < 0) {
				ptc.state = false;
			}
		}
		
//		ListIterator<Particle> it = list.listIterator();
//		while(it.hasNext()) {
//			Particle ptc = it.next();
//			
//			if(ptc.state) {
//				ptc.displacement(time);
//			}else {
//				list.remove(ptc);
//			}
//			
//			if(ptc.x > WIDTH || ptc.y > HEIGHT ||
//					ptc.x < 0 || ptc.y < 0) {
//				ptc.state = false;
//			}
//		}
		list = updateList;
		System.out.println(list.size() + ", ");
		System.out.println();
		
		int number = 12;
		emitter(number);		
	}
	
	//
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	
	
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
	
	public static void initMat(String path, boolean mode) {
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
	
	public static void updateMat(String path, int size, boolean mode) {
//		initMat(path, mode);
		
		for (int i = 0; i < list.size(); i++) {
			Particle ptc = list.get(i);
			
			int row = (int) (ptc.y + 0.5);
			int col = (int) (ptc.x + 0.5);
			ImgFile.copyMat(img_mat, current_mat, row, col, size);
		}
	}
	
	public static void backward(int size) {
		for (int i = 0; i < list.size(); i++) {
			Particle ptc = list.get(i);
			
			int row = (int) (ptc.y + 0.5);
			int col = (int) (ptc.x + 0.5);
			ImgFile.copyMat(bg_mat, current_mat, row, col, size);
		}
	}
	
	public static void img(String path, int height, int width) {
		ImgFile.writeImg(current_mat, height, width, path);
	}
	
	
	public static void initIMGMat(String path) {
		img_mat = ImgFile.getMatrix(path);
		HEIGHT = img_mat.length;
		WIDTH = img_mat[0].length;
		bg_mat = new int[HEIGHT][WIDTH][3];
		current_mat = new int[HEIGHT][WIDTH][3];
	}
	
	
	
	public static void refresh(JFrame frame) {
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < list.size(); i++) {
					g.setColor(Color.RED);
					Particle p = list.get(i);
					if(p.state) {
						g.fillRect((int)p.x, (int)p.y, 6, 6);
					}else {
						
					}
				}
			}
		};
		panel.setBounds(0, 0, 800, 800);
		frame.setContentPane(panel);
		frame.repaint();
	}
	
	public static JFrame drawFrame() {
		JFrame frame = new JFrame("welcome");
		frame.setSize(800, 800);
		frame.setBackground(Color.WHITE);
		frame.setLocation(600, 200);

		refresh(frame);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
}

class Particle {
	public double x;
	public double y;
	public int dx;
	public int dy;
	public int xstep = 0;
	public int ystep = 0;
	public double xmove = 0;
	public double ymove = 0;
	
	public Velocity v;
	public Velocity a;
	public boolean state = true;
	
	public Particle(double x1, double y1) {
		x = x1;
		y = y1;
	}
	
	public void setSpeed(Velocity sv) {
		v = sv;
	}
	
	public void setAcc(Velocity acc) {
		a = acc;
	}
	
	//per second
	public void displacement(double time) {
		double sxv = v.xv;
		double syv = v.yv;
		//
		v.operation(a, time);
		//
		double exv = v.xv;
		double eyv = v.yv;
		
		double xd = (sxv + exv) * time / 2;
		double yd = (syv + eyv) * time / 2;
		
		x = x + xd;
		y = y + yd;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return x + ", " + y;
	}
}

class Velocity {
	public double xv;
	public double yv;
	
	public Velocity(double sxv, double syv) {
		xv = sxv;
		yv = syv;
	}
	
	public void operation(Velocity a, double time) {
		this.xv = this.xv + a.xv * time;
		this.yv = this.yv + a.yv * time;
	}
}
