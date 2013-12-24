/**
 * 
 */
package com.jabaraster.glass.service.impl;

import jabara.general.ArgUtil;
import jabara.general.NotFound;
import jabara.jpa.JpaDaoBase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.entity.EStaffLookedCustomer;
import com.jabaraster.glass.entity.EStaffLookedCustomer_;
import com.jabaraster.glass.model.CarNumber;
import com.jabaraster.glass.model.StaffLookedCustomer;
import com.jabaraster.glass.service.ICustomerService;
import com.jabaraster.glass.service.IStaffLookedCustomerService;

/**
 * @author jabaraster
 */
public class StaffLookedCustomerServiceImpl extends JpaDaoBase implements IStaffLookedCustomerService {
    private static final long      serialVersionUID = -4851534233400209290L;

    private final ICustomerService customerService;

    /**
     * @param pEntityManagerFactory -
     * @param pCustomerService -
     */
    @Inject
    public StaffLookedCustomerServiceImpl(final EntityManagerFactory pEntityManagerFactory, final ICustomerService pCustomerService) {
        super(pEntityManagerFactory);
        this.customerService = ArgUtil.checkNull(pCustomerService, "pCustomerService"); //$NON-NLS-1$
    }

    /**
     * @see com.jabaraster.glass.service.IStaffLookedCustomerService#delete(long)
     */
    @Override
    public void delete(final long pId) {
        final EntityManager em = getEntityManager();
        em.remove(em.find(EStaffLookedCustomer.class, Long.valueOf(pId)));
    }

    /**
     * @see com.jabaraster.glass.service.IStaffLookedCustomerService#findByCondition(java.lang.String, java.lang.String)
     */
    @Override
    public EStaffLookedCustomer findByCondition(final String pSsDescriptor, final String pStaffName) throws NotFound {
        return findByConditionCore(pSsDescriptor, pStaffName);
    }

    /**
     * @see com.jabaraster.glass.service.IStaffLookedCustomerService#getLookedCustomers(java.lang.String, java.lang.String)
     */
    @Override
    public List<StaffLookedCustomer> getLookedCustomers(final String pRequestSs, final String pRequestStaffName) {
        ArgUtil.checkNullOrEmpty(pRequestSs, "pRequestSs"); //$NON-NLS-1$
        return convert(getLookedCustomersCore(pRequestSs, pRequestStaffName));
    }

    /**
     * @see com.jabaraster.glass.service.IStaffLookedCustomerService#setStaffLookedCustomer(java.lang.String, java.lang.String, CarNumber)
     */
    @Override
    public void setStaffLookedCustomer(final String pStaffName, final String pSsDescriptor, final CarNumber pCarNumber) throws NotFound {
        ArgUtil.checkNullOrEmpty(pStaffName, "pStaffName"); //$NON-NLS-1$
        ArgUtil.checkNull(pSsDescriptor, "pSsDescriptor"); //$NON-NLS-1$
        ArgUtil.checkNull(pCarNumber, "pCarNumber"); //$NON-NLS-1$

        final ECustomer customer = this.customerService.findByCarNumber(pCarNumber);
        try {
            final EStaffLookedCustomer s = findByConditionCore(pSsDescriptor, pStaffName);
            s.setLooked(customer); // この操作がUPDATEに相当する.
        } catch (final NotFound e) {
            insert(pStaffName, pSsDescriptor, customer);
        }
    }

    EStaffLookedCustomer findByConditionCore(final String pSsDescriptor, final String pStaffName) throws NotFound {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EStaffLookedCustomer> query = builder.createQuery(EStaffLookedCustomer.class);
        final Root<EStaffLookedCustomer> root = query.from(EStaffLookedCustomer.class);

        query.where( //
                builder.equal(root.get(EStaffLookedCustomer_.staffName), pStaffName) //
                , builder.equal(root.get(EStaffLookedCustomer_.ssDescriptor), pSsDescriptor) //
        );

        return JpaDaoBase.getSingleResult(em.createQuery(query));
    }

    @SuppressWarnings("static-method")
    private List<StaffLookedCustomer> convert(final List<EStaffLookedCustomer> pSource) {
        final List<StaffLookedCustomer> ret = new ArrayList<>();
        for (final EStaffLookedCustomer s : pSource) {
            ret.add(new StaffLookedCustomer(s));
        }
        return ret;
    }

    private List<EStaffLookedCustomer> getLookedCustomersCore(final String pRequestSs, final String pRequestStaffName) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EStaffLookedCustomer> query = builder.createQuery(EStaffLookedCustomer.class);
        final Root<EStaffLookedCustomer> root = query.from(EStaffLookedCustomer.class);

        query.where(builder.equal(root.get(EStaffLookedCustomer_.ssDescriptor), pRequestSs));
        query.orderBy(builder.asc(root.get(EStaffLookedCustomer_.staffName)));

        final List<EStaffLookedCustomer> ret = em.createQuery(query).getResultList();
        if (pRequestStaffName == null) {
            return ret;
        }
        try {
            final EStaffLookedCustomer self = removeByStaffName(ret, pRequestStaffName);
            ret.add(0, self);
            return ret;

        } catch (final NotFound e) {
            return ret;
        }
    }

    private void insert(final String pStaffName, final String pSsDescriptor, final ECustomer pCustomer) {
        final EStaffLookedCustomer s = new EStaffLookedCustomer();
        s.setLooked(pCustomer);
        s.setSsDescriptor(pSsDescriptor);
        s.setStaffName(pStaffName);
        getEntityManager().persist(s);
    }

    private static EStaffLookedCustomer removeByStaffName(final List<EStaffLookedCustomer> pList, final String pStaffName) throws NotFound {
        for (final EStaffLookedCustomer s : pList) {
            if (s.getStaffName().equals(pStaffName)) {
                pList.remove(s);
                return s;
            }
        }
        throw NotFound.GLOBAL;
    }

}
