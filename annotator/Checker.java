import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class Checker {

	public static JFrame mainwindow;
	public static JTextField text;
	public static JTextField textt;
	public static JTextArea texto;
	public static BufferedReader br;
	public static BufferedReader br2;
	public static int lineNum;
	public static LinkedList<String> dict;
	public static Writer fw;
	public static String chara;
	public static int freq;
	public static Random rand;
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		mainwindow = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		mainwindow.setTitle("Annotation Checker");
		mainwindow.setSize(300, 270);
		//mainwindow.setLocation(100, 100);
		mainwindow.setLocationRelativeTo(null);
		panel.setSize(mainwindow.getSize());
		text = new JTextField(12);
		textt = new JTextField(3);
		texto = new JTextArea(9,15);
		textt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				try {
					Checker.saveLine(e.getKeyChar());
					while(Checker.readLine()){};
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				textt.setText("");
			}
			
		});
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		texto.setBorder(BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		panel.setVisible(true);
		JLabel lbt = new JLabel("Input:");
		//panel.add(lbt);
		lbt.setVisible(true);
		panel.add(text);
		text.setVisible(true);
		JLabel lbtt = new JLabel("Target:");
		//panel.add(lbtt);
		lbtt.setVisible(true);
		panel.add(textt);
		textt.setVisible(true);
		JLabel lbto = new JLabel("Result:");
		//panel.add(lbto);
		lbto.setVisible(true);
		panel.add(texto);
		texto.setVisible(true);
		texto.setEditable(false);
		
		mainwindow.add(panel);
		mainwindow.setVisible(true);
		
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rand = new Random();
		
		if(args.length>0)
			Checker.run((new Integer(args[0])).intValue());
		else
			Checker.run(1);
		
		textt.requestFocus();

	}
	
	public static void run(int stLine) throws IOException {
		dict = new LinkedList<String>();
		FileInputStream fis=new FileInputStream("../wordlist.txt");
		InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
		br=new BufferedReader(isr);
		String line;
		while((line = br.readLine())!=null) {
			dict.add(line);
		}
		br.close();
		isr.close();
		fis.close();
		
		fis=new FileInputStream("../annotation_weimeng.txt");
		isr=new InputStreamReader(fis, "UTF-8");
		br=new BufferedReader(isr);
		FileInputStream fis2=new FileInputStream("../annotation_xiang.txt");
		InputStreamReader isr2=new InputStreamReader(fis2, "UTF-8");
		br2=new BufferedReader(isr2);
		fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("../annotation.txt", true),"UTF-8"));
//		fw=new FileWriter("/Users/zaa/annotation.txt");
		
		for(int i=1;i<stLine;i++) {
			if(br.ready() && br2.ready()) { 
				br.readLine();
				br2.readLine();
			}
			else
				throw new IOException("Line not match on line#"+lineNum);
		}
		
		lineNum = stLine;
		while(readLine()){};
	}
	
	public static boolean readLine() throws NumberFormatException, IOException {
		String line,line2;
		if(br.ready() && br2.ready()) {
			line=br.readLine();
			String[] spl = line.split(" ");
			line2=br2.readLine();
			String[] spl2 = line2.split(" ");
			chara = spl[0];
			freq = new Integer(spl[1]).intValue();
			String label1,label2;
			label1=(spl.length<=2?" ":spl[2]);
			label2=(spl2.length<=2?" ":spl2[2]);
			String chara2=spl2[0];
			if(!chara.equals(chara2)) {
				throw new IOException("Line not match on line#"+lineNum);
			}
			if(label1.charAt(0)==label2.charAt(0)) {
				saveLine(label1.charAt(0));
				lineNum++;
				return true;
			}
			
			if(freq<10) {
				text.setText("Finished!");
				textt.setEnabled(false);
				texto.setText("Congratulations!");
				br.close();
				br2.close();
				fw.close();
			}
			else {
				text.setText("l#"+lineNum+" "+chara+" f:"+(new Integer(freq)).toString()+" w:"+label1+" x:"+label2);
				
				int logged = 0;
				LinkedList<String> showIdioms = new LinkedList<String>();
				for(String idiom : dict) {
//					for(int i=0;i<idiom.length();i+=3) {
//						if(idiom.charAt(i)==chara.charAt(0)) {
//							if(idiom.charAt(i+1)==chara.charAt(1)) {
//								if(idiom.charAt(i+2)==chara.charAt(2)) {
//									texto.append(idiom+"\n");
//									logged++;
//									break;
//								}
//							}
//						}
//					}
					for(char c : idiom.toCharArray()) {
						if(c==chara.charAt(0)) {
							showIdioms.add(idiom);
							logged++;
							break;
						}
					}
				}
				for(int i=0;i<10;i++) {
					int sel = rand.nextInt(logged);
					texto.append(showIdioms.get(sel)+"\n");
					showIdioms.remove(sel);
					logged--;
				}
				lineNum++;
			}
		}
		else {
			text.setText("Finished!");
			textt.setEnabled(false);
			texto.setText("Congratulations!");
			br.close();
			br2.close();
			fw.close();
		}
		return false;
	}
	
	public static void saveLine(char c) throws IOException {
		if(c=='\n')
			c=' ';
		fw.write(chara+" "+(new Integer(freq)).toString()+" "+c+"\n");
		fw.flush();
		textt.setText("");
		texto.setText("");
	}

}
