package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import assignment1.ImgFrame;

public class FFTransform {
	
	public static void main(String[] args) {
		String path01 = "images/fft01.png";
		String path02 = "images/tester.jpg";
		
		ImgFrame.drawImg(path01);
		imgFFT(path01, path02);
		ImgFrame.drawImg(path02);
	}
	
	public static ComNum[] imgFFT(String path01, String path02) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(path01));

		} catch (Exception e) {
			// TODO: handle exception
		}
		int w = img.getWidth();
		int h = img.getHeight();

		int n = w * h;
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
		
		System.out.println("read image data");
		
		BufferedImage bi3 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		ComNum[] fourier = fft(list);
		System.out.println("fast fourier transform");
		
		int px = 0;
		int py = 0;
		
		number = 0;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				
				ComNum aa = fourier[number];
				aa.setReal(aa.getReal() * fourier.length);
				aa.setImag(aa.getImag() * fourier.length);
				
				int value = (int)aa.magnitude();
				if(value > 255) {
					value = 255;
				}
				Color c = new Color(value, value, value);
				
				if(i < w/2) {
					px = i + w/2;
				}else {
					px = i - w/2;
				}
				
				if(j < w/2) {
					py = j + w/2;
				}else{
					py = j - w/2;
				}
				
				bi3.setRGB(px, py, c.getRGB());
				
				number++;
			}
		}
		
		
		try {
			File ouptut = new File(path02);
			ImageIO.write(bi3, "jpg", ouptut);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return fourier;
	}
	
	
	public static void inverseFFT(ComNum[] fourier, String path) {
		
		
	}
	

	public static ComNum[] ee = null;
	public static ComNum[] arr = null;

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

		int mod = ee.length;
		index = index % mod;

		return ee[(int) index];
	}

	public static void initialArr(ComNum[] list) {
		int N = list.length;
		arr = new ComNum[N];
		for (int i = 0; i < N; i++) {
			arr[i] = list[i];
		}
	}

	public static ComNum[] fft(ComNum[] list) {
		int N = list.length;
		initialArr(list);
		initialEE(N);

		//
		ComNum[] F = new ComNum[N];

		for (int u = 0; u < N; u++) {
			ComNum aa = forward(u);
			F[u] = new ComNum(aa.getReal() / N, aa.getImag() / N);
//			F[u] = new ComNum(aa.getReal(), aa.getImag());
		}

		return F;
	}

	public static ComNum forward(int u) {

		//
		int N = arr.length;
		int num = (int) (Math.log(N) / Math.log(2));
		ComNum[] F = null;

		for (int i = 0; i < num; i++) {
			int mul = N / power(i + 1);
			if (i == 0) {
				F = butterfly(arr, u, mul);
			} else {
				F = butterfly(F, u, mul);
			}
		}

		return F[0];
	}

	public static ComNum[] butterfly(ComNum[] from, int u, int mul) {

		long index = (long) u * mul;

		int n1 = from.length;
		int n2 = n1 / 2;
		ComNum[] to = new ComNum[n2];

		for (int i = 0; i < n2; i++) {
			int fe = i;
			int fo = i + n2;
			ComNum e = getEE(index);
			to[i] = from[fe].add(from[fo].times(e));
		}

		return to;
	}

	public static int power(int num) {

		int n = 1;
		for (int i = 0; i < num; i++) {
			n = 2 * n;
		}
		return n;
	}

	public static void print(ComNum[] F) {
		for (int i = 0; i < F.length; i++) {
			System.out.println(F[i]);
		}
	}

}
