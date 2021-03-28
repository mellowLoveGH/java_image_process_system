package assignment1;

public class Matrix {

	public static double[][] rotate(double theta) {

		double[][] mat = new double[3][3];

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (i == j) {
					mat[i][j] = 1;
				} else {
					mat[i][j] = 0;
				}
			}
		}
		theta = Math.toRadians(theta);
		mat[0][0] = Math.cos(theta);
		mat[0][1] = -Math.sin(theta);
		mat[1][0] = Math.sin(theta);
		mat[1][1] = Math.cos(theta);

		return mat;
	}

	public static int[][] translate(int x, int y) {
		int[][] mat = new int[3][3];

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (i == j) {
					mat[i][j] = 1;
				} else {
					mat[i][j] = 0;
				}
			}
		}

		mat[0][2] = x;
		mat[1][2] = y;

		return mat;
	}

	public static int[] Vec3D(int x, int y) {
		int[] vec = new int[3];
		vec[0] = x;
		vec[1] = y;
		vec[2] = 1;

		return vec;
	}

	public static double[] Vec3D(double x, double y) {
		double[] vec = new double[3];
		vec[0] = x;
		vec[1] = y;
		vec[2] = 1;

		return vec;
	}
	
	public static int[] MatMulVec(int[][] mat, int[] vec) {
		int[] rel = new int[3];
		for (int i = 0; i < mat.length; i++) {
			rel[i] = 0;
			for (int j = 0; j < mat[0].length; j++) {
				rel[i] += mat[i][j] * vec[j];
			}

		}

		return rel;
	}
	
	public static double[] MatMulVec(double[][] mat, double[] vec) {
		double[] rel = new double[3];
		for (int i = 0; i < mat.length; i++) {
			rel[i] = 0;
			for (int j = 0; j < mat[0].length; j++) {
				rel[i] += mat[i][j] * vec[j];
			}

		}

		return rel;
	}
	

	public static void printVec(int[] vec) {
		for (int i = 0; i < vec.length; i++) {
			System.out.println(vec[i]);
		}
	}
	
	public static void printVec(double[] vec) {
		for (int i = 0; i < vec.length; i++) {
			System.out.println(vec[i]);
		}
	}

	public static void printMat(int[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
