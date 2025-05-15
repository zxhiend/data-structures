package com.datastruct;

/*
 * 
 * Kelompok 5
 * Shinichi Wijaya  535240025
 * Faiz Abdurrahman 535240041
 * Alvin Sanjaya    535240115
 *  
 */

public class QuadraticProbing<K, V> extends Hashing<K, V> {
    public QuadraticProbing(int capacity) {
        super(capacity);
    }

    @Override
    public void put(K key, V value) {
        HashNode<K, V> N = new HashNode<K, V>(key, value);
        int theKey = convertToNumber(key) % table.maxSize();
        int curKey = theKey;
        boolean firstScan = true;
        int i = 1;

        while (isCollision(curKey) && curKey >= 0) {
            curKey = (theKey + i * i) % table.maxSize();
            i++;
            if (i > table.maxSize() || (curKey == theKey && !firstScan)) {
                curKey = -1;
                break;
            }
            firstScan = false;
        }

        if (curKey >= 0) {
            table.set(curKey, N);
            incSize();
        } else
            System.out.println("Table is full!");
    }

    public HashNode<K, V> get(K key) {
        int theKey = convertToNumber(key) % table.maxSize();
        int curKey = theKey;
        boolean firstScan = true;
        int i = 1;

        while (isCollision(curKey) && convertToNumber(table.get(curKey).key) != convertToNumber(key) && curKey >= 0) {
            curKey = (theKey + i * i) % table.maxSize();
            i++;
            if (i > table.maxSize() || (curKey == theKey && !firstScan)) {
                curKey = -1;
                break;
            }
            firstScan = false;
        }

        if (curKey >= 0) {
            return table.get(curKey);
        } else {
            System.out.println("Not found!");
            return null;
        }
    }

    public HashNode<K, V> remove(K key) {
        HashNode<K, V> N;
        int theKey = convertToNumber(key) % table.maxSize();
        int curKey = theKey;
        boolean firstScan = true;
        int i = 1;

        while (isCollision(curKey) && convertToNumber(table.get(curKey).key) != convertToNumber(key) && curKey >= 0) {
            curKey = (theKey + i * i) % table.maxSize();
            i++;
            if (i > table.maxSize() || (curKey == theKey && !firstScan)) {
                curKey = -1;
                break;
            }
            firstScan = false;
        }

        if (curKey >= 0) {
            N = table.get(curKey);
            table.set(curKey, null);
            decSize();
            return N;
        } else {
            System.out.println("Not found!");
            return null;
        }
    }
}
