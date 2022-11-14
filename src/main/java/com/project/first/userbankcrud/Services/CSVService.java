package com.project.first.userbankcrud.Services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.project.first.userbankcrud.Domain.UserDomain;
import com.project.first.userbankcrud.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    UserRepository userRepository;

    public void save(MultipartFile file) {
        try {
            List<UserDomain> users = CSVHelper.csvToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

//    public ByteArrayInputStream load() {
//        List<UserDomain> users = userRepository.findAll();
//
//        ByteArrayInputStream in = CSVHelper.usersToCSV(tutorials);
//        return in;
//    }
//
//    public List<DeveloperTutorial> getAllTutorials() {
//        return repository.findAll();
//    }
}

