import java.io.*;
import java.util.Scanner;

class Queue {

  private class QueueEl {

    Node element;
    QueueEl next;

    public QueueEl(Node element) {
      this.element = element;
      this.next = null;
    }
  }

  private QueueEl head;
  private QueueEl tail;

  public Queue() {
    head = null;
    tail = null;
  }

  public void enqueue(Node element) {
    QueueEl newNode = new QueueEl(element);
    if (tail != null) {
      tail.next = newNode;
    }
    tail = newNode;
    if (head == null) {
      head = newNode;
    }
  }

  public Node dequeue() {
    if (head == null) {
      return null;
    }
    Node element = head.element;
    head = head.next;
    if (head == null) {
      tail = null;
    }
    return element;
  }

  public boolean isEmpty() {
    return head == null;
  }
}

class SetElement {

  Node conn;
  SetElement next;

  SetElement() {
    conn = null;
    next = null;
  }
}

class Set {

  private SetElement first;

  public Set() {
    makenull();
  }

  public void makenull() {
    first = new SetElement();
  }

  public SetElement first() {
    return first;
  }

  public SetElement next(SetElement pos) {
    return pos.next;
  }

  public void insert(Node conn) {
    // nov element vstavimo samo, ce ga ni med obstojecimi elementi mnozice
    if (!locate(conn)) {
      SetElement nov = new SetElement();
      nov.conn = conn;
      nov.next = first.next;
      first.next = nov;
    }
  }

  public void insertOrdered(Node conn) {
    // nov element vstavimo samo, ce ga ni med obstojecimi elementi mnozice
    if (!locate(conn)) {
      SetElement nov = new SetElement();
      nov.conn = conn;

      //poisce pozicijo
      SetElement prev = first;
      SetElement curr = first.next;
      while (curr != null && curr.conn.element > nov.conn.element) {
        prev = curr;
        curr = curr.next;
      }

      //vstavi element
      nov.next = curr;
      prev.next = nov;
    }
  }

  public void delete(SetElement pos) {
    pos.next = pos.next.next;
  }

  public boolean overEnd(SetElement pos) {
    if (pos.next == null) return true; else return false;
  }

  public boolean empty() {
    return first.next == null;
  }

  public Node retrieve(SetElement pos) {
    return pos.next.conn;
  }

  //vrne true, ce je element ze v mnozici
  public boolean locate(Node obj) {
    // sprehodimo se cez seznam elementov in preverimo enakost (uporabimo metodo equals)
    for (SetElement iter = first(); !overEnd(iter); iter = next(iter)) if (
      obj.equals(retrieve(iter))
    ) return true;

    return false;
  }

  public void print() {
    System.out.print("{");
    for (SetElement iter = first(); !overEnd(iter); iter = next(iter)) {
      System.out.print(retrieve(iter).element);
      if (!overEnd(next(iter))) System.out.print(", ");
    }
    System.out.println("}");
  }
}

class Node {

  int element;
  int h; //visina
  Set connections;

  Node prev;
  float percentage;
  int visitors;

  Node leftChild;
  Node rightChild;

  // parameterized constructor
  public Node(int element, float percentage) {
    connections = new Set();
    leftChild = null;
    rightChild = null;
    this.element = element;
    this.percentage = percentage;
    this.visitors = 0;
    h = 0;
  }

  public void addVisitors(int st) {
    visitors += st;
  }
}

class AVLTree {

  public Node rootNode;

  //Constructor to set null value to the rootNode
  public AVLTree() {
    rootNode = null;
  }

  //create removeAll() method to make AVL Tree empty
  public void removeAll() {
    rootNode = null;
  }

  // create checkEmpty() method to check whether the AVL Tree is empty or not
  public boolean checkEmpty() {
    if (rootNode == null) return true; else return false;
  }

  // create insertElement() to insert element to to the AVL Tree
  public void insertElement(int element, float percentage) {
    rootNode = insertElement(element, percentage, rootNode);
  }

  //create getHeight() method to get the height of the AVL Tree
  private int getHeight(Node node) {
    return node == null ? -1 : node.h;
  }

  //create maxNode() method to get the maximum height from left and right node
  private int getMaxHeight(int leftNodeHeight, int rightNodeHeight) {
    return leftNodeHeight > rightNodeHeight ? leftNodeHeight : rightNodeHeight;
  }

  //create insertElement() method to insert data in the AVL Tree recursively
  private Node insertElement(int element, float percentage, Node node) {
    //System.out.print("*");
    //check whether the node is null or not
    if (node == null) node = new Node(element, percentage);
    //insert a node in case when the given element is lesser than the element of the root node
    else if (element < node.element) {
      node.leftChild = insertElement(element, percentage, node.leftChild);
      if (getHeight(node.leftChild) - getHeight(node.rightChild) == 2) if (
        element < node.leftChild.element
      ) node = rotateWithLeftChild(node); else node = doubleWithLeftChild(node);
    } else if (element > node.element) {
      node.rightChild = insertElement(element, percentage, node.rightChild);
      if (getHeight(node.rightChild) - getHeight(node.leftChild) == 2) if (
        element > node.rightChild.element
      ) node = rotateWithRightChild(node); else node =
        doubleWithRightChild(node);
    } else; // if the element is already present in the tree, we will do nothing
    node.h =
      getMaxHeight(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;

    return node;
  }

  // creating rotateWithLeftChild() method to perform rotation of binary tree node with left child
  private Node rotateWithLeftChild(Node node2) {
    Node node1 = node2.leftChild;
    node2.leftChild = node1.rightChild;
    node1.rightChild = node2;
    node2.h =
      getMaxHeight(getHeight(node2.leftChild), getHeight(node2.rightChild)) + 1;
    node1.h = getMaxHeight(getHeight(node1.leftChild), node2.h) + 1;
    return node1;
  }

  // creating rotateWithRightChild() method to perform rotation of binary tree node with right child
  private Node rotateWithRightChild(Node node1) {
    Node node2 = node1.rightChild;
    node1.rightChild = node2.leftChild;
    node2.leftChild = node1;
    node1.h =
      getMaxHeight(getHeight(node1.leftChild), getHeight(node1.rightChild)) + 1;
    node2.h = getMaxHeight(getHeight(node2.rightChild), node1.h) + 1;
    return node2;
  }

  //create doubleWithLeftChild() method to perform double rotation of binary tree node. This method first rotate the left child with its right child, and after that, node3 with the new left child
  private Node doubleWithLeftChild(Node node3) {
    node3.leftChild = rotateWithRightChild(node3.leftChild);
    return rotateWithLeftChild(node3);
  }

  //create doubleWithRightChild() method to perform double rotation of binary tree node. This method first rotate the right child with its left child and after that node1 with the new right child
  private Node doubleWithRightChild(Node node1) {
    node1.rightChild = rotateWithLeftChild(node1.rightChild);
    return rotateWithRightChild(node1);
  }

  //create getTotalNumberOfNodes() method to get total number of nodes in the AVL Tree
  public int getTotalNumberOfNodes() {
    return getTotalNumberOfNodes(rootNode);
  }

  private int getTotalNumberOfNodes(Node head) {
    if (head == null) return 0; else {
      int length = 1;
      length = length + getTotalNumberOfNodes(head.leftChild);
      length = length + getTotalNumberOfNodes(head.rightChild);
      return length;
    }
  }

  //create searchElement() method to find an element in the AVL Tree
  public Node searchElement(int element) {
    return searchElement(rootNode, element);
  }

  private Node searchElement(Node root, int key) {
    // Base Cases: root is null or key is present at root
    if (root == null || root.element == key) return root;

    // val is greater than root's key
    if (root.element > key) return searchElement(root.leftChild, key);

    // val is less than root's key
    return searchElement(root.rightChild, key);
  }

  // create inorderTraversal() method for traversing AVL Tree in in-order form
  public void inorderTraversal() {
    inorderTraversal(rootNode);
  }

  private void inorderTraversal(Node head) {
    if (head != null) {
      inorderTraversal(head.leftChild);
      System.out.print(head.element + " ");
      inorderTraversal(head.rightChild);
    }
  }
}

public class Naloga6 {

  public static void main(String[] args) throws IOException {
    //Preberemo inpute
    Scanner sc = new Scanner(new File(args[0]));
    //prebermo prvo vrstico
    String teaSalesStr = sc.nextLine();
    String[] teaSalesSplit = teaSalesStr.split(",");
    int numOfNodes = teaSalesSplit.length;
    AVLTree tree = new AVLTree();
    for (int i = 1; i <= numOfNodes; i++) {
      //naredi novo vozlisce in doda podatke
      tree.insertElement(i, Float.parseFloat(teaSalesSplit[i - 1]));
    }

    //prebere st N in M
    String nmStr = sc.nextLine();
    String[] nmSplit = nmStr.split(",");
    int n = Integer.parseInt(nmSplit[0]); //stevilo povezav grafa
    int m = Integer.parseInt(nmSplit[1]); //stevilo dejtev

    //dodamo povezave grafa
    for (int i = 0; i < n; i++) {
      String connectionStr = sc.nextLine();
      String[] connectionSplit = connectionStr.split(",");
      Node a = tree.searchElement(Integer.parseInt(connectionSplit[0]));
      Node b = tree.searchElement(Integer.parseInt(connectionSplit[1]));

      a.connections.insertOrdered(b);
      b.connections.insertOrdered(a);
      //a.connections.print();
      //b.connections.print();
    }

    //prebere dejstva o turistih in jih zapise...
    for (int i = 0; i < m; i++) {
      String factStr = sc.nextLine();
      String[] factSplit = factStr.split(",");
      Node i1 = tree.searchElement(Integer.parseInt(factSplit[0]));
      int i2 = Integer.parseInt(factSplit[1]);
      int visitors = Integer.parseInt(factSplit[2]);
      /*Set path =*/shortestPath(i1, i2, visitors);
    }
sdasofaspfhg
    //izracuna za vsako vozlisce koliko caja je prodal in vrne max
    int maxTeaSoldNode = 0;
    int maxVisitorsNode = 0;
    float maxTeaSold = 0;
    int maxVisitors = 0;

    //cez vsa vozlisca
    for (int i = 1; i <= numOfNodes; i++) {
      Node t = tree.searchElement(i);
      int visitors_ = t.visitors;
      float teaSold = ((float) visitors_ * t.percentage);
      /*System.out.println(
        i +
        " - Visitors: " +
        visitors_ +
        " Procentage: " +
        t.percentage +
        " TeaSold: " +
        teaSold
      );*/
      if (maxTeaSold < teaSold) {
        maxTeaSold = teaSold;
        maxTeaSoldNode = i;
      }

      if (maxVisitors < visitors_) {
        maxVisitors = visitors_;
        maxVisitorsNode = i;
      }
    }

    sc.close();

    PrintWriter out = new PrintWriter(args[1]);

    out.println(maxTeaSoldNode + "," + maxVisitorsNode);

    out.close();
  }

  public static void shortestPath(Node a, int b, int vis) {
    Queue queue = new Queue();
    Set visited = new Set();

    queue.enqueue(a);
    visited.insert(a);

    while (!queue.isEmpty()) {
      //System.out.println(a.element + "-" + b);
      Node u = queue.dequeue();
      if (u.element == b) {
        // found the target node, trace the path using the prev field
        //System.out.println("*" + a.element + "-" + b);
        while (u != null) {
          //path.insert(u);
          //System.out.println(u.element);
          u.addVisitors(vis);
          //Node tmp = u;
          u = u.prev;
          //tmp.prev = null;
        }
        //System.out.println();

        for (
          SetElement iter = visited.first();
          !visited.overEnd(iter);
          iter = visited.next(iter)
        ) {
          Node v = visited.retrieve(iter);
          v.prev = null;
        }

        return;
      }

      for (
        SetElement iter = u.connections.first();
        !u.connections.overEnd(iter);
        iter = u.connections.next(iter)
      ) {
        Node v = u.connections.retrieve(iter);
        if (!visited.locate(v)) {
          queue.enqueue(v);
          visited.insert(v);
          v.prev = u; // set the prev field of v to u
        }
      }
    }
  }
}
