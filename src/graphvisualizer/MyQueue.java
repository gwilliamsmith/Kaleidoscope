package graphvisualizer;


public class MyQueue<T>{
	QueueNode<T> front = null;
	private int length = 0;
	public MyQueue(){	
	}//end constructor
	public void enqueue(T input){
		QueueNode<T> current = front;
		if(front == null && length == 0){
			front = new QueueNode<>(input);
			length++;
		}//end if
		else{
			while(current.next != null){
				current = current.next;
			}//end while
			QueueNode<T> temp = new QueueNode<>(input);
			current.next = temp;
			length++;
		}//end else
	}//end enqueue
	public T dequeue(){
		if(front != null){
			T out = front.data;
			front = front.next;
			length--;
			return out;
		}//end if
		else{
			return null;
		}//end else
	}//end dequeue
	public boolean hasFront(){
		if(front == null){
			return false;
		}//end if
		return true;
	}//end hasFront
	public void out(){
		QueueNode<T> current = front;
		while(current != null){
			System.out.println(current.data);
			current = current.next;
		}//end while
	}//end out
        public void empty(){
            front = null;
            length = 0;
        }//end empty
        public int size(){
            return length;
        }//end size
}//end MyQueue