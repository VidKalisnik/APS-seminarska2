import java.io.*;

class Tree {

  int id;
  char label;
  int numOfChildren;
  LinkedList children;

  public Tree(int id) {
    this.id = id;
    children = new LinkedList();
  }

  public void addData(String[] parts) {
    //id = Integer.parseInt(parts[0]); id vozlisca
    label = parts[1].charAt(0); // oznaka vozlisca
    numOfChildren = parts.length - 2;
    for (int j = 2; j < parts.length; j++) {
      Tree tmp = new Tree(Integer.parseInt(parts[j]));
      children.addLast(tmp);
    }
  }

  public Tree search(int id, Tree tree) {
    if (tree.id == id) {
      return tree;
    }

    LinkedListElement tmp = tree.children.first.next;
    while (tmp != null) {
      Tree foundTree = search(id, tmp.element);
      if (foundTree != null) {
        return foundTree;
      }
      tmp = tmp.next;
    }

    return null;
  }
}

class LinkedListElement {

  Tree element;
  LinkedListElement next;

  LinkedListElement(Tree obj) {
    element = obj;
    next = null;
  }

  LinkedListElement(Tree obj, LinkedListElement nxt) {
    element = obj;
    next = nxt;
  }
}

class LinkedList {

  protected LinkedListElement first;
  protected LinkedListElement last;

  LinkedList() {
    makenull();
  }

  //Funkcija makenull inicializira seznam
  public void makenull() {
    //drzimo se implementacije iz ucbenika:
    //po dogovoru je na zacetku glava seznama (header)
    first = new LinkedListElement(null, null);
    last = null;
  }

  //Funkcija addLast doda nov element na konec seznama
  public void addLast(Tree obj) {
    //najprej naredimo nov element
    LinkedListElement newEl = new LinkedListElement(obj, null);

    //ali je seznam prazen?
    // po dogovoru velja: ce je seznam prazen, potem kazalec "last" ne kaze nikamor
    if (last == null) {
      //ce seznam vsebuje samo en element, kazalca "first" in "last" kazeta na glavo seznama
      first.next = newEl;
      last = first;
    } else {
      last.next.next = newEl;
      last = last.next;
    }
  }

  //Funkcija write izpise elemente seznama
  public void write() {
    LinkedListElement el;

    //zacnemo pri elementu za glavo seznama
    el = first.next;
    while (el != null) {
      System.out.print(el.element + ", ");
      el = el.next;
    }

    System.out.println();
    /*
		//za kontrolo lahko izpisemo tudi vrednosti prvega in zadnjega elementa
		if (first.next != null)
			System.out.println("Prvi element: " + first.next.element);
		else
			System.out.println("Ni prvega elementa");
		
		if (last != null)
			System.out.println("Zadnji element: " + last.next.element);
		else
			System.out.println("Ni zadnjega elementa");
		*/
  }

  //Funkcija addFirst doda nov element na prvo mesto v seznamu (takoj za glavo seznama)
  void addFirst(Tree obj) {
    //najprej naredimo nov element
    LinkedListElement newEl = new LinkedListElement(obj);

    //novi element postavimo za glavo seznama
    newEl.next = first.next;
    first.next = newEl;

    if (last == null) last = first; else if ( //preverimo, ali je to edini element v seznamu
      last == first
    ) last = newEl; //preverimo, ali je seznam vseboval en sam element
  }

  //Funkcija length() vrne dolzino seznama (pri tem ne uposteva glave seznama)
  int length() {
    int counter;
    LinkedListElement el;

    counter = 0;

    //zacnemo pri elementu za glavo seznama
    el = first.next;
    while (el != null) {
      counter++;
      el = el.next;
    }

    return counter;
  }

  //Rekurzivna funkcija za izracun dolzine seznama
  int lengthRek(LinkedListElement el) {
    if (el == null) return 0; else return lengthRek(el.next) + 1;
  }

  //Funkcija lengthRek() klice rekurzivno funkcijo za izracun dolzine seznama
  int lengthRek() {
    return lengthRek(first.next);
  }

  //Funkcija insertNth vstavi element na n-to mesto v seznamu
  //(prvi element seznama, ki se nahaja takoj za glavo seznama, je na indeksu 0)
  boolean insertNth(Tree obj, int n) {
    LinkedListElement el;

    //zacnemo pri glavi seznama
    el = first;

    //premaknemo se n-krat
    for (int i = 0; i < n; i++) {
      el = el.next;
      if (el == null) return false;
    }

    LinkedListElement newEl = new LinkedListElement(obj);
    newEl.next = el.next;
    el.next = newEl;

    if (last == null) last = first; else if ( //ce smo dodali edini element
      last == el
    ) last = last.next; else if ( //ce smo dodali predzadnji element
      last.next == el
    ) last = el; //ce smo dodali zadnji element
    //v ostalih primerih se kazalec "last" ne spreminja

    return true;
  }

  //Funkcija deleteNth izbrise element na n-tem mestu v seznamu
  //(prvi element seznama, ki se nahaja takoj za glavo seznama, je na indeksu 0)
  boolean deleteNth(int n) {
    LinkedListElement el, prev_el;

    //zacnemo pri glavi seznama
    prev_el = null;
    el = first;

    //premaknemo se n-krat
    for (int i = 0; i < n; i++) {
      prev_el = el;
      el = el.next;
      if (el == null) return false;
    }

    if (el.next != null) {
      //preden izlocimo element preverimo, ali je potrebno popraviti kazalec "last"
      if (last == el.next) last = el; else if ( //ce brisemo predzadnji element
        last == el
      ) last = prev_el; //ce bri�emo zadnji element

      el.next = el.next.next;

      return true;
    } else return false;
  }

  //Funkcija reverse obrne vrstni red elementov v seznamu (pri tem ignorira glavo seznama)
  void reverse() {
    LinkedListElement curEl;
    LinkedListElement tempEl;

    //preverimo, ali seznam vsebuje vsaj dva elementa
    if (first.next != null && first.next.next != null) {
      //zaceli bomo pri drugem elementu seznama
      curEl = first.next.next;

      //takoj lahko oznacimo, da se bo niz zaklju�il z elementom, ki je trenutno prvi
      //vemo tudi, da bo kazalec "last" kazal na element, ki je trenutno na drugem mestu
      first.next.next = null;
      last = curEl;

      //premikamo se proti koncu seznama
      while (curEl != null) {
        tempEl = curEl.next;

        //ustrezno prevezemo elemente
        curEl.next = first.next;
        first.next = curEl;

        curEl = tempEl;
      }
    }
  }

  //Rekurzivna funkcija, ki obrne vrstni red elementov v seznamu
  void reverseRek(LinkedListElement el) {
    if (el == null) return;

    if (el.next == null) {
      first.next = el;
      last = first;
    } else {
      reverseRek(el.next);
      el.next = null;
      last = last.next;
      last.next = el;
    }
  }

  //Funkcija reverseRek klice rekurzivno funkcijo, ki obrne vrstni red elementov v seznamu
  void reverseRek() {
    reverseRek(first.next);
  }

  //Funkcija removeDuplicates odstrani ponavljajoce se elemente v seznamu
  void removeDuplicates() {
    LinkedListElement curEl;

    curEl = first.next;
    while (curEl != null) {
      LinkedListElement tmpEl;

      //preveri ali se element curEl.next nahaja v seznamu

      tmpEl = curEl;
      while (tmpEl.next != null) {
        if (tmpEl.next.element.equals(curEl.element)) {
          //element je ze v seznamu, izlocimo ga
          tmpEl.next = tmpEl.next.next;
        } else {
          //element ni kopija, pustimo ga v seznamu
          tmpEl = tmpEl.next;
        }
      }

      curEl = curEl.next;
    }

    //ne pozabimo na kazalec "last"
    last = null;
    curEl = first;
    while (curEl.next != null) {
      if (curEl.next.next == null) {
        last = curEl;
        break;
      } else curEl = curEl.next;
    }
  }
}

public class Naloga10 {

  public static void main(String[] args) throws IOException {
    String inputFile = args[0];
    String outputFile = args[1];

    //prebermo vhod
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    //st vozlisc prvega drevesa
    int n = Integer.parseInt(reader.readLine());

    //dodamo vozlisca v drevo in oznake dodajamo v hashtable
    String[] parts = reader.readLine().split(",");
    Tree tree1 = new Tree(1);
    tree1.addData(parts);
    for (int i = 1; i < n; i++) {
      parts = reader.readLine().split(",");
      tree1.search(Integer.parseInt(parts[0]), tree1).addData(parts);
    }

    //zgradimo drugo drevo
    int m = Integer.parseInt(reader.readLine());

    parts = reader.readLine().split(",");
    Tree tree2 = new Tree(1);
    tree2.addData(parts);
    for (int i = 1; i < m; i++) {
      parts = reader.readLine().split(",");
      tree2.search(Integer.parseInt(parts[0]), tree2).addData(parts);
    }
    reader.close();

    int count = traverse(tree1, tree2);

    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    writer.write(String.valueOf(count));
    writer.close();
  }

  public static int traverse(Tree tree1, Tree tree2) {
    int count = 0;

    if (check(tree1, tree2)) {
      count++;
    }

    for (
      LinkedListElement tmp = tree2.children.first.next;
      tmp != null;
      tmp = tmp.next
    ) {
      count += traverse(tree1, tmp.element);
    }

    return count;
  }

  // Method to visit a node
  public static boolean check(Tree tree1, Tree tree2) {
    //ce je "root" node isti in ima enako st otrok
    if (tree1.label == tree2.label) {
      if (tree1.numOfChildren == 0) {
        return true;
      }

      if (tree1.numOfChildren == tree2.numOfChildren) {
        // Iterate through the children of both trees
        LinkedListElement tmp1 = tree1.children.first.next;
        LinkedListElement tmp2 = tree2.children.first.next;
        while (tmp1 != null && tmp2 != null) {
          // Recursively check if the child nodes match
          if (!check(tmp1.element, tmp2.element)) {
            return false;
          }
          tmp1 = tmp1.next;
          tmp2 = tmp2.next;
        }
        return true;
      }
    }
    return false;
  }
}
