/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.wicket.Models;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author jabaraster
 */
public class NewCustomerPage extends CustomerEditPage {
    private static final long serialVersionUID = 1685254724844783758L;

    private final Handler     handler          = new Handler();

    /**
     * 
     */
    public NewCustomerPage() {
        super();
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.CustomerEditPage#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly("新しいお客様情報の登録"); //$NON-NLS-1$
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.CustomerEditPage#onSubmit(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void onSubmit(@SuppressWarnings("unused") final AjaxRequestTarget pTarget) {
        this.handler.onSutmit();
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = -5583390248974372359L;

        void onSutmit() {
            NewCustomerPage.this.customer.setCarNumber(NewCustomerPage.this.carNumber);
            NewCustomerPage.this.customerService.insert(NewCustomerPage.this.customer);
            setResponsePage(CustomerListPage.class);
        }
    }
}
