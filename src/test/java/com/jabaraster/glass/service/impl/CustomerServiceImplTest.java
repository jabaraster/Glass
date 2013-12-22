/**
 * 
 */
package com.jabaraster.glass.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import jabara.general.NotFound;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.jabaraster.glass.WebStarter;
import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.model.CarNumber;

/**
 * @author jabaraster
 */
public class CustomerServiceImplTest {

    /**
     * 
     */
    @Rule
    public JpaDaoRule<CustomerServiceImpl> support = new JpaDaoRule<CustomerServiceImpl>() {
                                                       @Override
                                                       protected CustomerServiceImpl createService(final EntityManagerFactory pEntityManagerFactory) {
                                                           return new CustomerServiceImpl(pEntityManagerFactory);
                                                       }
                                                   };

    /**
     * @throws NotFound -
     */
    @Test
    public void test_() throws NotFound {
        final EntityManager em = this.support.getEntityManager();

        final CarNumber source = createCarNumber();
        final ECarNumber carNumber = new ECarNumber(source);
        em.persist(carNumber);

        final ECustomer customer = new ECustomer();
        customer.setCarNumber(carNumber);
        customer.setName("河野 智遵"); //$NON-NLS-1$
        em.persist(customer);

        em.flush();
        em.clear();

        final ECustomer inDb = this.support.getSut().findByCarNumber(source);
        assertThat(customer, is(inDb));
    }

    /**
     * @throws NotFound -
     */
    @Test(expected = NotFound.class)
    public void test_findByCarNumber_レコードなし() throws NotFound {
        this.support.getSut().findByCarNumber(createCarNumber());
    }

    /**
     * @throws NotFound -
     */
    @Test(expected = NotFound.class)
    public void test_findByCarNumber_顧客情報はあるが車番は登録されていない() throws NotFound {
        final EntityManager em = this.support.getEntityManager();

        final ECustomer customer = new ECustomer();
        customer.setName("河野 智遵"); //$NON-NLS-1$
        em.persist(customer);

        em.flush();
        em.clear();

        this.support.getSut().findByCarNumber(createCarNumber());
    }

    /**
     * @throws NamingException -
     */
    @BeforeClass
    public static void beforeClass() throws NamingException {
        WebStarter.initializeDataSource();
    }

    @SuppressWarnings("nls")
    private static CarNumber createCarNumber() {
        final CarNumber carNumber = new CarNumber();
        carNumber.setCategory("わ");
        carNumber.setClassification("500");
        carNumber.setDepartment("福岡");
        carNumber.setNumber("12-34");
        return carNumber;
    }

}
