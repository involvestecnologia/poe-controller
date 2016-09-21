package br.com.involves.poe;

import br.com.involves.poe.config.DynamoDBConfig;
import br.com.involves.poe.exception.HashExistingException;
import br.com.involves.poe.exception.HashNotFindException;
import br.com.involves.poe.repository.PoeHashRepository;
import br.com.involves.poe.service.AddHashService;
import br.com.involves.poe.service.FindHashService;
import br.com.involves.poe.table.PoeHash;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DynamoDBConfig.class)
public class DuplicateEndPointIntegrationTest {

    private static final String KEY_NAME = "id";
    private static final Long READ_CAPACITY_UNITS = 5L;
    private static final Long WRITE_CAPACITY_UNITS = 5L;

    @Autowired
    private PoeHashRepository repository;

    @Autowired
    private AddHashService service;

    @Autowired
    private FindHashService findHashService;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Before
    public void init() throws Exception {

        ListTablesResult listTablesResult = amazonDynamoDB.listTables();

        if( listTablesResult != null ){
            for(String item : listTablesResult.getTableNames() ){
                amazonDynamoDB.deleteTable(item);
            }
        }

        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(KEY_NAME).withAttributeType("S"));

        List<KeySchemaElement> keySchemaElements = new ArrayList<KeySchemaElement>();
        keySchemaElements.add(new KeySchemaElement().withAttributeName(KEY_NAME).withKeyType(KeyType.HASH));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName("PoeHash")
                .withKeySchema(keySchemaElements)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(READ_CAPACITY_UNITS)
                        .withWriteCapacityUnits(WRITE_CAPACITY_UNITS));

        amazonDynamoDB.createTable(request);

    }

    @Test
    public void testGravarDiretoRepository() {

        PaginatedScanList list = (PaginatedScanList)repository.findAll();

        Assert.assertEquals(list.size(), 0);

        PoeHash item = new PoeHash("sf", UUID.randomUUID().toString(), "/xx/1", "{eu}");

        repository.save(item);

        list = (PaginatedScanList)repository.findAll();
        Assert.assertTrue(list.size() > 0 );
    }

    @Test
    public void testIncluir() throws HashExistingException {
        String client = "involves";
        String hash = UUID.randomUUID().toString();
        String json = "{xx}";
        String endPoint = "/v1/teste/1";

        service.add(client, hash, json, endPoint);
    }

    /*
    @Test(expected = HashExistingException.class)
    public void testIncluirComHashIgual() throws HashExistingException {
        String client = "involves";
        String hash = UUID.randomUUID().toString();
        String json = "{xx}";
        String endPoint = "/v1/teste/1";

        service.add(client, hash, json, endPoint);
        service.add(client, hash, json, endPoint);
    }

    @Test
    public void testFindHash() throws HashExistingException, HashNotFindException {
        String client = "involves";
        String hash = UUID.randomUUID().toString();
        String json = "{eu}";
        String endPoint = "/v1/teste/1";

        service.add(client, hash, json, endPoint);

        String jsonBanco = findHashService.find(client, hash, endPoint);

        Assert.assertEquals(jsonBanco, "{eu}");
    }

    @Test(expected = HashNotFindException.class)
    public void testFindHashNotExist() throws HashNotFindException {
        String client = "involves";
        String hash = UUID.randomUUID().toString();
        String json = "{eu}";
        String endPoint = "/v1/teste/1";

        findHashService.find(client, hash, endPoint);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncluirComNull() throws HashExistingException {
        String client = null;
        String hash = null;
        String json = null;
        String endPoint = null;

        service.add(client, hash, json, endPoint);
    }

    @Test
    public void testIncluirComJSonNUll() throws HashExistingException {
        String client = "involves";
        String hash = UUID.randomUUID().toString();
        String json = null;
        String endPoint = "/v1/teste/2";

        service.add(client, hash, json, endPoint);
    }*/
}
