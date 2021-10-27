package cn.moquan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/default")
public class DefaultController {

    @RequestMapping("/")
    public String toSuccess(){

        return "success";
    }

}
