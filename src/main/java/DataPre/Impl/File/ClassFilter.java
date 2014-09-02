package DataPre.Impl.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Tool.FileOp;
import DataPre.FileFilter;



public class ClassFilter extends FileFilter{
	String className;
	
	public ClassFilter(String inFile,String className) {
		super(inFile);
		this.className=className;
	}

	@Override
	public List<String> filter(List<String> oneGroup) {
		List<String> returnList=new ArrayList<>();
		for (String oneLine : oneGroup) {
			if(oneLine.contains(className))
				return oneGroup;
		}
		return null;
	}
}
