package com.maybach7.formhandler.mapper;

import com.maybach7.formhandler.dto.ApplicationDto;
import com.maybach7.formhandler.entity.Gender;
import com.maybach7.formhandler.entity.ProgrammingLanguage;
import com.maybach7.formhandler.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserMapper implements Mapper<User, ApplicationDto> {

    private final static UserMapper INSTANCE = new UserMapper();

    @Override
    public User mapFrom(ApplicationDto dto) {
        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .birthday(LocalDate.parse(dto.getBirthday()))
                .gender(Gender.valueOf(dto.getGender().toUpperCase()))
                .languages(mapProgrammingLanguages(dto.getProgrammingLanguages()))
                .biography(dto.getBiography())
                .build();
    }

    private List<ProgrammingLanguage> mapProgrammingLanguages(List<String> languages) {
        return languages.stream()
                .map(ProgrammingLanguage::find)
                .map(Optional::get)
                .toList();
    }

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}