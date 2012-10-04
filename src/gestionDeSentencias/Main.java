package gestionDeSentencias;

import java.io.*;

public class Main {

	public static void main (String[] args) throws IOException {	
		Almacen m;
		long t=System.currentTimeMillis();
		m=new Almacen("data/A3.txt");
		System.out.println(System.currentTimeMillis()-t);
		FileWriter fstream = new FileWriter("data/out.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		t=System.currentTimeMillis();
		m.entidadesSujetoObjeto(out);
		System.out.println(System.currentTimeMillis()-t);
		out.close();
	}
	
}
