import java.io.BufferedReader;
import java.io.IOException;

import DataPre.GraphPre;
import DataPre.Impl.Graph.CountQuery;


public class Test {
	public static void main(String[] args){
		GraphPre g=new CountQuery("train-data/train");
		try {
			g.process();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
