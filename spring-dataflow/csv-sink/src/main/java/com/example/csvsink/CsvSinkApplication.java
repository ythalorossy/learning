package com.example.csvsink;

import com.example.csvsink.dto.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@SpringBootApplication
@Log4j2
public class CsvSinkApplication {

    private File file;
    public static final Path PATH = Paths.get("C:\\\\sink\\stream.csv");

    public static void main(String[] args) {
        SpringApplication.run(CsvSinkApplication.class, args);


//        new CsvSinkApplication().
//        writeOpenCsv(FeatureListExportResponse.builder()
//                .featureLists(Collections.singletonList(FeatureListResponse.builder()
//                        .entry(FLEntryResponse.builder()
//                                .person(FLPersonResponse.builder()
//                                        .givenName("Ythalo Rossy")
//                                        .surname("Saldanha Lira")
//                                        .build())
//                                .build())
//                        .build()))
//                .build());

    }

    @Bean
    public Consumer<FeatureListExportResponse> sink() {
        return this::writeOpenCsv;
    }

    private void writeOpenCsv(FeatureListExportResponse featureListExportResponse) {

        log.info(String.format("Starting the process: %s", featureListExportResponse.getClientId()));

        Path path = prepareFilename(featureListExportResponse);

        log.info(String.format("Prepared path: %s", path.toString()));

        try (Writer fileWriter = new FileWriter(path.toString())){

            log.info("Prepared file writer ");

            StatefulBeanToCsv<CsvBean> sbc =
                    new StatefulBeanToCsvBuilder<CsvBean>(fileWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            if (ofNullable(featureListExportResponse.getFeatureLists()).isPresent()) {

                log.info("Start process of feature list");

                List<CsvBean> list = featureListExportResponse.getFeatureLists()
                        .stream()
                        .map(getFeatureListResponseSimplePositionBeanFunction())
                        .collect(Collectors.toList());
                sbc.write(list);
            }

            log.info(String.format("Finishing the process: %s", featureListExportResponse.getClientId()));

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    private Path prepareFilename(FeatureListExportResponse featureListExportResponse) {

        log.info(String.format("Preparing path to %s", featureListExportResponse.getClientId()));

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS"));

        return Paths.get(String.format("C:\\\\sink\\%s-%s.csv", featureListExportResponse.getClientId(), date));
    }

    private static Function<FeatureListResponse, SimplePositionBean> getFeatureListResponseSimplePositionBeanFunction() {
        return featureListResponse -> {

            log.info("Building feature list");

            SimplePositionBean.SimplePositionBeanBuilder builder = SimplePositionBean.builder();

            builder.clientId(ofNullable(featureListResponse.getClientId()).orElse(""));
            builder.codeno(ofNullable(featureListResponse.getFeatureCode())
                    .map(FeatureCodeResponse::getCodeNumber).orElse(""));
            builder.codetext(ofNullable(featureListResponse.getFeatureCode())
                    .map(FeatureCodeResponse::getFeatureCode).orElse(""));
            builder.year("yyyy");
            builder.yearMonth("yyyy-mm");
            builder.activationDate(ofNullable(featureListResponse.getActivationDate())
                    .map(a -> a.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).orElse(""));
            builder.expirationDate(ofNullable(featureListResponse.getExpirationDate())
                    .map(a -> a.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).orElse(""));

            if (ofNullable(featureListResponse.getEntry()).isPresent()) {

                log.info("Entry found");

                ofNullable(featureListResponse.getEntry().getPerson()).ifPresent(person -> {

                    log.info("Person found");

                    builder.givenName(of(person)
                            .map(FLPersonResponse::getGivenName).orElse(""));
                    builder.surname(of(person)
                            .map(FLPersonResponse::getSurname).orElse(""));
                });

                ofNullable(featureListResponse.getEntry().getAddress()).ifPresent(address -> {
                    log.info("Address found");
                    builder.street(of(address).map(FLAddressResponse::getStreet).orElse(""));
                    builder.houseNumber(of(address).map(FLAddressResponse::getHouseNumber).orElse(""));
                    builder.zipCode(of(address).map(FLAddressResponse::getZipCode).orElse(""));
                    builder.city(of(address).map(FLAddressResponse::getCity).orElse(""));
                    builder.country("?????");
                });

                ofNullable(featureListResponse.getEntry().getEmail()).ifPresent(email -> {
                    log.info("Email found");
                    builder.email(of(email).map(FLEmailResponse::getEmailAddress).orElse(""));
                });

                ofNullable(featureListResponse.getEntry().getBank()).ifPresent(bank -> {
                    log.info("Bank found");
                    builder.blz(of(bank).map(FLBankResponse::getBlz).orElse(""));
                    builder.bic(of(bank).map(FLBankResponse::getBic).orElse(""));
                });

                ofNullable(featureListResponse.getEntry().getBankConnection()).ifPresent(bankConnection -> {
                    log.info("Bank Connection found");
                    builder.bankAccountNumber(of(bankConnection).map(FLBankConnectionResponse::getAccountNumber).orElse(""));
                    builder.iban(of(bankConnection).map(FLBankConnectionResponse::getIban).orElse(""));
                });

            }

            return builder.build();
        };
    }

    private void writeManually(FeatureListExportResponse featureListExportResponse) {
        try {

            file = new File(PATH.toString());

            if (file.createNewFile()) {
                System.out.println("File created");
            }

            featureListExportResponse.getFeatureLists()
                    .stream()
//                        .map(FeatureListResponse::getEntry)
                    .filter(Objects::nonNull)
                    .map(this::getLineByType)
//                        .filter(Objects::nonNull)
                    .forEach(this::writeLine);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLineByType(FeatureListResponse featureListResponse) {

        //"0:ID_CLIENT,1:CODENO,2:CODETEXT,3:YEAR,4:YEAR-Month,5:ACTIVATION_DATE,6:EXPIRATION_DATE,7:FIRSTNAME,8:NAME,9:STREET,10:HOUSENO,11:ZIPCODE,12:CITY,13:COUNTRY,14:EMAIL,15:BLZ,16:ACCOUNTNUMBER,17:IBAN,18:BIC";

        String[] template = new String[19];

        FeatureCodeResponse featureCode = featureListResponse.getFeatureCode();

        template[0] = featureListResponse.getClientId().toString();
        template[1] = featureCode.getFeatureCode().toString();
        template[2] = featureCode.getCodeText();
        template[3] = "2020";
        template[4] = "2020-11";
        template[5] = featureListResponse.getActivationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        template[6] = featureListResponse.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        FLEntryResponse flEntryResponse = featureListResponse.getEntry();

        //"0:ID_CLIENT,1:CODENO,2:CODETEXT,3:YEAR,4:YEAR-Month,5:ACTIVATION_DATE,6:EXPIRATION_DATE,7:FIRSTNAME,8:NAME,9:STREET,10:HOUSENO,11:ZIPCODE,12:CITY,13:COUNTRY,14:EMAIL,15:BLZ,16:ACCOUNTNUMBER,17:IBAN,18:BIC";

        if (ofNullable(flEntryResponse.getPerson()).isPresent()) {
            template[7] = ofNullable(flEntryResponse.getPerson().getGivenName()).orElse("");
            template[8] = ofNullable(flEntryResponse.getPerson().getSurname()).orElse("");
        } else {
            template[7] = "";
            template[8] = "";
        }

        if (ofNullable(flEntryResponse.getAddress()).isPresent()) {
            template[9] = ofNullable(flEntryResponse.getAddress().getStreet()).orElse("");
            template[10] = ofNullable(flEntryResponse.getAddress().getHouseNumber()).orElse("");
            template[11] = ofNullable(flEntryResponse.getAddress().getZipCode()).orElse("");
            template[12] = ofNullable(flEntryResponse.getAddress().getCity()).orElse("");
        } else {
            template[9] = "";
            template[10] = "";
            template[11] = "";
            template[12] = "";
        }

        template[13] = "????";

        if (ofNullable(flEntryResponse.getEmail()).isPresent()){
            template[14] = ofNullable(flEntryResponse.getEmail().getEmailAddress()).orElse("");
        } else {
            template[14] = "";
        }

        if(ofNullable(flEntryResponse.getBank()).isPresent()) {
            template[15] = ofNullable(flEntryResponse.getBank().getBlz()).orElse("");
            template[18] = ofNullable(flEntryResponse.getBank().getBic()).orElse("");
        } else {
            template[15] = "";
            template[18] = "";
        }

        if(ofNullable(flEntryResponse.getBankConnection()).isPresent()) {
            template[16] = ofNullable(flEntryResponse.getBankConnection().getAccountNumber()).orElse("");
            template[17] = ofNullable(flEntryResponse.getBankConnection().getIban()).orElse("");
        } else {
            template[16] = "";
            template[17] = "";
        }

        String join = String.join(",", Arrays.asList(template));

        System.out.println(join);

        return join;
    }

    private void writeLine(String line) {
        try {
            Files.write(
                    Paths.get(file.getAbsolutePath()),
                    (line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
