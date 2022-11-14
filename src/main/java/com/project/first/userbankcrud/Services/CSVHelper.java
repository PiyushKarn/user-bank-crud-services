package com.project.first.userbankcrud.Services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.first.userbankcrud.Domain.UserDomain;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;


public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "name","phoneNumber","address","additionalDetailsUser" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("users.csv")) {
            return true;
        }

        return false;
    }

    public static List<UserDomain> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT);) {

            List<UserDomain> userDomainList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                UserDomain userDomain = new UserDomain(
                        (csvRecord.get("name")),
                        Long.parseLong( csvRecord.get("phoneNumber")),
                        csvRecord.get("address"),
                        (csvRecord.get("additionalDetailsUsers"))
                );

                userDomainList.add(userDomain);
            }

            return userDomainList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

//    public static ByteArrayInputStream UsersToCSV(List<UserDomain> userDomainList) {
//        final CSVFormat format = CSVFormat.DEFAULT;
//
//        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
//             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
//            for (UserDomain userDomain : userDomainList) {
//                List<String> data = Arrays.asList(
//                        String.valueOf(developerTutorial.getId()),
//                        developerTutorial.getTitle(),
//                        developerTutorial.getDescription(),
//                        String.valueOf(developerTutorial.isPublished())
//                );
//
//                csvPrinter.printRecord(data);
//            }
//
//            csvPrinter.flush();
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (IOException e) {
//            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
//        }
//    }
}
