package com.mongoconnect;

import org.bson.Document;

public class ConnectMongo {
    public static void main(String[] args)
    {
        String mongoUrl = "";
        String mongoCollectionName = "";
        String mongoDatabase = "";


        QueryMongo  queryMongo=new QueryMongo(mongoUrl,mongoDatabase,mongoCollectionName);
        for(Document document:queryMongo.findAllQuery())
        {
            System.out.println(document.toJson());

        }
    }
}
