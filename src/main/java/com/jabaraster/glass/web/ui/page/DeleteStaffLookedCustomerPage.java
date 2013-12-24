/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.general.ArgUtil;
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

import com.jabaraster.glass.entity.EStaffLookedCustomer;
import com.jabaraster.glass.service.IStaffLookedCustomerService;
import com.jabaraster.glass.web.ui.WicketApplication;

/**
 * @author jabaraster
 */
public class DeleteStaffLookedCustomerPage extends WebPageBase {
    private static final long          serialVersionUID = 5462426550236093968L;

    @Inject
    IStaffLookedCustomerService        staffLookedCustomerService;

    private final EStaffLookedCustomer staffCustomer;

    private final Handler              handler          = new Handler(this);

    private Link<?>                    goIndex;
    private Label                      staffName;
    private Form<?>                    form;
    private Button                     submitter;

    /**
     * @param pParameters -
     */
    public DeleteStaffLookedCustomerPage(final PageParameters pParameters) {
        super(pParameters);

        try {
            final String ss = pParameters.get(0).toString();
            final String sn = pParameters.get(1).toString();
            this.staffCustomer = this.staffLookedCustomerService.findByCondition(ss, sn);
            this.add(getStaffName());
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
        return Models.readOnly("スタッフ情報を削除"); //$NON-NLS-1$
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

    private Label getStaffName() {
        if (this.staffName == null) {
            this.staffName = new Label("staffName", this.staffCustomer.getStaffName()); //$NON-NLS-1$
        }
        return this.staffName;
    }

    @SuppressWarnings("serial")
    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") { //$NON-NLS-1$
                @Override
                public void onSubmit() {
                    DeleteStaffLookedCustomerPage.this.handler.onSubmit();
                }
            };
        }
        return this.submitter;
    }

    /**
     * @param pSsDescriptor -
     * @param pStaffName -
     * @return -
     */
    public static PageParameters createParameters(final String pSsDescriptor, final String pStaffName) {
        ArgUtil.checkNullOrEmpty(pSsDescriptor, "pSsDescriptor"); //$NON-NLS-1$
        ArgUtil.checkNull(pStaffName, "pStaffName"); //$NON-NLS-1$

        final PageParameters ret = new PageParameters();
        ret.set(0, pSsDescriptor);
        ret.set(1, pStaffName);
        return ret;

    }

    private static class Handler implements Serializable {
        private static final long     serialVersionUID = 4582890479480727797L;

        DeleteStaffLookedCustomerPage page;

        Handler(final DeleteStaffLookedCustomerPage pP) {
            this.page = pP;
        }

        void onSubmit() {
            this.page.staffLookedCustomerService.delete(this.page.staffCustomer.getId().longValue());
            this.page.setResponsePage(StaffLookedCustomerListPage.class);
        }
    }

}
