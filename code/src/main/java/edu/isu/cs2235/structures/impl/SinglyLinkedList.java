package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.List;

/***
 * @author Brigham Campbell
 * @param <E> The object type to store in the list
 */
public class SinglyLinkedList<E> implements List<E> {

    // Utility singly-linked Node class from
    // Data Structures and Algorithms in Java
    // pg. 144
    private static class Node<E> {
        private E element;
        private Node<E> next;
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() { return element; }
        public Node<E> getNext() { return next; }
        public void setNext(Node<E> n) { next = n; }
    }

    private int numberOfNodes = 0;
    private Node<E> first;
    private Node<E> last;

    /**
     * @return first element in the list or null if the list is empty.
     */
    @java.lang.Override
    public E first() {
        if(numberOfNodes > 0) {
            return first.getElement();
        }
        return null;
    }

    /**
     * @return last element in the list or null if the list is empty.
     */
    @java.lang.Override
    public E last() {
        if(numberOfNodes > 0) {
            return last.getElement();
        }
        return null;
    }

    /**
     * Adds the provided element to the end of the list, only if the element is
     * not null.
     *
     * @param element Element to be added to the end of the list.
     */
    @java.lang.Override
    public void addLast(E element) {
        if(element != null) { // don't add null elements(although, I think the Java List class would support this...)
            Node<E> newNode = new Node<E>(element, null);

            if (numberOfNodes > 0) {
                last.setNext(newNode);
            } else {
                // NOTE: If there are no pre-existing nodes, the new node is the first and last node!
                first = newNode;
            }
            last = newNode;
            numberOfNodes++;
        }
    }

    /**
     * Adds the provided element to the front of the list, only if the element
     * is not null.
     *
     * @param element Element to be added to the front of the list.
     */
    @java.lang.Override
    public void addFirst(E element) {
        if(element != null) {
            Node<E> newNode = new Node<E>(element, first);
            if(numberOfNodes == 0) {
                // NOTE: Again, if there are no pre-existing nodes, the new node is the first and last node!
                last = newNode;
            }
            first = newNode;
            numberOfNodes++;
        }
    }

    /**
     * Removes the element at the front of the list.
     *
     * @return Element at the front of the list, or null if the list is empty.
     */
    @java.lang.Override
    public E removeFirst() {
        if(numberOfNodes > 0) {
            E element = first.getElement();
            first = first.getNext();
            numberOfNodes--;
            return element;
        }
        return null;
    }

    /**
     * Removes the element at the end of the list.
     *
     * @return Element at the end of the list, or null if the list is empty.
     */
    @java.lang.Override
    public E removeLast() {
        if(numberOfNodes > 0) {
            if(numberOfNodes == 1) { // remove only existing node, don't traverse
                E retval = last.element;
                first = null;
                last = null;
                numberOfNodes--;
                return retval;
            }

            // traverse to find the second-to-last node
            Node<E> current = first;
            for(int i = 0; i < numberOfNodes - 2; i++) {
                current = current.next;
            }
            E retval = current.next.element;
            current.setNext(null);

            numberOfNodes--;
            return retval;
        }
        return null;
    }

    /**
     * Inserts the given element into the list at the provided index. The
     * element will not be inserted if either the element provided is null or if
     * the index provided is less than 0. If the index is greater than or equal
     * to the current size of the list, the element will be added to the end of
     * the list.
     *
     * @param element Element to be added (as long as it is not null).
     * @param index   Index in the list where the element is to be inserted.
     */
    @java.lang.Override
    public void insert(E element, int index) {
        if(element != null && index >= 0) {
            index = Math.min(numberOfNodes, index); // clamp max to last node

            if(index == 0) { // insert at the beginning, don't traverse
                addFirst(element);
                return;
            }

            // traverse to find the node before the specified index
            Node<E> before = first;
            for(int i = 0; i < index - 1; i++) {
                before = before.next;
            }
            Node<E> newNode = new Node<E>(element, before.next);
            before.setNext(newNode);

            numberOfNodes++;
        }
    }

    /**
     * Removes the element at the given index and returns the value.
     *
     * @param index Index of the element to remove
     * @return The value of the element at the given index, or null if the index
     * is greater than or equal to the size of the list or less than 0.
     */
    @java.lang.Override
    public E remove(int index) {
        if(index >= 0 && index < numberOfNodes) {

            if(index == 0) { // remove at beginning, don't traverse
                return removeFirst();
            }

            Node<E> before = first;
            for(int i = 0; i < index - 1; i++) {
                before = before.next;
            }
            E retval = before.next.element;
            before.setNext(before.next.next);

            numberOfNodes--;
            return retval;
        }
        return null;
    }

    /**
     * Retrieves the value at the specified index. Will return null if the index
     * provided is less than 0 or greater than or equal to the current size of
     * the list.
     *
     * @param index Index of the value to be retrieved.
     * @return Element at the given index, or null if the index is less than 0
     * or greater than or equal to the list size.
     */
    @java.lang.Override
    public E get(int index) {
        if(numberOfNodes > index && index >= 0) {
            // traverse through the nodes from the first node until we get to the "index"th node
            Node<E> current = first;
            for(int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.element;
        }
        return null;
    }

    /**
     * @return The current size of the list. Note that 0 is returned for an
     * empty list.
     */
    @java.lang.Override
    public int size() {
        return numberOfNodes;
    }

    /**
     * @return true if there are no items currently stored in the list, false
     * otherwise.
     */
    @java.lang.Override
    public boolean isEmpty() {
        return (numberOfNodes == 0);
    }

    /**
     * Prints the contents of the list in a single line separating each element
     * by a newline to the default System.out
     */
    @java.lang.Override
    public void printList() {
        Node<E> current = first;
        while(current != null) {
            // The tests say to print with spaces, but they actually test with newlines
            System.out.print(current.element + "\n");
            current = current.next;
        }
    }
}
