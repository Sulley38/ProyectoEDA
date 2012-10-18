package test;

import estructurasDeDatos.*;
import gestionDeSentencias.*;
import java.util.Scanner;

public class medirTiempos {

	public static void main (String[] args) {	
		Almacen m;
		Scanner in = new Scanner(System.in);
		String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
		String file = "data/in/";
		
		System.out.println("Escriba el nombre del fichero a cargar:");
		file += in.next();
			
		// Cargar el almacén
		System.out.println("Cargando el fichero \"" + file + "\"");
		long t, suma;				
		t = System.currentTimeMillis();
		m = new Almacen(file);
		t = System.currentTimeMillis() - t;
		System.out.print("Sentencias leídas en ");
		System.out.print(t);
		System.out.println(" ms");
		
		int respuesta = 1;
		ListaEnlazada<String> le;
		ListaArray<String> la;

		// Prueba 1
		System.out.println("Escribiendo sentencias que tienen el sujeto " + sujeto);
		suma = 0;
		for( int i = 0; i < 10; ++i ) {
			t = System.nanoTime();
			le = m.sentenciasPorSujeto(sujeto);
			suma += System.nanoTime() - t;
		}
		suma /= 10;
		//le.imprimirEnFichero("data/out/B0.txt");
		System.out.print("Escrito en ");
		System.out.print(suma / 1e6);
		System.out.println(" ms");

		// Prueba 2
		System.out.println("Escribiendo sentencias distintas que tienen el sujeto " + sujeto);
		suma = 0;
		for( int i = 0; i < 10; ++i ) {
			t = System.nanoTime();
			le = m.sentenciasDistintasPorSujeto(sujeto);
			suma += System.nanoTime() - t;
		}
		suma /= 10;
		//le.imprimirEnFichero("data/out/B1.txt");
		System.out.print("Escrito en ");
		System.out.print(suma / 1e6);
		System.out.println(" ms");

		// Prueba 3
		System.out.println("Escribiendo propiedades distintas que aparecen en el almacén");
		suma = 0;
		for( int i = 0; i < 10; ++i ) {
			t = System.nanoTime();
			la = m.propiedadesDistintas();
			suma += System.nanoTime() - t;
		}
		suma /= 10;
		//la.imprimirEnFichero("data/out/B2.txt");
		System.out.print("Escrito en ");
		System.out.print(suma / 1e6);
		System.out.println(" ms");

		// Prueba 4
		System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia");
		suma = 0;
		for( int i = 0; i < 10; ++i ) {
			t = System.nanoTime();
			le = m.entidadesSujetoObjeto();
			suma += System.nanoTime() - t;
		}
		suma /= 10;
		//le.imprimirEnFichero("data/out/B3.txt");
		System.out.print("Escrito en ");
		System.out.print(suma / 1e6);
		System.out.println(" ms");


	}
	
}
