import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements Iterable<E> {

    public Iterator<E> iterator() {
        return new IteratorPriv();
    }
    private static class Node<E> {
        private E data;
        private Node<E> previous; // pointer to previous node
        private Node<E> next; // pointer to next node

        public Node(E data, Node<E> previous, Node<E> next) {

            this.data = data;
            this.previous = previous;
            this.next = next;
        }

        public Node(E data) {
            this(data, null, null);
        }
    }

    private Node<E> head; // pointer to the first node of this list
    private Node<E> tail; // pointer to the last node of this list
    private int size;  // the number of nodes currently in this list

    public DoublyLinkedList() {
        tail = head = null;
        size = 0;
    }

    public void addFirst(E element) {
        Node<E> nextNode = head;
        head = new Node<>(element, null, head);
        if (nextNode != null)
            nextNode.previous = head;
        this.size++;
        if (size == 1)
            tail = head;
    }

    public void addLast(E element) {
        Node<E> previousNode = tail;
        tail = new Node<>(element, tail, null);
        size++;
        if (previousNode != null)
            previousNode.next = tail;
        if (size == 1) {
            head = tail;
        }
    }

    public E getFirst() {
        if (this.size == 0)
            throw new NoSuchElementException();
        return head.data;
    }

    public E getLast() {
        if (this.size == 0)
            throw new NoSuchElementException();
        return tail.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;

        while (current != null) {
            sb.append(current.data);

            if (current != tail) {
                sb.append(", ");
            }

            current = current.next;
        }

        sb.append("]");
        return sb.toString();
    }

    public String toReverseString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = tail;

        while (current != null) {
            sb.append(current.data);

            if (current != head) {
                sb.append(", ");
            }

            current = current.previous;
        }

        sb.append("]");
        return sb.toString();
    }

    public E removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();
        Node<E> oldHead = head;
        if (head.next != null) {
            head = head.next;
            head.previous = null;
            oldHead.next = null;
        }
        else {
            head = null;
            tail = null;
        }
        size--;
        return oldHead.data;
    }

    public E removeLast() {
        if (size == 0)
            throw new NoSuchElementException();

        Node<E> oldTail = tail;
        if (tail.previous != null) {
            tail = tail.previous;
            tail.next = null;
            oldTail.previous = null;
        }
        else {
            tail = null;
            head = null;
        }
        size--;
        return oldTail.data;
    }
    public void clear(){
        for (int i = 0; i<size;){
            removeLast();
        }
    }
    public boolean contains(Object o){
        Node<E> currentNode = head;
        for (int i=0; i<size; i++){
            if(currentNode.data.equals(o))
                return true;
            if(size != 1)
                currentNode = currentNode.next;
        }
        return false;
    }

    public boolean add(E e){
        addLast(e);
        return true;
    }

    public boolean remove(Object o){
        Node<E> currentNode = head;
        Node<E> previousNode;
        Node<E> nextNode;
        for (int i=0; i<size; i++){
            if(currentNode.data.equals(o)) {
                if (size == 1) {
                    head = null;
                    tail = null;
                    size--;
                    return true;
                }
                else if(i == size-1){
                    removeLast();
                    return true;
                }
                else if(i == 0){
                    removeFirst();
                    return true;
                }
                else {
                    previousNode = currentNode.previous;
                    nextNode = currentNode.next;
                    previousNode.next = nextNode;
                    nextNode.previous = previousNode;
                    currentNode.next = null;
                    currentNode.previous = null;
                    currentNode.data = null;
                    size--;
                    return true;
                }
            }
            if(size != 1)
                currentNode = currentNode.next;
        }
        return false;
    }
    public int indexOf(Object o){
        int index = 0;
        Node<E> currentNode = head;
        for (int i=0; i<size; i++){
            if(currentNode.data.equals(o))
                return index;
            if(size != 1)
                currentNode = currentNode.next;
            index++;
        }
        return -1;
    }
    public int lastIndexOf(Object o){
        int index = size-1;
        Node<E> currentNode = tail;
        for (int i=0; i<size; i++){
            if(currentNode.data.equals(o))
                return index;
            if(size != 1)
                currentNode = currentNode.previous;
            index--;
        }
        return -1;
    }
    public E get(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<E> currentNode = head;
        for(int i = 0;i<size;i++){
            if(index == i)
                return currentNode.data;
            else
                currentNode = currentNode.next;
        }
        throw new IndexOutOfBoundsException();
    }
    public E set(int index,E element){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<E> currentNode = head;
        Node<E> oldNode = new Node<>(null);
        for(int i = 0;i<size;i++){
            if(index == i) {
                oldNode.data = currentNode.data;
                currentNode.data = element;
                return oldNode.data;
            }
            else
                currentNode = currentNode.next;
        }
        throw new IndexOutOfBoundsException();
    }
    public void add(int index, E element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        Node<E> currentNode = head;
        if (size == 0 || index == 0) {
            this.addFirst(element);
            return;
        }
        if (index == size){
            this.addLast(element);
            return;
        }
        for(int i = 0;i<size;i++){
            if(index == i) {
                Node<E> previousNode = currentNode.previous;
                Node<E> newNode = new Node<>(element, currentNode.previous, currentNode);
                previousNode.next = newNode;
                currentNode.previous = newNode;
                size++;
            }
            else
                currentNode = currentNode.next;
        }
    }
    public E remove(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<E> currentNode = head;
        if (size == 0 || index == 0) {
            return this.removeFirst();
        }
        if (index == size-1){
            return this.removeLast();
        }
        for(int i = 0;i<size;i++){
            E removedElement;
            if(index == i) {
                removedElement = currentNode.data;
                Node<E> previousNode = currentNode.previous;
                Node<E> nextNode = currentNode.next;
                previousNode.next = currentNode.next;
                nextNode.previous = previousNode;
                size--;
                return removedElement;
            }
            else
                currentNode = currentNode.next;
        }
        throw new IndexOutOfBoundsException();
    }
    private class IteratorPriv implements Iterator<E>{
        private Node<E> currentNode;
        public IteratorPriv(){
            currentNode = head;
        }
        @Override
        public boolean hasNext() {
            return (currentNode != null);
        }

        @Override
        public E next() {
            if (currentNode == null)
                throw new NoSuchElementException();
            E oldNode = currentNode.data;
            currentNode = currentNode.next;
            return oldNode;
        }
    }
}