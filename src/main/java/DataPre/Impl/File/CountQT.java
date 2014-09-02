package DataPre.Impl.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DataPre.FileFilter;

public class CountQT extends FileFilter{

	public CountQT(String inFile) {
		super(inFile);
	}

	@Override
	public List<String> filter(List<String> oneGroup) {
		Set<String> set = new HashSet<>();
		for (String string : oneGroup) {
			//String q=string.split("\t")[1];
			String[] temp=string.split("\t");
			if(temp.length==3){
				String t=string.split("\t")[2];
				set.add(t);
			}
			
			//set.add(q);
			
		}
		return new ArrayList<>(set);
	}
	
}
