package com.datastruct;
/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */

import java.util.*;

class WeightedEdge<T> {
  private T vertex;
  private T neighbor; // connected vertex
  private int weight; // weight

  // Constructor, Time O(1) Space O(1)
  public WeightedEdge(T u, T v, int w) {
    this.vertex = u;
    this.neighbor = v;
    this.weight = w;
  }

  public void setVertex(T vertex) {
    this.vertex = vertex;
  }

  public T getVertex() {
    return vertex;
  }

  public void setNeighbor(T neighbor) {
    this.neighbor = neighbor;
  }

  public T getNeighbor() {
    return neighbor;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  // Time O(1) Space O(1)
  @Override
  public String toString() {
    return "(" + vertex.toString() + "," + neighbor.toString() + "," + weight + ")";
  }
}

// Class to store vertex with its distance from source for Dijkstra's algorithm
class VertexDistance<T> {
  private T vertex;
  private int distance;

  public VertexDistance(T vertex, int distance) {
    this.vertex = vertex;
    this.distance = distance;
  }

  public T getVertex() {
    return vertex;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    return "(" + vertex + "," + distance + ")";
  }
}

public class WeightedGraph<T> {
  // Map<T, LinkedList<Edge<T>>> adj;
  private Map<T, MyLinearList<WeightedEdge<T>>> adj;
  boolean directed;

  // Constructor, Time O(1) Space O(1)
  public WeightedGraph(boolean type) {
    adj = new HashMap<>();
    directed = type; // false: undirected, true: directed
  }

  // Add edges including adding nodes, Time O(1) Space O(1)
  public void addEdge(T a, T b, int w) {
    adj.putIfAbsent(a, new MyLinearList<>()); // add node
    adj.putIfAbsent(b, new MyLinearList<>()); // add node
    WeightedEdge<T> edge1 = new WeightedEdge<>(a, b, w);
    adj.get(a).pushQ(edge1);// add(edge1); //add edge
    if (!directed) { // undirected
      WeightedEdge<T> edge2 = new WeightedEdge<>(b, a, w);
      adj.get(b).pushQ(edge2);
    }
  }

  // Delete edge, Time O(V+E), Space O(1)
  public boolean deleteEdge(T a, T b) {
    // Check if both vertices exist in the graph
    if (!adj.containsKey(a) || !adj.containsKey(b)) {
      return false;
    }

    boolean edgeDeleted = false;

    // Delete edge from a to b
    MyLinearList<WeightedEdge<T>> edgesFromA = adj.get(a);
    Node<WeightedEdge<T>> currA = edgesFromA.head;

    while (currA != null) {
      if (currA.getData().getNeighbor().equals(b)) {
        edgeDeleted = edgesFromA.remove(currA.getData());
        break;
      }
      currA = currA.getNext();
    }

    // For undirected graph, also delete edge from b to a
    if (!directed && edgeDeleted) {
      MyLinearList<WeightedEdge<T>> edgesFromB = adj.get(b);
      Node<WeightedEdge<T>> currB = edgesFromB.head;

      while (currB != null) {
        if (currB.getData().getNeighbor().equals(a)) {
          edgesFromB.remove(currB.getData());
          break;
        }
        currB = currB.getNext();
      }
    }

    return edgeDeleted;
  }

  // Print graph as hashmap, Time O(V+E), Space O(1)
  public void printGraph() {
    for (T key : adj.keySet()) {
      // System.out.println(key.toString() + " : " + adj.get(key).toString());
      System.out.print(key.toString() + " : ");
      MyLinearList<WeightedEdge<T>> thelist = adj.get(key);
      Node<WeightedEdge<T>> curr = thelist.head;
      while (curr != null) {
        System.out.print(curr.getData());
        curr = curr.getNext();
      }
      System.out.println();
    }
  }

  // DFS - Depth First Search
  public void DFS(T src) {
    // Check if source vertex exists
    if (!adj.containsKey(src)) {
      System.out.println("Source vertex not found in graph!");
      return;
    }

    // Create a set to track visited vertices
    Set<T> visited = new HashSet<>();

    // Create a stack using MyLinearList for DFS traversal
    MyLinearList<T> stack = new MyLinearList<>();

    // Push the source vertex onto the stack
    stack.pushS(src);

    System.out.print("DFS Traversal from " + src + ": ");

    while (!stack.isEmpty()) {
      // Pop a vertex from the stack
      T current = stack.remove();

      // Skip if already visited
      if (visited.contains(current)) {
        continue;
      }

      // Mark as visited and print
      visited.add(current);
      System.out.print(current + " ");

      // Get all adjacent vertices
      MyLinearList<WeightedEdge<T>> edges = adj.get(current);
      Node<WeightedEdge<T>> curr = edges.head;

      // Create a temporary list to reverse the order of neighbors
      MyLinearList<T> tempList = new MyLinearList<>();

      // Add all unvisited neighbors to the temporary list
      while (curr != null) {
        T neighbor = curr.getData().getNeighbor();
        if (!visited.contains(neighbor)) {
          tempList.pushS(neighbor);
        }
        curr = curr.getNext();
      }

      // Push all neighbors from temp list to stack (maintains proper DFS order)
      while (!tempList.isEmpty()) {
        stack.pushS(tempList.remove());
      }
    }
    System.out.println();
  }

  // BFS - Breadth First Search
  public void BFS(T src) {
    // Check if source vertex exists
    if (!adj.containsKey(src)) {
      System.out.println("Source vertex not found in graph!");
      return;
    }

    // Create a set to track visited vertices
    Set<T> visited = new HashSet<>();

    // Create a queue using MyLinearList for BFS traversal
    MyLinearList<T> queue = new MyLinearList<>();

    // Add the source vertex to visited set and enqueue it
    visited.add(src);
    queue.pushQ(src);

    System.out.print("BFS Traversal from " + src + ": ");

    while (!queue.isEmpty()) {
      // Dequeue a vertex
      T current = queue.remove();

      // Print the current vertex
      System.out.print(current + " ");

      // Get all adjacent vertices of the dequeued vertex
      MyLinearList<WeightedEdge<T>> edges = adj.get(current);
      Node<WeightedEdge<T>> curr = edges.head;

      // For each adjacent vertex, if not visited, mark as visited and enqueue it
      while (curr != null) {
        T neighbor = curr.getData().getNeighbor();

        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.pushQ(neighbor);
        }

        curr = curr.getNext();
      }
    }
    System.out.println();
  }

  // Finds the vertex with the minimum distance value from the set of vertices not
  // yet included in shortest path tree
  private T getMinDistanceVertex(Map<T, Integer> distances, Set<T> visited) {
    T minVertex = null;
    int minDistance = Integer.MAX_VALUE;

    for (Map.Entry<T, Integer> entry : distances.entrySet()) {
      T vertex = entry.getKey();
      int distance = entry.getValue();

      if (!visited.contains(vertex) && distance < minDistance) {
        minDistance = distance;
        minVertex = vertex;
      }
    }

    return minVertex;
  }

  // Reconstructs and prints the path from source to a destination
  private void printPath(Map<T, T> predecessors, T destination) {
    MyLinearList<T> path = new MyLinearList<>();
    T step = destination;

    // Check if a path exists
    if (predecessors.get(step) == null && !path.isEmpty()) {
      System.out.println("No path exists to " + destination);
      return;
    }

    // Build path from destination to source
    while (step != null) {
      path.pushS(step);
      step = predecessors.get(step);
    }

    // Print the path
    System.out.print("Path: ");
    while (!path.isEmpty()) {
      T current = path.remove();
      System.out.print(current);
      if (!path.isEmpty()) {
        System.out.print(" -> ");
      }
    }
    System.out.println();
  }

  // Dijkstra's algorithm to find shortest paths from source vertex to all other
  // vertices
  public void shortestPath(T src) {
    // Check if source vertex exists
    if (!adj.containsKey(src)) {
      System.out.println("Source vertex not found in graph!");
      return;
    }

    // Map to store the shortest distance from source to each vertex
    Map<T, Integer> distances = new HashMap<>();

    // Map to store the predecessor of each vertex in the shortest path
    Map<T, T> predecessors = new HashMap<>();
    // Set to keep track of vertices whose shortest path has been found
    Set<T> visited = new HashSet<>();

    // Initialize distances to all vertices as INFINITE except source
    for (T vertex : adj.keySet()) {
      distances.put(vertex, Integer.MAX_VALUE);
    }
    distances.put(src, 0);

    // Find shortest path for all vertices
    for (int i = 0; i < adj.size(); i++) {
      // Get the vertex with minimum distance
      T current = getMinDistanceVertex(distances, visited);

      // If no more reachable vertices
      if (current == null) {
        break;
      }

      // Mark current vertex as processed
      visited.add(current);

      // Update distances of adjacent vertices
      MyLinearList<WeightedEdge<T>> edges = adj.get(current);
      Node<WeightedEdge<T>> curr = edges.head;

      while (curr != null) {
        WeightedEdge<T> edge = curr.getData();
        T neighbor = edge.getNeighbor();
        int weight = edge.getWeight();

        // If vertex is not processed and there is a shorter path through current
        if (!visited.contains(neighbor)) {
          int newDistance = distances.get(current) + weight;

          if (newDistance < distances.get(neighbor)) {
            distances.put(neighbor, newDistance);
            predecessors.put(neighbor, current);
          }
        }

        curr = curr.getNext();
      }
    }

    // Print shortest distances and paths
    System.out.println("Shortest paths from " + src + ":");
    for (T vertex : adj.keySet()) {
      if (!vertex.equals(src)) {
        int distance = distances.get(vertex);

        if (distance == Integer.MAX_VALUE) {
          System.out.println(vertex + ": Not reachable");
        } else {
          System.out.println(vertex + ": Distance = " + distance);
          printPath(predecessors, vertex);
        }
      }
    }
  }

  public void MSTPrim() {
    if (adj.isEmpty()) {
      System.out.println("Graph is empty!");
      return;
    }

    if (directed) {
      System.out.println("MST is only applicable to undirected graphs!");
      return;
    }

    T startVertex = adj.keySet().iterator().next(); // Pick any vertex as start
    Set<T> inMST = new HashSet<>();
    Heap<Integer, WeightedEdge<T>> minHeap = new Heap<>(1000, true); // Min-heap
    MyLinearList<WeightedEdge<T>> mstEdges = new MyLinearList<>();
    int totalWeight = 0;

    inMST.add(startVertex);

    // Insert all edges from the starting vertex
    Node<WeightedEdge<T>> curr = adj.get(startVertex).head;
    while (curr != null) {
      WeightedEdge<T> edge = curr.getData();
      minHeap.insert(edge.getWeight(), edge);
      curr = curr.getNext();
    }

    System.out.print("[");
    while (inMST.size() < adj.size() && minHeap.size() > 0) {
      BTNode<Integer, WeightedEdge<T>> minNode = minHeap.removeFirst();
      if (minNode == null)
        break;

      WeightedEdge<T> minEdge = minNode.getData();
      T to = minEdge.getNeighbor();

      if (inMST.contains(to)) {
        continue;
      }

      inMST.add(to);
      mstEdges.pushQ(minEdge);
      totalWeight += minEdge.getWeight();
      System.out.print(minEdge.toString());

      // Insert all edges from the newly added vertex
      Node<WeightedEdge<T>> neighborEdge = adj.get(to).head;
      while (neighborEdge != null) {
        WeightedEdge<T> edge = neighborEdge.getData();
        if (!inMST.contains(edge.getNeighbor())) {
          minHeap.insert(edge.getWeight(), edge);
        }
        neighborEdge = neighborEdge.getNext();
      }
    }
    System.out.println("]");

    System.out.println("MST Length: " + totalWeight);

    if (inMST.size() < adj.size()) {
      System.out.println("Graph is not connected - MST incomplete!");
    }
  }

  private class UnionFind<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;

    public UnionFind() {
      parent = new HashMap<>();
      rank = new HashMap<>();
    }

    public void makeSet(T vertex) {
      parent.put(vertex, vertex);
      rank.put(vertex, 0);
    }

    public T find(T vertex) {
      if (!parent.get(vertex).equals(vertex)) {
        parent.put(vertex, find(parent.get(vertex))); // Path compression
      }
      return parent.get(vertex);
    }

    public boolean union(T x, T y) {
      T rootX = find(x);
      T rootY = find(y);

      if (rootX.equals(rootY)) {
        return false; // Already in same set (would create cycle)
      }

      // Union by rank
      int rankX = rank.get(rootX);
      int rankY = rank.get(rootY);

      if (rankX < rankY) {
        parent.put(rootX, rootY);
      } else if (rankX > rankY) {
        parent.put(rootY, rootX);
      } else {
        parent.put(rootY, rootX);
        rank.put(rootX, rankX + 1);
      }

      return true;
    }
  }

  // MST using Kruskal's Algorithm
  public void MSTKruskal() {
    if (adj.isEmpty()) {
      System.out.println("Graph is empty!");
      return;
    }

    // For directed graphs, MST doesn't make sense
    if (directed) {
      System.out.println("MST is only applicable to undirected graphs!");
      return;
    }

    // Create list of all edges
    MyLinearList<WeightedEdge<T>> allEdges = new MyLinearList<>();
    Set<String> addedEdges = new HashSet<>(); // To avoid duplicate edges in undirected graph

    for (T vertex : adj.keySet()) {
      MyLinearList<WeightedEdge<T>> edges = adj.get(vertex);
      Node<WeightedEdge<T>> curr = edges.head;

      while (curr != null) {
        WeightedEdge<T> edge = curr.getData();
        T neighbor = edge.getNeighbor();

        // Create edge identifier to avoid duplicates
        String edgeId = vertex.toString().compareTo(neighbor.toString()) < 0 ? vertex + "-" + neighbor
            : neighbor + "-" + vertex;

        if (!addedEdges.contains(edgeId)) {
          allEdges.pushQ(new WeightedEdge<>(vertex, neighbor, edge.getWeight()));
          addedEdges.add(edgeId);
        }

        curr = curr.getNext();
      }
    }

    // Sort edges by weight using heap
    Heap<Integer, WeightedEdge<T>> edgeHeap = new Heap<>(1000, true);

    Node<WeightedEdge<T>> curr = allEdges.head;
    while (curr != null) {
      WeightedEdge<T> edge = curr.getData();
      edgeHeap.insert(edge.getWeight(), edge);
      curr = curr.getNext();
    }

    // Initialize Union-Find
    UnionFind<T> unionFind = new UnionFind<>();
    for (T vertex : adj.keySet()) {
      unionFind.makeSet(vertex);
    }

    int totalWeight = 0;
    int edgesAdded = 0;
    int targetEdges = adj.size() - 1;

    System.out.print("[");
    while (edgesAdded < targetEdges && edgeHeap.size() > 0) {
      BTNode<Integer, WeightedEdge<T>> minNode = edgeHeap.removeFirst();
      if (minNode == null)
        break;

      WeightedEdge<T> edge = minNode.getData();

      if (unionFind.union(edge.getVertex(), edge.getNeighbor())) {
        System.out.print(edge.toString());
        totalWeight += edge.getWeight();
        edgesAdded++;
      }
    }
    System.out.println("]");

    System.out.println("MST length: " + totalWeight);

    if (edgesAdded < targetEdges) {
      System.out.println("Graph is not connected - MST incomplete!");
    }
  }
}
