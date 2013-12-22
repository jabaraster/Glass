/**
 * 
 */
package com.jabaraster.glass.entity;

import jabara.bean.BeanProperties;
import jabara.jpa.entity.EntityBase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import net.arnx.jsonic.JSONHint;

/**
 * @author jabaraster
 */
@Entity
public class ECustomer extends EntityBase<ECustomer> {
    private static final long     serialVersionUID    = 7351194852002180436L;

    /**
     * 
     */
    public static final int       MAX_CHAR_COUNT_NAME = 100;

    private static BeanProperties _properties         = BeanProperties.getInstance(ECustomer.class);

    @Column(nullable = false, length = MAX_CHAR_COUNT_NAME * 3)
    String                        name;

    @OneToOne
    // 本来はOneToOneではなくManyToManyになるべきかもしれない・・・
    @JoinColumn(nullable = true)
    ECarNumber                    carNumber;

    /**
     * @return carNumberを返す.
     */
    @JSONHint(ignore = true)
    public ECarNumber getCarNumber() {
        return this.carNumber;
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
     * @see jabara.jpa.entity.EntityBase#getId()
     */
    @Override
    @JSONHint(ignore = true)
    public Long getId() {
        return super.getId();
    }

    /**
     * @return nameを返す.
     */
    public String getName() {
        return this.name;
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
     * @param pCarNumber carNumberを設定.
     */
    public void setCarNumber(final ECarNumber pCarNumber) {
        this.carNumber = pCarNumber;
    }

    /**
     * @param pName nameを設定.
     */
    public void setName(final String pName) {
        this.name = pName;
    }

    /**
     * @return -
     */
    public static BeanProperties getMeta() {
        return _properties;
    }

}
