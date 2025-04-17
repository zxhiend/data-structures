package com.datastruct;

public class HuffmanCoding {
    public static void main(String[] args) {
        char[] charArray = { 'E', 'T', 'N', 'I', 'S'};
        int[] charFreq = { 29, 10, 9, 5, 4};
        
        // buat priority queue dengan heap min
        Heap<Integer, HuffmanNode> pq = new Heap<>();
        
        // inputkan setiap huruf dan frekuensinya ke pq
        for(int i=0; i < charArray.length; i++) {
            pq.insert(charFreq[i], new HuffmanNode(charFreq[i], charArray[i]));
        }
        
        // membuat heap minimum dari pq
        HuffmanNode root = null;
        HuffmanNode x, y;
        int sum;
        
        while(pq.size() > 1) {
            sum = pq.getKey(pq.first());
            x = pq.getData(pq.first());
            pq.removeFirst();
            
            sum += pq.getKey(pq.first());
            y = pq.getData(pq.first());
            pq.removeFirst();
            
            root = new HuffmanNode(sum, '\0', x, y);
            pq.insert(sum, root);
        }
        
        MuArrayList<String> codeList;
        codeList = root.getHuffmanCodes(root, 5);
        
        System.out.println("-------------------------");
        System.out.println(" Huruf | Huffman code ");
        System.out.println("-------------------------");
        
        for(int i=0; i < codeList.size(); i++) {
            // split setiap string di ArrayList untuk mendapatkan huruf dan Huffman codenya
            String[] parts = codeList.get(i).split(" ");
            // tampilkan huruf dan Huffman codenya
            System.out.println(" " + parts[0] + "      | " + parts[1]);
        }
        
        System.out.println("-------------------------");
    }
}