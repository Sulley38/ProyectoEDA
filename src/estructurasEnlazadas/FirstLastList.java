package estructurasEnlazadas;
/**
 * Ejemplo de estructura enlazada simple (en forma de lista de enteros)
 * con apuntadores al principio y al final.
 */
public class FirstLastList<T> {
	   private Link<T> first;               // ref to first link
	   private Link<T> last;                // ref to last link
	// -------------------------------------------------------------
	   public FirstLastList()            // constructor
	      {
	      first = null;                  // no links on list yet
	      last = null;
	      }
	// -------------------------------------------------------------
	   public boolean isEmpty()          // true if no links
	      { return first==null; }
	// -------------------------------------------------------------
	   public void insertFirst(T dd)  // insert at front of list
	      {
	      Link<T> newLink = new Link<T>(dd);   // make new link

	      if( isEmpty() )                // if empty list,
	         last = newLink;             // newLink <-- last
	      newLink.next = first;          // newLink --> old first
	      first = newLink;               // first --> newLink
	      }
	// -------------------------------------------------------------
	   public void insertLast(T dd)   // insert at end of list
	      {
	      Link<T> newLink = new Link<T>(dd);   // make new link
	      if( isEmpty() )                // if empty list,
	         first = newLink;            // first --> newLink
	      else
	         last.next = newLink;        // old last --> newLink
	      last = newLink;                // newLink <-- last
	      }
	// -------------------------------------------------------------
	   public T deleteFirst()         // delete first link
	      {                              // (assumes non-empty list)
	      T temp = first.dData;
	      if(first.next == null)         // if only one item
	         last = null;                // null <-- last
	      first = first.next;            // first --> old next
	      return temp;
	      }
	// -------------------------------------------------------------
	   public T getFirst()
	   {
		   return first.dData;
	   }
	   public T getLast()
	   {
		   return last.dData;
	   }
	// -------------------------------------------------------------
	   public void displayList()
	      {
	      System.out.print("List (first-->last): ");
	      Link<T> current = first;          // start at beginning
	      while(current != null)         // until end of list,
	         {
	         current.displayLink();      // print data
	         current = current.next;     // move to next link
	         }
	      System.out.println("");
	      }
	// -------------------------------------------------------------
}  // end class FirstLastList


