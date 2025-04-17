package com.datastruct;

public class BTNode<K, V> {
    private K key;
    private V data;
    private BTNode<K, V> llink;
    private BTNode<K, V> rlink;
    
    public BTNode() {
        this.key = null;
        this.data = null;
        this.llink = null;
        this.rlink = null;
    }
    
    public BTNode(K key, V data) {
        this.key = key;
        this.data = data;
        this.llink = null;
        this.rlink = null;
    }
    
    public K getKey() {
        return key;
    }
    
    public void setKey(K key) {
        this.key = key;
    }
    
    public V getData() {
        return data;
    }
    
    public void setData(V data) {
        this.data = data;
    }
    
    public BTNode<K, V> getLlink() {
        return llink;
    }
    
    public void setLlink(BTNode<K, V> llink) {
        this.llink = llink;
    }
    
    public BTNode<K, V> getRlink() {
        return rlink;
    }
    
    public void setRlink(BTNode<K, V> rlink) {
        this.rlink = rlink;
    }
}