package covid.lucene;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Locale;

public class MainLucene {

	String indexDir = "INDEX";
	String dataDir = "DATA";
	Indexer indexer;
	Searcher searcher;
	IndexSearcher search;
	private String files;
	private String filecontent;
	private int j;
	private int count=0;
	private boolean bool;
	private boolean keywords=true;
	private String str;
	private boolean ifInSpell=false;
	private boolean titleCheck;
	private boolean sortByDate=false;
	private String s2;
	private String err;
	private String right;
	private String str1="";
	private String correct="";
	private String title;
	private String html = "<html>";
	private String end = "</html>";
	private String newLine = "<br/>";
	private ArrayList<String> history = new ArrayList<String>();
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> dataFileName = new ArrayList<String>();
	private ArrayList<String> dataFileTitle = new ArrayList<String>();
	private ArrayList<String> relevantHistory = new ArrayList<String>();
	private ArrayList<String> dataContent = new ArrayList<String>();
	private ArrayList<String> sortedData = new ArrayList<String>();
	private ArrayList<String> sortedDataFileName = new ArrayList<String>();
	//Our Covid-19 Dictionary
	String[] dictionary = {"covid19","covid-19","coronavirus","vaccine","disease","covid","pandemic","astrazeneca","pfizer","research","side","effects","cases"};

	String[] detectGRLanguage = {"α","β","γ","δ","ε","ζ","η","θ","ι","κ","λ","μ","ν","ξ","ο","π","ρ","σ","τ","υ","φ","χ","ψ","ω"};

	//Spelling check array
	String[] spellckeck = {"kovid","kobid","kobit","kovit","cobid","covit","kovid19","kobid19","kobit19","kovit19","cobid19","covit19","koronavirus","corona","koronavirous","coronavirous","koronabirous","vacine","vaksine","vacinne","desease","disis"};


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

	//Method to index files from directory
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

	// History Method to save the search of the User
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
		return  str;
	}

	// Delete the User History
	public void deleteHistory(){
		history = new ArrayList<String>();
	}

	// Method to get User's search and display the files(documents) that are the most relevant
	public String search(String searchQuery) throws IOException, ParseException, InvalidTokenOffsetsException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		//add search query in history
		history.add("(" + dtf.format(now) + "):  " + "  " + searchQuery);
		String[] splitter = searchQuery.toLowerCase(Locale.ROOT).split("(?!^)");
		for(int i=0; i<detectGRLanguage.length; i++){
			if(splitter[0].equals(detectGRLanguage[i])){
				err=html + "Please change your keyboard language from Greek to English" + newLine + "Παρακαλώ αλλάξτε την γλώσσα του πληκτρολογίου σας από Ελληνικά σε Αγγλικά";;
				s2="";
				return err;
			}
		}

		if(searchQuery.equals("")){
			err="";
			return "Error: Try to search for COVID-19 relevant words";
		}
		bool = false;
		//Check for spelling grammar
		String[] quest = searchQuery.toLowerCase(Locale.ROOT).split(" ");
		for(int i=0; i<quest.length; i++) {
			for(int j = 0; j < spellckeck.length; j++)
				if (quest[i].equals(spellckeck[j])){
					bool = true;
					ifInSpell = true;
					if(j==0 || j==1 || j==2 || j==3 || j==4 || j==5 || j==6 || j==7 || j==8 || j==9 || j==10 || j==11){
						correct = correct + "covid-19 ";
					}
					else if(j==12 || j==13 || j==14 || j==15 || j==16){
						correct= correct + "coronavirus ";
					}
					else if(j==17 || j==18 || j==19){
						correct = correct + "vaccine ";
					}
					else if(j==20 || j==21){
						correct = correct + "disease ";
					}
				}
		}
		right = "";
		// Check if the search of the user is in our dictionary
		if(ifInSpell==false) {
			for (int i = 0; i < quest.length; i++) {
				for (int j = 0; j < dictionary.length; j++) {
					if (quest[i].equals(dictionary[j])) {
						if(quest[0].equals("vaccine") && quest.length==1){
							bool = true;
							err = html + "No results found for search: " + searchQuery + newLine;
							s2="";
							correct = "covid-19 vaccine";
							return err;
						}
						right = right + " " + quest[i];
						break;
					}
				}
			}
		}
		if(right.equals("")){
			err = html + "No results found for search: " + searchQuery + newLine ;
			if(relevantHistory.size()>0){
				for (String str : relevantHistory){
					str1 = str1 + str + newLine;
				}
				s2="";
				ifInSpell=false;
				err = html + "No results found for search: " + searchQuery + newLine + newLine + "Try searching for:" + newLine + str1;
				str1="";
				return err;
			}
			s2="";
			return err;
		}
		relevantHistory.add(right);
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
		// Display sorted files
		if(sortByDate==true){
			for(int i=0; i<10; i++) {
				files = files + sortedDataFileName.get(i) + newLine + newLine;
			}
		}
		// Files that have a hit and count the score of every file
		for(ScoreDoc scoreDoc : hits.scoreDocs){
			Document doc = searcher.getDocument(scoreDoc);
			dataFileName.add(doc.get(LuceneConstants.FILE_NAME));
			if(titleCheck==true){
				dataFileTitle.add(doc.get(LuceneConstants.TITLE));
			}
			Path path = Paths.get(doc.get(LuceneConstants.FILE_PATH));
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			// Reset Highlight to default
			content = content.replaceAll( "</strong>" , "");
			content = content.replaceAll("<strong>","");
			Files.write(path, content.getBytes(charset));
			// HIGHLIGHT KEY WORDS
			for(int i=0; i<quest.length; i++ ){
				if(quest[i].equals("covid-19")){
					String a = quest[i].toUpperCase(Locale.ROOT);
					content = content.replaceAll(a, "<strong>" + a + "</strong>" );
				}else if(quest[i].equals("vaccine")){
					String[] a = quest[i].split("(?!^)");
					String mix="V";
					for(int e=1; e<a.length; e++){
						mix = mix + a[e];
					}
					String y = quest[i] + "s";
					content = content.replaceAll(y, "<strong>" + y + "</strong>" );
					content = content.replaceAll(mix, "<strong>" + mix + "</strong>" );
				}else if(quest[i].equals("astrazeneca")){
					String[] a = quest[i].split("(?!^)");
					String mix="A";
					for(int e=1; e<5; e++){
						mix = mix + a[e];
					}
					mix = mix + "Z";
					for(int e=6; e<a.length; e++){
						mix = mix + a[e];
					}
					content = content.replaceAll(mix, "<strong>" + mix + "</strong>" );
				}else if(quest[i].equals("pfizer")){
					String[] a = quest[i].split("(?!^)");
					String mix="P";
					for(int e=1; e<a.length; e++){
						mix = mix + a[e];
					}
					content = content.replaceAll(mix, "<strong>" + mix + "</strong>" );
				}
				content = content.replaceAll(quest[i], "<strong>" +  quest[i] + "</strong>");
			}
			Files.write(path, content.getBytes(charset));

			data.add(doc.get(LuceneConstants.FILE_PATH));
			if(count<10 && sortByDate==false){
				files = files + doc.get(LuceneConstants.FILE_NAME) + newLine + newLine;
			}
			count++;
			disp++;
		}
		SortByDate();
		searcher.close();
		s2 = html + hits.totalHits + " 	Documents found in " + (endTime - startTime) + "ms" + newLine + newLine  + "Displaying the " + disp + " most relevant documents";
		return html + files;
	}
	// Method to Sort (descending order) from newest to oldest docs
	public void SortByDate() throws IOException {
		BasicFileAttributes file1;
		BasicFileAttributes file2;
		int f1=1;
		int f2;
		for(int i=0; i < data.size(); i++){
			file1 =Files.readAttributes(Path.of(data.get(i)), BasicFileAttributes.class);
			String splitted = file1.creationTime().toString();
			String[] splt = splitted.split("\\W+");
			splitted = String.join("",splt);
			String[] splt2 = splitted.split("T");
			f1 = Integer.parseInt(splt2[0]);
			for(int j=1; j < (data.size()-i); j++){
				file2 =Files.readAttributes(Path.of(data.get(j)), BasicFileAttributes.class);
				String splitted2 = file2.creationTime().toString();
				String[] splt3 = splitted2.split("\\W+");
				splitted2 = String.join("",splt3);
				String[] splt4 = splitted2.split("T");
				f2 = Integer.parseInt(splt4[0]);
				if( f1 > f2 ) {
					sortedData.add(i,data.get(i));
					sortedDataFileName.add(i,dataFileName.get(i));
				}else{
					sortedData.add(i,data.get(j));
					sortedDataFileName.add(i,dataFileName.get(j));
				}

			}
		}
	}

	public String getS2(){
		return s2;
	}

	public void setSortByDate(boolean z){
		this.sortByDate=z;
	}

	public boolean getSortByDate(){
		return sortByDate;
	}

	public ArrayList<String> getSortedData(){
		return sortedData;
	}

	public ArrayList<String> getSortedDataFileName(){
		return sortedDataFileName;
	}

	public void setTitleCheck(boolean titleCheck) {
		this.titleCheck = titleCheck;
	}

	// To open file in UI
	public void OpenFile(String file){
		try{
			Desktop.getDesktop().open(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String ToHTML(String path) throws IOException {
		String fin = "COVID-19.html";
		File file = new File(fin);
		boolean result = file.createNewFile();
		String content="";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		String line;
		int c=0;
		while ((line = bufferedReader.readLine()) != null){
			if(c<=4){
				content = content + line + " ";
				if(c==4){
					c=0;
					content = content + newLine;
				}
			}
			c++;
		}
		bufferedReader.close();
		// Write to the HTML file
		FileWriter myWriter = new FileWriter(fin);
		myWriter.write(content);
		myWriter.close();
		return fin;
	}

	public String getErr(){
		return err;
	}

	public boolean getBool(){
		return bool;
	}

	public ArrayList<String> getTitleData(){
		return dataFileTitle;
	}

	public ArrayList<String> getData(){
		return data;
	}

	public ArrayList<String> getDataContent(){
		return dataContent;
	}

	public ArrayList<String> getDataFileName(){
		return dataFileName;
	}

	public String getCorrect(){
		return correct;
	}

	public void setCorrect(String correct){
		this.correct=correct;
	}

	public int getDataSize(){
		return data.size();
	}
}