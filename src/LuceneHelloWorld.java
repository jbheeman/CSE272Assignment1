import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;


public class LuceneHelloWorld {

 public static void main(String[] args) throws IOException, ParseException {

  //Map<String, JournalModel> data = Parser.parseFile("./CSE272Assignment1/src/data/ohsumed.88-91");
  //Parser.createDocuments(data, Parser.TokenizerTypes.ENGLISH);
 //ParseQuery.parse(Parser.TokenizerTypes.ENGLISH.getTokenizer(), "./CSE272Assignment1/src/data/query.ohsu.1-63");
  ArrayList<QueryModel> query = ParseQuery.parseQueries("./CSE272Assignment1/src/data/query.ohsu.1-63");



//  //Now let's try to search for Hello
  IndexReader reader = DirectoryReader.open(Parser.directory);
  IndexSearcher searcher = new IndexSearcher(reader);
//
//  QueryParser parser = new QueryParser ("content", Parser.analyzer);
//  Query query = parser.parse("Hello");
//  TopDocs results = searcher.search(query, 5);
//  System.out.println("Hits for Hello -->" + results.totalHits);
//
//  //case insensitive search
//  query = parser.parse("hello");
//  results = searcher.search(query, 5);
//  System.out.println("Hits for hello -->" + results.totalHits);
//
//  //search for a value not indexed
//  query = parser.parse("Hi there");
//  results = searcher.search(query, 5);
//  System.out.println("Hits for Hi there -->" + results.totalHits);
 }
}