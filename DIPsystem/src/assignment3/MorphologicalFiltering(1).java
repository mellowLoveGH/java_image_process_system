package assignment3;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class MorphologicalFiltering {

	public static void main(String[] args) {

		String path01 = "images/mf.jpg";
		String path02 = "images/tester.jpg";

		int threshold = 3;
		// open(path01, path02, threshold);
		close(path01, path02, threshold);
	}

	public static void open(String path01, String path02, int threshold) {
		//������
		//ͼ���ֵ��
		binaryImage(path01, path02);
		//��ʾԭͼ��
		ImgFrame.drawImg(path02);
		//��ͼ�񵽶�ά����
		int[][] img = imageToArray(path02);
		//��ʴ
		img = correde(img, threshold);
		//����
		img = dilate(img, threshold);
		//ͼ��ҶȻ�
		arrayToGreyImage(img, path02);
		//��ʾ���������ͼ��
		ImgFrame.drawImg(path02);
	}

	public static void close(String path01, String path02, int threshold) {
		// �ղ���
		// ͼ���ֵ��
		binaryImage(path01, path02);
		// ��ʾԭͼ��
		ImgFrame.drawImg(path02);
		// ��ͼ�񵽶�ά����
		int[][] img = imageToArray(path02);
		// ����
		img = dilate(img, threshold);
		// ��ʴ
		img = correde(img, threshold);
		// �ҶȻ�
		arrayToGreyImage(img, path02);
		// ��ʾ�ղ������ͼ��
		ImgFrame.drawImg(path02);
	}

	//
	// �ṹԪ��
	private static int sData[] = { 0, 0, 0, 0, 1, 0, 0, 1, 1 };

	//
	public static void arrayToGreyImage(int[][] sourceArray, String path) {
		int width = sourceArray[0].length;
		int height = sourceArray.length;
		BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int greyRGB = sourceArray[j][i];
				int rgb = (greyRGB << 16) | (greyRGB << 8) | greyRGB;

				targetImage.setRGB(i, j, rgb);
			}
		}

		try {
			ImageIO.write(targetImage, "jpg", new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//
	public static int[][] imageToArray(String path) {

		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(path));
		} catch (Exception e) {
			// TODO: handle exception
		}

		int width = image.getWidth();
		int height = image.getHeight();

		int[][] result = new int[height][width];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int rgb = image.getRGB(i, j);
				int grey = (rgb >> 16) & 0xFF;
				// System.out.println(grey);
				result[j][i] = grey;
			}
		}
		return result;
	}

	//
	private static int[][] dilate(int[][] source, int threshold) {
		int width = source[0].length;
		int height = source.length;

		int[][] result = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				/// ��Ե�����в���
				if (i > 0 && j > 0 && i < height - 1 && j < width - 1) {
					int max = 0;

					/// �ԽṹԪ�ؽ��б���
					for (int k = 0; k < sData.length; k++) {
						int x = k / 3;/// �̱�ʾxƫ����
						int y = k % 3;/// ������ʾyƫ����

						if (sData[k] != 0) {
							/// ���ṹԪ���в�Ϊ0ʱ,ȡ��ͼ���ж�Ӧ��������ֵ����ͼ��ǰλ����Ϊ�Ҷ�ֵ
							if (source[i - 1 + x][j - 1 + y] > max) {
								max = source[i - 1 + x][j - 1 + y];
							}
						}
					}

					//// �˴�����������ֵ����maxС����ֵ��ʱ��͸�Ϊ0
					if (max < threshold) {
						result[i][j] = 0;
					} else {
						result[i][j] = max;
					}
					// result[i][j]=max;

				} else {
					/// ֱ�Ӹ�ֵ
					result[i][j] = source[i][j];
				}

			}
		}

		return result;
	}

	//
	private static int[][] correde(int[][] source, int threshold) {
		int width = source[0].length;
		int height = source.length;

		int[][] result = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				/// ��Ե�����в�������Ե�ڲŲ���
				if (i > 0 && j > 0 && i < height - 1 && j < width - 1) {
					int max = 0;

					/// �ԽṹԪ�ؽ��б���
					for (int k = 0; k < sData.length; k++) {
						int x = k / 3;/// �̱�ʾxƫ����
						int y = k % 3;/// ������ʾyƫ����

						if (sData[k] != 0) {
							/// ��Ϊ0ʱ������ȫ��������ֵ�����������Ϊ0����������
							if (source[i - 1 + x][j - 1 + y] >= threshold) {
								if (source[i - 1 + x][j - 1 + y] > max) {
									max = source[i - 1 + x][j - 1 + y];
								}
							} else {
								//// ��ṹԪ�ز�ƥ��,��ֵ0,��������
								max = 0;
								break;
							}
						}
					}

					//// �˴�����������ֵ����maxС����ֵ��ʱ��͸�Ϊ0
					result[i][j] = max;

				} else {
					/// ֱ�Ӹ�ֵ
					result[i][j] = source[i][j];

				} /// end of the most out if-else clause .

			}
		} /// end of outer for clause

		return result;
	}

	//
	public static void binaryImage(String path01, String path02) {
		File file = new File(path01);
		BufferedImage image = null;

		try {
			image = ImageIO.read(file);
		} catch (Exception e) {
			// TODO: handle exception
		}

		int width = image.getWidth();
		int height = image.getHeight();

		// �ص㣬�������������BufferedImage.TYPE_BYTE_BINARY
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}

		File out = new File(path02);
		try {
			ImageIO.write(grayImage, "jpg", out);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
