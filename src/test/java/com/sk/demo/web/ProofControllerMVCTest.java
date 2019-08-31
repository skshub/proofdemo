package com.sk.demo.web;

import com.sk.demo.service.ProofService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProofControllerMVC.class)
public class ProofControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProofService proofService;

    @Test
    public void storeTest() throws Exception {
        String url = "/v2/data/cars/car.engine?tag=EV&tag=HYBRID";
        mockMvc.perform(put(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content("electric motor"))
                .andExpect(status().isAccepted());
        verify(proofService, times(1)).addKeyValue(eq("cars"), eq("car.engine"), anySet(), eq("electric motor"));
    }


//    @Test
    public void retrieveTest() throws Exception {

        String url = "/v1/data/cars/car/CIVIC/HYBRID";

        mockMvc.perform(get(url)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

}