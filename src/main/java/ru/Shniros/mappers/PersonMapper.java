package ru.Shniros.mappers;

import org.mapstruct.Mapper;
import ru.Shniros.domain.Person;
import ru.Shniros.web.dto.PersonDto;

@Mapper(componentModel = "cdi")
public interface PersonMapper {
    Person toPerson(PersonDto personDto);

    PersonDto toPersonDto(Person person);
}
