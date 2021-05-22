package covid.lucene;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException; 
import org.apache.lucene.search.ScoreDoc; 
import org.apache.lucene.search.TopDocs;
import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Locale;

public class MainLucene {

	String indexDir = "INDEX";
	//String dataDir = "DATA";
	//Indexer indexer;
	Searcher searcher;
	private String files;
	private String filecontent;
	private int j;
	private int count=0;
	private String str;
	private String s2;
	private String err;
	private String html = "<html>";
	private String end = "</html>";
	private String newLine = "<br/>";
	private ArrayList<String> history = new ArrayList<String>();
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> dataFileName = new ArrayList<String>();

	String dictionary[] = {"covid19","covid-19","coronavirus","vaccine","disease","covid","pandemic","corona","astrazeneca","pfizer","research"};

	// Main Engine
	public static void main(String[] args) throws IOException{
		//MainLucene tester;
		try{
		//tester = new MainLucene();
		//tester.createIndex();
		JFrame start = new UI("COVID SEARCH ENGINE");

		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/*private void createIndex() throws IOException{
		indexer = new Indexer(indexDir);
		int numIndexed; 
		long startTime = System.currentTimeMillis(); 
		numIndexed = indexer.createIndex(dataDir, new TextFileFilter()); 
		long endTime = System.currentTimeMillis();
		indexer.close();
		//s = numIndexed+" File indexed, time taken: " +(endTime-startTime)+" ms";
		System.out.println(numIndexed+" File indexed, time taken: " +(endTime-startTime)+" ms");
		//return s;
	}*/

	// History Method to get and save the search of the User
	public String getHistory(){
		j=1;
		str="";
		for(int i=0; i<history.size(); i=i+2){
			if(history.size()==1){
				str = history.get(i);
				break;
			}else if(j>=history.size()){
				str = str + history.get(i) + newLine + end;
				break;
			}else {
				str = str + html + history.get(i) + newLine + history.get(j) + newLine;
				j = j + 2;
			}
		}
		//System.out.println(str);
		return  str;
	}

	// Delete the User History
	public void deleteHistory(){
		history = new ArrayList<String>();
	}

	// Method to get User's search and display the files that are the most relevant
	public String search(String searchQuery) throws IOException, ParseException{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		history.add("(" + dtf.format(now) + "):  " + "  " + searchQuery);
		String right = "";
		String[] quest = searchQuery.toLowerCase(Locale.ROOT).split(" ");
		for(int i=0; i<quest.length; i++) {
			for (int j = 0; j < dictionary.length; j++) {
				if(quest[i].equals(dictionary[j])){
					right =right + " " + quest[i];
					break;
				}
			}
		}
		if(right.equals("")){
			err = "Error: Search '" + searchQuery + "' found 0 documents";
			s2="";
			return err;
		}
		err="";
		searcher = new Searcher(indexDir);
		long startTime = System.currentTimeMillis();
		TopDocs hits = searcher.search(right);
		long endTime = System.currentTimeMillis();
		int disp=0;
		files="";
		filecontent="";
		count=0;
		dataFileName = new ArrayList<String>();
		data = new ArrayList<String>();
		// Files that have a hit
		for(ScoreDoc scoreDoc : hits.scoreDocs){
			Document doc = searcher.getDocument(scoreDoc);
			dataFileName.add(doc.get(LuceneConstants.FILE_NAME));
			data.add(doc.get(LuceneConstants.FILE_PATH));
			if(count<10){
				files = files + doc.get(LuceneConstants.FILE_NAME) + newLine + newLine;
			}
			count++;
			disp++;
		}
		searcher.close();
		s2 = html + hits.totalHits + " 	Documents found in " + (endTime - startTime) + "ms" + newLine + newLine  + "Displaying the " + disp + " most relevant documents";
		return html + files;
	}

	public String getS2(){
		return s2;
	}

	// To open file in UI
	public void OpenFile(String file){
		try{
			Desktop.getDesktop().open(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getErr(){
		return err;
	}

	public ArrayList<String> getData(){
		return data;
	}

	public ArrayList<String> getDataFileName(){
		return dataFileName;
	}


	public int getDataSize(){
		return data.size();
	}
}