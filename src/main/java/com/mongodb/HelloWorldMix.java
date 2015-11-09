package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dhyey on 22-10-2015.
 */
public class HelloWorldMix {
    public static void main(String args[]){
        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldSparkFreemarker.class, "/");
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        final MongoCollection<Document> coll = db.getCollection("findTest");
        coll.drop();
        coll.insertOne(new Document("name","MongoDB"));

        Spark.get("/hello", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                Template helloTemplate = config.getTemplate("Hello.ftl");
                Document doc = coll.find().first();
                StringWriter writer = new StringWriter();
                helloTemplate.process(doc, writer);
                return writer;
            }
        });
    }
}
