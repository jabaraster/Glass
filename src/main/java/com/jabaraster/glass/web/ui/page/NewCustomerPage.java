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

    private final Handler     handler          = new Handler(this);

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

    private static class Handler implements Serializable {
        private static final long     serialVersionUID = -5583390248974372359L;

        private final NewCustomerPage page;

        Handler(final NewCustomerPage pPage) {
            this.page = pPage;
        }

        void onSutmit() {
            this.page.customer.setCarNumber(this.page.carNumber);
            this.page.customerService.insert(this.page.customer);
            this.page.setResponsePage(CustomerListPage.class);
        }
    }
}
