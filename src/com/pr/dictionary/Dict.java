package com.pr.dictionary;

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.HashSet;
 
public class Dict{
	private static HashMap<String, String> hMapEV;
	private static ArrayList<String> wordEV;
	private static HashMap<String, String> hMapVE;
	private static ArrayList<String> wordVE;
	public static void EditHashMap(int mode, String mean, String word){
		if (mode ==0){
			wordEV.add(word.toLowerCase());
			Collections.sort(wordEV);
			hMapEV.replace(word.trim(), Dict.hMapEV.get(word.trim()), mean);
			//hMapEV.put(word.trim(), mean);
		}
		else{
			wordVE.add(word.toLowerCase());
			Collections.sort(wordVE);
			hMapVE.replace(word.trim(), Dict.hMapVE.get(word.trim()), mean);
		}
	}
	public static void addToHashMap(int mode, String mean, String word){
		if (mode ==0){
			wordEV.add(word.toLowerCase());
			Collections.sort(wordEV);
			hMapEV.put(word.trim(), mean);
		}
		else{
			wordVE.add(word.toLowerCase());
			Collections.sort(wordVE);
			hMapVE.put(word.trim(), mean);
		}
	}
	public static void CreateHashMapEV() throws FileNotFoundException{
		wordEV = new ArrayList<String>();
		hMapEV = new HashMap<String, String>();
		//FileOutputStream fo = new FileOutputStream("test.txt");

		File f = new File("Data/DictEV.dic");
		try{
			Scanner sc = new Scanner(f);
			try{
				while (sc.hasNextLine()){
					String[] str = sc.nextLine().split("=");
					//System.out.println(str[0]);
					if (str[0] != "" && str[1] != "") {
						wordEV.add(str[0].toLowerCase());
						hMapEV.put(str[0].trim(), str[1]);
					}else
						System.out.println("Đã có lỗi !");
				}
			}catch (Exception e){
				System.out.println(e.getMessage());
			}finally{
				wordEV = removeDuplicate(wordEV);
				Collections.sort(wordEV);
				if (sc != null) sc.close();
			}
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public static void CreateHashMapVE(){
		wordVE = new ArrayList<String>();
		hMapVE = new HashMap<String, String>();
		File f = new File("Data/DictVE.dic");
		try{
			Scanner sc = new Scanner(f);
			try{
				while (sc.hasNextLine()){
					String[] str = sc.nextLine().split("=");
					if (str[0] != "" && str[1] != "") {
						wordVE.add(str[0].toLowerCase());
						hMapVE.put(str[0].trim(), str[1]);
					}else
						System.out.println("Đã có lỗi !");
				}
			}catch (Exception e){
				System.out.println(e.getMessage());
			}finally{
				wordVE = removeDuplicate(wordVE);
				Collections.sort(wordVE);
				if (sc != null) sc.close();
			}
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public static String Translate(String wToTrans, int mode){
		String mean = "";
		if (mode == 0){
			String resulf = Dict.gethMapEV().get(wToTrans);
			if (resulf != null) {
				String[] kq = resulf.split("#");
				mean = mean + kq[0] + "\n\n";
				for (int i=1; i<kq.length-1;i++){
					if (kq[i] == "") mean += "\n";
					else mean += kq[i]+"\n";
				}
			}
		}else{
			String resulf = Dict.gethMapVE().get(wToTrans);
			if (resulf != null) {
				String[] kq = resulf.split("#");
				mean = mean + kq[0] + "\n\n";
				for (int i=1; i<kq.length-1;i++){
					if (kq[i] == "") mean += "\n";
					else mean += kq[i]+"\n";
				}
			}
		}
		mean = mean.trim();
		return mean;
	}
	public static HashMap<String, String> gethMapEV(){
		return hMapEV;
	}
	public static void sethMapEV(HashMap<String, String> hMapEV){
		Dict.hMapEV = hMapEV;
	}
	public static ArrayList<String> getWordEV(){
		//Dict.CreateHashMapEV();
		return wordEV;
	}
	public static void setWordEV(ArrayList<String> wordEV){
		Dict.wordEV = wordEV;
	}
	public static HashMap<String, String> gethMapVE(){
		return hMapVE;
	}
	public static void sethMapVE(HashMap<String, String> hMapVE){
		Dict.hMapVE = hMapVE;
	}
	public static ArrayList<String> getWordVE(){
		return wordVE;
	}
	public static void setWordVE(ArrayList<String> wordVE){
		Dict.wordVE = wordVE;
	}
	public static ArrayList<String> removeDuplicate(ArrayList<String> arrList){
	    HashSet<String> h = new HashSet<String>(arrList);
	    arrList.clear();
	    arrList.addAll(h);
	    return arrList;
	}
}

