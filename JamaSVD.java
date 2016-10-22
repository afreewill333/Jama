import java.io.*;
import java.util.*;
import Jama.*;

public class JamaSVD {

	public static void main(String[] args) {

		double[][] array = { { 1, 1, 1, 0, 0 }, { 2, 2, 2, 0, 0 },
				{ 1, 1, 1, 0, 0 }, { 5, 5, 5, 0, 0 }, { 1, 1, 0, 2, 2 },
				{ 0, 0, 0, 3, 3 }, { 0, 0, 0, 1, 1 }, };
		Matrix m = new Matrix(array);

		SingularValueDecomposition svd = m.svd();
		double[] values = svd.getSingularValues();
		System.out.println(values.toString());
		for (double v : values)
			System.out.println(v);
		Matrix U = svd.getU();
		Matrix V = svd.getV();

		for (double[] d : U.getArray()) {
			for (double v : d)
				System.out.print(v + "              ");
			System.out.println();

		}
		System.out.println("----------------------------------------");
		for (double[] d : svd.getS().getArray()) {
			for (double v : d)
				System.out.print(v + "              ");
			System.out.println();

		}
		System.out.println("----------------------------------------");
		for (double[] d : V.getArray()) {
			for (double v : d)
				System.out.print(v + "              ");
			System.out.println();

		}

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
		
		Matrix S = svd.getS().copy();
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
		for (double[] d : (U.times(svd.getS()).times(V.inverse())).getArray()) {
			for (double v : d)
				System.out.print(v + "              ");
			System.out.println();

		}
		System.out.println("#################################################################");
		for (double[] d : U.times(S).times(V.inverse()).getArray()) {
			for (double v : d)
				System.out.print(v + "              ");
			System.out.println();

		}
		// Matrix x = A.solve(b);
		// Matrix Residual = A.times(x).minus(b);
		// double rnorm = Residual.normInf();
		
		System.out.println("#################################################################");
		for (double[] d : Matrix.random(4, 4).getArray()) {
			for (double v : d)
				System.out.print(v + "              ");
			System.out.println();

		}
	}

}
