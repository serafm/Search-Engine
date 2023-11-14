# Search Engine

## Final Report: Search engine for articles related to COVID-19

### Document Collection:

We initially gathered COVID-19-related articles from ScienceDirect.com and the World Health Organization (WHO) official website, amassing a total of 516 articles. These encompass cases, various studies, treatment approaches, global statistics, vaccine research, and recorded deaths associated with COVID-19. The collected articles are subject to further editing and analysis using the Lucene Open-Source Library (Version 3.6.0).

### Search Engine General Description:

This is a straightforward COVID-19 article search engine designed to swiftly deliver relevant files with optimal display. Functionality is achieved through Lucene's available tools, including classes and methods such as StandardAnalyzer, IndexWriter, Field, Document, IndexSearcher, IndexReader, QueryParser, TopDocs, and ScoreDocs.

### Text Analysis-Index Construction:

nitially in PDF format, articles undergo conversion to TXT format using Apache Tika for subsequent Lucene processing. Prior to indexing, stemming is applied for simplification, and stop words are removed to enhance tokenization. Each article is divided into fields (FileName, FilePath, TitleName, FileContent) using the Field class, and documents are saved. StandardAnalyzer and IndexWriter classes are then used to analyze TitleName and FileContent fields, facilitating faster searches. The index is constructed, encompassing two sub-indexes—one for content distribution with DocID in binary format and the other for easier file path retrieval. A dictionary containing relevant COVID-19-related words is also created.

### Search:

Boolean search method is initially employed for its speed. Users can search for phrases, and the program, using QueryParser, analyzes and compares them with the keyword dictionary. Articles with the highest keyword matches are retrieved using TopDocs and ScoreDocs classes. Spell check is incorporated to suggest corrections for user spelling mistakes during searches, with a list of suggested words provided as the user types.

### Presentation of Results:

Search results are presented in groups of ten per page. Each relevant article is displayed with keywords highlighted in bold. Users can view the entire article, along with search time and the total number of articles related to the search. A search history feature aids future searches, and articles are ranked in descending order based on the publication date. Users receive an error message for irrelevant searches with no results.

This comprehensive approach aims to enhance the user's experience by providing efficient, accurate, and user-friendly access to COVID-19-related articles.
