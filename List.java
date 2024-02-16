public class List {
    private Node first;
    private int size;

    public void addFirst(char chr) {
        CharData newCharData = new CharData(chr);

        Node newNode = new Node(newCharData, first);
        first = newNode;

        size++;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node current = first;

        while (current != null) {
            result.append(current.cp.toString());

            if (current.next != null) {
                result.append(", ");
            }

            current = current.next;
        }

        result.append("]");
        return result.toString();
    }


    public int indexOf(char chr) {
        Node current = first;
        int index = 0;

        while (current != null) {
            if (current.cp.getChar() == chr) {
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
            if (current.cp.getChar() == chr) {
                current.cp.incrementCounter();
                return;
            }

            current = current.next;
        }

 
        addFirst(chr);
    }


    public boolean remove(char chr) {
        Node current = first;
        Node previous = null;

        while (current != null) {
            if (current.cp.getChar() == chr) {
                if (previous != null) {
 
                    previous.next = current.next;
                } else {

                    first = current.next;
                }

                size--;

                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }


    public CharData get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node current = first;
        int currentIndex = 0;

        while (currentIndex < index) {
            current = current.next;
            currentIndex++;
        }

        return current.cp;
    }

    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    public ListIterator listIterator(int index) {

	    if (size == 0) return null;

	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
	    return new ListIterator(current);
    }
}
