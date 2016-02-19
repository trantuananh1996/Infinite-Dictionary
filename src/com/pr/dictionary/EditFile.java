package com.pr.dictionary;

import java.io.*;


public class EditFile{
	private FileOutputStream fout;
	
	private int mode = 0;
	private String word;
	private String meaning;
	private static String filename;
	private static int fl = 0;
	public EditFile(String word, String mean, int mode){
		this.word = word;
		this.meaning = mean;
		this.mode = mode;
		if (mode == 0)	EditFile.filename = "data/DictEV.dic";
		else 	EditFile.filename = "data/DictVE.dic";


	}

	public void AddWord() throws IOException{
		FileOutputStream fout;
		try{
			fout = new FileOutputStream(EditFile.filename,true);
			PrintWriter pw = new PrintWriter(fout);
			pw.println(this.word + "=#" + this.meaning + "#null");
			pw.close();
			fout.close();
		}catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Called");
	}
	
	public void EditWord(){
		EditFileThread E1 = new EditFileThread("Sửa file", this.word, this.meaning, EditFile.filename);
		Thread t1 = new Thread(E1);
		t1.start();
		EditFile.fl = 1;
	}

	public FileOutputStream getFout(){
		return fout;
	}

	public void setFout(FileOutputStream fout){
		this.fout = fout;
	}

	public int getMode(){
		return mode;
	}

	public void setMode(int mode){
		this.mode = mode;
	}

	public String getWord(){
		return word;
	}

	public void setWord(String word){
		this.word = word;
	}

	public String getMeaning(){
		return meaning;
	}

	public void setMeaning(String meaning){
		this.meaning = meaning;
	}

	public static int getFl(){
		return fl;
	}

	public static void setFl(int fl){
		EditFile.fl = fl;
	}
	
}