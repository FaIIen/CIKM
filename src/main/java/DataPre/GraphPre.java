package DataPre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import Tool.FileOp;

public abstract class GraphPre {
	public String inFile;
	public GraphPre(String inFile){
		this.inFile=FileOp.basePath+inFile;
	}
	
	public void process() throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(inFile));
		List<String> oneGroup=readFile(br);
		while(oneGroup!=null){
			filter(oneGroup);
			oneGroup=readFile(br);
		}
		br.close();
		writeMap();
	}
	
	public static List<String> readFile(BufferedReader br) throws IOException{
		List<String> oneGroup=new ArrayList<>();
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			if(oneLine.equals("")){
				return oneGroup;
			}else{
				oneGroup.add(oneLine);
			}
		}
		br.close();
		return null;
	}
	public abstract void filter(List<String> oneGroup);
	public abstract void writeMap() throws IOException;
}
