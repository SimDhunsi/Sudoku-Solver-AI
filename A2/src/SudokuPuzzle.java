
import java.util.*;

public class SudokuPuzzle {																	//Initialize class named SudokoPuzzle
	public int unitSide = 3;																//Initialize integer unitSide with value of 3
	public int puzzleSide = 9;																//Initialize integer puzzleSide with value of 9
	private SudokuPuzzleValue[][] puzzle;													

	public SudokuPuzzle() {
		this.makePuzzle();
	}

	public SudokuPuzzleValue empty() {
		return new SudokuPuzzleValue(0);
	}

	public void makePuzzle() {
		SudokuPuzzleValue[][] puz = {
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() },
			{ empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty(), empty() }
		};
		this.puzzle = puz;
	}

	public SudokuPuzzle(int[][] puzzle) {
		this.makePuzzle();

		for (int r = 0; r < puzzle.length; r++) {
			for (int c = 0; c < puzzle.length; c++) {
				if (puzzle[r][c] != 0) {
					
					this.puzzle[r][c] = new SudokuPuzzleValue(puzzle[r][c], true);
					
				}
			}
		}
	}

	public void print() {
		System.out.println("|-------|-------|-------|");

		for (int r = 0; r < this.puzzleSide; r++) {
			for (int c = 0; c < this.puzzleSide; c++) {
				String value = this.puzzle[r][c].toString();
				String piece = value.equals("0") ? "0" : value;

				if (c == 0) {
					piece = "| " + piece;
				}
				else if (c == 2 || c == 5 || c == 8) {
					piece += " |";
				}

				System.out.print(piece + " ");
			}

			System.out.println();

			if ((r + 1) % 3 == 0) {
				System.out.println("|-------|-------|-------|");
			}
		}
	}

	public int get(int row, int col) {
		return this.puzzle[row][col].value;
	}

	public LinkedList<Integer> getDomain(int row, int col) {
		return this.puzzle[row][col].domain;
	}

	public void set(int row, int col, int value) {
		this.puzzle[row][col] = new SudokuPuzzleValue(value);
	}

	public void unset(int row, int col) {
		this.puzzle[row][col] = empty();
	}

	public LinkedList<Integer> getDomainCopy(int row, int col) {
		LinkedList<Integer> domain = this.getDomain(row, col);
		LinkedList<Integer> domainCopy = new LinkedList<Integer>();

		for (int i = 0; i < domain.size(); i++) {
			domainCopy.add(domain.get(i));
		}

		return domainCopy;
	}

	public void setDomain(int row, int col, LinkedList<Integer> domain) {
		this.puzzle[row][col].domain = domain;
	}

	//we remove nodes from the domain, but the domain copy still is the copy of original domain.
	//this function removes the value from the domain of the node(row,col).
	
	public void removeFromDomain(int row, int col, int value) {
		this.puzzle[row][col].domain.remove((Integer) value);
	}

	public void addToDomain(int row, int col, int value) {
		this.puzzle[row][col].domain.add((Integer) value);
	}

	public boolean IsInDomain(int row, int col, int value) {
		return this.puzzle[row][col].domain.contains((Integer) value);
	}

	public int getDomainLength(int row, int col) {
		return this.puzzle[row][col].domain.size();
	}

	public SudokuPuzzle getCopy() {
		SudokuPuzzle newPuzzle = new SudokuPuzzle();

		for (int r = 0; r < this.puzzleSide; r++) {
			for (int c = 0; c < this.puzzleSide; c++) {
				newPuzzle.set(r, c, this.get(r, c));
				newPuzzle.setDomain(r, c, this.getDomainCopy(r, c));
			}
		}

		return newPuzzle;
	}
}
