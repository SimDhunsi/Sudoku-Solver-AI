
import java.util.*;

public class SudokuPuzzleSolver {																									
	static PriorityQueue<Edge> arcs = new PriorityQueue<Edge>();
	public static int unitSide = 3;																								//creating public integer unitSide with the value of 3
	public static int puzzleSide = 9;																							//creating public integer PuzzelSide with the value of 9
	static int numSteps = 0;																									//creating integer numSteps with the value of 0
	

	

	public static SudokuPuzzle backtrackingSearch(SudokuPuzzle puzzle) {

		SudokuPuzzle solution = backtrack(puzzle);

		return solution;
	}

	public static SudokuPuzzle backtrack(SudokuPuzzle puzzle) {
		
		//all the nodes already have the values in [1,9] which doesn't violate the 3 constraints.
		if (FullofValues(puzzle)) {
			return puzzle;
		}

		//get the unsigned node that still has the value 0 and not a value from 1 to 9
		Node unassigned = selectUnAssignedVariable(puzzle);
		
		//get the domain of unsigned node
		LinkedList<Integer> domain = puzzle.getDomain(unassigned.row, unassigned.col);
		
		//goes through each value in the domain of unsigned node and checks to see with if that value doesn't violate the constraints. 
		for (int i = 0; i < domain.size(); i++) {
		
			
			int value = domain.get(i);
			
			//set the value of unsigned node to value
			puzzle.set(unassigned.row, unassigned.col, value);

			//Increment the number of steps
			numSteps++;

			//if the value doesn't violate constraints:
			if (ConsistencyCheck(puzzle)) {

				

				if (AC3(puzzle)) {
					SudokuPuzzle result = backtrack(puzzle);

					if (result != null) {
						return result;
					}
					
				}
				else {
					continue;
				}

			}

			//if the value violates the constraints, unset it from node
			puzzle.unset(unassigned.row, unassigned.col);
			
			
		}

		return null;
	}
	




	public static boolean AC3(SudokuPuzzle puzzle) {													 //Function named AC3 that returns a boolean
	
		
		FillTheQueue(puzzle);
	
		while (arcs.size() > 0) {
			Edge edge = arcs.remove();        				 									 //gets the binary arrays and set it to edge,then compare the nodes in edge
		
			if (revise(puzzle, edge.StartNode, edge.EndNode)) {   										 //if the domain of edge[0] has been reduced due to making edge[0] arc consistent to edge[1] 
				
				int Row1 = edge.StartNode.row;
				int Col1 = edge.StartNode.col;
				int Row2 = edge.EndNode.row;
				int Col2 = edge.EndNode.col;
	
				if (puzzle.getDomainLength(Row1, Col1) == 0) {
					
					return false;
				}
				
				
				//the neighbors of the index (assignedRow,assignedCol) which are not the neighbors of the index (otherRow,otherCol)
				//need to be added to queue once again to be compared to the index (assignedRow,assignedCol).
				//That is because the domain of this index has been changed so its neighbors should be compared to it again.
				//However, since the domain of index (otherRow,otherCol) has not been changed, the common neighbors of (assignedRow,assignedCol) and (otherRow,otherCol) are not considered for re-comparisons.
				
				
				
				//this part goes through the neighbors of the Index(Index1Row,Index1Col),
				//and the neighbors which are not in the same row and same column with the Index and are not the common neighbor with
				//Index (Index2Row,Index2Col) are found.Then a binary list of Index(Index1Row,Index1Col) and that neighbor is built, 
				//and the list is added to the queue.
				
				int startRow = (Row1 / unitSide) * unitSide;
				int startCol = (Col1 / unitSide) * unitSide;
				int endRow = startRow + unitSide;
				int endCol = startCol + unitSide;

				for (int row = startRow; row < endRow; row++) {
					for (int col = startCol; col < endCol; col++) {
						if (row != Row1 && col != Col1 && row != Row2 && col != Col2) {
							Node StartNode=new Node(row,col,puzzle.get(row, col));
							Node EndNode=new Node(Row1,Col1,puzzle.get(Row1, Col1));
							arcs.insert(new Edge(StartNode,EndNode));
							
						}
					}
				}
				

				
				/* Queue squares in current row */
				
				
				for (int col = 0; col < puzzleSide; col++) {
					if (col != Col1 && col != Col2) {
						Node StartNode=new Node(Row1,col,puzzle.get(Row1, col));
						Node EndNode=new Node(Row1,Col1,puzzle.get(Row1, Col1));
						arcs.insert(new Edge(StartNode,EndNode));
					}
				}
				
				/* Queue squares in current column */
				
				
				for (int row = 0; row < puzzleSide; row++) {
					if (row != Row1 && row != Row2) {
						Node StartNode=new Node(row,Col1,puzzle.get(row, Col1));
						Node EndNode=new Node(Row1,Col1,puzzle.get(Row1,Col1));
						arcs.insert(new Edge(StartNode,EndNode));
					}
				}
			}
		}

		return true;
	}
	
	

	//Index i and index j need to be compared to each other.Each index has a row and column.
	//The revise function does the job of comparison between two indices(nodes) i and j.
	//[(1,0)(0,0)] is the edge. Index i=(1,0)   Index j=(0,0)
	
	public static boolean revise(SudokuPuzzle puzzle, Node StartNode, Node EndNode) {
		boolean revised = false;
		
		LinkedList<Integer> domainI = puzzle.getDomainCopy(StartNode.row, StartNode.col);
		int n=domainI.size();
		LinkedList<Integer> domainJ = puzzle.getDomain(EndNode.row, EndNode.col);

		for (Integer x : domainI) {
			boolean satisfiable = false;

			for (Integer y : domainJ) {
				if ((int) y != (int) x) {
					satisfiable = true;
				}
			}

			if (!satisfiable) {
				//System.out.println("i: ("+i.row+","+i.col+")  j: ("+j.row+","+j.col);
				puzzle.removeFromDomain(StartNode.row, StartNode.col, x);
				revised = true;
			}
		}

		return revised;
	}

	//This functions creates an array of two indices that should be compared to each other and put that array inside the arcs priority queue.
	public static void FillTheQueue(SudokuPuzzle puzzle) {
		//puzzle.print();
		arcs.print();
		for (int CurrentRow = 0; CurrentRow < puzzleSide; CurrentRow++) {
			for (int CurrentCol = 0; CurrentCol < puzzleSide; CurrentCol++) {

				//This part goes through the neighbors of the Index(currentRow,currentCol) in each unit of the puzzle.
				//the neighbors in the unit that are not in the same row and same column as the Index (currentRow,currentCol)are chosen.
				//Then it makes a binary array of the index and its specific neighbor and add that array to the arcs queue.

				int startRow = (CurrentRow / puzzle.unitSide) * puzzle.unitSide;
				int startCol = (CurrentCol / puzzle.unitSide) * puzzle.unitSide;
				int endRow = startRow + puzzle.unitSide;
				int endCol = startCol + puzzle.unitSide;

				for (int row = startRow; row < endRow; row++) {
					for (int col = startCol; col < endCol; col++) {
						if (row != CurrentRow && col != CurrentCol) {
							Node StartNode=new Node(row,col,puzzle.get(row, col));
							Node EndNode=new Node(CurrentRow,CurrentCol,puzzle.get(CurrentRow, CurrentCol));
							arcs.insert(new Edge(StartNode,EndNode));
							
						}
					}
				}

				
				//This part goes through the neighbors of the index(currentRow,currentCol) that are in the same column as the index
				//and creates a binary array of the index and that neighbor, add that array to the arcs.
				
					for (int row = 0; row < puzzleSide; row++) {
						if (row != CurrentRow) {
							Node StartNode=new Node(row,CurrentCol,puzzle.get(row, CurrentCol));
							Node EndNode=new Node(CurrentRow,CurrentCol,puzzle.get(CurrentRow,CurrentCol));
							arcs.insert(new Edge(StartNode,EndNode));
							
						}
					}
				
				//This part goes through the neighbors of the index(currentRow,currentCol) that are in the same row as the index
				//and creates a binary array of the index and that neighbor, add that array to the arcs.
				
					for (int col = 0; col < puzzleSide; col++) {
						if (col != CurrentCol) {
							Node StartNode=new Node(CurrentRow,col,puzzle.get(CurrentRow, col));
							Node EndNode=new Node(CurrentRow,CurrentCol,puzzle.get(CurrentRow, CurrentCol));
							arcs.insert(new Edge(StartNode,EndNode));
							
						}
					}

				
			}
		}
	}
	

	
	//returns the node that its value is still 0.
	public static Node selectUnAssignedVariable(SudokuPuzzle puzzle) {
		for (int row = 0; row < puzzleSide; row++) {
			for (int col = 0; col < puzzleSide; col++) {
				if (puzzle.get(row, col) == 0) {
					return new Node(row, col,puzzle.get(row, col));
				}
			}
		}

		return null;
	}

	//searches through the puzzle to see if it is filled in completely with numbers from 1 to 9.
	//If there still exits a node with value 0 the function returns false.
	public static boolean FullofValues(SudokuPuzzle puzzle) {
		for (int row = 0; row < puzzleSide; row++) {
			for (int col = 0; col < puzzleSide; col++) {
				if (puzzle.get(row, col) == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isSolved(SudokuPuzzle puzzle) {
		return FullofValues(puzzle) && ConsistencyCheck(puzzle);
	}
	
	//check to see if the current sudkupuzzle does not violate the 3 constraints.
	//Therefore checks each 3*3 block,each row and each column to see if it the nodes violate the constraints.
	//return true if the rules for each block, each row and each column are met.
	public static boolean ConsistencyCheck(SudokuPuzzle puzzle) {
		/* Check each unit */
		for (int uRow = 0; uRow < puzzle.unitSide; uRow++) {
			for (int uCol = 0; uCol < puzzle.unitSide; uCol++) {
				int[] nums = new int[9];
				int startRow = uRow * puzzle.unitSide;
				int startCol = uCol *puzzle.unitSide;
				int endRow = startRow + puzzle.unitSide;
				int endCol = startCol + puzzle.unitSide;
				int at = 0;

				for (int row = startRow; row < endRow; row++) {
					for (int col = startCol; col < endCol; col++) {
						nums[at++] = puzzle.get(row, col);
					}
				}

				Arrays.sort(nums);

				for (int i = 1; i < at; i++) {
					if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
						return false;
					}
				}
			}
		}

		/* Check each row */
		for (int row = 0; row < puzzle.puzzleSide; row++) {
			int[] nums = new int[9];

			for (int col = 0; col < puzzle.puzzleSide; col++) {
				nums[col] = puzzle.get(row, col);
			}

			Arrays.sort(nums);

			for (int i = 1; i < nums.length; i++) {
				if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
					return false;
				}
			}
		}

		/* Check each column */
		for (int col = 0; col < puzzle.puzzleSide; col++) {
			int[] nums = new int[9];

			for (int row = 0; row < puzzle.puzzleSide; row++) {
				nums[row] = puzzle.get(row, col);
			}

			Arrays.sort(nums);

			for (int i = 1; i < nums.length; i++) {
				if (nums[i] != 0 && nums[i - 1] != 0 && nums[i] == nums[i - 1]) {
					return false;
				}
			}
		}

		return true;
	}
}
