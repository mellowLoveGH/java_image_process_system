package assignment1;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//this class is to show image on frame
//java swing framework
public class ImgFrame {
	
	public static void main(String[] args) {
		//test
		String path = "images/animation.bmp";
		drawImg(path);
//		CFrame() ;
	}
	
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	
	public static JFrame CFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("image");
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true); 
		JLabel label = new JLabel();
		frame.getContentPane().add(label);
//		System.out.println(frame.getContentPane().getComponentCount());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	public static JLabel getLabel(JFrame frame) {
		
		return (JLabel)frame.getContentPane().getComponent(0);
	}
	
	public static Image readImg(String path) {
		BufferedImage image = null;
		Image img = null;
		try {
			image = ImageIO.read(new File(path));
			int w = image.getWidth();
			int h = image.getHeight();
			double wr = 1;
			double hr = 1;
			if(w > h) {
				hr = (double)h/w;
			}else {
				wr = (double)w/h;
			}
			
			if(w < WIDTH && h < HEIGHT) {
				img = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			}else {
				img = image.getScaledInstance((int)(WIDTH*wr), (int)(HEIGHT*hr), Image.SCALE_SMOOTH);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return img;
	}
	
	public static void setImg(JFrame frame, String path) {
		
		Image img = readImg(path);
		JLabel label = getLabel(frame);
		label.setIcon(new ImageIcon(img));
		frame.repaint();
		
	}
	
	
	
	public static void drawImg(String path) {		
		//创建JFrame窗口
		JFrame frame = new JFrame();
		frame.setTitle("image");
		frame.setSize(WIDTH, HEIGHT);//设置长和宽
		
		//根据路径名读取图片
		BufferedImage image = null;
		Image img = null;
		try {
			image = ImageIO.read(new File(path));
			int w = image.getWidth();
			int h = image.getHeight();
			double wr = 1;
			double hr = 1;
			//获得长宽比
			if(w > h) {
				hr = (double)h/w;
			}else {
				wr = (double)w/h;
			}
			
			//如果图片的长宽比窗口的长宽大的话，进行等比放缩
			if(w < WIDTH && h < HEIGHT) {
				img = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			}else {
				img = image.getScaledInstance((int)(WIDTH*wr), (int)(HEIGHT*hr), Image.SCALE_SMOOTH);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//图片放在容器JLabel中
		JLabel label = new JLabel(new ImageIcon(img));
		
		frame.getContentPane().add(label);
		frame.setVisible(true); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

		
}

