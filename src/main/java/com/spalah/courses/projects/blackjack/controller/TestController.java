package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.model.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Denis Loshkarev.
 */
@Controller
public class TestController {
    TableService tableService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
//        tableService.getTableTypesVariants();
    }
}
