package com.project.first.userbankcrud.Services;

import com.project.first.userbankcrud.Domain.CombinedObject;
import com.project.first.userbankcrud.Response.Response;

public interface CombinedService {

    Response save(CombinedObject combinedObject);
    Response getCombinedDomainByPhoneNumber(Long phoneNumber);
    String delete(Long id);
    String update(CombinedObject combinedObject);
}
