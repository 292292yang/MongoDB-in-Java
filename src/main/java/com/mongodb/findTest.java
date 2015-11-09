package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class findTest
{
    public static void main( String[] args )
    {
        //MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("findTest");
        coll.drop();

        for(int i=0; i<10; i++){
            coll.insertOne(new Document("x", i));
        }
        System.out.println("Find one:");
        Document first = coll.find().first();
        printJson(first);

        /**
         System.out.println("Find all with into");
         List<Document> all = coll.find().into(new ArrayList<Document>());
         for(Document a: all){
         printJson(a);
         }
         */
        System.out.println("Find all with iteration:");
        MongoCursor<Document> cursor = coll.find().iterator();
        try{
            while(cursor.hasNext()){
                Document d = cursor.next();
                printJson(d);
            }
        }
        finally{
            cursor.close();
        }

        System.out.println("Count:");
        long count = coll.count();
        System.out.print(count);

        /*Document doc = new Document()
                .append("str", "MongoDB, Hello")
                .append("int", 42)
                .append("l", 12L)
                .append("b", true)
                .append("double", 14.215489)
                .append("date", new Date())
                .append("objectId", new ObjectId())
                .append("null", null)
                .append("embeddedDoc", new Document("x", 0))
                .append("list", Arrays.asList(1,2,3));
        //String str = doc.getString("str");
        printJson(doc);*/
    }

    public static void printJson(Document doc){
        JsonWriter jw = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));
        new DocumentCodec().encode(jw, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        System.out.println(jw.getWriter());
        System.out.println();
        System.out.flush();
    }
}
