/**
 * 
 */
package com.jabaraster.glass.service;

import jabara.general.NotFound;
import jabara.general.Sort;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.model.CarNumber;
import com.jabaraster.glass.service.impl.CustomerServiceImpl;

/**
 * @author jabaraster
 */
@ImplementedBy(CustomerServiceImpl.class)
public interface ICustomerService {

    /**
     * @return -
     */
    long countAll();

    /**
     * @param pCustomerId
     */
    void delete(long pCustomerId);

    /**
     * @param pCarNumber -
     * @return -
     * @throws NotFound -
     */
    ECustomer findByCarNumber(CarNumber pCarNumber) throws NotFound;

    /**
     * @param pId -
     * @return -
     * @throws NotFound -
     */
    ECustomer findById(long pId) throws NotFound;

    /**
     * @param pSort -
     * @param pFirst -
     * @param pCount -
     * @return -
     */
    List<ECustomer> get(Sort pSort, int pFirst, int pCount);

    /**
     * @param pCustomer -
     */
    void insert(ECustomer pCustomer);

    /**
     * @param pCustomer -
     */
    void update(ECustomer pCustomer);
}
