package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
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

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * Created by dhyey on 22-10-2015.
 */
public class findWithSort {
    public static void main(String[] args) {
        //MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findTest");
        coll.drop();

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                coll.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());
        Bson sort = new Document("i", 1).append("j", -1);
        Bson sort1 = orderBy(ascending("i"), descending("j"));

        List<Document> all = coll.find().sort(sort1).limit(50).skip(10).projection(projection).into(new ArrayList<Document>());
        for(Document curr : all){
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
