package test;

import gestionDeSentencias.Fichero;
import java.io.IOException;

/**
 * Casos de prueba para los métodos de acceso a ficheros
 */
public class pruebaFichero {

	public static void main (String[] args) {
		try {
			
			// Escribir en el fichero
			Fichero f1 = new Fichero("data/pruebaFichero.txt",true);
			f1.escribirSentencia("Dani no mola mazo, Mati sí.");
			f1.cerrar();
			
			// Escribir en la consola la sentencia que se ha guardado previamente en el fichero
			Fichero f2 = new Fichero("data/pruebaFichero.txt",false);
			System.out.println(f2.leerSentencia());
			f2.cerrar();
			
		} catch (IOException e) {
			System.out.println("Error al acceder al fichero especificado.");
		}
	}
	
}
