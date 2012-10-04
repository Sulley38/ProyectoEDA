package gestionDeSentencias;

import java.io.*;

public class Main {

	public static void main (String[] args) throws IOException {	
		Almacen m;
		long t=System.currentTimeMillis();
		m=new Almacen("data/A3.txt");
		System.out.println(System.currentTimeMillis()-t);
		System.out.print("aaa");
		FileWriter fstream = new FileWriter("data/out.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		m.sentenciasPorSujeto("<http://swat.cse.lehigh.edu/onto/univ-bench.owl#Article>",out);
		out.close();
		System.out.println("Este programa ya hace mucho.");
		System.out.println("Disfrutalo...");
	}
	
}
