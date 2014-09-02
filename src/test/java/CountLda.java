import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CountLda {

	public static void main(String[] args) throws IOException {
		BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Fallen\\Desktop\\1.txt"));
		String oneLine=null;
		Map<String, Integer> countMap=new HashMap<>();
		while((oneLine=br.readLine())!=null){
			if(countMap.containsKey(oneLine)){
				countMap.put(oneLine, countMap.get(oneLine)+1);
			}else{
				countMap.put(oneLine, 1);
			}
		}
		for(Map.Entry<String, Integer> entry:countMap.entrySet()){
			System.out.println(entry.getKey()+"\t"+entry.getValue());
		}
	}

}
