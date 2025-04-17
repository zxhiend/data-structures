package com.datastruct;

public class HuffmanNode extends BTNode<Integer, Character> {
    private MuArrayList<String> codeList;
    
    public HuffmanNode(int freq, char letter) {
        super(freq, letter);
        setLlink(null);
        setRlink(null);
    }
    
    public HuffmanNode(int freq, char letter, HuffmanNode node1, HuffmanNode node2) {
        super(freq, letter);
        setLlink(node1);
        setRlink(node2);
    }
    
    public MuArrayList<String> getHuffmanCodes(HuffmanNode root, int n) {
        String s = "";
        codeList = new MuArrayList<String>(n);
        printCode(root, s);
        return codeList;
    }
    
    // print code (deretan bit)
    public void printCode(HuffmanNode node, String s) {
        if (node.getLlink() == null && node.getRlink() == null) {
            codeList.add(node.getData() + " " + s);
            return;
        }
        
        printCode((HuffmanNode)node.getLlink(), s + "0");
        printCode((HuffmanNode)node.getRlink(), s + "1");
    }
}