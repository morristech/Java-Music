package utils;

/**
 * Created by Nick on 2016-08-30.
 * A simplified generic circular linked list.  This is useful since a pitch class collection is cyclic.
 */
public class CircularLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;  // tail.next points to head

    public Node<E> getHead() {
        return head;
    }

    public CircularLinkedList() {
    }

    public CircularLinkedList(Node<E> head) {
        this.head = head;
        this.tail = head;
    }

    public void append(Node<E> node) {
        if (head == null) {
            head = node;
            tail = node;
            node.setNext(head);
        } else {
            tail.setNext(node);
            node.setNext(head);
            tail = node;
        }
    }

    public int length() {
        if (head == null) return 0;

        int counter = 0;
        Node<E> curr = head;
        do {
            curr = curr.getNext();
            counter++;
        } while (curr != head);

        return counter;
    }

    public Node<E> findNode(E data) {
        if (head == null) return null;

        Node<E> curr = head;
        do {
            curr = curr.getNext();
            if (curr == null) return null;
            if (curr.getData().equals(data)) {
                return curr;
            }
        } while (curr != head);

        return null;
    }

    @Override
    public String toString() {
        if (head == null) return "[]";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (Node<E> curr = head;;) {
            stringBuilder.append(curr.getData());
            curr = curr.getNext();
            if (curr == head) {
                return stringBuilder.append(']').toString();
            } else {
                stringBuilder.append(',').append(' ');
            }
        }
    }

    public static class Node<E> {
        private Node<E> next;
        private E data;

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Node(E data) {
            this(null, data);
        }

        public Node(Node<E> next, E data) {
            this.next = next;
            this.data = data;
        }

        public String traverse() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');

            for (Node<E> curr = this;;) {
                stringBuilder.append(curr.getData());
                curr = curr.getNext();
                if (curr == this) {
                    return stringBuilder.append(']').toString();
                } else {
                    stringBuilder.append(',').append(' ');
                }
            }
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
