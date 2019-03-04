import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws FileNotFoundException {
	
		File file = new File("test.txt");
		Scanner scanner = new Scanner(file);
		int[][] puzzle_Array = new int[9][9];
		int k = 0;
		while(scanner.hasNextLine()){
			String[] v = scanner.nextLine().split(" ");
			for (int i = 0; i < v.length; i++){
				puzzle_Array[k][i] = Integer.parseInt(v[i]);
			}
		
			k++;
		}

		SudokuPuzzle puzzle = new SudokuPuzzle(puzzle_Array);
		puzzle.print();
		System.out.println();
		SudokuPuzzle solution = SudokuPuzzleSolver.backtrackingSearch(puzzle);

		if (solution == null) {
			System.out.println("No solution");
		} else {
			solution.print();
			System.out.println("The number of Steps: "+ SudokuPuzzleSolver.numSteps);
		}
	

	}
}
