package DataPre;

import java.io.File;
import java.io.IOException;

import javax.swing.text.DocumentFilter;

import Tool.FileOp;
import DataPre.Impl.Code.CodeConvert;
import DataPre.Impl.File.ClassFilter;
import DataPre.Impl.File.CountQT;
import DataPre.Impl.File.MultiClassFilter;
import DataPre.Impl.File.WordFilter;
import DataPre.Impl.Graph.DocumentGraph;
import DataPre.Impl.Graph.OccurGraph;
import DataPre.Impl.Graph.RefineGraph;

public class DataPreMainClass {

	public static void main(String[] args) {
		try {
//			CodeConvert convert=new CodeConvert("train-data/train");
//			convert.startChange(1, "train-data/train_tmpcodechange.txt","Q","QueryMap.txt");
//			File tmpFile=new File(FileOp.basePath+"train-data/train_tmpcodechange.txt");
//			convert=new CodeConvert("train-data/train_tmpcodechange.txt");
//			convert.startChange(2, "train-data/train_codechange.txt", "T","TitleMap.txt");
//			if(tmpFile.exists())
//				tmpFile.delete();
			GraphPre refine=new RefineGraph("train-data/train_codechange.txt");
			refine.process();
			refine=null;
//			GraphPre occur=new OccurGraph("train-data/train_codechange.txt");
//			occur.process();
//			occur=null;
//			GraphPre doc=new DocumentGraph("train-data/train_codechange.txt");
//			doc.process();
//			doc=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
