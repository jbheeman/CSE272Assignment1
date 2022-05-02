import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.CoreParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseQuery {
    static void parse(Analyzer a, String fileName) {
        //QueryParser parser = new QueryParser("fieldname", a);
        CoreParser coreParser = new CoreParser("fieldname", a);

        try {
            InputStream stream = new FileInputStream(fileName);
            Query query = coreParser.parse(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static ArrayList<QueryModel> parseQueries(String fileName) {
        File file = new File(fileName);
        ArrayList<QueryModel> modelList = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            String line;
            StringBuilder num = new StringBuilder();
            StringBuilder title = new StringBuilder();
            StringBuilder description = new StringBuilder();
            String currentTag = "<top>";
            while(scan.hasNextLine()) {
                line = scan.nextLine();
                String removedTag = line.replaceAll("<[/a-z]+>", "").stripLeading();
                 if(line.startsWith("</top>")) {
                    currentTag = "<top>";
                    modelList.add(new QueryModel(num.toString(), title.toString(), description.toString()));
                    num = new StringBuilder();
                    title = new StringBuilder();
                    description = new StringBuilder();
                } else if(line.startsWith("<desc>") || currentTag.equals("<desc>")) {
                     currentTag = "<desc>";
                     description.append(removedTag);
                 }  else if(line.startsWith("<title>") || currentTag.equals("<title>")) {
                    currentTag = "<title>";
                    title.append(removedTag);
                } else if(line.startsWith("<num>") || currentTag.equals("<num>")) {
                     currentTag = "<num>";
                     num.append(removedTag);
                 }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return modelList;
    }
}
