import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;


public class PatternStat {
	
	public static LinkedList<String> nList;
	public static LinkedList<String> aList;
	public static LinkedList<String> iList;
	public static LinkedList<String> vList;
	public static LinkedList<String> fList;
	
	public static HashMap<String, Integer> pMap;

	public static void main(String[] args) throws IOException {
		nList = new LinkedList<String>();
		aList = new LinkedList<String>();
		iList = new LinkedList<String>();
		vList = new LinkedList<String>();
		fList = new LinkedList<String>();
		pMap = new HashMap<String, Integer>();
		
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("../list_n.txt"), "UTF-8"));
		String line;
		while((line = br.readLine())!=null) {
			String[] ls = line.split(" ");
			nList.add(ls[0]);
		}
		br.close();
		
		br=new BufferedReader(new InputStreamReader(new FileInputStream("../list_a.txt"), "UTF-8"));
		while((line = br.readLine())!=null) {
			String[] ls = line.split(" ");
			aList.add(ls[0]);
		}
		br.close();
		
		br=new BufferedReader(new InputStreamReader(new FileInputStream("../list_i.txt"), "UTF-8"));
		while((line = br.readLine())!=null) {
			String[] ls = line.split(" ");
			iList.add(ls[0]);
		}
		br.close();
		
		br=new BufferedReader(new InputStreamReader(new FileInputStream("../list_v.txt"), "UTF-8"));
		while((line = br.readLine())!=null) {
			String[] ls = line.split(" ");
			vList.add(ls[0]);
		}
		br.close();
		
		br=new BufferedReader(new InputStreamReader(new FileInputStream("../list_f.txt"), "UTF-8"));
		while((line = br.readLine())!=null) {
			String[] ls = line.split(" ");
			fList.add(ls[0]);
		}
		br.close();
		
		br=new BufferedReader(new InputStreamReader(new FileInputStream("../wordlist.txt"), "UTF-8"));
		while((line = br.readLine())!=null) {
			String p = getPattern(line);
			if(p==null)
				continue;
			if(!pMap.containsKey(p))
				pMap.put(p, 1);
			else
				pMap.put(p, pMap.get(p)+1);
		}
		br.close();
		
		LinkedList<Pattern> pList = new LinkedList<Pattern>();
		for(String k : pMap.keySet()) {
			pList.add(new Pattern(k,pMap.get(k)));
		}
		Collections.sort(pList);
		
		BufferedWriter fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("../patternstat.txt"),"UTF-8"));
		for(Pattern p : pList) {
			//System.out.println(p.p+" "+p.f);
			boolean mark=false;
			for(int i=0;i<p.p.length();i++) {
				if(p.p.charAt(i)=='f')
					mark=true;
			}
			if(mark)
				continue;
			fw.write(p.p+" "+p.f+"\n");
		}
		fw.close();
		
		fw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("../patternstat_withF.txt"),"UTF-8"));
		for(Pattern p : pList) {
			//System.out.println(p.p+" "+p.f);
			fw.write(p.p+" "+p.f+"\n");
		}
		fw.close();
	}
	
	public static String getPOS(String c) {
		if(nList.contains(c))
			return "n";
		if(aList.contains(c))
			return "a";
		if(iList.contains(c))
			return "i";
		if(vList.contains(c))
			return "v";
		if(fList.contains(c))
			return "f";
		return "-";
	}
	
	public static String getPattern(String s) {
		if(s.length()!=4)
			return null;
		String ret = new String();
		for(int i=0;i<s.length();i++) {
			String r = getPOS(s.substring(i, i+1));
			if(r.equals("-")/*||r.equals("f")*/)
				return null;
			ret+=r;
		}
		return ret;
	}

}

class Pattern implements Comparable{
	public String p;
	public int f;
	public Pattern(String p, int f) {
		this.p=p;
		this.f=f;
	}
	
	public int compareTo(Object o) {
		return ((Pattern)o).f-this.f;
	}
}
