package com.increff.pos.api;

import com.increff.pos.dao.SalesDao;
import com.increff.pos.pojo.SalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class SalesApi {

    @Autowired
    SalesDao dao;

    @Transactional
    public void add(SalesPojo salesPojo){
        dao.insert(salesPojo);
    }

    @Transactional
    public List<SalesPojo> getAll() {
        return dao.selectAll(SalesPojo.class);
    }

    @Transactional
    public SalesPojo getByDate(ZonedDateTime date) {
        return dao.selectByDate(date);
    }

    @Transactional
    public void update(ZonedDateTime date, SalesPojo newPojo)
    {
        SalesPojo pojo = dao.selectByDate(date);
        pojo.setInvoicedOrderCount(newPojo.getInvoicedOrderCount());
        pojo.setTotalRevenue(newPojo.getTotalRevenue());
        pojo.setInvoicedItemsCount(newPojo.getInvoicedItemsCount());
    }

    // Business Logic Methods

    @Transactional
    public List<SalesPojo> getAllBetweenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        return dao.selectBetweenDates(startDate,endDate);
    }
}
