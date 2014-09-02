package DataPre.Impl.Graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Tool.FileOp;
import DataPre.GraphPre;

public class DocumentGraph extends GraphPre{
	Map<String,Integer> docMap;
	public DocumentGraph(String inFile) {
		super(inFile);
		docMap=new HashMap<>();
	}

	@Override
	/**
	 * compute the query to document set
	 * e.g Q1 -T1
	 * we record T1 is in the document set of Q1
	 */
	public void filter(List<String> oneGroup) {
		Set<String> docSet=new HashSet<>();
		for(String line:oneGroup){
			String[] items=line.split("\t");
			if(items.length==2 || items[2].equals("-"))
				continue;
			else{
				String query=items[1];
				String doc=items[2];
				docSet.add(query+"\t"+doc);
			}
		}
		for(String oneDoc:docSet){
			if(docMap.containsKey(oneDoc))
				docMap.put(oneDoc, docMap.get(oneDoc)+1);
			else
				docMap.put(oneDoc, 1);
		}
		
	}

	@Override
	/**
	 * write the docMap to File
	 * File Format:Q\tDoc\tCount
	 */
	public void writeMap() throws IOException {
		BufferedWriter[] refineFileBw=new BufferedWriter[10];
		for(int i=0;i<10;i++){
			String fileName=FileOp.basePath+"train-data/document/doc--"+i+"--.txt";
			File dir=new File(FileOp.basePath+"train-data/document");
			dir.mkdir();
			refineFileBw[i]=new BufferedWriter(new FileWriter(new File(fileName)));
		}
		for(Map.Entry<String, Integer> entry:docMap.entrySet()){
			String query=entry.getKey();
			int count=entry.getValue();
			int index=Integer.valueOf(query.substring(query.length()-1,query.length()));
			refineFileBw[index].write(query+"\t"+count+"\n");
		}
		for(int i=0;i<10;i++){
			refineFileBw[i].close();
		}
		
	}

}
