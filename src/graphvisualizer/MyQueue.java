package graphvisualizer;

/**
 * A queue implementation
 *
 * @param <T> The data type stored by the queue
 */
public class MyQueue<T> {

    QueueNode<T> front = null;
    private int length = 0;

    public MyQueue() {
    }//end constructor

    /**
     * Enters data into the queue
     *
     * @param input The data to put into the queue
     */
    public void enqueue(T input) {
        QueueNode<T> current = front;
        if (front == null && length == 0) {
            front = new QueueNode<>(input);
            length++;
        }//end if
        else {
            while (current.next != null) {
                current = current.next;
            }//end while
            current.next = new QueueNode<>(input);
            length++;
        }//end else
    }//end enqueue

    /**
     * @return The data at the front of the queue
     */
    public T dequeue() {
        T out = front.data;
        front = front.next;
        length--;
        return out;
    }//end dequeue

    /**
     * @return True if the front of the queue is not null
     */
    public boolean hasFront() {
        return front != null;
    }//end hasFront

    /**
     * Empties the queue
     */
    public void empty() {
        front = null;
        length = 0;
    }//end empty

    /**
     * @return The number of objects in the queue
     */
    public int size() {
        return length;
    }//end size

    //Does not check if the contents are equal, simply the objects referred to
    /**
     * Checks to see if data is contained within the queue
     *
     * @param info The data to be checked against data in the queue
     * @return True if the data in question is in the queue
     */
    public boolean containsObject(T info) {
        QueueNode<T> current = front;
        while (current != null) {
            if (current == info) {
                return true;
            }//end if
            current = current.next;
        }//end while
        return false;
    }//end contains Object

    /**
     * @return True if the queue is empty
     */
    public boolean isEmpty() {
        return front == null;
    }//end isEmpty
}//end MyQueue
