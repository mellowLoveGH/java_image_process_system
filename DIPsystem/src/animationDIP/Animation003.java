package animationDIP;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import assignment1.ImgFrame;

public class Animation003 {
	public static void main(String[] args) {

		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy1.jpg";
		String bg_path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\flower4.jpg";

		boolean mode = true;

		initIMGMat(path01);
		initBGMat(bg_path, mode);
		initMat(bg_path, mode);
		//
		int sx1 = 0, sy1 = 760;
		int number = 120;
		double theta1 = -60;
		List<Particle> list1 = initList(sx1, sy1, theta1, number);
		
		//
		int sx2 = 800, sy2 = 760;
		double theta2 = 60;
		List<Particle> list2 = initList(sx2, sy2, theta2, number);
		
		int size = 6;
		JFrame frame = ImgFrame.CFrame();
		ImgFrame.setImg(frame, bg_path);
		
		
		
//		JFrame frame = drawFrame(list);
		int count = 0;
		while (true) {
			int time = 30;
			count = count + 1;
//			theta1 = theta1 + 1;
//			theta2 = theta2 - 1;
//			if(theta1 > -30) {
//				theta1 = -150;
//			}
//			if(theta2 < 30) {
//				theta2 = 150;
//			}
			
			
			running(list1, sx1, sy1, theta1, (double) time / 1000);
			//
			running(list2, sx2, sy2, theta2, (double) time / 1000);
			
			String path = path02;
			updateMat(bg_path, list1, size, !mode);
			updateMat(bg_path, list2, size, !mode);
			
			img(path, HEIGHT, WIDTH);
			ImgFrame.setImg(frame, path);
			backward(list1, size);
			backward(list2, size);
			
//			try {
//				Thread.sleep((long)time);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			refresh(frame, list);
		}
		
	}
	
	
	
	
	
	
//	public static List<Particle> list;
	public static int HEIGHT = 1000;
	public static int WIDTH = 1000;

	public static List<Particle> initList(int sx, int sy, double theta, int number) {
		List<Particle> list = new LinkedList<>();
//		int number = 120;
		emitter(list, sx, sy, theta, number);
		return list;
	}

	public static void emitter(List<Particle> list, int sx, int sy, double theta, int number) {
//		double theta = 30;
		theta = Math.toRadians(theta);
		
		Random ran = new Random();
		for (int i = 0; i < number; i++) {

			Particle ptc = new Particle(sx, sy);
			// starting speed
			int sxv = ran.nextInt(60);
			if (i % 2 == 0) {
				sxv = -sxv;
			}
			int syv = -200;
			Velocity v = new Velocity(
					sxv * Math.cos(theta) + syv * Math.sin(theta), 
					-sxv * Math.sin(theta) + syv * Math.cos(theta)
					);
			ptc.setSpeed(v);
			
			//accelerate
			sxv = ran.nextInt(3) + 1;
			if (i % 2 == 0) {
				sxv = -sxv;
			}
			syv = (ran.nextInt(30) + 0);
			Velocity a = new Velocity(
					sxv * Math.cos(theta) + syv * Math.sin(theta), 
					-sxv * Math.sin(theta) + syv * Math.cos(theta)
					);
			ptc.setAcc(a);
			//
			list.add(ptc);
		}
	}

	public static void running(List<Particle> list, int sx, int sy, double theta, double time) {
		
		int number = 30;
		emitter(list, sx, sy, theta, number);
		System.out.print(list.size() + ", ");
		List<Particle> removeList = new LinkedList<>();

		for (int i = 0; i < list.size(); i++) {

			Particle ptc = list.get(i);

			if (ptc.state) {
				ptc.displacement(time);
			} else {
				removeList.add(ptc);
			}

			if (ptc.x > WIDTH || ptc.y > HEIGHT || ptc.x < 0 || ptc.y < 0) {
				ptc.state = false;
			}
		}

		// ListIterator<Particle> it = list.listIterator();
		// while(it.hasNext()) {
		// Particle ptc = it.next();
		//
		// if(ptc.state) {
		// ptc.displacement(time);
		// }else {
		// list.remove(ptc);
		// }
		//
		// if(ptc.x > WIDTH || ptc.y > HEIGHT ||
		// ptc.x < 0 || ptc.y < 0) {
		// ptc.state = false;
		// }
		// }
		System.out.print(removeList.size() + ", ");
		for (int i = 0; i < removeList.size(); i++) {
			list.remove(removeList.get(i));
		}
		removeList.clear();
		
		System.out.println(list.size() + ", ");
	}

	//
	public static int[][][] bg_mat;
	public static int[][][] img_mat;
	public static int[][][] current_mat;
	
	public static void initIMGMat(String path) {
		img_mat = ImgFile.getMatrix(path);
		HEIGHT = img_mat.length;
		WIDTH = img_mat[0].length;
		bg_mat = new int[HEIGHT][WIDTH][3];
		current_mat = new int[HEIGHT][WIDTH][3];
		System.out.println(HEIGHT + ", " + WIDTH);
	}

	
	public static void initBGMat(String path, boolean mode) {
		if (mode) {
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
		if (mode) {
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
	
	public static void updateMat(String path, List<Particle> list, int size, boolean mode) {
		// initMat(path, mode);

		for (int i = 0; i < list.size(); i++) {
			Particle ptc = list.get(i);

			int row = (int) (ptc.y + 0.5);
			int col = (int) (ptc.x + 0.5);
			ImgFile.copyMat(img_mat, current_mat, row, col, size);
		}
	}

	public static void backward(List<Particle> list, int size) {
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

	
	public static void refresh(JFrame frame, List<Particle> list) {
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (int i = 0; i < list.size(); i++) {
					g.setColor(Color.RED);
					Particle p = list.get(i);
					if (p.state) {
						g.fillRect((int) p.x, (int) p.y, 6, 6);
					} else {

					}
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
}