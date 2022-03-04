# Search Engine

## Final Report: Search engine for articles related to COVID-19

### Document Collection:

We first collected COVID-19 related articles through ScienceDirect.com and the official website of the World Health Organization (WHO).
We have collected a total of 516 articles on the condition that either there will be cases where a patient has covid, a multitude of various studies and possible ways to treat them, symptoms and statistics where they are recorded worldwide, research on the difference vaccines on the market, recorded deaths from the specific disease of COVID-19.All these articles we have collected will be edited and viewed with the help of the Lucene Open-Source Library with Version 3.6.0.

### Search Engine General Description:

It is a simple article search engine related to COVID-19. The goal of the search engine is to show the user the files it is looking for, in the shortest possible time and with the best display of results. The functionality of the search engine is achieved through the available tools ( classes, methods) provided by the Lucene library. Using the classes StandardAnalyzer, IndexWriter, Field, Document the index is created and immediately after with the classes-methods IndexSearcher, IndexReader, QueryParser, TopDocs and ScoreDocs the index will be processed and the results are displayed to the user.

### Text Analysis-Index Construction:

The articles are originally in PDF format and after editing using Apache Tika were converted to TXT file format. By converting them to TXT they are not yet ready for editing. This is where the Lucene library comes in handy. Before indexing we did stemming in our documents to simplify them and stemming to cut suffixes (where if our files were not English we would not use). Also using stop words we put a certain number of frequently used words that are everywhere in the documents so that we leave them in the process to become tokens, words like <and, is, etc.> and we removed the space between the words to make it easier to identify and search. Initially, using the Field class, each file is divided into 4 fields which are the file name (FileName), the file path (FilePath), the file title (TitleName), and the contents of the file (FileContent). Document are saved. Immediately after using the StandardAnalyzer and IndexWriter classes, the TitleName and FileContent fields are analyzed in a simpler format if they are first retrieved from Document.
There is a faster search. After all this process the index will be created and it will be ready for any search method we choose. Each txt file goes through the 2 different indexes where we have one is the collection of the contents of each file where it is distributed with their DocID where it is simply the name of the file in binary format because we use Boolean search. The next index is just the path of each file so that you can find it more easily. We also create a dictionary that contains relevant words keys related to COVID-19.

### Search:

Articles will initially be searched using the Boolean search method as it is faster to search through the index. The user will be able to search for a
phrase, using QueryParser the program will analyze and separate this phrase into words and will compare them with the dictionary in which we have the keywords. After the comparison the articles with the most views of the keywords from dictionary using the TopDocs and ScoreDocs classes. Links and articles will not be downloaded
Spell Check is also used where when the user makes spelling mistakes during the search the phrase "Do you mean" will appear and next to it the correct spelling word he wanted to search. When the user tries to write his phrase Suggested words for him will be presented depending on what he has written so far.

### Presentation of Results:

The results of each search will be presented to the user ten on each page and for each relevant article will be displayed the keywords in bold highlight letters he has searched. There will also be the ability to view the entire article, the search time and the total number of articles related to the user search. It is possible to display the search history that you use for future searches of the user to facilitate the search and use the ranking of articles in descending order based on the date written and The article was published.Finally, if the user searches for something that is irrelevant to the content of the articles, he will be presented with an error message informing him of the wrong search and that there are no results for what he is looking for. 
