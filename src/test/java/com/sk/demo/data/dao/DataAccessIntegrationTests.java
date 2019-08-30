package com.sk.demo.data.dao;

import com.sk.demo.data.CassandraConfig;
import com.sk.demo.data.ProofRepository;
import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Data layer integration tests.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CassandraConfig.class})
@TestPropertySource(locations="classpath:application-test.properties")
public class DataAccessIntegrationTests {

//    private static final String keyspaceName = "proof_test";
            // String.format("proof-test_%08x", new Random().nextInt());

    @Autowired
    ProofRepository proofRepository;
    @Autowired
    NameKeyValueDao nameKeyValueDao;

    @Test
    public void testCrud() {
        NameKeyValueKey keyValueKey = new NameKeyValueKey("cars", "car",
                "engine" , "EV");
        NameKeyValueEntity entity = new NameKeyValueEntity();
        entity.setPrimaryKey(keyValueKey);
        entity.setValue("electric motor");

        List<NameKeyValueEntity> list = new ArrayList<>();
        list.add(entity);

        proofRepository.saveAll(list);


        Optional<NameKeyValueEntity> resKeyValueEntity = proofRepository.findById(keyValueKey);
        Assert.assertTrue(resKeyValueEntity.isPresent());

    }


    /*
    The test will create multiple records in the sane request and then retrieve one of the records.
     */
    @Test
    public void testMultipleWrite() {
        //tag and value for engine
        String[][] engine = new String[][]{{"EV", "electric motor"}, {"HYBRID", "electric motor"}, {"GAS", "internal combustion engine"}, {"DIESEL", "internal combustion engine"}};
        //tag for body
        String[][] body = new String[][]{{"CIVIC", "sedan"},{"GOLF", "hatchback"},{"CAMARY", "sedan"}};

        List<NameKeyValueEntity> entities = new ArrayList<>();
        for (int i =0; i< engine.length; i++) {
//            insert values for engine
            NameKeyValueEntity entity = new NameKeyValueEntity();
            entity.setPrimaryKey(new NameKeyValueKey("cars", "car" , "engine", engine[i][0]));
            entity.setValue(engine[i][1]);
            entities.add(entity);
        }
        for (int i =0; i< body.length; i++) {
//            insert values for body
            NameKeyValueEntity entity = new NameKeyValueEntity();
            entity.setPrimaryKey(new NameKeyValueKey("cars", "car" , "body", body[i][0]));
            entity.setValue(body[i][1]);
            entities.add(entity);
        }

        nameKeyValueDao.addAll(entities);

        // fetch values and verify

        NameKeyValueKey lookKey = new NameKeyValueKey("cars", "car", "body", "GOLF");
        NameKeyValueEntity reEntity = nameKeyValueDao.get(lookKey);

        Assert.assertEquals("The values in the db for tag GOLF do not match", "hatchback", reEntity.getValue());

        lookKey = new NameKeyValueKey("cars", "car", "engine", "DIESEL");
        reEntity = nameKeyValueDao.get(lookKey);
        Assert.assertEquals("The values in the db for tag DIESEL do not match","internal combustion engine", reEntity.getValue());
    }

}