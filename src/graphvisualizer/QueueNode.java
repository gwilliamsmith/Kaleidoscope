package graphvisualizer;

/**
 * A node in the {@link MyQueue} class
 * @param <T> The type of data stored by the node
 */
public class QueueNode<T>{
	T data;
	QueueNode<T> next;
        /**
         * @param input The data stored by the node
         */
	public QueueNode(T input){
		data = input;
	}//end data
}//end QueueNode