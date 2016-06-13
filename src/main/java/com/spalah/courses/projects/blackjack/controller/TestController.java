package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import com.spalah.courses.projects.blackjack.service.TableService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Denis Loshkarev.
 */
@Controller
public class TestController {
    TableService tableService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public List<TableType> test() {
        return tableService.getTableTypesVariants();
    }
}
