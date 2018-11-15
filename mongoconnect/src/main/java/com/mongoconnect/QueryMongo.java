package com.mongoconnect;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class QueryMongo {

    MongoCollection collection = null;

    public QueryMongo(String databaseName, String collectionName, String hostName, int port) {
        collection = new MongoClient(hostName, port).getDatabase(databaseName).getCollection(collectionName);
    }

    public FindIterable<Document> findAllQuery() {
        return collection.find();
    }

}
