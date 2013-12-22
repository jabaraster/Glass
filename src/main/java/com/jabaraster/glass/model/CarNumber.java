/**
 * 
 */
package com.jabaraster.glass.model;

import jabara.general.ExceptionUtil;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author jabaraster
 */
@Embeddable
public class CarNumber implements Serializable, Cloneable {
    private static final long serialVersionUID = 6110424210383072363L;

    String                    department;
    String                    classification;
    String                    category;
    String                    number;

    /**
     * 
     */
    public CarNumber() {
        //
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public CarNumber clone() {
        try {
            return (CarNumber) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarNumber other = (CarNumber) obj;
        if (this.category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!this.category.equals(other.category)) {
            return false;
        }
        if (this.classification == null) {
            if (other.classification != null) {
                return false;
            }
        } else if (!this.classification.equals(other.classification)) {
            return false;
        }
        if (this.department == null) {
            if (other.department != null) {
                return false;
            }
        } else if (!this.department.equals(other.department)) {
            return false;
        }
        if (this.number == null) {
            if (other.number != null) {
                return false;
            }
        } else if (!this.number.equals(other.number)) {
            return false;
        }
        return true;
    }

    /**
     * @return categoryを返す.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * @return classificationを返す.
     */
    public String getClassification() {
        return this.classification;
    }

    /**
     * @return departmentを返す.
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * @return numberを返す.
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.category == null ? 0 : this.category.hashCode());
        result = prime * result + (this.classification == null ? 0 : this.classification.hashCode());
        result = prime * result + (this.department == null ? 0 : this.department.hashCode());
        result = prime * result + (this.number == null ? 0 : this.number.hashCode());
        return result;
    }

    /**
     * @param pCategory categoryを設定.
     */
    public void setCategory(final String pCategory) {
        this.category = pCategory;
    }

    /**
     * @param pClassification classificationを設定.
     */
    public void setClassification(final String pClassification) {
        this.classification = pClassification;
    }

    /**
     * @param pDepartment departmentを設定.
     */
    public void setDepartment(final String pDepartment) {
        this.department = pDepartment;
    }

    /**
     * @param pNumber numberを設定.
     */
    public void setNumber(final String pNumber) {
        this.number = pNumber;
    }

}
