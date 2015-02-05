import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Collections;

public class Extractor {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("../annotation_final.txt"), "UTF-8"));
		LinkedList<Colle> nList = new LinkedList<Colle>();
		LinkedList<Colle> vList = new LinkedList<Colle>();
		LinkedList<Colle> aList = new LinkedList<Colle>();
		LinkedList<Colle> iList = new LinkedList<Colle>();
		LinkedList<Colle> fList = new LinkedList<Colle>();

		String line;
		int lineNum=-1;
		while((line = br.readLine())!=null) {
			String[] slot = line.split(" ");
			lineNum++;
			if(slot.length<3)
				continue;
			if(slot[2].equals("n"))
				nList.add(new Colle(slot[0],slot[1],lineNum));
			else if(slot[2].equals("v"))
				vList.add(new Colle(slot[0],slot[1],lineNum));
			else if(slot[2].equals("a"))
				aList.add(new Colle(slot[0],slot[1],lineNum));
			else if(slot[2].equals("i"))
				iList.add(new Colle(slot[0],slot[1],lineNum));
			else if(slot[2].equals("f"))
				fList.add(new Colle(slot[0],slot[1],lineNum));
		}

		br.close();

		Collections.sort(nList);
		Collections.sort(vList);
		Collections.sort(aList);
		Collections.sort(iList);
		Collections.sort(fList);

		writeFile("../list_n.txt",nList);
		writeFile("../list_v.txt",vList);
		writeFile("../list_a.txt",aList);
		writeFile("../list_i.txt",iList);
		writeFile("../list_f.txt",fList);
	}

	public static void writeFile(String filename, LinkedList<Colle> cList) throws IOException{
		BufferedWriter fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(filename),"UTF-8"));
		for(Colle c : cList) {
			fw.write(c.chara+" "+c.freq+"\n");
		}
		fw.close();
	}
}

class Colle implements Comparable<Colle> {
	String chara;
	int freq;
	int lineNum;

	public Colle(String _chara, int _freq) {
		this.chara=_chara;
		this.freq=_freq;
		this.lineNum=-1;
	}

	public Colle(String _chara, String _freq) {
		this.chara=_chara;
		this.freq=new Integer(_freq);
		this.lineNum=-1;
	}

	public Colle(String _chara, int _freq, int _lineNum) {
		this.chara=_chara;
		this.freq=_freq;
		this.lineNum=_lineNum;
	}

	public Colle(String _chara, String _freq, int _lineNum) {
		this.chara=_chara;
		this.freq=new Integer(_freq);
		this.lineNum=_lineNum;
	}

	@Override
	public String toString() {
		return chara+" "+freq+(lineNum==-1?"":(" "+lineNum));
	}

	@Override
	public int compareTo(Colle o) {
		return o.freq-this.freq;
	}
}
