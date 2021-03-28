package assignment1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class BMPReader {

	public static void main(String[] args) {
		String path = "images/animation.bmp";

		drawBMPImg(path);
	}

	public static void drawBMPImg(String path) {
		int[][] date = readFile(path);
		
		int w = date.length;
		int h = date[0].length;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		// �ж��Ƿ����
		if (date != null) {
			// ����
			for (int i = 0; i < date.length; i++) {
				for (int j = 0; j < date[i].length; j++) {
					Color c = new Color(date[i][j]);
					img.setRGB(i, j, c.getRGB());
				}
			}
			
			try {
				File ouptut = new File(path);
				ImageIO.write(img, "bmp", ouptut);
				
				ImgFrame.drawImg(path);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static int[][] readFile(String path) {
		try {
			// ������ȡ�ļ����ֽ���
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			// ��ȡʱ����ǰ���18λ��
			// ��ȡͼƬ��18~21�Ŀ��
			bis.skip(18);
			byte[] b = new byte[4];
			bis.read(b);
			// ��ȡͼƬ�ĸ߶�22~25
			byte[] b2 = new byte[4];
			bis.read(b2);

			// �õ�ͼƬ�ĸ߶ȺͿ��
			int width = byte2Int(b);
			int heigth = byte2Int(b2);
			// ʹ�����鱣���ͼƬ�ĸ߶ȺͿ��
			int[][] date = new int[heigth][width];

			int skipnum = 0;
			if (width * 3 / 4 != 0) {
				skipnum = 4 - width * 3 % 4;
			}
			// ��ȡλͼ�е����ݣ�λͼ������ʱ��54λ��ʼ�ģ��ڶ�ȡ����ǰҪ����ǰ�������
			bis.skip(28);
			for (int i = 0; i < date.length; i++) {
				for (int j = 0; j < date[i].length; j++) {
					// bmp��ͼƬ��window������3��byteΪһ������
					int blue = bis.read();
					int green = bis.read();
					int red = bis.read();
					// ����һ��Color���󣬽�rgb��Ϊ������������
					Color c = new Color(red, green, blue);
					// Color c = new Color(blue,green,red);
					// ���õ������ر��浽date������
					date[i][j] = c.getRGB();
				}
				// �����0�ĸ�����Ϊ0������Ҫ������Щ���ϵ�0
				if (skipnum != 0) {
					bis.skip(skipnum);
				}
			}

			bis.close();
			return date;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	// ���ĸ�byteƴ�ӳ�һ��int
	public static int byte2Int(byte[] by) {
		int t1 = by[3] & 0xff;
		int t2 = by[2] & 0xff;
		int t3 = by[1] & 0xff;
		int t4 = by[0] & 0xff;
		int num = t1 << 24 | t2 << 16 | t3 << 8 | t4;
		return num;
	}
}
