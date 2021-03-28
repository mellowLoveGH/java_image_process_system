package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class InverseFFT {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		String path01 = "images/fft01.png";
		String path02 = "images/fft02.png";
		
		ImgFrame.drawImg(path01);
		inverseFFT(path01, path02);
		ImgFrame.drawImg(path02);
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public static ComNum[] ee = null;

	public static void initialEE(int N) {
		ee = new ComNum[N];
		int half = N / 2;

		for (int i = 0; i < half; i++) {
			double angle = 2 * Math.PI * i / N;
			ee[i] = new ComNum(Math.cos(angle), -Math.sin(angle));
		}

		for (int i = half; i < N; i++) {
			ComNum mirror = ee[i - half];
			ee[i] = new ComNum(-mirror.getReal(), -mirror.getImag());
		}
	}

	public static ComNum getEE(long index) {

		if (index < 0) {
			System.out.println("index smaller than 0: " + index);
		}

		int mod = ee.length;
		index = index % mod;

		return ee[(int) index];
	}

	public static ComNum forwardOne(ComNum[] list, int u) {
		int n = list.length;
		double rSum = 0.0;
		double iSum = 0.0;
		for (int x = 0; x < n; x++) {
			long index = (long) u * (long) x;
			ComNum e = getEE(index);
			ComNum aa = list[x].times(e);

			rSum = rSum + aa.getReal();
			iSum = iSum + aa.getImag();
		}
		// System.out.println("one fourier: " + rSum + ", " + iSum);
		return new ComNum(rSum / n, iSum / n);
	}

	public static ComNum[] ft(ComNum[] list) {
		//
		int n = list.length;
		ComNum[] fourier = new ComNum[n];
		for (int u = 0; u < n; u++) {
			fourier[u] = forwardOne(list, u);
		}

		return fourier;
	}

	//
	public static ComNum backwardOne(ComNum[] fourier, int x) {
		int n = fourier.length;
		double rSum = 0.0;
		double iSum = 0.0;
		for (int u = 0; u < n; u++) {
			long index = (long) u * (long) x;

			//
			ComNum e = getEE(index);
			ComNum ie = new ComNum(e.getReal(), -e.getImag());

			//
			ComNum aa = fourier[u].times(ie);
			rSum = rSum + aa.getReal();
			iSum = iSum + aa.getImag();
		}

		return new ComNum(rSum, iSum);
	}

	public static ComNum[] inverseft(ComNum[] fourier) {
		int n = fourier.length;
		ComNum[] list = new ComNum[n];

		for (int i = 0; i < n; i++) {
			list[i] = backwardOne(fourier, i);
		}

		return list;
	}

	public static void inverseFFT(String path01, String path02) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(path01));

		} catch (Exception e) {
			// TODO: handle exception
		}
		int w = img.getWidth();
		int h = img.getHeight();

		int n = w * h;

		initialEE(n);

		ComNum[] list = new ComNum[n];
		int number = 0;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color c = new Color(img.getRGB(i, j));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();
				int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);

				list[number] = new ComNum(gray, 0);
				number++;
			}
		}

		System.out.println("fast fourier transform");
		ComNum[] fourier = ft(list);

		System.out.println("inverse fourier transform");
		list = inverseft(fourier);

		BufferedImage bi3 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		number = 0;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				ComNum aa = list[number];
				int value = (int) aa.getReal();
				Color c = new Color(value, value, value);
				bi3.setRGB(i, j, c.getRGB());
				number++;
			}
		}

		try {
			File ouptut = new File(path02);
			ImageIO.write(bi3, "jpg", ouptut);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
