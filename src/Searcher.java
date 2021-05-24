package covid.lucene;

import java.io.File; 
import java.io.IOException;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory; 
import org.apache.lucene.util.Version;

public class Searcher{

	IndexSearcher indexSearcher; 
	QueryParser queryParser; 
	Query query;
	IndexReader getread;

	// Searcher Constructor
	public Searcher(String indexDirectoryPath) throws IOException{
		// Opens the directory of indexed files
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
		// Reading the indexed files
		IndexReader reader = IndexReader.open(indexDirectory);
		getread = reader;
		indexSearcher = new IndexSearcher(reader);
		// Setting the Lucene Version we are using to Analyze the file content
		queryParser = new QueryParser(Version.LUCENE_36,LuceneConstants.CONTENTS, new StandardAnalyzer(Version.LUCENE_36));
	}

	/*public void TitleSearcher(String indexDirectoryPath) throws IOException{
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
		IndexReader reader = IndexReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(reader);
		queryParser = new QueryParser(Version.LUCENE_36, LuceneConstants.TITLE, new StandardAnalyzer(Version.LUCENE_36));
	}*/

	// Showing the 50 top documents of the search
	public TopDocs search( String searchQuery) throws IOException, ParseException{
		query = queryParser.parse(searchQuery); 
		return indexSearcher.search(query, LuceneConstants.MAX_SEARCH); 
	}

	// Get the score of a document
	public Document getDocument(ScoreDoc scoreDoc) throws IOException{
		return indexSearcher.doc(scoreDoc.doc); 
	}

	public void close() throws IOException{
		indexSearcher.close(); 
	} 
}