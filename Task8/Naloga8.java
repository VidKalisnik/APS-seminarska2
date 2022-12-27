import java.io.*;

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



class SetElement
{
    Node conn;
	SetElement next;

	SetElement()
	{
        conn = null;
		next = null;
	}
}

class Set 
{
	private SetElement first;
	
	public Set() 
	{
		makenull();
	}
	
	public void makenull()
	{
		first = new SetElement();

	}
	
	public SetElement first()
	{
		return first;
	}
	
	public SetElement next(SetElement pos)
	{
		return pos.next;
	}
	
	public void insert(Node conn) 
	{
		// nov element vstavimo samo, ce ga ni med obstojecimi elementi mnozice
		if (!locate(conn))
		{
			SetElement nov = new SetElement();
            nov.conn = conn;
			nov.next = first.next;
			first.next = nov;
		}
	}
	
	public void delete(SetElement pos)
	{
		pos.next = pos.next.next;
	}
	
	public boolean overEnd(SetElement pos)
	{
		if (pos.next == null)
			return true;
		else
			return false;
	}
	
	public boolean empty()
	{
		return first.next == null;
	}
	
	public Node retrieve(SetElement pos)
	{
		return pos.next.conn;
	}
	
    //vrne true, ce je element ze v mnozici
	public boolean locate(Node obj)
	{
		// sprehodimo se cez seznam elementov in preverimo enakost (uporabimo metodo equals)
		for (SetElement iter = first(); !overEnd(iter); iter = next(iter))
			if (obj.equals(retrieve(iter)))
				return true;
		
		return false;
	}
	
	public void print()
	{
		System.out.print("{");
		for (SetElement iter = first(); !overEnd(iter); iter = next(iter))
		{
			System.out.print(retrieve(iter));
			if (!overEnd(next(iter)))
				System.out.print(", ");
		}
		System.out.println("}");
	}
}


class Node  
{      
    int element;  
    int h;  //visina
    Set connections;

    Node leftChild;  
    Node rightChild;  
      
    //default constructor to create null node  
    public Node()  
    {  
        connections = new Set();
        leftChild = null;  
        rightChild = null;  
        element = 0;  
        h = 0;  
    }  
    // parameterized constructor  
    public Node(int element)  
    {  
        connections = new Set();
        leftChild = null;  
        rightChild = null;  
        this.element = element;  
        h = 0;  
    }       
}  
   
class AVLTree  
{  
    public Node rootNode;       
  
    //Constructor to set null value to the rootNode  
    public AVLTree()  
    {  
        rootNode = null;  
    }  
      
    //create removeAll() method to make AVL Tree empty  
    public void removeAll()  
    {  
        rootNode = null;  
    }  
      
    // create checkEmpty() method to check whether the AVL Tree is empty or not  
    public boolean checkEmpty()  
    {  
        if(rootNode == null)  
            return true;  
        else   
            return false;  
    }  
      
    // create insertElement() to insert element to to the AVL Tree  
    public void insertElement(int element)  
    {  
        rootNode = insertElement(element, rootNode);  
    }  
      
    //create getHeight() method to get the height of the AVL Tree  
    private int getHeight(Node node )  
    {  
        return node == null ? -1 : node.h;  
    }  
      
    //create maxNode() method to get the maximum height from left and right node  
    private int getMaxHeight(int leftNodeHeight, int rightNodeHeight)  
    {  
    return leftNodeHeight > rightNodeHeight ? leftNodeHeight : rightNodeHeight;  
    }  
      
      
    //create insertElement() method to insert data in the AVL Tree recursively   
    private Node insertElement(int element, Node node)  
    {  

        //System.out.print("*");
        //check whether the node is null or not  
        if (node == null)  
            node = new Node(element);  
        //insert a node in case when the given element is lesser than the element of the root node  
        else if (element < node.element)  
        {  
            node.leftChild = insertElement( element, node.leftChild );  
            if( getHeight( node.leftChild ) - getHeight( node.rightChild ) == 2 )  
                if( element < node.leftChild.element )  
                    node = rotateWithLeftChild( node );  
                else  
                    node = doubleWithLeftChild( node );  
        }  
        else if( element > node.element )  
        {  
            node.rightChild = insertElement( element, node.rightChild );  
            if( getHeight( node.rightChild ) - getHeight( node.leftChild ) == 2 )  
                if( element > node.rightChild.element)  
                    node = rotateWithRightChild( node );  
                else  
                    node = doubleWithRightChild( node );  
        }  
        else  
            ;  // if the element is already present in the tree, we will do nothing   
        node.h = getMaxHeight( getHeight( node.leftChild ), getHeight( node.rightChild ) ) + 1;  
          
        return node;  
          
    }  
      
    // creating rotateWithLeftChild() method to perform rotation of binary tree node with left child        
    private Node rotateWithLeftChild(Node node2)  
    {  
        Node node1 = node2.leftChild;  
        node2.leftChild = node1.rightChild;  
        node1.rightChild = node2;  
        node2.h = getMaxHeight( getHeight( node2.leftChild ), getHeight( node2.rightChild ) ) + 1;  
        node1.h = getMaxHeight( getHeight( node1.leftChild ), node2.h ) + 1;  
        return node1;  
    }  
  
    // creating rotateWithRightChild() method to perform rotation of binary tree node with right child        
    private Node rotateWithRightChild(Node node1)  
    {  
        Node node2 = node1.rightChild;  
        node1.rightChild = node2.leftChild;  
        node2.leftChild = node1;  
        node1.h = getMaxHeight( getHeight( node1.leftChild ), getHeight( node1.rightChild ) ) + 1;  
        node2.h = getMaxHeight( getHeight( node2.rightChild ), node1.h ) + 1;  
        return node2;  
    }  
  
    //create doubleWithLeftChild() method to perform double rotation of binary tree node. This method first rotate the left child with its right child, and after that, node3 with the new left child  
    private Node doubleWithLeftChild(Node node3)  
    {  
        node3.leftChild = rotateWithRightChild( node3.leftChild );  
        return rotateWithLeftChild( node3 );  
    }  
  
    //create doubleWithRightChild() method to perform double rotation of binary tree node. This method first rotate the right child with its left child and after that node1 with the new right child  
    private Node doubleWithRightChild(Node node1)  
    {  
        node1.rightChild = rotateWithLeftChild( node1.rightChild );  
        return rotateWithRightChild( node1 );  
    }      
  
    //create getTotalNumberOfNodes() method to get total number of nodes in the AVL Tree  
    public int getTotalNumberOfNodes()  
    {  
        return getTotalNumberOfNodes(rootNode);  
    }  
    private int getTotalNumberOfNodes(Node head)  
    {  
        if (head == null)  
            return 0;  
        else  
        {  
            int length = 1;  
            length = length + getTotalNumberOfNodes(head.leftChild);  
            length = length + getTotalNumberOfNodes(head.rightChild);  
            return length;  
        }  
    }  
  
    //create searchElement() method to find an element in the AVL Tree  
    public Node searchElement(int element)  
    {  
        return searchElement(rootNode, element);  
    }  
  
    private Node searchElement(Node root, int key) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.element == key)
            return root;
    
        // val is greater than root's key
        if (root.element > key)
            return searchElement(root.leftChild, key);
    
        // val is less than root's key
        return searchElement(root.rightChild, key);
    }
    
    // create inorderTraversal() method for traversing AVL Tree in in-order form  
    public void inorderTraversal()  
    {  
        inorderTraversal(rootNode);  
    }  
    private void inorderTraversal(Node head)  
    {  
        if (head != null)  
        {  
            inorderTraversal(head.leftChild);  
            System.out.print(head.element+" ");  
            inorderTraversal(head.rightChild);  
        }  
    } 
} 

public class Naloga8 {
    public static void main(String[] args) throws IOException {
        // Read the input file
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        PrintWriter writer = new PrintWriter(new File(args[1]));
        int numConnections = Integer.parseInt(reader.readLine());
        
        AVLTree tree = new AVLTree();
        //System.out.println("?");
        //tree.inorderTraversal();

        for (int i = 0; i < numConnections; i++) {
            String[] parts = reader.readLine().split(",");
            int island1 = Integer.parseInt(parts[0]);
            int island2 = Integer.parseInt(parts[1]);

            if(tree.searchElement(island1) == null){
                tree.insertElement(island1);
            }

            if(tree.searchElement(island2) == null){
                tree.insertElement(island2);
            }

            //tree.inorderTraversal();
            if(!insertConnection(tree,island1,island2)){
                //System.out.print("#");
                writer.println(island1 + "," + island2);
            }

        }
        reader.close();
        writer.close();
    }

    public static boolean insertConnection(AVLTree tree, int island1, int island2){
        
        Node i1 = tree.searchElement(island1);
        Node i2 = tree.searchElement(island2);

        //System.out.println(island1 + "," + island2);
        //Ce ima v island1 v svoji mnozici povezav ze island2 je ta povezava reduntantna
        if(reachable(i1, island2, tree)){
            return false;
        }else{
            // v povezave island1 dodamo povezavo island2
            i1.connections.insert(i2);
            // v povezave island2 dodamo povezavo island1
            i2.connections.insert(i1);
        }

        //printAllConnections(tree);
        return true;
    }

    public static boolean reachable(Node i1, int island2, AVLTree tree){
        
        Queue queue = new Queue();
        Set visited = new Set();

        queue.enqueue(i1);
        visited.insert(i1);
        //System.out.println(i1);

        while(!queue.isEmpty()){
            Node u = queue.dequeue();
            if(u.element == island2){
                return true;
            }
            //more it cez use sosede i1 in jih doda v vrsto
            for(SetElement iter = u.connections.first(); !u.connections.overEnd(iter); iter = u.connections.next(iter)){

                Node v = u.connections.retrieve(iter);
                if(!visited.locate(v)){
                    queue.enqueue(v);
                    visited.insert(v);
                }

            }
        }


        return false;
    }

    public static void printAllConnections(AVLTree tree){
        
        for(int i = 1; i <= tree.getTotalNumberOfNodes(); i++){
            System.out.print(i +":");
            tree.searchElement(i).connections.print();
        }

    }
}


/*
SetElement e2 = i2.first().next;
            while(e2 != null){
                
                if(!e2.element.equals(island1)){
                    //printAllConnections(tree);
                    i1.insert(e2.element);

                    tree.searchElement((int)e2.element).connections.insert(island1);
                }
                e2 = e2.next;
            }
            
            //enako kot za island1 
            i2.insert(island1);
            SetElement e1 = i1.first().next;
            while(e1 != null){
                //System.out.println("islan1: " + island1 + " island2: " + island2 + " e.element: " + e1.element);
                
                if(!e1.element.equals(island2)){
                    //printAllConnections(tree);
                    i2.insert(e1.element);
                
                    tree.searchElement((int)e1.element).connections.insert(island2);

                    
                }
                e1 = e1.next;
            }*/