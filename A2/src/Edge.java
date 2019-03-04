//Initializing class Edge to create edge nodes
public class Edge {
	//initializing Node called Start 
	Node StartNode;
	//initializing Node called Start 
	Node EndNode;
	
	//Edge Node constructor to keep track of start and end
	public Edge(Node start,Node end) {
		//assigning values to StartNode
		this.StartNode=start;
		//assigning values to EndNode
		this.EndNode=end;
	}
	//print method to print out the values of StartNode as well as EndNode values
	public void print() {
		System.out.println( "("+StartNode.value+" , "+EndNode.value+")");
		
	}
}
