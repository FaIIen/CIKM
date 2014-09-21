import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Tool.FileOp;
import DataPre.GraphPre;
import DataPre.Impl.Graph.CountQuery;


public class Test {
	public static void main(String[] args) throws IOException{
		Map<String, String> map=new HashMap<>();
		List<String> testList=FileOp.readList("train-data/89161404981093269.txt");
		BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.basePath+"train-data/test.txt")));
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.basePath+"train-data/newtest.txt")));
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			String[] items=oneLine.split("\t");
			map.put(items[0], items[1]);
		}
		br.close();
		for(String test:testList){
			bw.write(test+"\t"+map.get(test));
			bw.newLine();
		}
		bw.close();
	}
}
