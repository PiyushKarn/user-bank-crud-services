package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.CombinedObject;

import java.util.List;

public interface CombinedService {

    String save(CombinedObject combinedObject);
    List<CombinedObject> getCombinedDomainByPhoneNumber(Long phoneNumber);
    String delete(Long id);
    String update(CombinedObject combinedObject);
}
