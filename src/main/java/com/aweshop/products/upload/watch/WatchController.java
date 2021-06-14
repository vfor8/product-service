package com.aweshop.products.upload.watch;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/watches")
@RequiredArgsConstructor
public class WatchController {

    private final WatchService service;
    private final WatchMapper mapper;

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Upload Watch information")
    public ResponseEntity<Void> createWatch(@RequestBody @Valid WatchDto watch) {

        WatchEntity entity = mapper.toEntity(watch);
        service.createWatch(entity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
