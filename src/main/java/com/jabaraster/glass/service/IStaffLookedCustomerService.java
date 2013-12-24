/**
 * 
 */
package com.jabaraster.glass.service;

import jabara.general.NotFound;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.jabaraster.glass.entity.EStaffLookedCustomer;
import com.jabaraster.glass.model.CarNumber;
import com.jabaraster.glass.model.StaffLookedCustomer;
import com.jabaraster.glass.service.impl.StaffLookedCustomerServiceImpl;

/**
 * @author jabaraster
 */
@ImplementedBy(StaffLookedCustomerServiceImpl.class)
public interface IStaffLookedCustomerService {

    /**
     * @param pId -
     */
    void delete(long pId);

    /**
     * @param pSsDescriptor -
     * @param pStaffName -
     * @return -
     * @throws NotFound -
     */
    EStaffLookedCustomer findByCondition(String pSsDescriptor, String pStaffName) throws NotFound;

    /**
     * @param pRequestSs null禁止.
     * @param pRequestStaffName このスタッフ名はリストの先頭になる.
     * @return -
     */
    List<StaffLookedCustomer> getLookedCustomers(String pRequestSs, String pRequestStaffName);

    /**
     * @param pStaffName -
     * @param pSsDescriptor -
     * @param pCarNumber -
     * @throws NotFound 車番に該当する顧客情報が存在しなかった場合.
     */
    void setStaffLookedCustomer(String pStaffName, String pSsDescriptor, CarNumber pCarNumber) throws NotFound;
}
