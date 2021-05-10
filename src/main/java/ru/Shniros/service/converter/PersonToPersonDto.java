package ru.Shniros.service.converter;

import org.springframework.core.convert.converter.Converter;
import ru.Shniros.domain.Person;
import ru.Shniros.web.dto.PersonDto;

public class PersonToPersonDto implements Converter<Person, PersonDto> {

    @Override
    public PersonDto convert(Person person) {
        return new PersonDto().setId(person.getId())
                .setEmail(person.getEmail())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName());
    }
}
