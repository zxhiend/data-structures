package com.datastruct;
/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */

import java.util.*;

class Edge<T> {
  private T neighbor; // connected vertex
  private int weight; // weight

  // Constructor, Time O(1) Space O(1)
  public Edge(T v, int w) {
    this.neighbor = v;
    this.weight = w;
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
    return "(" + neighbor + "," + weight + ")";
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

public class Graph<T> {
  // Map<T, LinkedList<Edge<T>>> adj;
  private Map<T, MyLinearList<Edge<T>>> adj;
  boolean directed;

  // Constructor, Time O(1) Space O(1)
  public Graph(boolean type) {
    adj = new HashMap<>();
    directed = type; // false: undirected, true: directed
  }

  // Add edges including adding nodes, Time O(1) Space O(1)
  public void addEdge(T a, T b, int w) {
    adj.putIfAbsent(a, new MyLinearList<>()); // add node
    adj.putIfAbsent(b, new MyLinearList<>()); // add node
    Edge<T> edge1 = new Edge<>(b, w);
    adj.get(a).pushQ(edge1);// add(edge1); //add edge
    if (!directed) { // undirected
      Edge<T> edge2 = new Edge<>(a, w);
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
    MyLinearList<Edge<T>> edgesFromA = adj.get(a);
    Node<Edge<T>> currA = edgesFromA.head;

    while (currA != null) {
      if (currA.getData().getNeighbor().equals(b)) {
        edgeDeleted = edgesFromA.remove(currA.getData());
        break;
      }
      currA = currA.getNext();
    }

    // For undirected graph, also delete edge from b to a
    if (!directed && edgeDeleted) {
      MyLinearList<Edge<T>> edgesFromB = adj.get(b);
      Node<Edge<T>> currB = edgesFromB.head;

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
      MyLinearList<Edge<T>> thelist = adj.get(key);
      Node<Edge<T>> curr = thelist.head;
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
      MyLinearList<Edge<T>> edges = adj.get(current);
      Node<Edge<T>> curr = edges.head;

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
      MyLinearList<Edge<T>> edges = adj.get(current);
      Node<Edge<T>> curr = edges.head;

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
      MyLinearList<Edge<T>> edges = adj.get(current);
      Node<Edge<T>> curr = edges.head;

      while (curr != null) {
        Edge<T> edge = curr.getData();
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
}
