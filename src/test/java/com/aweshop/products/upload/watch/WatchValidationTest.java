package com.aweshop.products.upload.watch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WatchValidationTest {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validWatch() {
        var watch = WatchDto.builder().title("Prim").price(250000).description("A watch with a water fountain picture").fountain("base64imagestr").build();
        Set<ConstraintViolation<WatchDto>> violations = validator.validate(watch);

        assertThat(violations).isEmpty();
    }

    @Test
    void titleMustNotBeNull() {
        String value = null;
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, TITLE, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("must not be null");
    }

    @Test
    void titleShorterThanMinSize() {
        String value = "";
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, TITLE, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("size must be between 1 and 150");
    }

    @Test
    void titleLongerThanMaxSize() {
        String value = "Prim567890".repeat(16);
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, TITLE, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("size must be between 1 and 150");
    }

    @Test
    void descriptionMustNotBeNull() {
        String value = null;
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, DESCRIPTION, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("must not be null");
    }

    @Test
    void descriptionShorterThanMinSize() {
        String value = "";
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, DESCRIPTION, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("size must be between 1 and 3000");
    }

    @Test
    void descriptionLongerThanMaxSize() {
        String value = "Descr67890".repeat(301);
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, DESCRIPTION, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("size must be between 1 and 3000");
    }

    @Test
    void priceAtLeast1() {
        int value = 0;
        Set<ConstraintViolation<WatchDto>> violations = validator.validateValue(WatchDto.class, PRICE, value);

        assertThat(violations).hasSize(1);
        assertThat(getViolationMessage(violations)).isEqualTo("must be greater than or equal to 1");
    }

    private String getViolationMessage(Set<ConstraintViolation<WatchDto>> violations) {
        return violations.stream().findAny().map(ConstraintViolation::getMessage).get();
    }

}
