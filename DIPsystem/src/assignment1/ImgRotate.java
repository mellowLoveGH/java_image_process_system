package assignment1;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class ImgRotate {
	
	public static void main(String[] args) {
		
		String path01 = "images/img02.jpg";
		String path02 = "images/tester.jpg";
		
		ImgFrame.drawImg(path01);
		rotateImg(path01, path02, 30);
		ImgFrame.drawImg(path02);
		
	}
	
	public static void rotateImg(String path01, String path, double theta) {
		
		try {
			BufferedImage image = ImageIO.read(new File(path01));
			int width = image.getWidth(null);
		    int height = image.getHeight(null);

		    double x0 = 0.5 * (width - 1);     // point to rotate about
		    double y0 = 0.5 * (height - 1);     // center of image
		    double[][] mat = Matrix.rotate(theta);
		    
		    //
		    WritableRaster inRaster = image.getRaster();
		    BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    WritableRaster outRaster = pic2.getRaster();
		    int[] pixel = new int[3];

		    // rotation
		    for (int x = 0; x < width; x++) {
		        for (int y = 0; y < height; y++) {
		            double a = x - x0;
		            double b = y - y0;
		            double[] vec = Matrix.Vec3D(a, b);
		            vec = Matrix.MatMulVec(mat, vec);
		            vec[0] = vec[0] + x0;
		            vec[1] = vec[1] + y0;
		            
		            int xx = (int) vec[0];
		            int yy = (int) vec[1];

		            if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
		                outRaster.setPixel(x, y, inRaster.getPixel(xx, yy, pixel));
		            }
		        }
		    }
		    ImageIO.write(pic2, "jpg", new File(path));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void rotateImg2(String path01, String path, double theta) {
		
		try {
			BufferedImage image = ImageIO.read(new File(path01));
			int width = image.getWidth(null);
		    int height = image.getHeight(null);

		    double[][] mat = Matrix.rotate(theta);
		    
		    //
		    WritableRaster inRaster = image.getRaster();
		    BufferedImage pic2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    WritableRaster outRaster = pic2.getRaster();
		    int[] pixel = new int[3];

		    // rotation
		    for (int x = 0; x < width; x++) {
		        for (int y = 0; y < height; y++) {
		            double[] vec = Matrix.Vec3D((double)x, (double)y);
		            vec = Matrix.MatMulVec(mat, vec);
		            
		            int xx = (int) vec[0];
		            int yy = (int) vec[1];

		            if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
		                outRaster.setPixel(x, y, inRaster.getPixel(xx, yy, pixel));
		            }
		        }
		    }
		    ImageIO.write(pic2, "jpg", new File(path));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
		
}
