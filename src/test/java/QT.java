import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Tool.FileOp;


public class QT {

	public static void main(String[] args) throws IOException {
		
		Set<String> set=new HashSet<>();
		BufferedReader br=new BufferedReader(new FileReader(FileOp.basePath+"tCount.txt"));
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			set.add(oneLine);
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter(FileOp.basePath+"tCount1.txt"));
		for(String sets:set){
			bw.write(sets+"\n");
		}
		bw.close();
	}

}
