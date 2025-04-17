package com.datastruct;

import java.util.ArrayList;

public class Heap<K extends Comparable<K>, V> {
    private ArrayList<HeapNode<K, V>> heapArray;
    
    public Heap() {
        heapArray = new ArrayList<>();
    }
    
    public void insert(K key, V data) {
        HeapNode<K, V> newNode = new HeapNode<>(key, data);
        heapArray.add(newNode);
        siftUp(heapArray.size() - 1);
    }
    
    public int first() {
        if (heapArray.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return 0;
    }
    
    public K getKey(int index) {
        return heapArray.get(index).key;
    }
    
    public V getData(int index) {
        return heapArray.get(index).data;
    }
    
    public void removeFirst() {
        if (heapArray.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int lastIndex = heapArray.size() - 1;
        heapArray.set(0, heapArray.get(lastIndex));
        heapArray.remove(lastIndex);
        
        if (heapArray.size() > 0) {
            siftDown(0);
        }
    }
    
    public int size() {
        return heapArray.size();
    }
    
    private void siftUp(int index) {
        HeapNode<K, V> node = heapArray.get(index);
        int parentIndex = (index - 1) / 2;
        
        while (index > 0 && node.key.compareTo(heapArray.get(parentIndex).key) < 0) {
            heapArray.set(index, heapArray.get(parentIndex));
            index = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        
        heapArray.set(index, node);
    }
    
    private void siftDown(int index) {
        int size = heapArray.size();
        HeapNode<K, V> node = heapArray.get(index);
        int smallerChildIndex;
        
        while (index < size / 2) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;
            
            if (rightChildIndex < size && 
                heapArray.get(rightChildIndex).key.compareTo(heapArray.get(leftChildIndex).key) < 0) {
                smallerChildIndex = rightChildIndex;
            } else {
                smallerChildIndex = leftChildIndex;
            }
            
            if (node.key.compareTo(heapArray.get(smallerChildIndex).key) <= 0) {
                break;
            }
            
            heapArray.set(index, heapArray.get(smallerChildIndex));
            index = smallerChildIndex;
        }
        
        heapArray.set(index, node);
    }
    
    private static class HeapNode<K, V> {
        K key;
        V data;
        
        public HeapNode(K key, V data) {
            this.key = key;
            this.data = data;
        }
    }
}