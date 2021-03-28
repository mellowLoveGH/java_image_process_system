package animationDIP;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MassNet {
	
	public static void main(String[] args) {
		
//		List<Mass> list = initMass();
//		JFrame frame = drawFrame(list);
//		while(true) {
//			int time = 30;
//			running(list, (double)time / 1000);
//			try {
//				Thread.sleep(time);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			refresh(frame, list);
//		}
		
		double v1 = 18;
		double dis = 0;
		double v2 = 0;
		double k = 1;
		double m = 4;
		
		double delta = 0;
		double[] ta1 = moveToElastic(v1, m, k);
		int time1 = Math.abs((int)ta1[0]);
		double a1 = ta1[1];
		System.out.println(time1);
		
		System.out.println(a1);
		for (int i = 0; i < time1; i++) {
			v2 = v1 + a1;
			delta = 0.5 * (v1 + v2) * 1;
			dis = dis + delta;
			System.out.println(delta);
			v1 = v2;
		}
		System.out.println("whole distance: " + dis);
		
		double[] ta2 = elasticToMove(dis, k, m);
		int time2 = Math.abs((int)ta2[0]);
		double a2 = ta2[1];
		System.out.println(a2);
		for (int i = 0; i < time2; i++) {
			v2 = v1 + a2;
			delta = 0.5 * (v1 + v2) * 1;
			dis = dis - delta;
			System.out.println(delta);
			v1 = v2;
		}
		System.out.println("whole distance: " + dis);
		
	}
	
	public static double[] moveToElastic(double v, double m, double k) {
		double move_energy = 0.5 * m * v * v;
		double delta2 = move_energy / (0.5 * k);
		double dis = Math.sqrt(delta2);
		double[] ta = new double[2];
		ta[0] = dis * 2 / v;
		ta[1] = -v / ta[0];
		
		return ta;
	}
		
	public static double[] elasticToMove(double dis, double k, double m) {
		double elastic_energy = 0.5 * k * dis * dis;
		double v2 = elastic_energy / (0.5 * m);
		double v = Math.sqrt(v2);
		
		double[] ta = new double[2];
		ta[0] = dis * 2 / v;
		ta[1] = v / ta[0];
		
		return ta;
	}
	
	
	
	
	public static List<Mass> initMass() {
		List<Mass> list = new LinkedList<>();
		Mass[] m = new Mass[5];
		
		m[0] = new Mass(300, 300);
		m[1] = new Mass(240, 360);
		m[2] = new Mass(300, 360);
		m[3] = new Mass(360, 360);
		m[4] = new Mass(300, 420);
		
		m[0].color = new Color(255, 0, 0);
		m[1].color = new Color(0, 255, 0);
		m[2].color = new Color(0, 0, 255);
		m[3].color = new Color(255, 255, 0);
		m[4].color = new Color(255, 0, 255);
		
		//
		m[0].addCM(m[2]);
		
		m[1].addCM(m[2]);
		
		m[2].addCM(m[0]);
		m[2].addCM(m[1]);
		m[2].addCM(m[3]);
		m[2].addCM(m[4]);
		
		m[3].addCM(m[2]);
		
		m[4].addCM(m[2]);
		
		m[4].v.xv = 10;
		m[2].v.yv = 10;
//		m[5].addCM(m[2]);
//		m[5].addCM(m[4]);
//		m[5].addCM(m[8]);
//		
//		m[6].addCM(m[3]);
//		m[6].addCM(m[7]);
//		
//		m[7].addCM(m[4]);
//		m[7].addCM(m[6]);
//		m[7].addCM(m[8]);
//		
//		m[8].addCM(m[5]);
//		m[8].addCM(m[7]);
//		
		
		
		for (int i = 0; i < m.length; i++) {
			list.add(m[i]);
		}
		return list;
	}
	
	public static void running(List<Mass> list, double time) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).displacement(time);
//			System.out.println(list.get(i));
		}
	}
	
	public static JFrame drawFrame(List<Mass> list) {
		JFrame frame = new JFrame("welcome");
		frame.setSize(800, 800);
		frame.setBackground(Color.WHITE);
		frame.setLocation(600, 200);

		refresh(frame, list);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	public static void refresh(JFrame frame, List<Mass> list) {
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
//				g.setColor(Color.RED);
				
				for (int i = 0; i < list.size(); i++) {
					Mass ptc = list.get(i);
					g.setColor(ptc.color);
					g.fillRect((int)ptc.x, (int)ptc.y, 5, 5);
				}
				
			}
		};
		panel.setBounds(0, 0, 800, 800);
		frame.setContentPane(panel);
		frame.repaint();
	}
	
}

class Mass{
	
	public Velocity v;
	public Velocity a;
	public Color color;
	
	public double x;
	public double y;
	public int ma = 10;
	public double cx = 0;
	public double cy = 0;
	public int size = 30;
	
	public List<Mass> connects;
	
	public Mass(double sx, double sy) {
		x = sx;
		y = sy;
		connects = new LinkedList<>();
		v = new Velocity(0, 0);
		a = new Velocity(0, 0);
		cx = sx;
		cy = sy;
	}
	
	public void addCM(Mass m) {
		connects.add(m);
	}
	
	public void updateAcc() {
		for (int i = 0; i < connects.size(); i++) {
			Velocity tmp = Spring.twoMass(this, connects.get(i));
			a.xv = a.xv + tmp.xv;
			a.yv = a.yv + tmp.yv;
		}
	}
	
	public void displacement(double time) {
		double sxv = v.xv;
		double syv = v.yv;
		//
		updateAcc();
//		System.out.println(this);
		v.operation(a, time);
		//
		double exv = v.xv;
		double eyv = v.yv;
		//
		double xd = (sxv + exv) * time / 2;
		double yd = (syv + eyv) * time / 2;
		
		//
//		double dis = xd * xd + yd * yd;
//		if(dis < 100) {
		x = x + xd/1;
		y = y + yd/1;
//		}else {
//			v.xv = 0;
//			v.yv = 0;
//			a.xv = 0;
//			a.yv = 0;
//		}
		double dis = (x - cx) * (x - cx) + (y - cy) * (y - cy);
		if(dis < size * size) {
			
		}else {
			v.xv = 0;
			v.yv = 0;
//			a.xv = 0;
//			a.yv = 0;
			x = x - xd/1;
			y = y - yd/1;
		}
		
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String vstr = v.xv + ", " + v.yv;
//		updateAcc();
		String astr = a.xv + ", " + a.yv;
		
		return vstr + "\t" + astr;
	}
}


class Spring{
	public static int springLen = 60;
	public static int springK = 10;	
	
	public static Velocity twoMass(Mass m1, Mass m2) {
		Velocity acc = null;
		//
		double dx = m2.x - m1.x;
		double dy = (m2.y - m1.y);
		
		double dis = Math.sqrt(dx * dx + dy * dy);
//		System.out.println(m1.x + ", " + m1.y + " " + m2.x + ", " + m2.y + " distance:" + dis);
		if(dis - springLen > 0.1) {
			double F = (dis - springLen) * springK;
			double a = F / m1.ma;
			double sxv = a * (dx / dis);
			double syv = a * (dy / dis);
			
			sxv = (double)((int)(sxv * 1000)) / 1000;
			syv = (double)((int)(syv * 1000)) / 1000;
			acc = new Velocity(sxv, syv);
		}else if(dis - springLen < -0.1) {
			double F = (dis - springLen) * springK;
			double a = F / m1.ma;
			double sxv = a * (dx / dis);
			double syv = a * (dy / dis);
			
			sxv = (double)((int)(sxv * 1000)) / 1000;
			syv = (double)((int)(syv * 1000)) / 1000;
			acc = new Velocity(sxv, syv);			
		}else {
			acc = new Velocity(0, 0);
		}
		
		return acc;
	}
	
}
