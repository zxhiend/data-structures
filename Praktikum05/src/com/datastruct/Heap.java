package com.datastruct;

public class Heap<K extends Comparable<? super K>, V> {
    private MyArrayList<BTNode<K, V>> arrList;
    private boolean priority;

    public Heap(int capacity, boolean priority) {
        arrList = new MyArrayList<BTNode<K, V>>(capacity);
        this.priority = priority;
    }

    public int size() {
        return arrList.size();
    }

    public V getData(int index) {
        return arrList.get(index).getData();
    }

    public V getData(BTNode<K, V> node) {
        return node.getData();
    }

    public K getKey(int index) {
        return arrList.get(index).getKey();
    }

    public K getKey(BTNode<K, V> node) {
        return node.getKey();
    }

    public void add(K key, V data) {
        arrList.add(new BTNode<K, V>(key, data));
    }

    public void insert(K key, V data) {
        arrList.add(new BTNode<K, V>(key, data));
        int size = arrList.size();
        for (int i = size / 2 - 1; i >= 0; i = (i + 1) / 2 - 1) {
            if (priority)
                heapifyMin(size, i);
            else
                heapifyMax(size, i);
        }
    }

    public void buildHeap() {
        int size = arrList.size();
        for (int i = size / 2 - 1; i >= 0; i--) {
            if (priority)
                heapifyMin(size, i);
            else
                heapifyMax(size, i);
        }
    }

    public void heapifyMax(int size, int i) {
        int parent = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && arrList.get(left).getKey().compareTo(arrList.get(parent).getKey()) > 0)
            parent = left;
        if (right < size && arrList.get(right).getKey().compareTo(arrList.get(parent).getKey()) > 0)
            parent = right;

        if (parent != i) {
            BTNode<K, V> temp = arrList.get(i);
            arrList.set(i, arrList.get(parent));
            arrList.set(parent, temp);
            heapifyMax(size, parent);
        }
    }

    public void sort() {
        int size = arrList.size();
        if (size <= 1)
            return;

        buildHeap();

        for (int i = size - 1; i > 0; i--) {
            BTNode<K, V> temp = arrList.get(0);
            arrList.set(0, arrList.get(i));
            arrList.set(i, temp);
            if (priority)
                heapifyMin(i, 0);
            else
                heapifyMax(i, 0);
        }
    }

    private void heapifyMin(int size, int i) {
        int parent = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && arrList.get(left).getKey().compareTo(arrList.get(parent).getKey()) < 0)
            parent = left;
        if (right < size && arrList.get(right).getKey().compareTo(arrList.get(parent).getKey()) < 0)
            parent = right;

        if (parent != i) {
            BTNode<K, V> temp = arrList.get(i);
            arrList.set(i, arrList.get(parent));
            arrList.set(parent, temp);
            heapifyMin(size, parent);
        }
    }

    public BTNode<K, V> first() {
        if (arrList.size() == 0)
            return null; // Handle empty heap
        return arrList.get(0);
    }

    public BTNode<K, V> removeFirst() {
        if (arrList.size() == 0)
            return null; // Handle empty heap safely
        BTNode<K, V> temp = first();
        arrList.set(0, arrList.get(arrList.size() - 1));
        arrList.remove(arrList.size() - 1);
        if (arrList.size() > 0) {
            if (priority)
                heapifyMin(arrList.size(), 0);
            else
                heapifyMax(arrList.size(), 0);
        }
        return temp;
    }

    public void display() {
        arrList.cetakList();
    }
}