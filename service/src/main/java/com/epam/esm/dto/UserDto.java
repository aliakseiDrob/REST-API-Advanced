package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Data
public class UserDto extends RepresentationModel<UserDto> implements Serializable {

    private long id;
    @NotBlank(message = "User name is mandatory")
    private String name;

    public UserDto(String name) {
        this.name = name;
    }

    public UserDto(long id, @NotBlank(message = "User name is mandatory") String name) {
        this.id = id;
        this.name = name;
    }
}
