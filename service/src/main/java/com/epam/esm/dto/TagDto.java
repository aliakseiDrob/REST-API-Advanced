package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Data
public class TagDto extends RepresentationModel<UserDto> implements Serializable {
    private long id;
    private String name;

    public TagDto(String name) {
        this.name = name;
    }

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
