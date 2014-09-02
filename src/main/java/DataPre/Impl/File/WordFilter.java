package DataPre.Impl.File;

import java.util.List;

import DataPre.FileFilter;

public class WordFilter extends FileFilter{

	public WordFilter(String inFile) {
		super(inFile);
	}

	@Override
	public List<String> filter(List<String> oneGroup) {
		for (String string : oneGroup) {
			if(string.contains("0381742 0616057 0041297 0458228"))
				return oneGroup;
		}
		return null;
	}

}
