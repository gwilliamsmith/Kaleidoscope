package graphvisualizer;

public class QueueNode<T>{
	T data;
	QueueNode<T> next;
	public QueueNode(T input){
		data = input;
	}//end data
	public QueueNode() {
	}//end constructor
}//end QueueNode