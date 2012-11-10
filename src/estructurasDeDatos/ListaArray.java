package estructurasDeDatos;

import gestionDeSentencias.Fichero;
import java.io.IOException;

/**
 * Estructura de vector:
 * Representaci�n como array est�tico. Se redimensiona en caso de necesitar m�s espacio.
 * Puede insertar mantiendo un orden ascendente de sus elementos.
 * @param <T> - Par�metro gen�rico que implemente la interfaz Comparable<T>
 */
public class ListaArray<T extends Comparable<T>> implements Comparable<ListaArray<T>> {
	
	// Constantes del vector
	private final static int defaultSize = 100;
	private final static int resizeFactor = 2;
	
	// Atributos para representar el TAD
	private Object[] datos;
	private int capacidad;
	private int longitud;

	
	// Comparadora seg�n la longitud del vector
	@Override
	public int compareTo(final ListaArray<T> o) {
		return (longitud - o.longitud);
	}
	
	/**
	 * Construye un vector vac�o del tama�o por defecto (100).
	 */
	public ListaArray() {
		this(defaultSize);
	}
	
	/**
	 * Construye un vector vac�o del tama�o especificado.
	 * @param maxSize - Tama�o m�ximo del vector
	 */
	public ListaArray(final int maxSize) {
		capacidad = maxSize;
		datos = new Object[capacidad];
		longitud = 0;
	}

	/**
	 * Comprueba si hay alg�n elemento en el vector.
	 * @return true si el vector es vac�o
	 */
	public boolean isEmpty() {
		return (longitud == 0);
	}

	/**
	 * Devuelve el tama�o del vector.
	 * @return el n�mero de elementos que componen el vector
	 */
	public int size() {
		return longitud;
	}

	/**
	 * Devuelve el valor del primer elemento del vector.
	 * @return primer valor del vector
	 */
	public T first() {
		return accesoDatos(0);
	}

	/**
	 * Devuelve el valor del �ltimo elemento del vector.
	 * @return �ltimo valor del vector
	 */
	public T last() {
		return accesoDatos(longitud - 1);
	}
	
	/**
	 * Devuelve la posici�n del primer elemento igual a T.
	 * @param elemento - elemento a buscar en la lista
	 * @return elemento encontrado, o -1 si no se ha encontrado
	 */
	public int find(final T elemento) {
		for( int i = 0; i < longitud; ++i )
			if( accesoDatos(i).equals(elemento) )
				return i;
		return -1;
	}
	
	/**
	 * Devuelve el valor del elemento en la posici�n indicada.
	 * @param posicion - la posici�n del valor que se busca, empezando a contar desde cero
	 * @return el valor del elemento en la posici�n index, o null si no existe
	 */
	public T get(final int posicion) {
		if (posicion < 0 || posicion >= longitud)
			return null;
		return accesoDatos(posicion);
	}
	
	/**
	 * Inserta un elemento en el vector en la posici�n indicada
	 * @param index - la posici�n en la que insertar el dato
	 * @param elemento - dato a insertar
	 */
	public void set(final int posicion, final T elemento) {
		if( longitud == capacidad )
			expandir();
		for( int i = longitud; i > posicion; --i )
			datos[i] = datos[i-1];
		datos[posicion] = elemento;
		longitud++;
	}
	
	/**
	 * Inserta un elemento en el vector en �ltima posici�n.
	 * @param elemento - dato a insertar
	 */
	public void insertLast(final T elemento) {
		if( longitud == capacidad )
			expandir();
		datos[longitud++] = elemento;
	}

	/**
	 * Inserta un elemento en su posici�n siguiendo un orden creciente de elementos.
	 * @param elemento - dato a insertar
	 */
	public void insertOrdered(final T elemento) {
		set( busquedaBinaria(elemento), elemento );
	}

	/**
	 * Elimina el �ltimo elemento del vector y lo devuelve. Si el vector es vac�o, devuelve null.
	 * @return elemento eliminado
	 */
	public T removeLast() {
		if (longitud == 0)
			return null;
		return accesoDatos(--longitud);
	}
	
	/**
	 * Elimina todos los elementos del vector.
	 */
	public void removeAll() {
		longitud = 0;
	}
	
	/**
	 * Ordenaci�n de los elementos del array mediante ordenaci�n por inserci�n
	 * @return un array con los �ndices de los elementos del array original en orden creciente
	 */
	public ListaArray<Integer> sort() {
		ListaArray<Integer> resultado = new ListaArray<Integer>();
		int j;
		for (int i = 0; i < longitud; i++) {
			j = 0;
			while (j < i && accesoDatos(i).compareTo( accesoDatos(resultado.get(j)) ) > 0)
				j++;
			resultado.set(j, i);
		} 
		return resultado;
	}
	
	/**
	 * Devuelve un array a partir del vector.
	 * @return un array cuyos elementos son los valores del vector
	 */
	public Object[] toArray() {
		Object[] o = new Object[longitud];
		for( int i = 0; i < longitud; ++i )
			o[i] = accesoDatos(i);
		return o;
	}

	/**
	 * Escribe todos los elementos del vector en un fichero de texto, uno por l�nea.
	 * @param filename - Archivo a escribir
	 */
	public void printToFile(final String filename) {
		try {
			Fichero.abrir(filename, true, false);
			for (int i = 0; i < longitud; ++i)
				Fichero.escribirSentencia(accesoDatos(i).toString());
			Fichero.cerrar();
		} catch (IOException e) {
			System.out.println("Error: Imposible acceder al fichero especificado.");
		}
	}
	

	// Accede al array de elementos haciendo un casting al tipo de dato gen�rico
	@SuppressWarnings("unchecked")
	private T accesoDatos(final int index) {
		return (T) datos[index];
	}
	
	// Incrementa la capacidad del vector en base a la constante de redimensionamiento
	private void expandir() {
		capacidad *= resizeFactor;
		Object[] nuevosDatos = new Object[capacidad];
		for (int i = 0; i < longitud; ++i)
			nuevosDatos[i] = datos[i];
		datos = nuevosDatos;
	}
	
	// Devuelve la posici�n en la que corresponder�a insertar el elemento en orden
	private int busquedaBinaria(final T elemento) {
		int menor = 0, mayor = longitud - 1, mitad, comp;
		while( menor <= mayor ) {
			mitad = (menor+mayor)/2;
			comp = accesoDatos(mitad).compareTo(elemento);
			if( comp == 0 ) {
				return mitad + 1;
			} else if( comp < 0 ) {
				menor = mitad + 1;
			} else {
				mayor = mitad - 1;
			}
		}
		return menor;
	}
	
}
