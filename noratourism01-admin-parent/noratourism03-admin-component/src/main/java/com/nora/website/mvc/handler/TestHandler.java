package com.nora.website.mvc.handler;

import com.nora.website.entity.Admin;
import com.nora.website.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap){

        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        return "target";
    }

    @ResponseBody
    @RequestMapping("send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array){
        for (Integer number: array
             ) {
            System.out.println("array = " + number);
        }
        return "success";
    }


}
