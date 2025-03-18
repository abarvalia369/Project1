package util;


import java.util.Iterator;
public class List<E> implements Iterable<E> {

    private static final int INITIAL_CAP = 4;
    private static final int GROW = 4;
    private static final int NOT_FOUND = -1;
    private E[] objects;
    private int size;


    public List() {
        this.objects = (E[]) new Object[INITIAL_CAP];
        this.size = 0;
    }


    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private void grow() {
        E[] grown = (E[]) new Object [objects.length + GROW];
        for(int i = 0; i < size; i++){
            grown[i] = this.objects[i];
        }
        this.objects = grown;

    }

    public boolean contains(E e) {
        return find(e) != NOT_FOUND;

    }

    public void add(E e) {
        if(this.contains(e)){
            return;
        }
        if (size == objects.length) {
            grow();
        }
        for(int i =0; i < objects.length; i++){
            if(this.objects[i] == null){
                this.objects[i] = e;
                this.size++;
                break;
            }
        }

    }

    public void remove(E e){
        if (this.contains(e)) {
            this.objects[this.indexOf(e)] = this.objects[this.size - 1];
            this.objects[this.size - 1] = null;
            this.size--;
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    } //or traversing the list

    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return objects[index];
    }

    public void set(int index, E e) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        this.objects[index] = e;
    }

    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private class ListIterator<E> implements Iterator<E> {
        int current = 0; //current index when traversing the list (array)

        @Override
        public boolean hasNext(){
            return current < size;

        } //false if it’s empty or at end of list
        public E next(){
            if (!hasNext()) throw new IndexOutOfBoundsException();
            return (E) objects[current++];
        } //return the next object in the list
    }
}