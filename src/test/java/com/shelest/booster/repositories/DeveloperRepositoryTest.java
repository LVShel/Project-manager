package com.shelest.booster.repositories;

import com.shelest.booster.domain.Developer;
import com.shelest.booster.utilities.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DeveloperRepositoryTest {

    @Autowired
    private DeveloperRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void whenFindByExperienceGreaterThan_thenReturnDeveloper() {
        //given
        Developer senior = new Developer();
        senior.setExperience(50);
        entityManager.persist(senior);
        entityManager.flush();

        //when
        List<Developer> found = repository.findByExperienceGreaterThan(30);

        //then
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getExperience()).isEqualTo(50);
    }

    @Test
    public void whenFindByState_thenReturnDeveloper() {
        //given
        Developer developer = new Developer();
        developer.setState(State.ON_BENCH);
        entityManager.persist(developer);
        entityManager.flush();

        //when
        List<Developer> found = repository.findByState(State.ON_BENCH);

        //then
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getState()).isEqualTo(State.ON_BENCH);
    }
}