package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {

        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        SimpleBeanPropertyFilter filter = new SimpleBeanPropertyFilter
                                                .FilterExceptFilter(Set.of("field1", "field2"));
        FilterProvider filters = new SimpleFilterProvider()
                                        .addFilter("SomeBeanFilter", filter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }


    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListSomeBean() {
        List<SomeBean> asList = Arrays.asList(
            new SomeBean("value1", "value2", "value3"), 
            new SomeBean("value12", "value22", "value32")
            );

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(asList);
            SimpleBeanPropertyFilter filter = new SimpleBeanPropertyFilter
                                                    .FilterExceptFilter(Set.of("field2", "field3"));
            FilterProvider filters = new SimpleFilterProvider()
                                            .addFilter("SomeBeanFilter", filter);
            mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
