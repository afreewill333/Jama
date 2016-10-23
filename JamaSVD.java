import java.io.*;
import java.util.*;
import Jama.*;

public class JamaSVD {

	public class SVD{
		private Matrix M;
		private Matrix U;
		private Matrix S;
		private Matrix V;
		private double[] values;
		private double rate=0.9;
		private SingularValueDecomposition svd;		
		
		
		public Matrix getM() {
			return M;
		}

		public void setM(Matrix m) {
			M = m;
		}

		public Matrix getS() {
			return S;
		}

		public void setS(Matrix s) {
			S = s;
		}

		public double[] getValues() {
			return values;
		}

		public void setValues(double[] values) {
			this.values = values;
		}

		public double getRate() {
			return rate;
		}

		public void setRate(double rate) {
			this.rate = rate;
		}

		public Matrix getU() {
			return U;
		}

		public Matrix getV() {
			return V;
		}

		public SingularValueDecomposition getSvd() {
			return svd;
		}

		public void println(Matrix M) {
			for (double[] d : M.getArray()) {
				for (double v : d)
					System.out.printf("%12f\t", v);
				System.out.println();
			}
		}
		
		public SVD() {
		}
		public SVD(Matrix M, double rate) {
			this.M = M;
			this.rate = rate;
		}
		
		public void decompose() {
			this.svd = M.svd();
			this.U = svd.getU();
			this.V = svd.getV();
			this.S = svd.getS();
			this.values = svd.getSingularValues();
		}
		
		public SVD recompose(){
			return recompose(rate);
		}
		public SVD recompose(double rate) {
			SingularValueDecomposition svd = M.svd();
			Matrix U = svd.getU();
			Matrix V = svd.getV();
			Matrix S = svd.getS();
			double[] values = svd.getSingularValues();			

			//rate = 0.9;//
			double totalEnergy = 0.0;
			for (double d : values)
				totalEnergy += d * d;
			
			double keepEnergy=rate*totalEnergy;
			double energy=0.0;
			int idx=0;
			double[] newValues=new double[values.length];
			while(energy<keepEnergy){
				energy+=values[idx]*values[idx];
				newValues[idx]=values[idx];
				++idx;
			}			
			Matrix newS = S.copy();
			while(idx<values.length){
				newS.set(idx,idx,0);
				idx++;
			}			
			
			SVD newSvd=new SVD();
			newSvd.setM(U.times(newS).times(V.transpose()));
			newSvd.setRate(rate);
			newSvd.setS(newS);
			newSvd.setValues(newValues);
			

			
			for(double d: values)
				System.out.print(d+"         ");
			System.out.println();
			for(double d: newValues)
				System.out.print(d+"         ");
			System.out.println();
			
			return newSvd;			
		}

	};
	
	public static void main(String[] args) {

		double[][] array = { { 1, 1, 1, 0, 0 }, { 2, 2, 2, 0, 0 },
				{ 1, 1, 1, 0, 0 }, { 5, 5, 5, 0, 0 }, { 1, 1, 0, 2, 2 },
				{ 0, 0, 0, 3, 3 }, { 0, 0, 0, 1, 1 }, };
		double[][] arrays={{0.8,0.12},{0.14,0.47},{0.1,0.87},{0.3,0.03}};
		Matrix m = new Matrix(arrays);

		JamaSVD jsvd=new JamaSVD();
		SVD de = jsvd.new SVD();
		de.setM(m);
		de.setRate(0.6);
		SVD t =de.recompose();
		t.println(t.getM());
		
		
	
		
		/*
		SingularValueDecomposition svd = m.svd();
		Matrix U = svd.getU();
		Matrix S = svd.getS();
		double[] values = svd.getSingularValues();
		for (double v : values)
			System.out.println(v);
		Matrix V = svd.getV();


		double rate = 0.9;
		double totalEnergy = 0.0;
		for (double d : values)
			totalEnergy += d * d;
		System.out.println(totalEnergy);
		System.out.println(rate*totalEnergy);
		
		double currentEnergy=0.0;
		int idx=0;
		double[] newValues=values.clone();
		while(currentEnergy<rate*totalEnergy){
			currentEnergy+=values[idx]*values[idx];
			++idx;
		}
		
		S = svd.getS().copy();
		int i=idx;
		while(i<values.length){
			S.set(i,i,0);
			i++;
		}
		
		
		while(idx<values.length){
			newValues[idx]=0;
			++idx;
		}
		for(double d: values)
			System.out.print(d+"         ");
		System.out.println();
		for(double d: newValues)
			System.out.print(d+"         ");
		System.out.println();
		
		System.out.println("#################################################################");
		for (double[] d : U.getArray()) {
			for (double v : d)
				System.out.printf("%.1f\t",v);
			System.out.println();

		}
		
		System.out.println("#################################################################");
		for (double[] d : (U.times(svd.getS()).times(V.transpose())).getArray()) {
			for (double v : d)
				System.out.printf("%12f\t",v);
			System.out.println();

		}
		System.out.println("#################################################################");
		for (double[] d : U.times(S).times(V.transpose()).getArray()) {
			for (double v : d)
				System.out.printf("%.1f\t",v);
			System.out.println();

		}
		
		double[] d= new double[3];
		for(double v:d)
			System.out.println(v);
			
			*/
	}

}
