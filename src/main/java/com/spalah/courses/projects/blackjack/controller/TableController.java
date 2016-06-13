package com.spalah.courses.projects.blackjack.controller;

import com.spalah.courses.projects.blackjack.exception.AccountException;
import com.spalah.courses.projects.blackjack.model.domain.response.TableCreateResponse;
import com.spalah.courses.projects.blackjack.model.domain.table.FormCreateTable;
import com.spalah.courses.projects.blackjack.model.domain.table.Table;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import com.spalah.courses.projects.blackjack.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
@Controller
public class TableController {
    @Autowired
    TableService tableService;

    @RequestMapping(
            value = "/account/{login}/select-table",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<TableType> getTableType(@PathVariable String login) {
        return tableService.getTableTypesVariants();
    }

    @RequestMapping(
            value = "/account/{login}/table",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public TableCreateResponse getTableType(
            @Valid @RequestBody FormCreateTable formCreateTable,
            @PathVariable String login
    ) throws AccountException {
        Long tableTypeId = formCreateTable.getTableTypeId();
        Table table = tableService.createTable(tableTypeId, login);
        return new TableCreateResponse(table);
    }
}