/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.wicket.Models;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jabaraster
 */
public class UpdateCustomerPage extends CustomerEditPage {
    private static final long serialVersionUID = -5105119358597502001L;

    private final Handler     handler          = new Handler();

    /**
     * @param pParameters
     */
    public UpdateCustomerPage(final PageParameters pParameters) {
        super(pParameters);
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly("お客様情報の編集"); //$NON-NLS-1$
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.CustomerEditPage#onSubmit(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void onSubmit(@SuppressWarnings("unused") final AjaxRequestTarget pTarget) {
        this.handler.onSubmit();
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = 2224649196186495170L;

        void onSubmit() {
            UpdateCustomerPage.this.customer.setCarNumber(UpdateCustomerPage.this.carNumber);
            UpdateCustomerPage.this.customerService.update(UpdateCustomerPage.this.customer);
            setResponsePage(CustomerListPage.class);
        }
    }
}
