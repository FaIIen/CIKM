package DataPre.Impl.Graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Tool.FileOp;
import DataPre.GraphPre;

public class OccurGraph extends GraphPre{
	private Map<String, Integer> occureMap;
	int curindex;
	public OccurGraph(String inFile,int index) {
		super(inFile);
		occureMap=new HashMap<>();
		this.curindex=index;
	}

	@Override
	/**
	 * compute the co-occur set of the seesion
	 * e.g Q1-
	 *     Q2-
	 *     Q3-
	 * then we record Q2 and Q3 is in the co-occur set of Q1
	 *                Q1 and Q2 is in the co-occur set of Q3
	 *                Q3 and Q1 is in the co-occur set of Q2
	 */
	public void filter(List<String> oneGroup) {
		Set<String> occurSet=new HashSet<>();
		for(int i=0;i<oneGroup.size()-1;i++){
			for(int j=i+1;j<oneGroup.size();j++){
				String preQuery=oneGroup.get(i).split("\t")[1];
				String curQuery=oneGroup.get(j).split("\t")[1];
				if(preQuery.compareTo(curQuery)==0){
					continue;
				}else if(preQuery.compareTo(curQuery)==1){
					String q=preQuery+"\t"+curQuery;
					int index=Integer.valueOf(q.substring(q.length()-1,q.length()));
					if(index!=curindex)
						continue;
					occurSet.add(preQuery+"\t"+curQuery);
				}else{
					String q=curQuery+"\t"+preQuery;
					int index=Integer.valueOf(q.substring(q.length()-1,q.length()));
					if(index!=curindex)
						continue;
					occurSet.add(curQuery+"\t"+preQuery);
				}
			}
		}
		for(String occurQuery:occurSet){
			if(occureMap.containsKey(occurQuery)){
				occureMap.put(occurQuery, occureMap.get(occurQuery)+1);
			}else{
				occureMap.put(occurQuery, 1);
			}
		}
	}

	/**
	 * write the co-occur Map to File
	 * File format:Map<Q1\tQ2,count> will be written into 
	 * Q1\tQ2\tCount and Q2\tQ1\tCount 
	 * To be written twice is easy for index in the next step
	 */
	@Override
	public void writeMap() throws IOException {
		BufferedWriter[] refineFileBw=new BufferedWriter[10];
		for(int i=0;i<10;i++){
			String fileName=FileOp.basePath+"train-data/co-occur/occur--"+i+"--.txt";
			File dir=new File(FileOp.basePath+"train-data/co-occur");
			if(!dir.exists())
				dir.mkdir();
			refineFileBw[i]=new BufferedWriter(new FileWriter(new File(fileName),true));
		}
		for(Map.Entry<String, Integer> entry:occureMap.entrySet()){
			String queryName=entry.getKey();
			int count=entry.getValue();
			int index=Integer.valueOf(queryName.substring(queryName.length()-1,queryName.length()));
			refineFileBw[index].write(queryName+"\t"+count+"\n");
		}
		for(int i=0;i<10;i++){
			refineFileBw[i].close();
		}
	}

}
