/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.general.NotFound;
import jabara.wicket.Models;

import java.io.Serializable;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.service.ICustomerService;
import com.jabaraster.glass.web.ui.WicketApplication;

/**
 * @author jabaraster
 */
public class DeleteCustomerPage extends WebPageBase {
    private static final long serialVersionUID = 3884476225679182641L;

    @Inject
    ICustomerService          customerService;

    private final ECustomer   customer;

    private final Handler     handler          = new Handler();

    private Link<?>           goIndex;
    private Label             customerName;
    private Form<?>           form;
    private Button            submitter;

    /**
     * @param pParameters -
     */
    public DeleteCustomerPage(final PageParameters pParameters) {
        super(pParameters);

        try {
            final long id = Long.parseLong(pParameters.get(0).toString());
            this.customer = this.customerService.findById(id);

            this.add(getCustomerName());
            this.add(getForm());
            this.add(getGoIndex());

        } catch (NumberFormatException | NotFound e) {
            throw new RestartResponseAtInterceptPageException(WicketApplication.get().getHomePage());
        }
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly("お客様情報の削除"); //$NON-NLS-1$
    }

    private Label getCustomerName() {
        if (this.customerName == null) {
            this.customerName = new Label("customerName", this.customer.getName()); //$NON-NLS-1$
        }
        return this.customerName;
    }

    private Form<?> getForm() {
        if (this.form == null) {
            this.form = new StatelessForm<>("form"); //$NON-NLS-1$
            this.form.add(getSubmitter());
        }
        return this.form;
    }

    private Link<?> getGoIndex() {
        if (this.goIndex == null) {
            this.goIndex = new BookmarkablePageLink<>("goIndex", CustomerListPage.class); //$NON-NLS-1$
        }
        return this.goIndex;
    }

    @SuppressWarnings("serial")
    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") { //$NON-NLS-1$
                @Override
                public void onSubmit() {
                    DeleteCustomerPage.this.handler.onSubmit();
                }
            };
        }
        return this.submitter;
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = 4582890479480727797L;

        void onSubmit() {
            DeleteCustomerPage.this.customerService.delete(DeleteCustomerPage.this.customer.getId().longValue());
            setResponsePage(CustomerListPage.class);
        }
    }

}
