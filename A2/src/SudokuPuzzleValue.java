import java.util.LinkedList;

//initialize class SudokuPuzzle to communicate with SodokuPuzzle class
public class SudokuPuzzleValue {
	
		//initialize integer called valued
		int value;
		//implementing a new LinkedList that handles only integers and assign it a variable called domain
		LinkedList<Integer> domain;
		//Initialize integer value called puzzleSide with value of 9
		int puzzleSide=9;
		//initialize method called SudokuPuzzleValue that takes in integers and booleans as parameter
		public SudokuPuzzleValue(int num, boolean entireDomain) {
			//uses the method called constructValue to construct a value with a boolean expression that is always true
			this.constructValue(num, true);
		}
		//initialize method called SudokuPuzzleValue that takes in integers as parameter
		public SudokuPuzzleValue(int num) {
			//uses the method called constructValue to construct a value with a boolean expression that is always false
			this.constructValue(num, false);
		}
		//a constructor method that assigns values to value with num parameter, and a new construction of domain LinkedList
		public void constructValue(int num, boolean entireDomain) {
			//value takes on the value from num in the parameter 
			this.value = num;
			//new linkedList that only handles integers are assigned to domain 
			this.domain = new LinkedList<Integer>();

			//if statement checking for entireDomain
			if (entireDomain) {
				//if above is true
				this.domain.add(num);
			//else statement to hand everything else other than entireDomain
			} else {
				//enhanced for loop that checks if value of i is above or equal to the puzzleSide in order to perform further actions 
				for (int i = 1; i <= puzzleSide; i++) {
					//if i is above or equal to puzzleSide then its added to domain
					this.domain.add(i);
				}
			}
		}
		//method that returns string outputs of value 
		public String toString() {
			return this.value + "";
		}
	
}
