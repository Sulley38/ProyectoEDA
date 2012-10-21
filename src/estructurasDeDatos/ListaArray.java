package estructurasDeDatos;

import gestionDeSentencias.Fichero;
import java.io.IOException;

/**
 * Estructura de vector:
 * Representación como array estático. Se redimensiona automáticamente en caso de necesitar más espacio.
 * Puede insertar mantiendo un orden ascendente de sus elementos.
 * @param <T> - Parámetro genérico
 */
public class ListaArray<T> {
	
	private final static int defaultSize = 100;
	private final static int resizeFactor = 2;
	
	private Object[] datos;
	private int capacidad;
	private int longitud;

	
	public ListaArray() {
		this(defaultSize);
	}
	
	public ListaArray(int maxSize) {
		capacidad = maxSize;
		datos = new Object[capacidad];
		longitud = 0;
	}

	
	public boolean isEmpty() {
		return (longitud == 0);
	}
	
	public int size() {
		return longitud;
	}
	
	public T first() {
		return accesoDatos(0);
	}

	public T last() {
		return accesoDatos(longitud - 1);
	}

	public T elementAt(int posicion) {
		return accesoDatos(posicion);
	}

	public void insertLast(T elemento) {
		datos[longitud] = elemento;
		longitud++;
	}
	
	public T removeLast() {
		return accesoDatos(--longitud);
	}

	public void printToFile(String filename) {
		try {
			Fichero.abrir(filename, true);
			for (int i = 0; i < longitud; ++i)
				Fichero.escribirSentencia(accesoDatos(i).toString());
			Fichero.cerrar();
		} catch (IOException e) {
			System.out.println("Error: Imposible acceder al fichero especificado.");
		}
	}
	

	@SuppressWarnings("unchecked")
	private T accesoDatos(int index) {
		return (T) datos[index];
	}

}
