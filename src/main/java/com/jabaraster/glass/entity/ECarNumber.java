/**
 * 
 */
package com.jabaraster.glass.entity;

import jabara.general.Empty;
import jabara.jpa.entity.EntityBase;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import net.arnx.jsonic.JSONHint;

import com.jabaraster.glass.model.CarNumber;

/**
 * @author jabaraster
 */
@Entity
public class ECarNumber extends EntityBase<ECarNumber> {
    private static final long serialVersionUID = 1421975767552290518L;

    @Embedded
    CarNumber                 carNumber        = new CarNumber();

    /**
     * 
     */
    public ECarNumber() {
        //
    }

    /**
     * @param pSource
     */
    public ECarNumber(final CarNumber pSource) {
        this.carNumber = pSource.clone();
    }

    /**
     * @return -
     */
    public CarNumber convert() {
        return this.carNumber.clone();
    }

    /**
     * @return -
     * @see com.jabaraster.glass.model.CarNumber#getCategory()
     */
    public String getCategory() {
        return this.carNumber == null ? Empty.STRING : this.carNumber.getCategory();
    }

    /**
     * @return -
     * @see com.jabaraster.glass.model.CarNumber#getClassification()
     */
    public String getClassification() {
        return this.carNumber == null ? Empty.STRING : this.carNumber.getClassification();
    }

    /**
     * @see jabara.jpa.entity.EntityBase#getCreated()
     */
    @Override
    @JSONHint(ignore = true)
    public Date getCreated() {
        return super.getCreated();
    }

    /**
     * @return -
     * @see com.jabaraster.glass.model.CarNumber#getDepartment()
     */
    public String getDepartment() {
        return this.carNumber == null ? Empty.STRING : this.carNumber.getDepartment();
    }

    /**
     * @return -
     */
    public String getDisplayString() {
        final StringBuilder s = new StringBuilder();
        final String SEPARATOR = " "; //$NON-NLS-1$
        s.append(getDepartment());
        s.append(SEPARATOR).append(getClassification());
        s.append(SEPARATOR).append(getCategory());
        s.append(SEPARATOR).append(getNumber());
        return new String(s);
    }

    /**
     * @see jabara.jpa.entity.EntityBase#getId()
     */
    @Override
    @JSONHint(ignore = true)
    public Long getId() {
        return super.getId();
    }

    /**
     * @return -
     * @see com.jabaraster.glass.model.CarNumber#getNumber()
     */
    public String getNumber() {
        return this.carNumber == null ? Empty.STRING : this.carNumber.getNumber();
    }

    /**
     * @see jabara.jpa.entity.EntityBase#getUpdated()
     */
    @Override
    @JSONHint(ignore = true)
    public Date getUpdated() {
        return super.getUpdated();
    }

    /**
     * @see jabara.jpa.entity.EntityBase#isPersisted()
     */
    @Override
    @JSONHint(ignore = true)
    public boolean isPersisted() {
        return super.isPersisted();
    }

    /**
     * @param pCategory
     * @see com.jabaraster.glass.model.CarNumber#setCategory(java.lang.String)
     */
    public void setCategory(final String pCategory) {
        if (this.carNumber == null) {
            this.carNumber = new CarNumber();
        }
        this.carNumber.setCategory(pCategory);
    }

    /**
     * @param pClassification
     * @see com.jabaraster.glass.model.CarNumber#setClassification(java.lang.String)
     */
    public void setClassification(final String pClassification) {
        if (this.carNumber == null) {
            this.carNumber = new CarNumber();
        }
        this.carNumber.setClassification(pClassification);
    }

    /**
     * @param pDepartment
     * @see com.jabaraster.glass.model.CarNumber#setDepartment(java.lang.String)
     */
    public void setDepartment(final String pDepartment) {
        if (this.carNumber == null) {
            this.carNumber = new CarNumber();
        }
        this.carNumber.setDepartment(pDepartment);
    }

    /**
     * @param pNumber
     * @see com.jabaraster.glass.model.CarNumber#setNumber(java.lang.String)
     */
    public void setNumber(final String pNumber) {
        if (this.carNumber == null) {
            this.carNumber = new CarNumber();
        }
        this.carNumber.setNumber(pNumber);
    }
}
