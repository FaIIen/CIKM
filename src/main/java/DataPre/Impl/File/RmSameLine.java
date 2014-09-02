package DataPre.Impl.File;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DataPre.FileFilter;

public class RmSameLine extends FileFilter{

	
	public RmSameLine(String inFile) {
		super(inFile);
	}

	@Override
	public List<String> filter (List<String> oneGroup){
		if(oneGroup.size()==1)
			return oneGroup;
		String preString = null;
		String curString = null;
		List<String> returnList=new ArrayList<>();
		returnList.add(oneGroup.get(0));
		for(int i=1;i<oneGroup.size();i++){
			preString=oneGroup.get(i-1);
			curString=oneGroup.get(i);
			if(!preString.equals(curString))
				returnList.add(curString);
		}			
		return returnList;
	}
}
