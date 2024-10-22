package com.laberit.sina.bootcamp.extra.awesomefinalproject.controller.backoffice.manager;

import com.laberit.sina.bootcamp.extra.awesomefinalproject.service.manager.DemographicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/backoffice/manager/patients")
public class DemographicController {
    private final DemographicService demographicService;

    public DemographicController(DemographicService demographicService) {
        this.demographicService = demographicService;
    }

    @GetMapping("/gender")
    @ResponseBody
    public Map<String, Integer> patientsByGender() {
        return demographicService.patientsByGender();
    }

    @GetMapping("/age")
    @ResponseBody
    public Map<String, Double> patientsMeanAge() {
        return demographicService.patientsMeanAge();
    }

    @GetMapping("/diagnosis")
    @ResponseBody
    public Map<String, Long> patientsWithDiagnosis() {
        return demographicService.patientsWithDiagnosis();
    }
}
