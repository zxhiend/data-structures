package com.datastruct;

public class MuArrayList<E> {
    private Object[] elementData;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    
    public MuArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }
    
    public MuArrayList(int initialCapacity) {
        if (initialCapacity >= 0) {
            this.elementData = new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.size = 0;
    }
    
    public boolean add(E e) {
        ensureCapacity(size + 1);
        elementData[size++] = e;
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elementData[index];
    }
    
    public int size() {
        return size;
    }
    
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            int newCapacity = Math.max(elementData.length * 2, minCapacity);
            Object[] newData = new Object[newCapacity];
            System.arraycopy(elementData, 0, newData, 0, size);
            elementData = newData;
        }
    }
}