package com.pr.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class EditFileThread implements Runnable{
    private String prefix="";
    private static String filename;
    private String word;
    private String mean;
    public EditFileThread(String p, String word, String mean, String filename){
        prefix=p;
        this.word = word;
        this.mean = mean;
        EditFileThread.filename = filename;
    }
    public void run(){
    	//code here
    	System.out.println(this.prefix);
    	File f = new File(EditFileThread.filename);
		try{
			Scanner sc = new Scanner(f);
			try{
				while (sc.hasNextLine()){
					String line = sc.nextLine();
					String[] str = line.split("=");
					if (str[0].equals(this.word)){
						String replaceWith = "";
						replaceWith = replaceWith + this.word + "=#" + this.mean + "##null";
						replaceSelected(line,replaceWith);
					}
				}
			}catch (Exception e){
				System.out.println(e.getMessage());
			}finally{
				if (sc != null) sc.close();
			}
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}
		/*
		for (int i = 0; i <3000000; i++){
			System.out.println(i);
		}*/
    	EditFile.setFl(0);
        JOptionPane.showMessageDialog(null,"Sửa từ thành công, khởi động lại chương trình để thấy kết quả");


    }
	private static void replaceSelected(String replace, String replaceWith){
	    try {
	        // input the file content to the String "input"
	        BufferedReader file = new BufferedReader(new FileReader(EditFileThread.filename));
	        String line;
	        String input = "";
	        while ((line = file.readLine()) != null) input += line + "\n";
	        file.close();
	        System.out.println("Loaded data to edit");
	        input = input.replace(replace,replaceWith );
	        // write the new String with the replaced line OVER the same file
	        FileOutputStream fileOut = new FileOutputStream(EditFileThread.filename);
	        fileOut.write(input.getBytes());
	        System.out.println("Done");
	        fileOut.close();
	    }catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }finally{
	    	System.out.println("Edit File done");
	    }
	}
	
}