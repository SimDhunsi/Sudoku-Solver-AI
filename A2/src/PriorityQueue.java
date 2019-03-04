import java.util.LinkedList;

//Initialize class called PriorityQueue
public class PriorityQueue<N> {																						
	
	//creating and integer value maxValue of 0
	int maxValue = 0;																								
	
	//creating and integer value priority of 0
	int priority = 0;																								
	//Linked list that contains a queue of PriorityNode values
	LinkedList<PriorityNode<N>> queue = new LinkedList<PriorityNode<N>>();											
	
	//Creating a remove function for PriorityQueue
	public N remove() {																								
		
		//min value is set as the current maxValue
		int min = this.maxValue;	
		//PriorityNode is set to null
		PriorityNode<N> minNode = null;																				
		//index is set to 0
		int index = 0;
		//minIndex is also set to 0																					
		int minIndex = 0;																							

		//Enhanced for loop runs through every PriorityNode in Queue
		for (PriorityNode<N> node : queue) {																		
			//checks if the priority value of the nose greater or equal to the min value
			if (node.priority <= min) {	
				//if above is true, then a minNode is created where the copy of the current node if taken
				minNode = node;						
				//min value is changed to be the same as the node priority value
				min = node.priority;		
				//Assigning current index to minIndex
				minIndex = index;																					
			}
			//incrementing the index value
			index++;																								
		}
		//checking size of queue to see it if equals 0
		if (queue.size() == 0) {		
			//if above equals true then new value of maxValue is 0
			this.maxValue = 0;																						
		}
		//if statement to check if minNode does not equal null
		if (minNode != null) {			
			//removing the minIndex from the queue
			queue.remove(minIndex);																					 
			//returning the element within minNode
			return minNode.element;																					
		}
		//return null otherwise
		return null;																								
	}
	
	//PriorityQueue constructor function
	public PriorityQueue() {																						
	}
	
	//creating an insert method
	public void insert(N node) {																					
		//inserts node and increases the priority value
		insert(node, priority++);																					
	}

	//insert for node and value
	public void insert(N node, int value) {							
		//PriorityNode constructor 
		PriorityNode<N> n = new PriorityNode<N>(node, value);		
		//adds PriorityNode made above to the linked list called queue
		queue.add(n);																								

		//checks is value is higher than maxValue
		if (value > this.maxValue) {				
			//if above is true then maxValue has a new value
			this.maxValue = value;																					
		}
	}

	
	//method to check if it contains a certain element 
	public boolean containsElement(N checkNode) {			
		//Enhanced for loop runs through every PriorityNode in Queue
		for (PriorityNode<N> node : queue) {			
			//checking if the element is the same 
			if (node.element.equals(checkNode)) {	
				//if above is true then return true
				return true;																						
			}
		}
		//otherwise return false 
		return false;																								
	}

	//method to change priority if it is higher than value
	public void changePriority(N updateNode, int value) {	
		//Enhanced for loop runs through every PriorityNode in Queue
		for (PriorityNode<N> node : queue) {
			//Checks to see if element in node is equal to the new updated node
			if (node.element.equals(updateNode)) {
				//checks to see if value if less than the node priority value
				if (value < node.priority) {
					//if above statement is true, then node priority is changed to be same as the value
					node.priority = value;
				}
			}
		}
	}
	
	//method that returns the size of a queue
	public int size() {
		//returns size of queue
		return queue.size();
	}
	//method to print out the entirety of the queue 
	public void print() {
		//Enhanced for loop runs the whole queue
		for(int i=0;i<size();i++) {
			//prints out values of i
			System.out.print(i+",");
		}
	}

	//Creating a separate class called PriorityNode that produces Node 
	public class PriorityNode<N> {
		//Node with variable element is initialized 
		N element;
		//priority integer of 0 has been assigned 
		int priority = 0;
		
		//PriorityNode constructor with input parameters of element and priority 
		public PriorityNode(N element, int priority) {
			//assigning values to element within the Node
			this.element = element;
			//assigning values to priority within the Node
			this.priority = priority;
		}
	}
}