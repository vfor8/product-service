package com.aweshop.products.upload.watch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WatchRepositoryTest {

    @Autowired
    private WatchRepository repository;

    @Test
    void testSave() {
        var newWath = WatchEntity.builder().title("W1").price(300000).description("W1 watch model A")
                .fountain(Base64.getDecoder().decode("R01GOD1hAQABAIAAAAUEBA"))
                .build();

        WatchEntity saved = repository.save(newWath);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo(newWath.getTitle());
    }

}
