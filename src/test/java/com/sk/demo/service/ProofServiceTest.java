package com.sk.demo.service;

import com.sk.demo.data.dao.NameKeyValueDao;
import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import com.sk.demo.web.NameKeyDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProofServiceTest {

    @Configuration
    @Import(ProofService.class)
    static class Config {

    }

    @MockBean
    private NameKeyValueDao nameKeyValueDao;

    @Autowired
    ProofService proofService;

    @Test
    public void addKeyValue() throws Exception {
        when(nameKeyValueDao.addAll(Mockito.anyList())).thenReturn(new ArrayList<NameKeyValueEntity>());
        proofService.addKeyValue("cars", "car.engine", Stream.of("EV", "HYBRID").collect(Collectors.toSet()), "electric motor");
        verify(nameKeyValueDao, times(1)).addAll(anyList());
    }

    @Test(expected = AssertionError.class)
    public void addBadKey() throws Exception {
        when(nameKeyValueDao.addAll(Mockito.anyList())).thenReturn(new ArrayList<NameKeyValueEntity>());
        proofService.addKeyValue("cars", "carengine", Stream.of("EV", "HYBRID").collect(Collectors.toSet()), "electric motor");

    }

    //test to add default value
    @Test
    public void addEmptyTag() {
        proofService.addKeyValue("cars", "car.engine", new HashSet<>(), "i.c.e");
        verify(nameKeyValueDao, times(1)).addAll(anyList());
    }

    //test to get default value

    @Test
    public void getMatchingTag() {
        NameKeyValueEntity engEnt = new NameKeyValueEntity();
        engEnt.setPrimaryKey(new NameKeyValueKey("cars", "car", "engine", "EV"));
        engEnt.setValue("electric motor");

        NameKeyValueEntity bodEnt = new NameKeyValueEntity();
        bodEnt.setPrimaryKey(new NameKeyValueKey("cars", "car", "body", "PRIUS"));
        bodEnt.setValue("hatchback");
        when(nameKeyValueDao.get(Mockito.any(NameKeyValueKey.class))).thenReturn(engEnt, bodEnt);
        NameKeyDto nameKeyDto = proofService.getValue("cars", "car", Arrays.asList("EV", "PRIUS"));

        Assert.assertEquals("car.engine" , nameKeyDto.getObjKey1());
        Assert.assertEquals("electric motor", nameKeyDto.getObjValue1());
        Assert.assertEquals("car.body", nameKeyDto.getObjKey2());
        Assert.assertEquals("hatchback", nameKeyDto.getObjValue2());
    }

    @Test
    public void getEmptyTags() {
        NameKeyValueEntity engEnt = new NameKeyValueEntity();
        engEnt.setPrimaryKey(new NameKeyValueKey("cars", "car", "engine", "DEFAULT"));
        engEnt.setValue("i c e");

        NameKeyValueEntity bodEnt = new NameKeyValueEntity();
        bodEnt.setPrimaryKey(new NameKeyValueKey("cars", "car", "body", "DEFAULT"));
        bodEnt.setValue("sedan");
        when(nameKeyValueDao.get(Mockito.any(NameKeyValueKey.class))).thenReturn(null, engEnt, null, bodEnt);
        NameKeyDto nameKeyDto = proofService.getValue("cars", "car", Arrays.asList("EV", "PRIUS"));

        Assert.assertEquals("car.engine" , nameKeyDto.getObjKey1());
        Assert.assertEquals("i c e", nameKeyDto.getObjValue1());
        Assert.assertEquals("car.body", nameKeyDto.getObjKey2());
        Assert.assertEquals("sedan", nameKeyDto.getObjValue2());
    }

    @Test
    public void getEntityWithNoDefaultsSet() {
        when(nameKeyValueDao.get(Mockito.any(NameKeyValueKey.class))).thenReturn(null);
        NameKeyDto nameKeyDto = proofService.getValue("cars", "car", Arrays.asList("EV", "PRIUS"));
        Assert.assertEquals("car.engine" , nameKeyDto.getObjKey1());
        Assert.assertEquals("NO-DATA", nameKeyDto.getObjValue1());
        Assert.assertEquals("car.body", nameKeyDto.getObjKey2());
        Assert.assertEquals("NO-DATA", nameKeyDto.getObjValue2());
    }
}
