package com.jabaraster.glass.model;

import net.arnx.jsonic.JSON;

import org.junit.Test;

import com.jabaraster.glass.entity.ECarNumber;

/**
 * @author jabaraster
 */
public class CarNumberTest {

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
        System.out.println(JSON.encode(cn, true));
    }

}
