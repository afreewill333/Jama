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

		
		public Matrix getV() {
			return V;
		}

		public void setV(Matrix v) {
			V = v;
		}

		public Matrix getW() {
			return W;
		}

		public Matrix getH() {
			return H;
		}

		public int getPc() {
			return pc;
		}

		public void setPc(int pc) {
			this.pc = pc;
		}

		public int getIter() {
			return iter;
		}

		public void setIter(int iter) {
			this.iter = iter;
		}

		
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
				
				this.W=W;
				this.H=H;
			}
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
		JamaNMF jnmf=new JamaNMF();

		double[][] array = { { 22, 28 }, { 49, 64 } };
		Matrix V = new Matrix(array);
		
		NonnegativeMatrixFactorization nmf=jnmf.new NonnegativeMatrixFactorization();
		nmf.setV(V);
		nmf.setIter(50);
		nmf.setPc(3);
		nmf.factorize();
		System.out.println("--------------------------------------");
		nmf.println(V);
		System.out.println("--------------------------------------");
		nmf.println(nmf.getW());
		System.out.println("--------------------------------------");
		nmf.println(nmf.getH());
	}

}