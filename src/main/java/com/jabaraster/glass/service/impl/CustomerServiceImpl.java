/**
 * 
 */
package com.jabaraster.glass.service.impl;

import jabara.general.ArgUtil;
import jabara.general.ExceptionUtil;
import jabara.general.NotFound;
import jabara.general.Sort;
import jabara.jpa.JpaDaoBase;
import jabara.jpa.entity.EntityBase_;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECarNumber_;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.entity.ECustomer_;
import com.jabaraster.glass.model.CarNumber;
import com.jabaraster.glass.service.ICustomerService;

/**
 * @author jabaraster
 */
public class CustomerServiceImpl extends JpaDaoBase implements ICustomerService {
    private static final long serialVersionUID = -4036529019241058048L;

    /**
     * @param pEntityManagerFactory
     */
    @Inject
    public CustomerServiceImpl(final EntityManagerFactory pEntityManagerFactory) {
        super(pEntityManagerFactory);
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#countAll()
     */
    @Override
    public long countAll() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<ECustomer> root = query.from(ECustomer.class);

        query.select(builder.count(root));

        return em.createQuery(query).getSingleResult().longValue();
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#delete(long)
     */
    @Override
    public void delete(final long pCustomerId) {
        try {
            final ECustomer customer = findByIdCore(ECustomer.class, pCustomerId);
            getEntityManager().remove(customer);
        } catch (final NotFound e) {
            // 処理なし
        }
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#findByCarNumber(CarNumber)
     */
    @Override
    public ECustomer findByCarNumber(final CarNumber pCarNumber) throws NotFound {
        ArgUtil.checkNull(pCarNumber, "pCarNumber"); //$NON-NLS-1$

        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<ECustomer> query = builder.createQuery(ECustomer.class);
        final Root<ECustomer> root = query.from(ECustomer.class);

        query.where( //
        builder.equal(root.get(ECustomer_.carNumber).get(ECarNumber_.carNumber), pCarNumber) //
        );

        return getSingleResult(em.createQuery(query));
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#findById(long)
     */
    @Override
    public ECustomer findById(final long pId) throws NotFound {
        final ECustomer ret = findByIdCore(ECustomer.class, pId);
        ret.getCarNumber(); // fetchする.
        return ret;
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#get(jabara.general.Sort, int, int)
     */
    @Override
    public List<ECustomer> get(Sort pSort, final int pFirst, final int pCount) {
        pSort = pSort == null ? Sort.asc(EntityBase_.id.getName()) : pSort;
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<ECustomer> query = builder.createQuery(ECustomer.class);
        final Root<ECustomer> root = query.from(ECustomer.class);

        root.fetch(ECustomer_.carNumber);

        query.orderBy(convertOrder(pSort, builder, root));

        return em.createQuery(query).setFirstResult(pFirst).setMaxResults(pCount).getResultList();
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#insert(com.jabaraster.glass.entity.ECustomer)
     */
    @Override
    public void insert(final ECustomer pCustomer) {
        ArgUtil.checkNull(pCustomer, "pCustomer"); //$NON-NLS-1$
        final EntityManager em = getEntityManager();
        em.persist(pCustomer.getCarNumber());
        em.persist(pCustomer);
    }

    /**
     * @see com.jabaraster.glass.service.ICustomerService#update(com.jabaraster.glass.entity.ECustomer)
     */
    @Override
    public void update(final ECustomer pCustomer) {
        ArgUtil.checkNull(pCustomer, "pCustomer"); //$NON-NLS-1$
        try {
            final ECustomer inDb = this.findByIdCore(ECustomer.class, pCustomer.getId().longValue());
            inDb.setName(pCustomer.getName());

            final ECarNumber pNewCarNumber = pCustomer.getCarNumber();
            final ECarNumber carNumberInDb = inDb.getCarNumber();
            if (pNewCarNumber == null) {
                if (carNumberInDb == null) {
                    // 処理なし
                } else {
                    inDb.setCarNumber(null);
                }
            } else {
                if (carNumberInDb == null) {
                    inDb.setCarNumber(pNewCarNumber);
                } else {
                    carNumberInDb.setCategory(pNewCarNumber.getCategory());
                    carNumberInDb.setClassification(pNewCarNumber.getClassification());
                    carNumberInDb.setDepartment(pNewCarNumber.getDepartment());
                    carNumberInDb.setNumber(pNewCarNumber.getNumber());
                }
            }
        } catch (final NotFound e) {
            throw ExceptionUtil.rethrow(e);
        }
    }
}
