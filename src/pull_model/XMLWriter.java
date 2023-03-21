package pull_model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * 
 * A simple class to save numerical results to an XML file.
 *
 */
public class XMLWriter {
	
	FileWriter writer;
	BufferedWriter bufferedWriter;
	public XMLWriter(String filename, String... parameters) {
		
		try {
			writer = new FileWriter(filename+".xml", false);
	        bufferedWriter = new BufferedWriter(writer);

	        
	        write("<figure "+paramsToString(parameters)+">\n");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void write(String s) {
		
		try {
			bufferedWriter.write(s);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String paramsToString(String [] params) {
		
		if (params.length%2 == 1) throw new RuntimeException("Odd number of parameters");
		
		String s = "";
		
		for (int i=0; i<params.length ; i+=2) {
			s += ""+params[i]+"=\""+params[i+1]+"\""+(i+2<params.length?" ":"");
		}
		
		return s;
		
	}
	
	public void plot(String x, String y, String... parameters) {
		
		write("\t<plot x=\""+x+"\" y=\""+y+"\" "+paramsToString(parameters)+"/>\n");
		
	}
	
	public void addData(String name, Object[] data) {
		
		write("\t<data name=\""+name+"\"> ");
		for (int i=0 ; i<data.length ; i++) {
			write(data[i].toString()+(i<data.length-1?",":" "));
		}
		write("</data>\n");
		
	}
	
	public void addData(String name, ArrayList<?> data) {
		
		write("\t<data name=\""+name+"\"> ");
		for (int i=0 ; i<data.size() ; i++) {
			write(data.get(i).toString()+(i<data.size()-1?",":" "));
		}
		write("</data>\n");
		
	}
	
	public void addData(String name, int[] data) {
		
		write("\t<data name=\""+name+"\"> ");
		for (int i=0 ; i<data.length ; i++) {
			write(data[i]+(i<data.length-1?",":" "));
		}
		write("</data>\n");
		
	}
	
	public void addData(String name, double[] data) {
		
		write("\t<data name=\""+name+"\"> ");
		for (int i=0 ; i<data.length ; i++) {
			write(data[i]+(i<data.length-1?",":" "));
		}
		write("</data>\n");
		
	}
	
	public void close() {
		
		write("</figure>\n");
		
		try {
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}

































