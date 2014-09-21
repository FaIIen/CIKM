package DataPre;

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
import java.util.TreeSet;

import javax.swing.text.DocumentFilter;

import Tool.FileOp;
import DataPre.Impl.Code.CodeConvert;
import DataPre.Impl.File.ClassFilter;
import DataPre.Impl.File.CountQT;
import DataPre.Impl.File.LDAFilter;
import DataPre.Impl.File.MultiClassFilter;
import DataPre.Impl.File.SameSession;
import DataPre.Impl.File.WordFilter;
import DataPre.Impl.Graph.CountQuery;
import DataPre.Impl.Graph.DocumentGraph;
import DataPre.Impl.Graph.OccurGraph;
import DataPre.Impl.Graph.RefineGraph;

public class DataPreMainClass {

	public static void main(String[] args) {
		try {			
//			CodeConvert convert=new CodeConvert("train-data/train");
//			convert.startChange(1, "train-data/train_tmpcodechange.txt",0,"QueryMap.txt");
//			File tmpFile=new File(FileOp.basePath+"train-data/train_tmpcodechange.txt");
//			convert=new CodeConvert("train-data/train_tmpcodechange.txt");
//			convert.startChange(2, "train-data/train_codechange.txt", 5000000,"TitleMap.txt");
//			if(tmpFile.exists())
//				tmpFile.delete();
//			GraphPre datapre=new RefineGraph("train-data/train_codechange.txt");
//			datapre.process();
//			for(int i=0;i<10;i++){
//				datapre=new OccurGraph("train-data/train_codechange.txt",i);
//				datapre.process();
//			}
//			datapre=new DocumentGraph("train-data/train_codechange.txt");
//			datapre.process();
//			GraphPre g=new CountQuery("train-data/train_codechange.txt");
//			g.process();
			
			
//			FileFilter ldaFilter=new LDAFilter("train-data/train");
//			ldaFilter.process("train-data/lda.txt");
//			BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.basePath+"train-data/lda.txt")));
//			BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.basePath+"train-data/ldanew.txt")));
//			String oneLine=null;
//			Set<String> set=new TreeSet<>();
//			while((oneLine=br.readLine()) != null){
//				set.add(oneLine);
//			}
//			for(String setLine:set){
//				bw.write(setLine);
//				bw.newLine();
//			}
//			br.close();
//			bw.close();
			
			GraphPre sessionPre=new SameSession("train-data/train");
			sessionPre.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
