package com.maybach7.formhandler.mapper;

import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Gender;
import com.maybach7.formhandler.entity.User;

import java.time.LocalDate;

public class UserMapper implements Mapper<User, ApplicationDto> {

    private final static UserMapper INSTANCE = new UserMapper();

    @Override
    public User mapFrom(ApplicationDto dto) {
        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .birthday(LocalDate.parse(dto.getBirthday()))
                .gender(Gender.valueOf(dto.getGender()))
                .languages(dto.getProgrammingLanguages())
                .biography(dto.getBiography())
                .build();
    }

    private UserMapper() {}

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}