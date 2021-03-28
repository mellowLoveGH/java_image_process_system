package assignment1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgTranslate {
	
	public static void main(String[] args) {
		
		String path01 = "images/img02.jpg";
		String path02 = "images/tester.jpg";
		
		int x = 100;
		int y = 60;
		
		ImgFrame.drawImg(path01);
		translateImg(path01, path02, x, y);
		ImgFrame.drawImg(path02);
	}

	public static void translateImg(String path01, String path, int x, int y) {
		
		//���x���y��ƽ�����ı任����
		int[][] mat = Matrix.translate(x, y);

		try {
			//��ͼ��
			File input = new File(path01);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();
			//����ƽ�ƺ��ͼ���BufferedImage
			BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					//ͼ�������ƽ�ƾ������
					Color c = new Color(image.getRGB(j, i));
					int[] vec = Matrix.MatMulVec(mat, Matrix.Vec3D(j, i));
					bi3.setRGB(vec[0] % width, vec[1] % height, c.getRGB());
				}
			}
			//д���µ�ͼ���ļ�
			File ouptut = new File(path);
			ImageIO.write(bi3, "jpg", ouptut);

		} catch (Exception e) {
		}
	}

	public static void translateImg2(String path01, String path, int x, int y) {
		int[][] mat = Matrix.translate(x, y);

		try {
			File input = new File(path01);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

			BufferedImage bi3 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					Color c = new Color(image.getRGB(j, i));
					int[] vec = Matrix.MatMulVec(mat, Matrix.Vec3D(j, i));
					if(vec[0] >= width || vec[1] >= height) {
						c = new Color(255, 255, 255);
					}
					bi3.setRGB(vec[0] % width, vec[1] % height, c.getRGB());
				}
			}

			File ouptut = new File(path);
			ImageIO.write(bi3, "jpg", ouptut);

		} catch (Exception e) {
		}
	}
}
