import com.datastruct.*;

class MyVertex {
  String nodeName;

  MyVertex(String name) {
    this.nodeName = name;
  }

  @Override
  public String toString() {
    return (nodeName);
  }
}

public class GraphMain {
  public static void main(String[] args) {
    // create vertex
    MyVertex v1 = new MyVertex("a");
    MyVertex v2 = new MyVertex("b");
    MyVertex v3 = new MyVertex("c");
    MyVertex v4 = new MyVertex("d");
    MyVertex v5 = new MyVertex("e");

    Graph<MyVertex> WG = new Graph<MyVertex>(false); // undirected
    WG.addEdge(v1, v2, 4);
    WG.addEdge(v1, v3, 6);
    WG.addEdge(v2, v3, 1);
    WG.addEdge(v2, v4, 2);
    WG.addEdge(v4, v5, 2);
    WG.addEdge(v5, v3, 1);

    System.out.println("Undirected Graph:");
    WG.printGraph();

    System.out.println("\n=== Traversal Algorithms ===");
    // Menjalankan DFS dari vertex a
    WG.DFS(v1);

    // Menjalankan BFS dari vertex a
    WG.BFS(v1);

    System.out.println("\n=== Shortest Path Algorithm ===");
    // Mencari dan menampilkan shortest path dari vertex a ke semua vertex lainnya
    WG.shortestPath(v1);

    // Mencoba shortest path dari vertex lain (e)
    System.out.println("\nShortest paths dari vertex e:");
    WG.shortestPath(v5);

    // Contoh menghapus edge dan melihat perubahan pada shortest path
    System.out.println("\nSetelah menghapus edge antara d dan e:");
    WG.deleteEdge(v4, v5);
    WG.printGraph();
    WG.shortestPath(v1);
  }
}
