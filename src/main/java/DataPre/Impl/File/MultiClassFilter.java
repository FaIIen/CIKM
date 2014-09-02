package DataPre.Impl.File;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DataPre.FileFilter;

public class MultiClassFilter extends FileFilter{

	public MultiClassFilter(String inFile) {
		super(inFile);
	}

	@Override
	public List<String> filter(List<String> oneGroup) {
		for (String line : oneGroup) {
			if(line.contains("|")){
				return oneGroup;
			}
			
		}
		return null;
	}

}
