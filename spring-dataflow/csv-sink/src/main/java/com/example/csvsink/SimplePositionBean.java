package com.example.csvsink;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SimplePositionBean extends CsvBean {

    @CsvBindByPosition(position = 0)
    private String clientId;

    @CsvBindByPosition(position = 1)
    private String codeno;

    @CsvBindByPosition(position = 2)
    private String codetext;

    @CsvBindByPosition(position = 3)
    private String year;

    @CsvBindByPosition(position = 4)
    private String yearMonth;

    @CsvBindByPosition(position = 5)
    private String activationDate;

    @CsvBindByPosition(position = 6)
    private String expirationDate;

    @CsvBindByPosition(position = 7)
    private String givenName;

    @CsvBindByPosition(position = 8)
    private String surname;

    @CsvBindByPosition(position = 9)
    private String street;

    @CsvBindByPosition(position = 10)
    private String houseNumber;

    @CsvBindByPosition(position = 11)
    private String zipCode;

    @CsvBindByPosition(position = 12)
    private String city;

    @CsvBindByPosition(position = 13)
    private String country;

    @CsvBindByPosition(position = 14)
    private String email;

    @CsvBindByPosition(position = 15)
    private String blz;

    @CsvBindByPosition(position = 16)
    private String bankAccountNumber;

    @CsvBindByPosition(position = 17)
    private String iban;

    @CsvBindByPosition(position = 18)
    private String bic;

}
