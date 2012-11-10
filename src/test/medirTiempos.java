package test;

import estructurasDeDatos.*;
import gestionDeSentencias.*;

import java.io.IOException;

/**
 * Programa de pruebas para medir los tiempos de ejecución de los métodos.
 */
public class medirTiempos {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			Almacen m, almacenes[] = new Almacen[3];
			almacenes[1] = new Almacen("data/in/A1.txt");
			almacenes[2] = new Almacen("data/in/A2.txt");
			String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
			// Vacíar fichero
			Fichero.abrir("data/tiempos.txt", true, false);
			Fichero.cerrar();
			
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
				Fichero.abrir("data/tiempos.txt", true, true);
				Fichero.escribirSentencia("A" + a + ".txt");
				Fichero.escribirSentencia(Long.toString(t));
	
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
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
	
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
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
	
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
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
	
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
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				//Prueba 5
				System.out.println("Escribiendo entidades que son sujeto común al almacén cargado, A1 y A2...");
				almacenes[0] = m;
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					le = Almacen.entidadesSujetoEnTodos(almacenes);
					suma += System.nanoTime() - t;
				}
				suma /= 10;
				System.out.print("Escrito en ");
				System.out.print(suma / 1e6);
				System.out.println(" ms");
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
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
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				// Prueba 9
				System.out.println("Descargando las sentencias del almacén en el fichero B9.txt ...");
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					m.descargar("data/out/B9.txt");
					suma += System.nanoTime() - t;
				}
				suma /= 10;
				System.out.print("Escrito en ");
				System.out.print(suma / 1e6);
				System.out.println(" ms");
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				Fichero.escribirSentencia("-------------------------");
				Fichero.cerrar();
			}
			
		} catch (IOException e) {
			System.out.println("Error al acceder al fichero especificado.");
		}
	}

}
