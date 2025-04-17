package com.datastruct;

public class HuffmanCoding {
    public static void main(String[] args) {

        char[] charArray = { 'A', 'F', 'I', 'K', 'M', 'N', 'O', 'R', 'T' };
        int[] charFreq = { 45, 8, 35, 15, 29, 10, 19, 5, 4 };

        // buat priority queue dengan heap min
        Heap<Integer, HuffmanNode> pq = new Heap<Integer, HuffmanNode>(charArray.length, true);

        // inputkan setiap huruf dan frekuensinya ke pq
        for (int i = 0; i < charArray.length; i++) {
            pq.insert(charFreq[i], new HuffmanNode(charFreq[i], charArray[i], null, null));
        }

        pq.buildHeap();

        pq.display();

        // membuat heap minimum dari pq
        HuffmanNode root = null;
        HuffmanNode x, y;
        int sum;

        while (pq.size() > 1) {
            // Extract the two nodes with the smallest frequencies
            x = pq.getData(pq.first());
            sum = pq.getKey(pq.first());
            pq.removeFirst();

            y = pq.getData(pq.first());
            sum += pq.getKey(pq.first());
            pq.removeFirst();

            // Ensure the smaller frequency node is always on the left
            if (x.getKey() > y.getKey()) {
                HuffmanNode temp = x;
                x = y;
                y = temp;
            }

            root = new HuffmanNode(sum, '-', x, y);
            pq.insert(sum, root);
        }

        MyArrayList<String> codeList;
        codeList = root.getHuffmanCodes(root, 9);

        System.out.println("-------------------------");
        System.out.println(" Huruf | Huffman code ");
        System.out.println("-------------------------");

        for (int i = 0; i < codeList.size(); i++) {
            // split setiap string di ArrayList untuk mendapatkan huruf dan Huffman codenya
            String[] parts = codeList.get(i).split(" ");
            // tampilkan huruf dan Huffman codenya
            System.out.println(" " + parts[0] + "     | " + parts[1]);
        }

        System.out.println("-------------------------");


        /*
          1.b.
          Output:
            -------------------------
            Huruf | Huffman code
            -------------------------
            I     | 11
            F     | 1011
            T     | 10101
            R     | 10100
            O     | 100
            A     | 01
            N     | 0011
            K     | 0010
            M     | 000
            -------------------------
          1.c. INFORMATIKA = 11 0011 1011 100 10100 000 01 00101 11 0010 01 = 11 0011 1011 100 10100 000 01 00101 11 0010 01   
          1.d. 10111001010000001 = 1011 100 10100 000 01 
                                F     O    R    M  A  


        */

         
    }
}