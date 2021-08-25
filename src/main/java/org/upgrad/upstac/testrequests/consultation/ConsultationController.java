package org.upgrad.upstac.testrequests.consultation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.testrequests.TestRequestUpdateService;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.users.User;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;
import static org.upgrad.upstac.exception.UpgradResponseStatusException.asConstraintViolation;


@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    Logger log = LoggerFactory.getLogger(ConsultationController.class);




    @Autowired
    private TestRequestUpdateService testRequestUpdateService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;


    @Autowired
    TestRequestFlowService  testRequestFlowService;

    @Autowired
    private UserLoggedInService userLoggedInService;



    @GetMapping("/in-queue")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForConsultations()  {

        return testRequestQueryService.findBy(RequestStatus.LAB_TEST_COMPLETED);
        // Implemented this method


        // Implemented this method to get the list of test requests having status as 'LAB_TEST_COMPLETED'
        // Made use of the findBy() method from testRequestQueryService class
        // returned the result
        // For reference checked the method getForTests() method from LabRequestController class

        // replaced the below line of code with my implementation
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");


    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public List<TestRequest> getForDoctor()  {
         User doctor = UserLoggedInService.getLoggedInUser();
         return testRequestQueryService.findByDoctor(doctor);
        // Implemented this method

        // Created an object of User class and store the current logged in user first
        // Implemented this method to return the list of test requests assigned to current doctor(make use of the above created User object)
        // Made use of the findByDoctor() method from testRequestQueryService class to get the list
        // For reference checked the method getForTests() method from LabRequestController class

        // replaced the below line of code with your implementation
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");




    }



    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/assign/{id}")
    public TestRequest assignForConsultation(@PathVariable Long id) {
        User doctor =userLoggedInService.getLoggedInUser();

        return   testRequestUpdateService.assignForConsultation(id,doctor);
        // Implemented this method

        // Implemented this method to assign a particular test request to the current doctor(logged in user)
        //Created an object of User class and get the current logged in user
        //Created an object of TestRequest class and use the assignForConsultation() method of testRequestUpdateService to assign the particular id to the current user
        // returned the above created object
        // For reference checked the method assignForLabTest() method from LabRequestController class
        try {
            User doctor =userLoggedInService.getLoggedInUser();
            return testRequestUpdateService.updateConsultation(id,doctor);
            // replaced the below line of code with your implementation
            // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");

        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }



    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/update/{id}")
    public TestRequest updateConsultation(@PathVariable Long id,@RequestBody CreateConsultationRequest testResult) {

        // Implemented this method

        // Implemented this method to update the result of the current test request id with test doctor comments
        // Created an object of the User class to get the logged in user
        // Created an object of TestRequest class and make use of updateConsultation() method from testRequestUpdateService class
        //to update the current test request id with the testResult details by the current user(object created)
        // For reference checked the method updateLabTest() method from LabRequestController class

        try {
            User user =userLoggedInService.getLoggedInUser();
            return testRequestUpdateService.updateConsultation(id,user);
            // replaced the below line of code with your implementation
            // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,"Not implemented");


        } catch (ConstraintViolationException e) {
            throw asConstraintViolation(e);
        }catch (AppException e) {
            throw asBadRequest(e.getMessage());
        }
    }



}
