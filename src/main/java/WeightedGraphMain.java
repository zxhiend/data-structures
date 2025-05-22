import com.datastruct.WeightedGraph;

public class WeightedGraphMain {
  public static void main(String[] args) {
    MyVertex va = new MyVertex("a");
    MyVertex vb = new MyVertex("b");
    MyVertex vc = new MyVertex("c");
    MyVertex vd = new MyVertex("d");
    MyVertex ve = new MyVertex("e");

    WeightedGraph<MyVertex> WG = new WeightedGraph<MyVertex>(false); // undirected
    WG.addEdge(va, vb, 4);
    WG.addEdge(va, vc, 6);
    WG.addEdge(vb, vc, 1);
    WG.addEdge(vb, vd, 3);
    WG.addEdge(vd, ve, 2);
    WG.addEdge(ve, vc, 1);

    System.out.println("Undirected Graph:");
    WG.printGraph();

    System.out.println("MST dengan Algoritma Prim: ");
    WG.MSTPrim();

    System.out.println("MST dengan Algoritma Kruskal: ");
    WG.MSTKruskal();

  }
}
