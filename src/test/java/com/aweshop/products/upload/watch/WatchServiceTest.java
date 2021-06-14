package com.aweshop.products.upload.watch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WatchServiceTest {

    private WatchService service;
    private WatchRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(WatchRepository.class);
        service = new WatchService(repository);
    }

    @Test
    void whenCreateWatch_callRepository() {
        var newWath = WatchEntity.builder().title("W1").price(300000).description("W1 watch model A")
                .fountain(Base64.getDecoder().decode("R01GOD1hAQABAIAAAAUEBA"))
                .build();
        service.createWatch(newWath);

        var captor = ArgumentCaptor.forClass(WatchEntity.class);
        verify(repository).save(captor.capture());

        assertThat(captor.getValue()).isEqualTo(newWath);
    }

}
