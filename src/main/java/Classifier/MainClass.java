package Classifier;

import java.io.IOException;

public class MainClass {

	public static void main(String[] args) {
		SortGraphResult sgr=new SortGraphResult();
		try {
			sgr.sortList();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
