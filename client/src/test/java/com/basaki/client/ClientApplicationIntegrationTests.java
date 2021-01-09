package com.basaki.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientApplicationIntegrationTests {

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Test
    public void contextLoads() {
        assertNotNull(converter);
    }
}