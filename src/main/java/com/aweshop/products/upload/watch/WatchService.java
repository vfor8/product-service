package com.aweshop.products.upload.watch;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WatchService {

    private final WatchRepository repository;

    @Transactional
    public WatchEntity createWatch(WatchEntity draft) {
        Objects.requireNonNull(draft);
        return repository.save(draft);
    }

}
