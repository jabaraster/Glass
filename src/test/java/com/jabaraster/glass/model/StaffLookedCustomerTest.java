/**
 * 
 */
package com.jabaraster.glass.model;

import net.arnx.jsonic.JSON;

import org.junit.Test;

import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.entity.EStaffLookedCustomer;

/**
 * @author jabaraster
 */
public class StaffLookedCustomerTest {

    /**
     * 
     */
    @SuppressWarnings({ "nls", "static-method" })
    @Test
    public void test() {
        final ECarNumber cn = new ECarNumber();
        cn.setCategory("わ");
        cn.setClassification("500");
        cn.setDepartment("熊本");
        cn.setNumber("12-34");

        final ECustomer customer = new ECustomer();
        customer.setName("田上 暢顕");
        customer.setCarNumber(cn);

        final EStaffLookedCustomer eslc = new EStaffLookedCustomer();
        eslc.setStaffName("河野 智遵");
        eslc.setLooked(customer);

        final StaffLookedCustomer slc = new StaffLookedCustomer(eslc);

        System.out.println(JSON.encode(slc, true));
    }
}
