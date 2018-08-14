package com.train;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

// TODO: Replace examples and use TDD development by writing your own tests

public class DeterminantOfMatrix {

    private static int[][][] matrix = { {{1}},
                                        {{1, 3}, {2,5}},
                                        {{2,5,3}, {1,-2,-1}, {1, 3, 4}}};
    
    private static int[] expected = {1, -1, -20};
    
    private static String[] msg = {"Determinant of a 1 x 1 matrix yields the value of the one element",
                                   "Should return 1 * 5 - 3 * 2 == -1 ",
                                   ""};
    
    public static void main(String args[]) {
    	int[][] matrix = Matrix.getSubSquareMatrix(new int[][]{{2,5,3}, {1,-2,-1}, {1, 3, 4}}, 0);
    	for(int i=0; i<matrix.length; i++) {
    		for(int j=0; j<matrix[i].length; j++) {
    			System.out.print(matrix[i][j]);
    			System.out.print(" ");
    		}
    		System.out.print("\n");
    	}
    }

    @Test
    public void sampleTests() {
        for (int n = 0 ; n < expected.length ; n++)
          assertEquals(msg[n], expected[n], Matrix.determinant(matrix[n]));
    }
}

class Matrix {
	public static int determinant(int[][] matrix) {
		int det=0;
		if(matrix != null) {
			// assume square matrix?
			if(matrix.length == 1) {
				return matrix[0][0];
			} else if(matrix.length == 2) {
				int val = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
				System.out.println("val="+val);
				return val;
			} else {
				int multiplier = 1;
				for(int i=0; i<matrix.length; i++) {
					int detr = determinant(getSubSquareMatrix(matrix, i));
					System.out.println(det + " + " + multiplier + " * " + matrix[i][i] *detr);
					det = det + multiplier * matrix[0][i] * detr;
					multiplier *= -1;
				}
			}
		}
		return det;
	}
	
	public static int[][] getSubSquareMatrix(int[][] origMatrix, int index) {
		int[][] subMatrix = new int[origMatrix.length-1][origMatrix.length-1];
		int m=0, n=0;
		for(int i=1; i<origMatrix.length; i++) {
			for(int j=0; j<origMatrix[i].length; j++) {
				if(j!=index) {
					subMatrix[m][n] = origMatrix[i][j];
					n++;
					if(n==origMatrix.length-1) {
						n = 0;
						m++;
					}
				}
			}
		}
		for(int i=0; i<subMatrix.length; i++) {
    		for(int j=0; j<subMatrix[i].length; j++) {
    			System.out.print(subMatrix[i][j]);
    			System.out.print(" ");
    		}
    		System.out.print("\n");
    	}
		return subMatrix;
	}
}