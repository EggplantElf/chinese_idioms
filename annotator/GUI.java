import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class GUI {

	public static JFrame mainwindow;
	public static JTextField text;
	public static JTextField textt;
	public static JTextArea texto;
	public static BufferedReader br;
	public static int lineNum;
	public static LinkedList<String> dict;
	public static Writer fw;
	public static String chara;
	public static int freq;
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		mainwindow = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		mainwindow.setTitle("Annotator");
		mainwindow.setSize(300, 270);
		//mainwindow.setLocation(100, 100);
		mainwindow.setLocationRelativeTo(null);
		panel.setSize(mainwindow.getSize());
		text = new JTextField(10);
		textt = new JTextField(3);
		texto = new JTextArea(9,15);
		textt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				try {
					GUI.saveLine(e.getKeyChar());
					GUI.readLine();
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
		
		GUI.run();
		
		textt.requestFocus();

	}
	
	public static void run() throws IOException {
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
		
		fis=new FileInputStream("../vocab.txt");
		isr=new InputStreamReader(fis, "UTF-8");
		br=new BufferedReader(isr);
		fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("../annotation.txt"),"UTF-8"));
//		fw=new FileWriter("/Users/zaa/annotation.txt");
		
		lineNum = 1;
		readLine();
	}
	
	public static void readLine() throws NumberFormatException, IOException {
		String line;
		if(br.ready()) {
			line=br.readLine();
			String[] spl = line.split(" ");
			chara = spl[0];
			freq = new Integer(spl[1]).intValue();
			
			if(freq<10) {
				text.setText("Finished!");
				textt.setEnabled(false);
				br.close();
				fw.close();
			}
			else {
				text.setText("l#"+lineNum+" "+chara+" f:"+(new Integer(freq)).toString());
				
				int logged = 0;
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
							texto.append(idiom+"\n");
							logged++;
							break;
						}
					}
					
					if(logged>=10)
						break;
				}
				lineNum++;
			}
		}
	}
	
	public static void saveLine(char c) throws IOException {
		fw.write(chara+" "+(new Integer(freq)).toString()+" "+c+"\n");
		fw.flush();
		textt.setText("");
		texto.setText("");
	}

}
