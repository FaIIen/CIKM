package DataPre.Impl.Code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Tool.FileOp;

public class CodeConvert {
	protected Map<String, String> convertMap;
	protected String inFile;
	
	public CodeConvert(String inFile){
		this.inFile=FileOp.basePath+inFile;
		convertMap=new HashMap<>();
	}
	
	public void convert(String codeName,int position) throws IOException{
		int Count=0;
		BufferedReader br=new BufferedReader(new FileReader(new File(inFile)));
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			if(oneLine.equals(""))
				continue;
			String[] items=oneLine.split("\t");
			if(position==1 || (position==2 && items.length==3 && !items[2].equals("") && !items[2].equals("-")) ){
				String query=oneLine.split("\t")[position];
				if(!convertMap.containsKey(query)){
					convertMap.put(query, codeName+Count);
					Count++;
				}
			}
		}
	}
	
	public void startChange(int position,String outFileName,String codeName,String mapName) throws IOException{
		convert(codeName,position);
		BufferedReader br=new BufferedReader(new FileReader(new File(inFile)));
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.basePath+outFileName)));
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			if(oneLine.equals("")){
				bw.newLine();
				continue;
			}
				
			String[] items=oneLine.split("\t");
			if(position==1 || (position==2 && items.length==3)){
				if(position==2 && items[2].equals("-")){
					bw.write(oneLine);
					bw.newLine();
					continue;
				}
				else if(position==2 && items[2].equals("")){
					bw.write(oneLine);
					bw.newLine();
					continue;
				}
				String oriString=items[position];
				String repString=convertMap.get(oriString);
				if(position==1)
					oneLine=oneLine.replace("\t"+oriString+"\t", "\t"+repString+"\t");
				if(position==2)
					oneLine=oneLine.replace("\t"+oriString, "\t"+repString);
				bw.write(oneLine);
				bw.newLine();
			}	
		}
		br.close();
		bw.close();
		bw=new BufferedWriter(new FileWriter(new File(FileOp.basePath+"train-data/"+mapName)));
		for(Map.Entry<String,String> entry:convertMap.entrySet()){
			bw.write(entry.getKey()+"\t"+entry.getValue()+"\n");
		}
		bw.close();
	}
}
