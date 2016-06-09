package com.spalah.courses.projects.blackjack.model.service;

import com.spalah.courses.projects.blackjack.model.dao.TableDao;
import com.spalah.courses.projects.blackjack.model.dao.TableTypeDao;
import com.spalah.courses.projects.blackjack.model.domain.table.TableType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author Denis Loshkarev on 03.06.2016.
 */
public class TableService{
    private TableDao tableDao;
    private TableTypeDao tableTypeDao;


    public TableService(TableDao tableDao, TableTypeDao tableTypeDao){
        this.tableDao = tableDao;
    }

    public List<TableType> getTableTypesVariants() {
        return tableTypeDao.getTableTypesVariants();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/application-servlet.xml");
        TableDao tableDao = context.getBean("tableDao", TableDao.class);
    }


}
