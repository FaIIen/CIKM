package StructGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Tool.FileOp;

public class MainClass {

	public static void main(String[] args) {
		GraphTxt gt=new GraphTxt("graph-data/graph.txt");
		gt.getGraphTxt();
		try {
			mergeDoc("graph-data/graph.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private  static void mergeDoc(String graphTxt) throws IOException{
		String docPath=FileOp.docPath;
		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FileOp.basePath+graphTxt),true));
		for(int i=0;i<10;i++){
			BufferedReader br=new BufferedReader(new FileReader(new File(docPath+"doc--"+i+"--.txt")));
			String oneLine=null;
			while((oneLine=br.readLine())!=null){
				bw.write(oneLine);
				bw.newLine();
			}
			br.close();
		}
		bw.close();
	}
}
