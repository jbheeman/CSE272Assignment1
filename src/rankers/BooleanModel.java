package rankers;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

class BooleanModel {
    IndexReader reader;
    IndexSearcher searcher;

    BooleanModel(Directory directory) throws Exception {
        reader = DirectoryReader.open(directory);
        searcher = new IndexSearcher(reader);
    }


    void





}
