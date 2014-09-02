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

public class RefineGraph extends GraphPre{
	/*
	 * <Query,Refine Set<Q2>>
	 */
	Map<String, Set<String>> refineMap;
	public RefineGraph(String inFile) {
		
		super(inFile);
		refineMap=new HashMap<>();
	}

	@Override
	/**
	 * compute the Refine Query Set
	 * e.g Q1-        Q1-
	 *     Q2-        Q3-
	 *    then refineMap contains a keyset <Q1,Set<Q2,Q3>>
	 */
	public void filter(List<String> oneGroup) {
		for(int i=0;i<oneGroup.size()-1;i++){
			String preQuery=oneGroup.get(i).split("\t")[1];
			String curQuery=oneGroup.get(i+1).split("\t")[1];
			if(!preQuery.equals(curQuery)){
				if(refineMap.containsKey(preQuery)){
					refineMap.get(preQuery).add(curQuery);
				}else{
					Set<String> tempSet=new HashSet<>();
					tempSet.add(curQuery);
					refineMap.put(preQuery, tempSet);
				}
			}
		}
	}

	/**
	 * write the refineMap to File
	 * the number of the filename is chosen as the second number of the query as an index
	 * FileFormat:Q\tQ1,Q2,Q3
	 * which Q1,Q2,Q3 is the refine query set of Q
	 */
	@Override
	public void writeMap() throws IOException {
		BufferedWriter[] refineFileBw=new BufferedWriter[10];
		for(int i=0;i<10;i++){
			String fileName=FileOp.basePath+"train-data/refine/refine--"+i+"--.txt";
			refineFileBw[i]=new BufferedWriter(new FileWriter(new File(fileName)));
		}
		for(Map.Entry<String, Set<String>> entry:refineMap.entrySet()){
			String queryName=entry.getKey();
			Set<String> refineSet=entry.getValue();
			int index=Integer.valueOf(queryName.substring(queryName.length()-1,queryName.length()));
			refineFileBw[index].write(queryName+"\t");
			int j=0;
			for(String refineQuery:refineSet){
				if(j!=0)
					refineFileBw[index].write(",");
				refineFileBw[index].write(refineQuery);
				j++;
			}
			refineFileBw[index].newLine();
		}
		for(int i=0;i<10;i++){
			refineFileBw[i].close();
		}
		
	}
	
}
