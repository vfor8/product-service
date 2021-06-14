package com.aweshop.products.upload.watch;

import com.aweshop.products.upload.error.ProductApplicationException;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import java.util.Base64;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface WatchMapper {

    Base64.Decoder DECODER = Base64.getDecoder();

    WatchEntity toEntity(WatchDto dto);

    default byte[] decodeBase64(String base64) {

        try {
            return DECODER.decode(base64);
        } catch (IllegalArgumentException e) {
            throw new ProductApplicationException("Invalid base64 provided as a fountain image", e, HttpStatus.BAD_REQUEST);
        }
    }
}
