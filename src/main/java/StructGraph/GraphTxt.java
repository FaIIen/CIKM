package StructGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Tool.FileOp;

public class GraphTxt {
	private String[] readRefine() throws IOException{
		for(int i=0;i<10;i++){
			BufferedReader br=new BufferedReader(new FileReader(new File(FileOp.refinePath+"refine--"+i+"--.txt")));
			String oneLine=null;
			while((oneLine=br.readLine())!=null){
				
			}
		}
		return null;
	}
	
}
