package StructGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pig.parser.QueryParser.foreach_clause_complex_return;

import Tool.FileOp;

public class GraphTxt {
	private Map<String, Integer> graphMap;
	private String outFile;
	public GraphTxt(String outFile){
		this.outFile=FileOp.basePath+outFile;
		graphMap=new HashMap<>();
	}
	
	
	private void readRefine() throws IOException{
		for(int k=0;k<10;k++){
			File delFile=new File(FileOp.subPath+"sub--"+k+"--.txt");
			if(delFile.exists())
				delFile.delete();
		}
		BufferedWriter[] writers=new BufferedWriter[10];
		for(int k=0;k<10;k++){
			writers[k]=new BufferedWriter(new FileWriter(new File(FileOp.subPath+"sub--"+k+"--.txt"),true));
		}
		for(int i=0;i<10;i++){
			BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.refinePath+"refine--"+i+"--.txt")));
			String oneLine=null;
			while((oneLine=br.readLine())!=null){
				if(!oneLine.contains(","))
					continue;
				else{
					//System.out.println(oneLine);
					String[] refineList=oneLine.split("\t")[1].split(",");
					computeOccur(refineList,writers);
				}
			}
			br.close();
		}
		for(int k=0;k<10;k++){
			writers[k].close();
		}
	}
	
	
	private void computeOccur(String[] refineList,BufferedWriter[] writers) throws IOException{
		for(int i=0;i<refineList.length-1;i++){
			for(int j=i+1;j<refineList.length;j++){
				String query1=refineList[i];
				String query2=refineList[j];
				String 	occurQuery="";
				int flag=query1.compareTo(query2);
				if(flag==0)
					return;
				else if(flag==1){
					occurQuery=query1+"\t"+query2;				
				}else{
					occurQuery=query2+"\t"+query1;
				}
				writeCoRefine(occurQuery,writers);	
			}
		}
	}
	
	private void writeCoRefine(String occurQuery,BufferedWriter[] writers) throws IOException{
		String index=occurQuery.substring(occurQuery.length()-1,occurQuery.length());
		writers[Integer.valueOf(index)].write(occurQuery+"\n");
	}
	
	private void compareOccur() throws IOException{
		File dFile=new File(FileOp.subPath+"subTotal.txt");
		if(dFile.exists())
			dFile.delete();
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.subPath+"subTotal.txt"),true));
		for(int indexNumber=0;indexNumber<10;indexNumber++){
			Map<String, String> occurMap=new HashMap<>();
			BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.occurPath+"occur--"+indexNumber+"--.txt")));
			String oneLine=null;
			while((oneLine=br.readLine())!=null){
				String[] items=oneLine.split("\t");
				occurMap.put(items[0]+"\t"+items[1], items[2]);
			}
			br=new BufferedReader(new FileReader(new File(FileOp.subPath+"sub--"+indexNumber+"--.txt")));
			while((oneLine=br.readLine())!=null){
				if(occurMap.containsKey(oneLine))
					bw.write(oneLine+"\t"+occurMap.get(oneLine)+"\n");
			}
			br.close();
		}
		bw.close();
		
	}
	public void getGraphTxt(){
		try {
			readRefine();
			compareOccur();
			BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.subPath+"subTotal.txt")));
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.basePath+"graph-data/graph.txt")));
			String oneLine=null;
			while((oneLine=br.readLine())!=null){
				String[] items=oneLine.split("\t");
				String query=items[0]+"\t"+items[1];
				int count=Integer.valueOf(items[2]);
				if(graphMap.containsKey(query)){
					graphMap.put(query, graphMap.get(query)+count);
				}else{
					graphMap.put(query, count);
				}
			}
			br.close();
			for(Map.Entry<String, Integer> entry:graphMap.entrySet()){
				bw.write(entry.getKey()+"\t"+entry.getValue()+"\n");
			}
			bw.close();
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
	}
}
