package com.pr.dictionary;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.swing.*;



public class AddWord extends JFrame implements ActionListener, KeyListener{
    private JButton btnAdd;
    private JButton btnCancel;
    private JTextField txaMean;
    private JTextField txtWord;
    private String word;
    private String meaning;
    int mode = 0;
	private static final long serialVersionUID = 1L;
	
    public AddWord(String word, String mean, int mode) throws MalformedURLException, IOException, ParseException{
    	this.word = word;
    	this.meaning = mean;
    	this.mode = mode;
        initComponents();
    }
	private JPanel BuildWordPanel() throws IOException, ParseException{
		JPanel p = new JPanel();
		p.setBackground(Color.lightGray);
		new BorderLayout();
		p.setBorder(BorderFactory.createTitledBorder("Từ"));
        txtWord = new JTextField(12);
        txtWord.setText(this.word);
        p.add(txtWord,BorderLayout.SOUTH);
		p.setPreferredSize(new Dimension(300, 70));
		return p;
	}
	private JPanel BuildMeaningPanel() throws IOException, ParseException{
		JPanel p = new JPanel();
		p.setBackground(Color.lightGray);
		new BorderLayout();
		p.setBorder(BorderFactory.createTitledBorder("Nghĩa của từ"));
		txaMean = new JTextField(20);
        txaMean.setText(meaning);
        p.add(txaMean,BorderLayout.SOUTH);
		p.setPreferredSize(new Dimension(300, 70));
		return p;
	}
	
    private void initComponents() throws IOException, ParseException{
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        btnAdd = new JButton();
        btnCancel = new JButton();
        JPanel main = new JPanel();
        main.setBackground(Color.lightGray);
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(this);
        btnCancel.setText("Hủy");
        btnCancel.addActionListener(this);
       
        main.add(this.BuildWordPanel());
        main.add(this.BuildMeaningPanel());
        main.add(btnAdd);
        main.add(btnCancel);
        this.add(main);
    }
    @Override
	public void actionPerformed(ActionEvent e){
		Object o = e.getSource();
		if (o.equals(btnAdd)){
		       if (txtWord.getText().equals("")){
		           JOptionPane.showMessageDialog(null,"Từ thêm vào không được để trống!");
		       }else{
		    	   EditFile E = new EditFile(this.word, this.meaning, this.mode);
		           try{
					E.AddWord();
					Dict.addToHashMap(this.mode, txaMean.getText(), txtWord.getText());
					}catch (IOException e1){
						e1.printStackTrace();
					}
		    	   if (mode == 0) Gui.ReloadJListEV();
		    	   else Gui.ReloadJListVE();
		    	   JOptionPane.showMessageDialog(null,"Thêm từ mới thành công");
		           this.dispose();
		       }
		}
		if (o.equals(btnCancel)){
			this.dispose();
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0){
	}

	@Override
	public void keyReleased(KeyEvent arg0){
	}

	@Override
	public void keyTyped(KeyEvent arg0){
	}

}
