package Classifier;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Tool.FileOp;

public class SortGraphResult {
	class ComparatorList implements Comparator<String>{

		@Override
		public int compare(String s1, String s2) {
			Pattern p=Pattern.compile("(.*): .*");
			Matcher m1=p.matcher(s1);
			Matcher m2=p.matcher(s2);
			String t1="";
			String t2="";
			if(m1.find())
				t1=m1.group(1);
			if(m2.find())
			    t2=m2.group(1);
			return t1.compareTo(t2);
		}
	}
	
	public void sortList() throws IOException{
		List<String> oriList=FileOp.readList("graph-data/stResult.txt");
		for(int i=0;i<oriList.size();i++){
			if(oriList.get(i).equals(""))
				oriList.remove(i);
		}
		Collections.sort(oriList,new ComparatorList());
		File classifierFile=new File(FileOp.classifierPath);
		if(!classifierFile.exists())
			classifierFile.mkdir();
		FileOp.writeList("classifier/sortedGraphResult.txt", oriList);
	}
}
