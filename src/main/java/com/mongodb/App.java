package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.StringWriter;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> coll = db.getCollection("grades");
        Bson sort = new Document().append("student_id", 1).append("score", 1);
        Bson projection = fields(include("student_id", "score"));

        MongoCursor<Document> cursor = coll.find().sort(sort).projection(projection).iterator();
        int temp = -2;
        ObjectId id = null;
        int student = -1;
        try{
            while(cursor.hasNext()){
                Document d = cursor.next();
                printJson(d);
                Iterator<Object> it = d.values().iterator();

                if(it.hasNext()){
                    id = (ObjectId)it.next();
                    student = (Integer)it.next();
                    System.out.println(student+ " " + id + " "+ temp);
                }
                if(student != temp){
                    coll.deleteOne(eq("_id",id));
                    //System.out.println(student+ " " + temp);
                }
                temp = student;
            }
        }
        finally{
            cursor.close();
        }

        System.out.println("Count:");
        long count = coll.count();
        System.out.print(count);

    }

    public static void printJson(Document doc){
        JsonWriter jw = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, true));
        new DocumentCodec().encode(jw, doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        System.out.println(jw.getWriter());
        System.out.println();
        System.out.flush();
    }
}
