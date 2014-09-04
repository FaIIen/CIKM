package Tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOp {
	
	public static String basePath=System.getProperty("user.dir")+"/src/main/resource/";
	public static String refinePath=basePath+"train-data/refine/";
	public static String occurPath=basePath+"train-data/co-occur/";
	public static String docPath=basePath+"train-data/document/";
	public static String subPath=basePath+"graph-data/sub/";
	public static String graphPath=basePath+"graph-data/";
	/**
	 * 从指定文件名读取信息
	 * @param fileName 文件名  默认文件路径为classpath:src/main/resource下
	 * @return 原文件每一行为一个String，组合成的List
	 * @throws IOException
	 */
	public static List<String> readList(String fileName) throws IOException{
		fileName=basePath+fileName;
		BufferedReader br=new BufferedReader(new FileReader(fileName));
		List<String> returnList=new ArrayList<>();
		String oneLine=null;
		while((oneLine=br.readLine())!=null){
			returnList.add(oneLine);
		}
		br.close();
		return returnList;
	}
	
	/**
	 * 将List写入文件
	 * @param fileName 文件名  默认文件路径为classpath:src/main/resource下
	 * @param writeList 需要写入文件的List
	 * @param flag true为每次从文件尾部继续写入，false为每次重新写文件 
	 * @throws IOException
	 */
	public static void writeList(String fileName,List<String> writeList,Boolean flag) throws IOException{
		fileName=basePath+fileName;
		BufferedWriter bw=null;
		if(flag){
			bw=new BufferedWriter(new FileWriter(fileName,true));
		}else{
			bw=new BufferedWriter(new FileWriter(fileName));
		}
			
		for (String string : writeList) {
			bw.write(string+"\n");
		}
		bw.flush();
		bw.close();
	}
	
	/**
	 * 将List写入文件，默认每次重新写文件
	 * @param fileName 文件名  默认文件路径为classpath:src/main/resource下  
	 * @param writeList 需要写入文件的List
	 * @throws IOException
	 */
	public static void writeList(String fileName,List<String> writeList) throws IOException{
		fileName=basePath+fileName;
		BufferedWriter bw=new BufferedWriter(new FileWriter(fileName));
		for (String string : writeList) {
			bw.write(string+"\n");
		}
		bw.flush();
		bw.close();
	}
}
