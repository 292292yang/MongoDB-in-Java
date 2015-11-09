package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

/**
 * Created by dhyey on 22-10-2015.
 */
public class deleteTest {
    public static void main(String args[]) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findTest");
        coll.drop();

        for(int i=0; i<8; i++){
            coll.insertOne(new Document().append("_id", i));
        }
        coll.deleteOne(eq("_id", 4));

        List<Document> all = coll.find().into(new ArrayList<Document>());
        for(Document curr: all){
            printJson(curr);
        }
    }

    public static void printJson(Document doc){
        JsonWriter jw = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));
        new DocumentCodec().encode(jw, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        System.out.println(jw.getWriter());
        System.out.println();
        System.out.flush();
    }
}
