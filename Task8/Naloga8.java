import java.io.*;

class UndirectedGraph{

    public GraphNode[] adjacencyList;

    UndirectedGraph(int numOfVertices){
        GraphNode[] adjacencyList = new GraphNode[numOfVertices];
        adjacencyList[0].value = 10;
    }

}

class GraphNode {
    
    public Object value;
    public LinkedList list; 

}

class LinkedList 
{
	protected LinkedListElement first;
	protected LinkedListElement last;
	
	LinkedList()
	{
		makenull();
	}
	
	//Funkcija makenull inicializira seznam
	public void makenull()
	{
		//drzimo se implementacije iz ucbenika:
		//po dogovoru je na zacetku glava seznama (header)
		first = new LinkedListElement(null, null);
		last = null;
	}
	
	//Funkcija addLast doda nov element na konec seznama
	public void addLast(Object obj)
	{
		//najprej naredimo nov element
		LinkedListElement newEl = new LinkedListElement(obj, null);
		
		//ali je seznam prazen?
		// po dogovoru velja: ce je seznam prazen, potem kazalec "last" ne kaze nikamor
		if (last == null)
		{
			//ce seznam vsebuje samo en element, kazalca "first" in "last" kazeta na glavo seznama
			first.next = newEl;
			last = first;
		}
		else
		{
			last.next.next = newEl;
			last = last.next;
		}
	}
	
	//Funkcija write izpise elemente seznama
	public void write()
	{
		LinkedListElement el;
		
		//zacnemo pri elementu za glavo seznama
		el = first.next;
		while (el != null)
		{
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
	void addFirst(Object obj)
	{
		//najprej naredimo nov element
		LinkedListElement newEl = new LinkedListElement(obj);
		
		//novi element postavimo za glavo seznama
		newEl.next = first.next;
		first.next = newEl;
		
		if (last == null)//preverimo, ali je to edini element v seznamu
			last = first;
		else if (last == first)//preverimo, ali je seznam vseboval en sam element
			last = newEl;
	}
	
	//Funkcija length() vrne dolzino seznama (pri tem ne uposteva glave seznama)
	int length()
	{
		int counter;
		LinkedListElement el;
		
		counter = 0;
		
		//zacnemo pri elementu za glavo seznama
		el = first.next;
		while (el != null)
		{
			counter++;
			el = el.next;
		}
		
		return counter;
	}

	//Funkcija removeDuplicates odstrani ponavljajoce se elemente v seznamu
	void removeDuplicates()
	{
		LinkedListElement curEl;
		
		curEl = first.next;
		while(curEl != null)
		{
			LinkedListElement tmpEl;
			
			//preveri ali se element curEl.next nahaja v seznamu 
			
			tmpEl = curEl;
			while (tmpEl.next != null)
			{
				if (tmpEl.next.element.equals(curEl.element)) 
				{
					//element je ze v seznamu, izlocimo ga
					tmpEl.next = tmpEl.next.next;	
				}
				else 
				{
					//element ni kopija, pustimo ga v seznamu
					tmpEl = tmpEl.next;
				}
			}
			
			curEl = curEl.next;
		}
		
		//ne pozabimo na kazalec "last"
		last = null;
		curEl = first;
		while(curEl.next != null)
		{
			if (curEl.next.next == null)
			{
				last = curEl;
				break;
			}
			else
				curEl = curEl.next;
		}
	}
}

class LinkedListElement 
{
	Object element;
	LinkedListElement next;
	
	LinkedListElement(Object obj)
	{
		element = obj;
		next = null;
	}
	
	LinkedListElement(Object obj, LinkedListElement nxt)
	{
		element = obj;
		next = nxt;
	}
}


public class Naloga8 {
    public static void main(String[] args) throws IOException {
        
        UndirectedGraph test = new UndirectedGraph(6);
        
    }
}
