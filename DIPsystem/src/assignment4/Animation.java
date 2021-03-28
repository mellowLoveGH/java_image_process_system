package assignment4;

import javax.swing.JFrame;

import assignment1.ImgAnd;
import assignment1.ImgFrame;
import assignment1.ImgRotate;
import assignment1.ImgScale;
import assignment3.ImgHistogramUpdate;
import assignment3.MedianFiltering;

public class Animation {
	
	public static void main(String[] args) {
		
		String path02 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy";
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		JFrame frame = ImgFrame.CFrame();
		
		for (int i = 3; i < 116; i++) {
			
			String path = path02 + i + ".jpg";
			ImgFrame.setImg(frame, path);
			
		}
		
		ImgFrame.setImg(frame, path02 + ".jpg");
		try {
			Thread.sleep(60);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String path = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy1000.jpg";
		String path01 = "C:\\Users\\13418\\Desktop\\新建文件夹\\discussion\\img\\cy\\cy.jpg";
//		ImgHistogramUpdate.equalisation(path, path01);
		//ImgFrame.setImg(frame, path01);
//		for (int i = 0; i < 10; i++) {
//			try {
//				Thread.sleep(60);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			ImgHistogramUpdate.equalisation(path, path);
//			ImgFrame.setImg(frame, path);
//		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		ImgHistogramUpdate.equalisation(path02 + ".jpg", path);
		ImgFrame.setImg(frame, path01);
		
	}
	
}
