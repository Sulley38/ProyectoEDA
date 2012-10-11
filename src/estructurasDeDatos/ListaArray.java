package estructurasDeDatos;

import gestionDeSentencias.Fichero;
import java.io.IOException;

public class ListaArray<T>{
	private int maxSize;  // tamaño del array
	private T[] laLista;
	private int longitud; // índice de la primera celda libre y 
			              // número de elementos de la lista
	//----------------------------------------------------------
	public ListaArray(int longitudMax){
		maxSize = longitudMax;             
	    laLista = (T[])(new Object[maxSize]); 
	    longitud = 0;
	}
	//----------------------------------------------------------
	public T first (){   // primero de la lista
		return laLista[0];   
	}
	//----------------------------------------------------------
	public T last () {   // último de la lista
		return laLista[longitud-1]; 
	}
	//----------------------------------------------------------
	public T removeLast () {   // retira el último de la lista
		T temp = laLista[--longitud]; // el último de la lista
		laLista[longitud] = null;  // anula la referencia 
		return temp;   
	}
	//----------------------------------------------------------
	public boolean isEmpty () { // Determina si la lista está vacía
		return (longitud == 0); 
	}
	
	public void insertLast(T elemento){
		laLista[longitud]=elemento;
		longitud++;
	}

	public T getElementByPosition(int posicion) {
		return laLista[posicion];
	}
	
	public int size(){
		return longitud;
	}
	
	public void imprimirEnFichero(String nombreDeArchivo) {
		try {
			Fichero f = new Fichero(nombreDeArchivo, true);
			for( int i = 0; i < longitud; ++i )
				f.escribirSentencia(laLista[i].toString());
			f.cerrar();
		} catch (IOException e) {
			System.out.println("Error: Imposible acceder al fichero especificado.");
		}
	}
	
}
