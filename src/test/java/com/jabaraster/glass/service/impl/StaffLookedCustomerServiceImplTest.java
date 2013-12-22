/**
 * 
 */
package com.jabaraster.glass.service.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import jabara.general.NotFound;

import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.jabaraster.glass.WebStarter;
import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.model.StaffLookedCustomer;

/**
 * @author jabaraster
 */
public class StaffLookedCustomerServiceImplTest {

    /**
     * 
     */
    @Rule
    public JpaDaoRule<StaffLookedCustomerServiceImpl> support = new JpaDaoRule<StaffLookedCustomerServiceImpl>() {

                                                                  @Override
                                                                  protected StaffLookedCustomerServiceImpl createService(
                                                                          final EntityManagerFactory pEntityManagerFactory) {
                                                                      return new StaffLookedCustomerServiceImpl(pEntityManagerFactory,
                                                                              new CustomerServiceImpl(pEntityManagerFactory));
                                                                  }
                                                              };

    /**
     * 
     */
    @SuppressWarnings("nls")
    @Test
    public void test() {
        final List<StaffLookedCustomer> list = this.support.getSut().getLookedCustomers("SS01", null);
        System.out.println(list);
    }

    /**
     * @throws NotFound -
     */
    @SuppressWarnings({ "nls", "boxing" })
    @Test
    public void test_setStaffLookedCustomer() throws NotFound {
        final EntityManager em = this.support.getEntityManager();

        final ECarNumber carNumber = createCarNumber();
        em.persist(carNumber);

        final ECustomer customer = new ECustomer();
        customer.setCarNumber(carNumber);
        customer.setName("河野 智遵");
        em.persist(customer);

        em.flush();
        em.clear();

        final StaffLookedCustomerServiceImpl sut = this.support.getSut();
        final String ssDescriptor = "SS01";
        sut.setStaffLookedCustomer("staff", ssDescriptor, carNumber.convert());

        final List<StaffLookedCustomer> list = sut.getLookedCustomers(ssDescriptor, null);
        assertThat(1, is(list.size()));
        assertThat(customer, is(list.get(0).getCustomer()));

        final ECarNumber carNumber2 = createCarNumber2();
        final ECustomer customer2 = new ECustomer();
        customer2.setCarNumber(carNumber2);
        customer2.setName("田上 暢顕2");
        em.persist(carNumber2);
        em.persist(customer2);

        em.flush();
        em.clear();
        sut.setStaffLookedCustomer("staff", ssDescriptor, carNumber2.convert());
        em.flush();

        final List<StaffLookedCustomer> list2 = sut.getLookedCustomers(ssDescriptor, null);
        assertThat(1, is(list2.size()));
        assertThat(customer2, is(list2.get(0).getCustomer()));
    }

    /**
     * @throws NamingException -
     */
    @BeforeClass
    public static void beforeClass() throws NamingException {
        WebStarter.initializeDataSource();
    }

    @SuppressWarnings("nls")
    private static ECarNumber createCarNumber() {
        final ECarNumber carNumber = new ECarNumber();
        carNumber.setCategory("わ");
        carNumber.setClassification("500");
        carNumber.setDepartment("福岡");
        carNumber.setNumber("12-34");
        return carNumber;
    }

    @SuppressWarnings("nls")
    private static ECarNumber createCarNumber2() {
        final ECarNumber carNumber = new ECarNumber();
        carNumber.setCategory("あ");
        carNumber.setClassification("512");
        carNumber.setDepartment("福岡");
        carNumber.setNumber("..-34");
        return carNumber;
    }

}
