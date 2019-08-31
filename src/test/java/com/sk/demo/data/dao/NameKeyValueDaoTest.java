package com.sk.demo.data.dao;

import com.sk.demo.data.ProofRepository;
import com.sk.demo.data.entity.NameKeyValueEntity;
import com.sk.demo.data.entity.NameKeyValueKey;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class NameKeyValueDaoTest {

    @Configuration
    @Import(NameKeyValueDaoImpl.class)
    static class Config {

    }

    @MockBean
    private ProofRepository repository;

    @Autowired
    private NameKeyValueDaoImpl nameKeyValueDao;

    @Test
    public void addAll() throws Exception {
        List<NameKeyValueEntity> input = new ArrayList<>(0);
        nameKeyValueDao.addAll(input);
        verify(repository, atLeastOnce()).saveAll(input);

    }


    @Test
    public void get() throws Exception {
        when(repository.findById(Mockito.any(NameKeyValueKey.class))).thenReturn(Optional.empty());
        Assert.assertNull(nameKeyValueDao.get(new NameKeyValueKey("name", "object", "key", "tag")));
    }

    //Test to validate that an empty list returned can be handled
    @Test
    public void getAll() throws Exception {
        when(repository.findAllById(Mockito.any())).thenReturn(new Iterable<NameKeyValueEntity>() {
            @Override
            public Iterator<NameKeyValueEntity> iterator() {
                return new ArrayList<NameKeyValueEntity>(0).iterator();
            }
        });
        Assert.assertTrue(nameKeyValueDao.getAll(Arrays.asList(new NameKeyValueKey("name", "object", "key", "tag"))).size() == 0);

    }



}