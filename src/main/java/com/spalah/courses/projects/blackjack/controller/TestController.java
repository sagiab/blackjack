package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.model.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Denis Loshkarev.
 */
@Controller
public class TestController {
    @Autowired
    TableService tableService;

    public void test() {
//        tableService.getTableTypesVariants();
    }
}
