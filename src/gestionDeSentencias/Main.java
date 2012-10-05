package gestionDeSentencias;
import java.io.*;

public class Main {

	public static void main (String[] args) throws IOException {	
		Almacen m;
		String file = "data/A0.txt";
		// Cargar el almacén
		System.out.println("Cargando el fichero \"" + file + "\"");
		long t=System.currentTimeMillis();
		m=new Almacen(file);
		System.out.print("Sentencias leídas en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		
		String sujeto = "<http://swat.cse.lehigh.edu/onto/univ-bench.owl#AdministrativeStaff>";
		
		// Prueba 1
		BufferedWriter out = new BufferedWriter(new FileWriter("data/out1.txt"));
		System.out.println("Escribiendo sentencias que tienen el sujeto "+sujeto);
		t=System.currentTimeMillis();
		m.sentenciasPorSujeto(sujeto, out);
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		out.close();
		
		// Prueba 2
		out = new BufferedWriter(new FileWriter("data/out2.txt"));
		System.out.println("Escribiendo sentencias distintas que tienen el sujeto "+sujeto);
		t=System.currentTimeMillis();
		m.sentenciasDistintasPorSujeto(sujeto, out);
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		out.close();
		
		// Prueba 3
		out = new BufferedWriter(new FileWriter("data/out3.txt"));
		System.out.println("Escribiendo propiedades distintas que aparecen en el almacén");
		t=System.currentTimeMillis();
		m.propiedadesDistintas(out);
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		out.close();
		
		// Prueba 4
		out = new BufferedWriter(new FileWriter("data/out4.txt"));
		System.out.println("Escribiendo entidades distintas que son sujeto y también objeto de alguna sentencia");
		t=System.currentTimeMillis();
		m.entidadesSujetoObjeto(out);
		System.out.print("Escrito en ");
		System.out.print(System.currentTimeMillis()-t);
		System.out.println(" ms");
		out.close();
		
	}
	
}
