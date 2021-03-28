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
		//开操作
		//图像二值化
		binaryImage(path01, path02);
		//显示原图像
		ImgFrame.drawImg(path02);
		//读图像到二维矩阵
		int[][] img = imageToArray(path02);
		//腐蚀
		img = correde(img, threshold);
		//膨胀
		img = dilate(img, threshold);
		//图像灰度化
		arrayToGreyImage(img, path02);
		//显示开操作后的图像
		ImgFrame.drawImg(path02);
	}

	public static void close(String path01, String path02, int threshold) {
		// 闭操作
		// 图像二值化
		binaryImage(path01, path02);
		// 显示原图像
		ImgFrame.drawImg(path02);
		// 读图像到二维矩阵
		int[][] img = imageToArray(path02);
		// 膨胀
		img = dilate(img, threshold);
		// 腐蚀
		img = correde(img, threshold);
		// 灰度化
		arrayToGreyImage(img, path02);
		// 显示闭操作后的图像
		ImgFrame.drawImg(path02);
	}

	//
	// 结构元素
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
				/// 边缘不进行操作
				if (i > 0 && j > 0 && i < height - 1 && j < width - 1) {
					int max = 0;

					/// 对结构元素进行遍历
					for (int k = 0; k < sData.length; k++) {
						int x = k / 3;/// 商表示x偏移量
						int y = k % 3;/// 余数表示y偏移量

						if (sData[k] != 0) {
							/// 当结构元素中不为0时,取出图像中对应各项的最大值赋给图像当前位置作为灰度值
							if (source[i - 1 + x][j - 1 + y] > max) {
								max = source[i - 1 + x][j - 1 + y];
							}
						}
					}

					//// 此处可以设置阈值，当max小于阈值的时候就赋为0
					if (max < threshold) {
						result[i][j] = 0;
					} else {
						result[i][j] = max;
					}
					// result[i][j]=max;

				} else {
					/// 直接赋值
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
				/// 边缘不进行操作，边缘内才操作
				if (i > 0 && j > 0 && i < height - 1 && j < width - 1) {
					int max = 0;

					/// 对结构元素进行遍历
					for (int k = 0; k < sData.length; k++) {
						int x = k / 3;/// 商表示x偏移量
						int y = k % 3;/// 余数表示y偏移量

						if (sData[k] != 0) {
							/// 不为0时，必须全部大于阈值，否则就设置为0并结束遍历
							if (source[i - 1 + x][j - 1 + y] >= threshold) {
								if (source[i - 1 + x][j - 1 + y] > max) {
									max = source[i - 1 + x][j - 1 + y];
								}
							} else {
								//// 与结构元素不匹配,赋值0,结束遍历
								max = 0;
								break;
							}
						}
					}

					//// 此处可以设置阈值，当max小于阈值的时候就赋为0
					result[i][j] = max;

				} else {
					/// 直接赋值
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

		// 重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY
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
