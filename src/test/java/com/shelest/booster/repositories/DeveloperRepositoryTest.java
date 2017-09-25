package com.shelest.booster.repositories;

import com.shelest.booster.domain.Developer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DeveloperRepositoryTest {

    private static final String NAME = "SENIOR";


    @Autowired
    private DeveloperRepository repository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testPersistence() {
        //given
        Developer developer = new Developer();
        developer.setName(NAME);

        //when
        repository.save(developer);

        //then
        Assert.assertNotNull(developer.getId());
        Developer newDeveloper = repository.findOne(developer.getId());
        Assert.assertEquals(NAME, newDeveloper.getName());
    }
}