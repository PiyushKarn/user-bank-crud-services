package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.UserDomain;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDomain> findAll();
    ResponseEntity<String> saveUser(UserDomain userdata);
    ResponseEntity<String> updateUser(UserDomain newUserData);
    Optional<UserDomain> findById(Long id);
    ResponseEntity<String> deleteUser(UserDomain deleteUserData);
    Page<UserDomain> findUsersWithPagination(int offset, int limit);
    Iterable<UserDomain> list();
    void save(List<UserDomain> users);

  String saveUserBulkJson(List<UserDomain> userDomain);

    String saveUserBulkCsv();
}

