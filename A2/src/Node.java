//Initializing class for creating of Nodes
public class Node {
	//initialized integer row
	int row;				
	//initialized integer col
	int col;			
	//initialized integer value
	int value;																								

	//Creating a Node constructor which takes parameters row, col, and value
	public Node(int row, int col, int value) {	
		//assigning values to row form parameter row
		this.row = row;		
		//assigning values to col form parameter col
		this.col = col;					
		//assigning values to value from parameter value
		this.value = value;																					
	}
	//initializing method that converts values to strings
	public String toString() {							
		//returns the combination of row, col, and value as well as spacing all in a string
		return "[" + row + "," + col + "]"+ "-" + value;													
	}
	//initializing method to compare otherNode, with Node
	public boolean equals(Object otherNode) {	
		//assigning Node to otherNode
		Node other = (Node) otherNode;			
		//returns true/false if any are the same
		return (this.row == other.row && this.col == other.col && this.value == other.value);				
	}

	
}



