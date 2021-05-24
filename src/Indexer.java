package covid.lucene;

import java.io.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document; 
import org.apache.lucene.document.Field; 
import org.apache.lucene.index.CorruptIndexException; 
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory; 
import org.apache.lucene.util.Version;

public class Indexer{

	private IndexWriter writer;
	private IndexWriterConfig config;
	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

	// Indexer Constructor
	public Indexer(String indexDirectoryPath) throws IOException{
		//this directory that will contain the indexes
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
		//Create the indexer
		config = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		writer = new IndexWriter(indexDirectory,config);
	}

	public void close() throws CorruptIndexException, IOException{
		writer.close(); 
	}

	public String getTitle(File file) throws IOException {
		BufferedReader Buff = new BufferedReader(new FileReader(file));
		String text = Buff.readLine();
		return text;
	}

	private Document getDocument(File file) throws IOException{
		Document document = new Document();
		//Index file Title
		Field fileTitle = new Field(LuceneConstants.TITLE, new FileReader(getTitle(file)));
		//Index file Contents
		Field contentField = new Field(LuceneConstants.CONTENTS, new FileReader(file)); 
		//Index file Name
		Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES,Field.Index.NOT_ANALYZED);
		//Index file Path
		Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES,Field.Index.NOT_ANALYZED);
		document.add(contentField);
		document.add(fileNameField); 
		document.add(filePathField);
		return document;
	}

	// Just to show how many files we have indexed
	private void indexFile(File file) throws IOException{
		System.out.println("Indexing "+file.getCanonicalPath());
		Document document = getDocument(file);
		writer.addDocument(document);
	}

	// Indexing the directory we want
	public int createIndex(String dataDirPath, FileFilter filter) throws IOException{
		//Get all files in the data directory
		File[] files = new File(dataDirPath).listFiles();
		for (File file : files){
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file) ){
				indexFile(file); 
			}
		}
		return writer.numDocs(); 
	}
}