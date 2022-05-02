import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TermFrequencyAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;


public class Parser {

    enum TokenizerTypes {
        STANDARD("Standard"), STOP("STOP"), SIMPLE("SIMPLE"), WHITESPACE("WHITESPACE"), ENGLISH("ENGLISH");

        private String tokentype;

        TokenizerTypes(String t) {
            this.tokentype = t;
        }

        Analyzer getTokenizer() {
            try {
                TokenizerTypes t = TokenizerTypes.valueOf(tokentype);
                if(t == STANDARD) {
                    return new StandardAnalyzer();
                } else if(t == STOP) {
                    return new StopAnalyzer(EnglishAnalyzer.getDefaultStopSet());
                } else if(t == SIMPLE) {
                    return new SimpleAnalyzer();
                }else if(t == WHITESPACE) {
                    return new WhitespaceAnalyzer();
                } else {
                    return new EnglishAnalyzer();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return new StandardAnalyzer();
            }
        }
    }


    static Directory directory = new ByteBuffersDirectory();

    static HashMap<String, JournalModel> parseFile(String fileName) {
        File f = new File(fileName);
        HashMap<String, JournalModel> models = new HashMap();
        HashMap<String, Optional<String>> dataValues = new HashMap<>();
        try {
            Scanner scan = new Scanner(f);
            String currentLine;


            while(scan.hasNextLine()) {

                currentLine = scan.nextLine();
                if(currentLine.stripLeading().startsWith(".I")) {
                    Optional<String> _i;
                    if(dataValues.containsKey(JournalModel.Headers.I.toString())) {
                        models.put(dataValues.get(JournalModel.Headers.I.toString()).get(), new JournalModel(dataValues.get(JournalModel.Headers.I), dataValues.get(JournalModel.Headers.U), dataValues.get(JournalModel.Headers.S), dataValues.get(JournalModel.Headers.M), dataValues.get(JournalModel.Headers.T), dataValues.get(JournalModel.Headers.P), dataValues.get(JournalModel.Headers.W), dataValues.get(JournalModel.Headers.A)));
                        dataValues.clear();
                    }
                    _i = Optional.of(currentLine.split(" ")[1]);
                    dataValues.put(".I", _i);
                } else {
                    dataValues.put(currentLine, Optional.of(scan.nextLine()));
                }
            }
            scan.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return models;
    }

    static void clearDocuments() {
        try {
            directory.close();
            directory = new ByteBuffersDirectory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String> tokenizeText(Analyzer tokenizer, String text) {
        List<String> tokens = new ArrayList<>();
        try {
            TokenStream stream = tokenizer.tokenStream("fieldname", text);
            CharTermAttribute attr = stream.addAttribute(CharTermAttribute.class);
            stream.reset();
            while (stream.incrementToken()){
                tokens.add(attr.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    static void createDocuments(Map<String, JournalModel> data, TokenizerTypes tokenizer) {
        Document doc = new Document();
        try {
            Analyzer analyze = tokenizer.getTokenizer();
            IndexWriterConfig writerConfig = new IndexWriterConfig(analyze);
            IndexWriter writer = new IndexWriter(Parser.directory, writerConfig);

            //In a real world example, content would be the actual content that needs to be indexed.
            //Setting content to Hello World as an example.

            for(String journalKey : data.keySet()) {
                JournalModel journalDoc = data.get(journalKey);
                if(journalDoc._w != null  && journalDoc._t.isPresent()) {
                    List <String> descriptionTokens = tokenizeText(analyze, journalDoc._w.get());
                    List <String> titleTokens = tokenizeText(analyze, journalDoc._t.get());
                    for (String description : descriptionTokens) {
                        doc.add(new TextField(String.format("<D>%s", description), journalDoc._w.get(), Field.Store.YES));
                        writer.addDocument(doc);
                    }

                    for (String title : titleTokens) {
                        doc.add(new TextField(String.format("<T>%s", title), journalDoc._t.get(), Field.Store.YES));
                        writer.addDocument(doc);
                    }
                }
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

