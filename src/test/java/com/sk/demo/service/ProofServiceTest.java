package com.sk.demo.service;

import com.sk.demo.data.dao.NameKeyValueDao;
import com.sk.demo.data.entity.NameKeyValueEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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

    public void addBAdKey() throws Exception {

    }

}
