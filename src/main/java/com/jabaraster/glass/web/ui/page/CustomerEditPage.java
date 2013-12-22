/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.general.NotFound;
import jabara.wicket.CssUtil;

import javax.inject.Inject;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.service.ICustomerService;
import com.jabaraster.glass.web.ui.WicketApplication;

/**
 * @author jabaraster
 */
public abstract class CustomerEditPage extends WebPageBase {
    private static final long  serialVersionUID = 1652464977430060704L;

    /**
     * 
     */
    @Inject
    protected ICustomerService customerService;

    /**
     * 
     */
    protected final ECustomer  customer;
    /**
     * 
     */
    protected final ECarNumber carNumber;

    private Link<?>            goIndex;

    private Form<?>            form;
    private FeedbackPanel      feedback;
    private TextField<String>  name;
    private TextField<String>  department;
    private TextField<String>  classification;
    private TextField<String>  category;
    private TextField<String>  number;

    private AjaxButton         submitter;

    /**
     * 
     */
    public CustomerEditPage() {
        this.customer = new ECustomer();
        this.carNumber = new ECarNumber();
        initialize();
    }

    /**
     * @param pParameters -
     */
    public CustomerEditPage(final PageParameters pParameters) {
        super(pParameters);

        try {
            final long id = Long.parseLong(pParameters.get(0).toString());
            this.customer = this.customerService.findById(id);
            this.carNumber = this.customer.getCarNumber() == null ? new ECarNumber() : this.customer.getCarNumber();
            initialize();
        } catch (NumberFormatException | NotFound e) {
            throw new RestartResponseAtInterceptPageException(WicketApplication.get().getHomePage());
        }
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        CssUtil.addComponentCssReference(pResponse, CustomerEditPage.class);
    }

    /**
     * @param pTarget
     */
    protected abstract void onSubmit(AjaxRequestTarget pTarget);

    private TextField<String> getCategory() {
        if (this.category == null) {
            final String NAME = "category"; //$NON-NLS-1$
            this.category = createTextField(NAME, this.carNumber, NAME);
        }
        return this.category;
    }

    private TextField<String> getClassification() {
        if (this.classification == null) {
            final String NAME = "classification"; //$NON-NLS-1$
            this.classification = createTextField(NAME, this.carNumber, NAME);
        }
        return this.classification;
    }

    private TextField<String> getDepartment() {
        if (this.department == null) {
            final String NAME = "department"; //$NON-NLS-1$
            this.department = createTextField(NAME, this.carNumber, NAME);
        }
        return this.department;
    }

    private FeedbackPanel getFeedback() {
        if (this.feedback == null) {
            this.feedback = new FeedbackPanel("feedback"); //$NON-NLS-1$
        }
        return this.feedback;
    }

    private Form<?> getForm() {
        if (this.form == null) {
            this.form = new Form<>("form"); //$NON-NLS-1$
            this.form.add(getName());
            this.form.add(getDepartment());
            this.form.add(getClassification());
            this.form.add(getCategory());
            this.form.add(getNumber());
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

    private TextField<String> getName() {
        if (this.name == null) {
            final String NAME = "name"; //$NON-NLS-1$
            this.name = createTextField(NAME, this.customer, NAME);
            this.name.setRequired(true);
        }
        return this.name;
    }

    private TextField<String> getNumber() {
        if (this.number == null) {
            final String NAME = "number"; //$NON-NLS-1$
            this.number = createTextField(NAME, this.carNumber, NAME);
        }
        return this.number;
    }

    @SuppressWarnings({ "nls", "serial" })
    private AjaxButton getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new IndicatingAjaxButton("submitter") {
                @Override
                protected void onError(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    pTarget.add(getFeedback());
                }

                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    pTarget.add(getFeedback());
                    CustomerEditPage.this.onSubmit(pTarget);
                }
            };
        }
        return this.submitter;
    }

    private void initialize() {
        this.add(getGoIndex());
        this.add(getFeedback());
        this.add(getForm());
    }

    private static TextField<String> createTextField(final String pId, final Object pBean, final String pPropertName) {
        return new TextField<>(pId, new PropertyModel<String>(pBean, pPropertName), String.class);
    }
}
