package com.jabaraster.glass.entity;

import jabara.jpa.entity.EntityBase_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-12-22T09:57:38.867+0900")
@StaticMetamodel(EStaffLookedCustomer.class)
public class EStaffLookedCustomer_ extends EntityBase_ {
	public static volatile SingularAttribute<EStaffLookedCustomer, String> staffName;
	public static volatile SingularAttribute<EStaffLookedCustomer, String> ssDescriptor;
	public static volatile SingularAttribute<EStaffLookedCustomer, ECustomer> looked;
}
