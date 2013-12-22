/**
 * 
 */
package com.jabaraster.glass.entity;

import jabara.jpa.entity.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author jabaraster
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "staffName", "ssDescriptor" }) })
public class EStaffLookedCustomer extends EntityBase<EStaffLookedCustomer> {
    private static final long serialVersionUID             = 6339231853879150771L;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_STAFF_NAME    = 100;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_SS_DESCRIPTOR = 20;

    @Column(nullable = false, length = MAX_CHAR_COUNT_STAFF_NAME * 3)
    String                    staffName;

    @Column(nullable = false, length = MAX_CHAR_COUNT_SS_DESCRIPTOR)
    String                    ssDescriptor;

    @ManyToOne
    ECustomer                 looked;

    /**
     * @return lookedを返す.
     */
    public ECustomer getLooked() {
        return this.looked;
    }

    /**
     * @return ssDescriptorを返す.
     */
    public String getSsDescriptor() {
        return this.ssDescriptor;
    }

    /**
     * @return staffNameを返す.
     */
    public String getStaffName() {
        return this.staffName;
    }

    /**
     * @param pLooked lookedを設定.
     */
    public void setLooked(final ECustomer pLooked) {
        this.looked = pLooked;
    }

    /**
     * @param pSsDescriptor ssDescriptorを設定.
     */
    public void setSsDescriptor(final String pSsDescriptor) {
        this.ssDescriptor = pSsDescriptor;
    }

    /**
     * @param pStaffName staffNameを設定.
     */
    public void setStaffName(final String pStaffName) {
        this.staffName = pStaffName;
    }
}
