package com.pr.dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.WindowEvent;

public class Gui extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	private JTextArea txt;
	private JTextArea modetext;
	private int mode = 0; // mode = 0: translate E-V ; mode = 1: translate V-E
	private static DefaultListModel<String> listModel;
	private JList<String> list;
	private JTextField tfEnterText;
	private JButton btnTranslate;
	private JButton btnAddWord;
	private JButton btnEditWord;
	private String data;
	GoogleTranslator translatorEV, translatorVE;

	private JMenu getOptionsMenu(){
		JMenu myMenu = new JMenu("Chiều dịch");
		JMenuItem myItem = new JMenuItem("Anh-Việt");
		myItem.addActionListener(this);
		myMenu.add(myItem);
		myItem = new JMenuItem("Việt-Anh");
		myItem.addActionListener(this);
		myMenu.add(myItem);
		return myMenu;
	}

	// CÁC HÀNH ĐỘNG (EVEN)
	// ReloadJList
	public static void ReloadJListEV(){
		listModel.clear();
		for (String i : Dict.getWordEV()){
			listModel.addElement(i);
		}
		System.out.println("EV List Loaded");
	}

	public static void ReloadJListVE(){
		listModel.clear();
		for (String i : Dict.getWordVE()){
			listModel.addElement(i);
		}
		System.out.println("VE List Loaded");
	}

	// Build UI
	public Gui() throws MalformedURLException, IOException, ParseException,NullPointerException{
		setSize(750, 480);
		setTitle("Infinite Dictionary by Trần Anh");
		setLocationRelativeTo(null);
		setResizable(true);
		this.addWindowListener(new WindowAdapter(){
			 @Override
	            public void windowClosed(WindowEvent e) {
	                super.windowClosed(e);
	                //do something
	            }

			@Override
		    public void windowClosing(WindowEvent e){
		        int ask = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình không?",
		                null, JOptionPane.YES_NO_OPTION);
		        if (ask == JOptionPane.YES_OPTION){
		        	super.windowClosing(e);
		        	if (EditFile.getFl() == 1){
		        		JOptionPane.showMessageDialog(null,"Vẫn đang sửa từ, chương trình sẽ chạy ngầm đến khi sửa xong");
		        	}
		        	else
		        		System.exit(0);
		        }else
					try{
						new Gui().setVisible(true);
					}catch (MalformedURLException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch (NullPointerException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch (IOException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}catch (ParseException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    }
		});
		BuidingGUi();
	}

	private JPanel BuildEnterText() throws IOException, ParseException{// NHẬP
		JPanel p = new JPanel();
		p.setBackground(Color.lightGray);
		JLabel lbNhap = new JLabel("Nhập từ cần tra:");
		tfEnterText = new JTextField(12);
		btnTranslate = new javax.swing.JButton();
		btnTranslate.setText("Dịch");
		p.setBorder(BorderFactory.createTitledBorder("Tìm từ"));
		new BorderLayout();
		p.add(lbNhap, BorderLayout.SOUTH);// add label
		p.add(tfEnterText, BorderLayout.SOUTH);// add text field "Nhập"
		p.add(btnTranslate, BorderLayout.SOUTH);// add button "Dịch"
		p.setPreferredSize(new Dimension(400, 100));
		tfEnterText.addKeyListener(this);
		btnTranslate.addActionListener(this);
		tfEnterText.setActionCommand("Translate");
		tfEnterText.addActionListener(btnTranslate.getActionListeners()[0]);
		return p;
	}

	private JPanel BuildOptionPanel() throws IOException, ParseException{// TÙY CHỌN
		JPanel p = new JPanel();
		p.setBackground(Color.lightGray);
		p.setBorder(BorderFactory.createTitledBorder("Tùy chọn"));
		// Add menubar
		JMenuBar myMenuBar = new JMenuBar();
		JMenu myMenu = getOptionsMenu();
		myMenuBar.add(myMenu);
		p.add(myMenuBar);
		// Add text
		modetext = new JTextArea(20, 33);
		modetext.setBackground(Color.lightGray);
		p.add(modetext,BorderLayout.WEST);
		modetext.setText("\tChiều dịch: Anh-Việt\n\tSố từ trong CSDL:"+Dict.getWordEV().size());
		p.setPreferredSize(new Dimension(400, 100));
		return p;
	}
	
	private JPanel BuildWordList() throws IOException, ParseException{// DANH SÁCH TỪ
		
		JPanel p = new JPanel();
		p.setBackground(Color.lightGray);
		p.setLayout(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder("Danh sách từ"));
		p.setPreferredSize(new Dimension(300, 530));
		listModel = new DefaultListModel<>();
		ReloadJListEV();
		list = new JList<>(listModel);
		list.setBorder(BorderFactory.createTitledBorder(""));
		p.add(new JScrollPane(list), BorderLayout.CENTER);

		return p;
	}
	
	private JPanel BuildMeaningField() throws IOException, ParseException{// NGHĨA CỦA TỪ
		JPanel p = new JPanel();
		 javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		p.setBackground(Color.lightGray);
		txt = new JTextArea(10, 33);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txt);

        p.add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 100, 100);
		p.add(txt,BorderLayout.NORTH);
		p.setBorder(BorderFactory.createTitledBorder("Nghĩa của từ"));
		// THÊM CÁC ACTIONLISTIONER
		btnAddWord = new JButton("Thêm vào từ điển");
		btnEditWord = new JButton("Sửa từ");
		btnAddWord.addActionListener(this);
		btnEditWord.addActionListener(this);
		p.add(btnEditWord, BorderLayout.SOUTH);
		p.add(btnAddWord, BorderLayout.SOUTH);
		btnEditWord.setVisible(false);
		btnAddWord.setVisible(false);
		p.setPreferredSize(new Dimension(400, 500));
		return p;
	}
	
	private void BuidingGUi() throws IOException, ParseException{
		
		// Dich Anh Viet Google translate
		translatorEV = new GoogleTranslator();
		translatorEV.setSrcLang("en");
		translatorEV.setDestLang("vi");
		// Dich Viet Anh Google translate
		translatorVE = new GoogleTranslator();
		translatorVE.setSrcLang("vi");
		translatorVE.setDestLang("en");

		JPanel p_main = new JPanel();
		p_main.setBackground(Color.lightGray);
		p_main.setLayout(new BorderLayout());

		// Thêm các panel vào panel chính
		JPanel P = new JPanel();
		P.setBackground(Color.lightGray);
		P.add(this.BuildOptionPanel());
		P.add(this.BuildEnterText());
		P.add(BuildMeaningField());
		p_main.add(BuildWordList(), BorderLayout.WEST);
		p_main.add(P);

		// Hành động click vào " Danh sách từ "
		list.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				JList<?> source = (JList<?>) e.getSource();
				String word = (String) source.getSelectedValue();
				if (mode == 0) {
					data = Dict.Translate(word,mode);
					tfEnterText.setText(word);
					txt.setText(data);
					btnEditWord.setVisible(true);
					btnAddWord.setVisible(false);
				
				}
				if (mode == 1) {
					data = Dict.Translate(word, mode);
					tfEnterText.setText(word);
					txt.setText(data);
					btnEditWord.setVisible(true);
					btnAddWord.setVisible(false);
				}
			}
		});
		this.add(p_main);	
}

	public static void main(String[] args) throws MalformedURLException, IOException, ParseException{
		
		Dict.CreateHashMapEV();
		Dict.CreateHashMapVE();
		new Gui().setVisible(true);
		
	}

	// Actionlistener
	@Override
	public void actionPerformed(ActionEvent e){
		Object o = e.getSource();

		if (o.equals(btnAddWord)){//Nhấn vào Thêm từ
	        AddWord addNew;
			try{
				addNew = new AddWord(tfEnterText.getText(),data,mode);
		        addNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        addNew.setResizable(true);
		        addNew.setVisible(true);
		        addNew.setSize(300, 250);
		        addNew.setTitle("Thêm từ");
		        addNew.setLocationRelativeTo(null);
		        addNew.setVisible(true);
			}catch (IOException | ParseException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		if (o.equals(btnEditWord)){//Nhấn vào Sửa từ
			 EditWord addNew;
				try{
					addNew = new EditWord(tfEnterText.getText(),data,mode);
			        addNew.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			        addNew.setResizable(true);
			        addNew.setVisible(true);
			        addNew.setSize(300, 250);
			        addNew.setTitle("Sửa từ");
			        addNew.setLocationRelativeTo(null);
			        addNew.setVisible(true);
				}catch (IOException | ParseException e1){
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		}
		
		if (e.getActionCommand() == "Anh-Việt") {
			mode = 0;
			ReloadJListEV();
			modetext.setText("\tChiều dịch: Anh-Việt\n\tSố từ trong CSDL:"+Dict.getWordEV().size());
		}else if (e.getActionCommand() == "Việt-Anh") {
			mode = 1;
			ReloadJListVE();
			modetext.setText("\tChiều dịch: Việt-Anh\n\tSố từ trong CSDL:" + Dict.getWordVE().size());
		}

		// Dich tieng Anh->Viet
		if ((e.getActionCommand().equals("Translate") || o.equals(btnTranslate)) && mode == 0) {
			String resulf = Dict.gethMapEV().get(tfEnterText.getText());
			if (resulf != null) {
				data = Dict.Translate(tfEnterText.getText(), mode);
				txt.setText(data);
				btnEditWord.setVisible(true);
				btnAddWord.setVisible(false);
			}else{// Dịch google
				try{
					data = translatorEV.translate(tfEnterText.getText()).trim();
				}catch (IOException e1){
					e1.printStackTrace();
				}
				txt.setText(data);
				
				if (txt.getText().trim() == "null" || txt.getText() == "") txt.setText("Đã xảy ra lỗi kết nối!");
				else txt.setText("Từ bạn tìm không có trong cơ sở dữ liệu." + "\n" + "Dữ liệu từ Google Dịch:\n\n\t" + data);
				btnEditWord.setVisible(false);
				btnAddWord.setVisible(true);

			}
		}
		// Dich tieng Viet -> Anh
		if ((e.getActionCommand().equals("Translate") ||o.equals(btnTranslate)) && mode == 1) {
			String resulf = Dict.gethMapVE().get(tfEnterText.getText());
			if (resulf != null) {
				data = Dict.Translate(tfEnterText.getText(), mode);
				txt.setText(data);
				btnEditWord.setVisible(true);
				btnAddWord.setVisible(false);
			}else{
				try{
					data = translatorVE.translate(tfEnterText.getText()).trim();
				}catch (IOException e1){
					e1.printStackTrace();
				}
				if (data == "null" || data == "") txt.setText("Đã xảy ra lỗi kết nối!");
				else txt.setText("Từ bạn tìm không có trong cơ sở dữ liệu." + "\n" + "Dữ liệu từ Google Dịch:\n\n\t" + data);
				btnEditWord.setVisible(false);
				btnAddWord.setVisible(true);
			}
		}
	}

	// Hành động nhập vào khung "Nhập" từ của từ điển
	@Override
	public void keyPressed(KeyEvent arg0){
	}

	@Override
	public void keyReleased(KeyEvent arg0){
		if (mode == 0) {
			String txt = tfEnterText.getText();
			listModel.clear();
			for (int i = 0; i < Dict.getWordEV().size(); i++){
				if (Dict.getWordEV().get(i).startsWith(txt)) {
					listModel.addElement(Dict.getWordEV().get(i));
				}
			}
		}
		if (mode == 1) {
			String txt = tfEnterText.getText();
			listModel.clear();
			for (int i = 0; i < Dict.getWordVE().size(); i++){
				if (Dict.getWordVE().get(i).startsWith(txt)) {
					listModel.addElement(Dict.getWordVE().get(i));
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0){
	}

	public int getMode(){
		return mode;
	}

	public void setMode(int mode){
		this.mode = mode;
	}
	
}
