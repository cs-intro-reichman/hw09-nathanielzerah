public class List {

    private Node first;
    private int size;

    public List() {
        first = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public CharData getFirst() {
        return first == null ? null : first.data;
    }

    public void addFirst(char chr) {
        Node newNode = new Node(new CharData(chr), first);
        first = newNode;
        size++;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node current = first;
        while (current != null) {
            builder.append(current.data.getChar());
            if (current.next != null) {
                builder.append(", ");
            }
            current = current.next;
        }
        builder.append("]");
        return builder.toString();
    }

    public int indexOf(char chr) {
        Node current = first;
        int index = 0;
        while (current != null) {
            if (current.data.getChar() == chr) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public void update(char chr) {
        Node current = first;
        while (current != null) {
            if (current.data.getChar() == chr) {
                current.data.incrementCount();
                return;
            }
            current = current.next;
        }
        addFirst(chr);
    }

    public boolean remove(char chr) {
        if (first == null) return false;
        if (first.data.getChar() == chr) {
            first = first.next;
            size--;
            return true;
        }

        Node current = first;
        while (current.next != null) {
            if (current.next.data.getChar() == chr) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public CharData get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public CharData[] toArray() {
        CharData[] arr = new CharData[size];
        Node current = first;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }

    public ListIterator listIterator(int index) {
        if (size == 0) return null;
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return new ListIterator(current);
    }
}
