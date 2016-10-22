import java.io.*;
import java.util.*;
import Jama.*;

public class JamaNMF {
	public class NonnegativeMatrixFactorization {
		private Matrix V;
		private Matrix W;
		private Matrix H;
		private int pc = 3;
		private int iter = 50;

		public void println(Matrix M) {
			for (double[] d : M.getArray()) {
				for (double v : d)
					System.out.printf("%12f\t", v);
				System.out.println();
			}
		}

		public double difcost(Matrix V, Matrix WH) {
			double dif = 0.0;
			for (int i = 0; i != V.getRowDimension(); i++) {
				for (int j = 0; j != V.getColumnDimension(); j++) {
					double diff = V.get(i, j) - WH.get(i, j);
					dif += diff * diff;
				}
			}
			return dif;
		}

		public NonnegativeMatrixFactorization() {
		}

		public NonnegativeMatrixFactorization(Matrix V, int pc, int iter) {
			this.V = V;
			this.pc = pc;
			this.iter = iter;
		}

		public void factorize() {
			int ic = V.getRowDimension();
			int fc = V.getColumnDimension();// feature count

			Matrix W = Matrix.random(ic, pc);
			Matrix H = Matrix.random(pc, fc);

			for (int it = 0; it != iter; ++it) {
				Matrix wh = W.times(H);

				double cost = difcost(V, wh);

				if (it % 10 == 0)
					System.out.println(cost);

				if (cost == 0)
					break;

				Matrix hn = W.transpose().times(V);
				Matrix hd = W.transpose().times(W).times(H);
				for (int i = 0; i != H.getRowDimension(); i++) {
					for (int j = 0; j != H.getColumnDimension(); j++) {
						double val = H.get(i, j) * hn.get(i, j) / hd.get(i, j);
						H.set(i, j, val);
					}
				}
				Matrix wn = V.times(H.transpose());
				Matrix wd = W.times(H).times(H.transpose());
				for (int i = 0; i != W.getRowDimension(); i++) {
					for (int j = 0; j != W.getColumnDimension(); j++) {
						double val = W.get(i, j) * wn.get(i, j) / wd.get(i, j);
						W.set(i, j, val);
					}
				}

			}
			System.out.println("==========================================");
			println(V);
			System.out.println("==========================================");
			println(W.times(H));

		}

	};

	public static void println(Matrix M) {
		for (double[] d : M.getArray()) {
			for (double v : d)
				System.out.printf("%12f\t", v);
			System.out.println();
		}
	}

	public static double difcost(Matrix V, Matrix WH) {
		double dif = 0.0;
		for (int i = 0; i != V.getRowDimension(); i++) {
			for (int j = 0; j != V.getColumnDimension(); j++) {
				double diff = V.get(i, j) - WH.get(i, j);
				dif += diff * diff;
			}
		}
		return dif;
	}

	public static void main(String[] args) {

		double[][] array = { { 22, 28 }, { 49, 64 } };
		Matrix V = new Matrix(array);
		
		//NonnegativeMatrixFactorization nmf=new NonnegativeMatrixFactorization();
		
		
		
		int ic = V.getRowDimension();
		int fc = V.getColumnDimension();// feature

		int pc = 3;
		Matrix W = Matrix.random(ic, pc);
		Matrix H = Matrix.random(pc, fc);

		int iter = 50;
		for (int it = 0; it != iter; ++it) {
			Matrix wh = W.times(H);

			double cost = difcost(V, wh);

			if (it % 10 == 0)
				System.out.println(cost);

			if (cost == 0)
				break;

			Matrix hn = W.transpose().times(V);
			Matrix hd = W.transpose().times(W).times(H);
			for (int i = 0; i != H.getRowDimension(); i++) {
				for (int j = 0; j != H.getColumnDimension(); j++) {
					double val = H.get(i, j) * hn.get(i, j) / hd.get(i, j);
					H.set(i, j, val);
				}
			}
			Matrix wn = V.times(H.transpose());
			Matrix wd = W.times(H).times(H.transpose());
			for (int i = 0; i != W.getRowDimension(); i++) {
				for (int j = 0; j != W.getColumnDimension(); j++) {
					double val = W.get(i, j) * wn.get(i, j) / wd.get(i, j);
					W.set(i, j, val);
				}
			}

		}
		System.out.println("==========================================");
		println(V);
		System.out.println("==========================================");
		println(W.times(H));
	}

}