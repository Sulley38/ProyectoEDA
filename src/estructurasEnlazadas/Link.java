package estructurasEnlazadas;
//Nodo de enlace simple, que contiene n�meros enteros (de clase long).
public class Link<T> {
	   public T dData;                 // data item
	   public Link<T> next;                  // next link in list
	   //OJO: Obs�rvese que los atributos est�n declarados public; 
	   //algo realmente desaconsejable, pero que admitimos en este
	   //ejemplo para simplificar el tratamiento de la estructura
	// -------------------------------------------------------------
	   public Link(T d)                // constructor
	      { dData = d; }
	// -------------------------------------------------------------
	   public void displayLink()          // display this link
	      { System.out.print(dData + " "); }
	// -------------------------------------------------------------
}  // end class Link

