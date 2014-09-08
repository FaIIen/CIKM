import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DataPre.GraphPre;
import DataPre.Impl.Graph.CountQuery;


public class Test {
	public static void main(String[] args){
		String x="5029684: 1.0*22529";
		Pattern p=Pattern.compile("(.*): .*");
		Matcher m=p.matcher(x);
		if(m.find()){
			System.out.println(m.group(1));
		}
	}
}
