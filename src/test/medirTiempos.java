package test;

import estructurasDeDatos.*;
import gestionDeSentencias.*;

/**
 * Programa de pruebas para medir los tiempos de ejecución de los métodos.
 */
public class medirTiempos {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Almacen m;
		String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";

		for (int a = 0; a <= 6; ++a) {

			// Cargar el almacén
			System.out.println("Cargando el fichero \"data\\in\\A" + a + ".txt\"");
			long t, suma;
			t = System.currentTimeMillis();
			m = new Almacen("data\\in\\A"+a+".txt");
			t = System.currentTimeMillis() - t;
			System.out.print("Sentencias leídas en ");
			System.out.print(t);
			System.out.println(" ms");

			ListaEnlazada<String> le;
			ListaArray<String> la;

			// Prueba 1
			System.out.println("Escribiendo sentencias que tienen el sujeto " + sujeto);
			suma = 0;
			for (int i = 0; i < 10; ++i) {
				t = System.nanoTime();
				le = m.sentenciasPorSujeto(sujeto);
				suma += System.nanoTime() - t;
			}
			suma /= 10;
			System.out.print("Escrito en ");
			System.out.print(suma / 1e6);
			System.out.println(" ms");

			// Prueba 2
			System.out.println("Escribiendo sentencias distintas que tienen el sujeto " + sujeto);
			suma = 0;
			for (int i = 0; i < 10; ++i) {
				t = System.nanoTime();
				le = m.sentenciasDistintasPorSujeto(sujeto);
				suma += System.nanoTime() - t;
			}
			suma /= 10;
			System.out.print("Escrito en ");
			System.out.print(suma / 1e6);
			System.out.println(" ms");

			// Prueba 3
			System.out.println("Escribiendo propiedades distintas que aparecen en el almacén");
			suma = 0;
			for (int i = 0; i < 10; ++i) {
				t = System.nanoTime();
				la = m.propiedadesDistintas();
				suma += System.nanoTime() - t;
			}
			suma /= 10;
			System.out.print("Escrito en ");
			System.out.print(suma / 1e6);
			System.out.println(" ms");

			// Prueba 4
			System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia");
			suma = 0;
			for (int i = 0; i < 10; ++i) {
				t = System.nanoTime();
				le = m.entidadesSujetoObjeto();
				suma += System.nanoTime() - t;
			}
			suma /= 10;
			System.out.print("Escrito en ");
			System.out.print(suma / 1e6);
			System.out.println(" ms");
			
			// Prueba 6
			System.out.println("Escribiendo en orden las sentencias que aparecen en el almacén...");
			suma = 0;
			for (int i = 0; i < 10; ++i) {
				t = System.nanoTime();
				la = m.sentenciasOrdenadas();
				suma += System.nanoTime() - t;
			}
			suma /= 10;
			System.out.print("Escrito en ");
			System.out.print(suma / 1e6);
			System.out.println(" ms");
			
		}
	}

}
