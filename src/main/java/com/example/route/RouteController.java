package com.example.route;

import com.example.route.service.LogisticOSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @Autowired
    private LogisticOSServiceImpl logisticOSServiceImpl;


    @PostMapping(path = "/optimizeRoutes")
    @ResponseBody
    public String getOptimizedRoutes(@RequestBody Object object) throws Exception {

        logisticOSServiceImpl.submitJob(object);
        return "Success";
    }

}
