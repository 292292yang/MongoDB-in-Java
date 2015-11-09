package com.mongodb;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by dhyey on 19-10-2015.
 */
public class HelloWorldSpark {
    public static void main(String args[]) {
        Spark.get("/hello", new Route() {
            public Object handle(Request request, Response response) throws Exception {
                return "Hello World Spark Style.";
            }
        });
    }
}
