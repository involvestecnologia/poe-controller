package br.com.involves.poe.table;

import br.com.involves.poe.utils.CreateHashId;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "PoeHash")
public class PoeHash {

    private String id;
    private String client;
    private String hash;
    private String endPoint;
    private String json;

    public PoeHash(){

    }

    public PoeHash(String client, String hash, String json, String endPoint) {
        this.client = client;
        this.hash = hash;
        this.json = json;
        this.endPoint = endPoint;

        CreateHashId hashId = new CreateHashId(client, hash, endPoint);
        this.id = hashId.toString();
    }

    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @DynamoDBAttribute
    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @DynamoDBAttribute
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @DynamoDBAttribute
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


}
