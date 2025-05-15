package com.datastruct;

public class BinarySearchTree<K extends Comparable<? super K>, V>
        extends BinaryTree<K, V>
        implements Tree<K, V> {
    private BTNode<K, V> root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(K key, V data) {
        root = insertNode(root, key, data);
    }

    public void delete(K key) {
        root = deleteNode(root, key);
    }

    public V search(K key) {
        V info = null;
        info = getData(find(root, key));
        return info;
    }

    public K max() {
        K kunci = null;
        kunci = getKey(findMax(root));
        return kunci;
    }

    public K min() {
        K kunci = null;
        kunci = getKey(findMin(root));
        return kunci;
    }

    // slide selanjutya...

    private BTNode<K, V> insertNode(BTNode<K, V> node, K k, V data) {
        // Jika tree masih kosong (belum ada node sama sekali), atau
        // T sebelumnnya adalah child node
        // Buat node baru yang akan dilink-kan ke child node sebelumnya
        if (node == null) {
            // buat node baru
            BTNode<K, V> newNode = new BTNode<K, V>(k, data);
            return newNode;
        }
        // key dari node baru lebih kecil dari key child node sebelumnya
        // go to the left node (subtree)
        // node baru akan dilink ke left link
        else if (k.compareTo(node.getKey()) < 0) {
            node.setLlink(insertNode(node.getLlink(), k, data));
            return node;
        }
        // key dari node baru lebih besar dari ata sama dengan
        // key child node sebelumnya
        // go to the right node (subtree)
        // node baru akan dilink ke right link
        else {
            node.setRlink(insertNode(node.getRlink(), k, data));
            return node;
        }
    }

    private BTNode<K, V> deleteNode(BTNode<K, V> node, K k) {
        if (node == null) {
            return null; // Node not found
        }

        if (k.compareTo(node.getKey()) < 0) {
            node.setLlink(deleteNode(node.getLlink(), k)); // Go to the left subtree
        } else if (k.compareTo(node.getKey()) > 0) {
            node.setRlink(deleteNode(node.getRlink(), k)); // Go to the right subtree
        } else {
            // Node to be deleted found
            if (node.getLlink() == null) {
                return node.getRlink(); // Replace with right child
            } else if (node.getRlink() == null) {
                return node.getLlink(); // Replace with left child
            }

            // Node with two children: Get the inorder successor (smallest in the right
            // subtree)
            BTNode<K, V> temp = findMin(node.getRlink());
            node.setKey(temp.getKey());
            node.setData(temp.getData());
            node.setRlink(deleteNode(node.getRlink(), temp.getKey())); // Delete the inorder successor
        }

        return node;
    }

    private BTNode<K, V> find(BTNode<K, V> node, K k) {
        // node adalah subtree (root dari subtree)
        // jika key ditemukan pada node saat ini, kembalikan node
        if (node == null || node.getKey() == k)
            return node;
        // jika bukan dan k lebih besar telusuri ke subtree kanan
        else if (node.getKey().compareTo(k) < 0)

        {
            return find(node.getRlink(), k);
        }
        // jika bukan dan k lebih kecil telusuri ke subtree kiri
        else return find(node.getLlink(), k);
    }

    private BTNode<K, V> findMin(BTNode<K, V> node) {
        if (node == null) {
            return null; // Tree is empty
        }
        while (node.getLlink() != null) {
            node = node.getLlink(); // Traverse to the leftmost node
        }
        return node;
    }

    private BTNode<K, V> findMax(BTNode<K, V> node) {
        if (node == null) {
            return null; // Tree is empty
        }
        while (node.getRlink() != null) {
            node = node.getRlink(); // Traverse to the rightmost node
        }
        return node;
    }

    public void inOrder() {
        printInOrder(root);
    }
        
    public void preOrder() {
        printPreOrder(root);
    }

    public void postOrder() {
        printPostOrder(root);
    }

    public void levelOrder() {
        printLevelOrder(root);
    }
        
    public K getKey(BTNode<K,V> node) {
        return node.getKey();
    }
    
    public V getData(BTNode<K,V> node) {
        return node.getData();
    }
}
