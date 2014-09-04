package DataPre.Impl.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Tool.FileOp;
import DataPre.GraphPre;

public class CountQuery extends GraphPre{

	Set<String> querySet;
	public CountQuery(String inFile) {
		super(inFile);
		querySet=new HashSet<>();
	}

	@Override
	public void filter(List<String> oneGroup) {
		for (String string : oneGroup) {
			if(!string.split("\t")[0].equals("CLASS=UNKNOWN")){
				querySet.add(string.split("\t")[1]);
			}
		}
		
	}

	@Override
	public void writeMap() throws IOException {
		FileOp.writeList("train-data/countqt.txt", new ArrayList<>(querySet));
		
	}

}
