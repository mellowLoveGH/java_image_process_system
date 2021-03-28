package assignment3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class SobelEdgeDetect {

	public static void main(String[] args) {

		SobelEdgeDetect test = new SobelEdgeDetect(50);// 100
		String path01 = "images/mf.png";
		
		String path = "images/tester.jpg";
		
		try {
			test.readImage(path01);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImgFrame.drawImg(path01);
		test.createEdgeImage(path);
		ImgFrame.drawImg(path);
	}

	public void createEdgeImage(String desImageName) {

		float[] gradient = gradientM();// ����ͼ������ص���ݶ�ֵ
		float maxGradient = gradient[0];
		for (int i = 1; i < gradient.length; ++i)
			if (gradient[i] > maxGradient)
				maxGradient = gradient[i];// ��ȡ�ݶ����ֵ
		float scaleFactor = 255.0f / maxGradient;// �����������ڵ����ݶȴ�С
		if (gradientThreshold >= 0) {
			for (int y = 1; y < height - 1; ++y)
				for (int x = 1; x < width - 1; ++x)
					if (Math.round(scaleFactor * gradient[y * width + x]) >= gradientThreshold)
						outBinary.setRGB(x, y, 0xffffff);// ��ɫ
		} // ���ݶȴ�С������ֵ����
		else {
			for (int y = 1; y < height - 1; ++y)
				for (int x = 1; x < width - 1; ++x)
					outBinary.setRGB(x, y, 0x000000);// ��ɫ;
		} // //�����ݶȴ�С������ֵ����, ֱ�Ӹ����ñ������ӵ������ֵ

		writeImage(outBinary, desImageName);
	}
	
	
	int width;// ͼ���
	int height;// ͼ���
	int[] grayData;// ͼ��Ҷ�ֵ
	int size; // ͼ���С
	int gradientThreshold = -1;// �ж�ʱ�õ�����ֵ
	BufferedImage outBinary;// ����ı�Եͼ��

	public SobelEdgeDetect(int threshold) {
		gradientThreshold = threshold;
	}

	public void readImage(String imageName) throws IOException {
		File imageFile = new File(imageName);
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		width = bufferedImage.getWidth();
		height = bufferedImage.getHeight();
		size = width * height;
		int imageData[] = bufferedImage.getRGB(0, 0, width, height, null, 0, width);// ������ͼ���RGBֵ

		outBinary = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// ���ɱ�Եͼ��
		grayData = new int[width * height];// �����ڴ�ռ�

		for (int i = 0; i < imageData.length; i++) {
			grayData[i] = (imageData[i] & 0xff0000) >> 16;// ���ڶ����ǻҶ�ͼ����ֻ����һ��������������ֵ��ͬ��
		}
	}

	

	// ���Ӽ��� ͼ��ÿ�����ص� �� �ݶȴ�С
	protected float[] gradientM() {
		float[] mag = new float[size];

		int gx, gy;
		for (int y = 1; y < height - 1; ++y)
			for (int x = 1; x < width - 1; ++x) {
				gx = GradientX(x, y);
				gy = GradientY(x, y);
				// �ù�ʽ g=|gx|+|gy|����ͼ��ÿ�����ص���ݶȴ�С.ԭ���Ǳ���ƽ���Ϳ����ķѴ���ʱ��
				mag[y * width + x] = (float) (Math.abs(gx) + Math.abs(gy));
			}
		return mag;
	}

	// �õ���(x,y)���ĻҶ�ֵ
	public int getGrayPoint(int x, int y) {
		return grayData[y * width + x];

	}

	// ���� ���� ��(x,y)����x�����ݶȴ�С
	protected final int GradientX(int x, int y) {
		return getGrayPoint(x - 1, y - 1) + 2 * getGrayPoint(x - 1, y) + getGrayPoint(x - 1, y + 1)
				- getGrayPoint(x + 1, y - 1) - 2 * getGrayPoint(x + 1, y) - getGrayPoint(x + 1, y + 1);
	}// �������ص�(x,y)X�����ϵ��ݶ�ֵ

	// ���� ���� ��(x,y)����y�����ݶȴ�С
	protected final int GradientY(int x, int y) {
		return getGrayPoint(x - 1, y - 1) + 2 * getGrayPoint(x, y - 1) + getGrayPoint(x + 1, y - 1)
				- getGrayPoint(x - 1, y + 1) - 2 * getGrayPoint(x, y + 1) - getGrayPoint(x + 1, y + 1);
	}// �������ص�(x,y)Y�����ϵ��ݶ�ֵ

	public void writeImage(BufferedImage bi, String imageName) {
		File skinImageOut = new File(imageName);
		try {
			ImageIO.write(bi, "jpg", skinImageOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
