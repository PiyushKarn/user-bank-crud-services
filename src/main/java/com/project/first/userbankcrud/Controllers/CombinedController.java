package com.project.first.userbankcrud.Controllers;

import com.project.first.userbankcrud.Domain.CombinedObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.first.userbankcrud.Services.CombinedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combined")
public class CombinedController {

    @Autowired
    CombinedService combinedService;

    @PostMapping()
    public String save(@RequestBody CombinedObject combinedObject)
    {
        return combinedService.save(combinedObject);
    }

    @GetMapping("/{phone_num}")
    public List<CombinedObject> getAccountByPhoneNumber(@PathVariable Long phone_num)
    {
        return combinedService.getCombinedDomainByPhoneNumber(phone_num);
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id)
    {
        return combinedService.delete(id);
    }

    @PutMapping()
    public String updateCombined(@RequestBody CombinedObject combinedObject){
        return combinedService.update(combinedObject);

    }

//    @PostMapping("/bulk")
//    public String bulkUploadCombined(){
//        return combinedService.bulkUpload();
//    }
}
