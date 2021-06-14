package com.aweshop.products.upload.watch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    private String title;

    @Min(1)
    private int price;

    @NotNull
    @Size(min = 1, max = 3000)
    private String description;

    private String fountain;

}
