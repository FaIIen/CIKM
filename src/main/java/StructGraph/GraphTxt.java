package StructGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Tool.FileOp;

public class GraphTxt {
	private Map<String, Integer> graphMap;
	private String outFile;
	public GraphTxt(String outFile){
		this.outFile=FileOp.basePath+outFile;
		graphMap=new HashMap<>();
	}
	private void readRefine() throws IOException{
		for(int i=0;i<10;i++){
			System.out.println(i);
			BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.refinePath+"refine--"+i+"--.txt")));
			String oneLine=null;
			while((oneLine=br.readLine())!=null){
				if(!oneLine.contains(","))
					continue;
				else{
					//System.out.println(oneLine);
					String[] refineList=oneLine.split("\t")[1].split(",");
					computeOccur(refineList);
				}
			}
			br.close();
		}
	}
	
	
	private void computeOccur(String[] refineList) throws IOException{
		for(int i=0;i<refineList.length-1;i++){
			for(int j=i+1;j<refineList.length;j++){
				String query1=refineList[i];
				String query2=refineList[j];
				String 	occurQuery="";
				if(query1.equals(query2))
					return;
				else if(query1.compareTo(query2)==1){
					occurQuery=query1+"\t"+query2;				
				}else{
					occurQuery=query2+"\t"+query1;
				}
				int count=readOccur(occurQuery.substring(occurQuery.length()-1,occurQuery.length()), occurQuery);
				if(count==-1){
					//System.out.println(occurQuery);
					continue;
				}	
				if(graphMap.containsKey(occurQuery)){
					graphMap.put(occurQuery, graphMap.get(occurQuery)+count);
				}else{
					graphMap.put(occurQuery, count);
				}
			}
		}
	}
	
	private int readOccur(String indexNumber,String indexString) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.occurPath+"occur--"+indexNumber+"--.txt")));
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			if(oneLine.startsWith(indexString))
				return Integer.valueOf(oneLine.split("\t")[2]);
		}
		return -1;
	}
	public void getGraphTxt(){
		try {
			readRefine();
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File(outFile)));
			for(Map.Entry<String, Integer> entry:graphMap.entrySet()){
				bw.write(entry.getKey()+"\t"+entry.getValue()+"\n");
			}
			bw.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
	}
}
