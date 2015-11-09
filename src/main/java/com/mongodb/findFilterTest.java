package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by dhyey on 22-10-2015.
 */
public class findFilterTest {
    public static void main(String[] args) {
        //MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findTest");
        coll.drop();

        for(int i=0; i<10; i++){
            coll.insertOne(new Document()
            .append("x", new Random().nextInt(2))
            .append("y", new Random().nextInt(100)));
        }

        Bson filter = new Document("x", 0)
                .append("Y", new Document("$gt", 10));

        Bson filter2 = and(eq("x", 0), gt("y", 20));
        Bson projection = new Document("y", 1).append("_id", 0);
        Bson projection1 = Projections.exclude("x");

        List<Document> all = coll.find(filter2)
                .projection(projection1)
                .into(new ArrayList<Document>());
        for(Document curr: all){
            printJson(curr);
        }

        long count = coll.count(filter2);
        System.out.println("Count : "+ count);
    }
    public static void printJson(Document doc){
        JsonWriter jw = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));
        new DocumentCodec().encode(jw, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        System.out.println(jw.getWriter());
        System.out.println();
        System.out.flush();
    }
}
