package test;

import estructurasDeDatos.*;
import gestionDeSentencias.*;

import java.io.IOException;

/**
 * Programa de pruebas para medir los tiempos de ejecuci�n de los m�todos.
 */
public class medirTiempos {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			Almacen m, almacenes[] = new Almacen[3];
			almacenes[1] = Almacen.cargar("data/in/A1.txt");
			almacenes[2] = Almacen.cargar("data/in/A2.txt");
			String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>",
					clase = "<http://www.w3.org/2002/07/owl#ObjectProperty>",
					profesor = "<http://www.Department0.University0.edu/FullProfessor0>",
					universidad = "<http://www.University0.edu>";
			// Vac�ar fichero
			Fichero.abrir("data/tiempos.txt", true, false);
			Fichero.cerrar();
			
			for (int a = 0; a <= 6; ++a) {
				
				// Cargar el almac�n
				System.out.println("Cargando el fichero \"data\\in\\A" + a + ".txt\"");
				long t, suma;
				t = System.currentTimeMillis();
				m = Almacen.cargar("data\\in\\A"+a+".txt");
				t = System.currentTimeMillis() - t;
				System.out.print("Sentencias le�das en ");
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
				System.out.println("Escribiendo propiedades distintas que aparecen en el almac�n");
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
				System.out.println("Escribiendo entidades distintas que son sujeto y tambi�n objeto de alguna sentencia");
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
				System.out.println("Escribiendo entidades que son sujeto com�n al almac�n cargado, A1 y A2...");
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
				System.out.println("Escribiendo en orden las sentencias que aparecen en el almac�n...");
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
				
				// Prueba 7a
				System.out.println("Escribiendo las clases de " + sujeto + "...");
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					le = m.clasesDe(sujeto);
					suma += System.nanoTime() - t;
				}
				suma /= 10;
				System.out.print("Escrito en ");
				System.out.print(suma / 1e6);
				System.out.println(" ms");
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				// Prueba 7b
				System.out.println("Escribiendo las superclases de " + sujeto + "...");
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					le = m.superClasesDe(sujeto);
					suma += System.nanoTime() - t;
				}
				suma /= 10;
				System.out.print("Escrito en ");
				System.out.print(suma / 1e6);
				System.out.println(" ms");
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				// Prueba 8
				System.out.println("Escribiendo entidades que son de la clase " + clase + "...");
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					le = m.entidadesDeClase(clase);
					suma += System.nanoTime() - t;
				}
				suma /= 10;
				System.out.print("Escrito en ");
				System.out.print(suma / 1e6);
				System.out.println(" ms");
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				// Prueba 9
				Fichero.cerrar();
				System.out.println("Descargando las sentencias del almac�n en el fichero B9.txt ...");
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
				Fichero.abrir("data/tiempos.txt", true, true);
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				// Prueba 10
				System.out.println("Escribiendo los estudiantes distintos de alguna asignatura del profesor " + profesor + "...");
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					le = m.estudiantesDelProfesor(profesor);
					suma += System.nanoTime() - t;
				}
				suma /= 10;
				System.out.print("Escrito en ");
				System.out.print(suma / 1e6);
				System.out.println(" ms");
				Fichero.escribirSentencia(Double.toString(suma / 1e6));
				
				// Prueba 11
				System.out.println("Escribiendo los profesores distintos que trabajan para alg�n departamento de la universidad " + universidad + "...");
				suma = 0;
				for (int i = 0; i < 10; ++i) {
					t = System.nanoTime();
					le = m.profesoresDeUniversidad(universidad);
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
