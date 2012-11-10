package estructurasDeDatos;

import gestionDeSentencias.Fichero;
import java.io.IOException;

/**
 * Estructura de vector:
 * Representación como array estático. Se redimensiona en caso de necesitar más espacio.
 * Puede insertar mantiendo un orden ascendente de sus elementos.
 * @param <T> - Parámetro genérico que implemente la interfaz Comparable<T>
 */
public class ListaArray<T extends Comparable<T>> implements Comparable<ListaArray<T>> {
	
	// Constantes del vector
	private final static int defaultSize = 100;
	private final static int resizeFactor = 2;
	
	// Atributos para representar el TAD
	private Object[] datos;
	private int capacidad;
	private int longitud;

	
	// Comparadora según la longitud del vector
	@Override
	public int compareTo(final ListaArray<T> o) {
		return (longitud - o.longitud);
	}
	
	/**
	 * Construye un vector vacío del tamaño por defecto (100).
	 */
	public ListaArray() {
		this(defaultSize);
	}
	
	/**
	 * Construye un vector vacío del tamaño especificado.
	 * @param maxSize - Tamaño máximo del vector
	 */
	public ListaArray(final int maxSize) {
		capacidad = maxSize;
		datos = new Object[capacidad];
		longitud = 0;
	}

	/**
	 * Comprueba si hay algún elemento en el vector.
	 * @return true si el vector es vacío
	 */
	public boolean isEmpty() {
		return (longitud == 0);
	}

	/**
	 * Devuelve el tamaño del vector.
	 * @return el número de elementos que componen el vector
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
	 * Devuelve el valor del último elemento del vector.
	 * @return último valor del vector
	 */
	public T last() {
		return accesoDatos(longitud - 1);
	}
	
	/**
	 * Devuelve la posición del primer elemento igual a T.
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
	 * Devuelve el valor del elemento en la posición indicada.
	 * @param posicion - la posición del valor que se busca, empezando a contar desde cero
	 * @return el valor del elemento en la posición index, o null si no existe
	 */
	public T get(final int posicion) {
		if (posicion < 0 || posicion >= longitud)
			return null;
		return accesoDatos(posicion);
	}
	
	/**
	 * Inserta un elemento en el vector en la posición indicada
	 * @param index - la posición en la que insertar el dato
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
	 * Inserta un elemento en el vector en última posición.
	 * @param elemento - dato a insertar
	 */
	public void insertLast(final T elemento) {
		if( longitud == capacidad )
			expandir();
		datos[longitud++] = elemento;
	}

	/**
	 * Inserta un elemento en su posición siguiendo un orden creciente de elementos.
	 * @param elemento - dato a insertar
	 */
	public void insertOrdered(final T elemento) {
		set( busquedaBinaria(elemento), elemento );
	}

	/**
	 * Elimina el último elemento del vector y lo devuelve. Si el vector es vacío, devuelve null.
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
	 * Ordenación de los elementos del array mediante ordenación por inserción
	 * @return un array con los índices de los elementos del array original en orden creciente
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
	 * Escribe todos los elementos del vector en un fichero de texto, uno por línea.
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
	

	// Accede al array de elementos haciendo un casting al tipo de dato genérico
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
	
	// Devuelve la posición en la que correspondería insertar el elemento en orden
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
