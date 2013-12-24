/**
 * 
 */
package com.jabaraster.glass.model;

import jabara.general.ArgUtil;

import java.io.Serializable;

import net.arnx.jsonic.JSONHint;

import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.entity.EStaffLookedCustomer;

/**
 * @author jabaraster
 */
public class StaffLookedCustomer implements Serializable {
    private static final long          serialVersionUID = -8768551749059035743L;

    private final EStaffLookedCustomer customer;

    /**
     * @param pCustomer -
     */
    public StaffLookedCustomer(final EStaffLookedCustomer pCustomer) {
        this.customer = ArgUtil.checkNull(pCustomer, "pCustomer"); //$NON-NLS-1$
    }

    /**
     * @return carNumberを返す.
     */
    public ECarNumber getCarNumber() {
        return this.customer.getLooked().getCarNumber();
    }

    /**
     * @return customerを返す.
     */
    public ECustomer getCustomer() {
        return this.customer.getLooked();
    }

    /**
     * @return -
     */
    @JSONHint(ignore = true)
    public String getSsDescriptor() {
        return this.customer.getSsDescriptor();
    }

    /**
     * @return staffNameを返す.
     */
    public String getStaffName() {
        return this.customer.getStaffName();
    }
}