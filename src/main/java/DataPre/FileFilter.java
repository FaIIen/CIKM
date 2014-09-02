package DataPre;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Tool.FileOp;

/**
 * 对文档内容进行过滤（提取） 默认以一个查询块为单位进行过滤
 * 入口为process函数，其中调用filter为每一个查询块进行过滤
 * @author Fallen
 */
public abstract class FileFilter {
	String inFile;
	
	public FileFilter(String inFile){
		this.inFile=FileOp.basePath+inFile;
	}
	
	/**
	 * 将每一个查询块通过filter函数过滤后，将过滤后的内容写入文件
	 * @param outFile  写入文件的文件名
	 * @throws IOException
	 */
	public void process(String outFile) throws IOException{
		outFile=FileOp.basePath+outFile;
		File out=new File(outFile);
		if(out.exists())
			out.delete();
		BufferedReader br=new BufferedReader(new FileReader(inFile));
		BufferedWriter bw=new BufferedWriter(new FileWriter(outFile,true));
		List<String> oneGroup=readFile(br);
		while(oneGroup!=null){
			oneGroup=filter(oneGroup);
			writeFile(bw, oneGroup);
			oneGroup=readFile(br);
			bw.flush();
		}
		br.close();
		bw.close();
		
	}
	
	/**
	 * 从文件读入内容，每次返回一个查询块
	 * @param br 
	 * @return 一个查询块的内容
	 * @throws IOException
	 */
	public List<String> readFile(BufferedReader br) throws IOException{
		List<String> oneGroup=new ArrayList<>();
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			if(oneLine.equals("")){
				return oneGroup;
			}else{
				oneGroup.add(oneLine);
			}
		}
		br.close();
		return null;
	}
	
	/**
	 * 写入一个查询块的内容
	 * @param bw
	 * @param oneGroup  查询块
	 * @throws IOException
	 */
	public void writeFile(BufferedWriter bw,List<String> oneGroup) throws IOException{
		if(oneGroup==null)
			return;
		for (String group : oneGroup) {
			bw.write(group+"\n");
		}
		bw.newLine();
	}
	public abstract List<String> filter(List<String> oneGroup);
	
}
