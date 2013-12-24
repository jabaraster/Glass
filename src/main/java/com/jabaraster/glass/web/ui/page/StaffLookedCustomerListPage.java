/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.general.IProducer2;
import jabara.wicket.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.jabaraster.glass.model.StaffLookedCustomer;
import com.jabaraster.glass.service.IStaffLookedCustomerService;
import com.jabaraster.glass.web.ui.component.DeleteLinkColumn;
import com.jabaraster.glass.web.ui.component.GoTopLink;

/**
 * @author jabaraster
 */
public class StaffLookedCustomerListPage extends WebPageBase {
    private static final long                                         serialVersionUID      = -2694715736785122899L;

    /**
     * 
     */
    public static final String                                        DEFAULT_SS_DESCRIPTOR = "SS01";               //$NON-NLS-1$

    @Inject
    IStaffLookedCustomerService                                       staffLookedCustomerService;

    private GoTopLink                                                 goTopLink;
    private AjaxFallbackDefaultDataTable<StaffLookedCustomer, String> list;

    /**
     * 
     */
    public StaffLookedCustomerListPage() {
        this.add(getGoTopLink());
        this.add(getList());
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly("スタッフが見たお客様情報一覧"); //$NON-NLS-1$
    }

    private GoTopLink getGoTopLink() {
        if (this.goTopLink == null) {
            this.goTopLink = new GoTopLink("goTop"); //$NON-NLS-1$
        }
        return this.goTopLink;
    }

    @SuppressWarnings({ "nls", "serial" })
    private AjaxFallbackDefaultDataTable<StaffLookedCustomer, String> getList() {
        if (this.list == null) {
            final List<IColumn<StaffLookedCustomer, String>> columns = new ArrayList<>();

            columns.add(new LabelColumn("SS", new IProducer2<StaffLookedCustomer, String>() {
                @Override
                public String produce(final StaffLookedCustomer pArgument) {
                    return pArgument.getSsDescriptor();
                }
            }));
            columns.add(new LabelColumn("スタッフ名", new IProducer2<StaffLookedCustomer, String>() {
                @Override
                public String produce(final StaffLookedCustomer pArgument) {
                    return pArgument.getStaffName();
                }
            }));
            columns.add(new LabelColumn("お客様情報", new IProducer2<StaffLookedCustomer, String>() {
                @Override
                public String produce(final StaffLookedCustomer pArgument) {
                    return pArgument.getCustomer().getName();
                }
            }));
            columns.add(new LabelColumn("車番", new IProducer2<StaffLookedCustomer, String>() {
                @Override
                public String produce(final StaffLookedCustomer pArgument) {
                    return pArgument.getCarNumber().getDisplayString();
                }
            }));

            final IProducer2<StaffLookedCustomer, PageParameters> p = new IProducer2<StaffLookedCustomer, PageParameters>() {
                @Override
                public PageParameters produce(final StaffLookedCustomer pArgument) {
                    return DeleteStaffLookedCustomerPage.createParameters(pArgument.getSsDescriptor(), pArgument.getStaffName());
                }
            };
            columns.add(new DeleteLinkColumn<>(Models.readOnly("削除"), DeleteStaffLookedCustomerPage.class, p)); //$NON-NLS-1$

            final Provider provider = new Provider();
            this.list = new AjaxFallbackDefaultDataTable<>("list", columns, provider, 20); //$NON-NLS-1$
        }
        return this.list;
    }

    private static class LabelColumn extends AbstractColumn<StaffLookedCustomer, String> {
        private static final long                             serialVersionUID = 8538110082622080428L;

        private final IProducer2<StaffLookedCustomer, String> producer;

        /**
         * @param pDisplay -
         * @param pProducer -
         */
        public LabelColumn(final String pDisplay, final IProducer2<StaffLookedCustomer, String> pProducer) {
            super(Models.readOnly(pDisplay));
            this.producer = pProducer;
        }

        @Override
        public void populateItem(final Item<ICellPopulator<StaffLookedCustomer>> pCellItem, final String pComponentId,
                final IModel<StaffLookedCustomer> pRowModel) {
            pCellItem.add(new Label(pComponentId, this.producer.produce(pRowModel.getObject())));
        }
    }

    private class Provider extends SortableDataProvider<StaffLookedCustomer, String> {
        private static final long serialVersionUID = 7486547392084318929L;

        @Override
        public Iterator<? extends StaffLookedCustomer> iterator(final long pFirst, final long pCount) {
            if (pFirst > Integer.MAX_VALUE) {
                throw new IllegalStateException();
            }
            if (pCount > Integer.MAX_VALUE) {
                throw new IllegalStateException();
            }
            return StaffLookedCustomerListPage.this.staffLookedCustomerService.getLookedCustomers(DEFAULT_SS_DESCRIPTOR, null).iterator();
        }

        @Override
        public IModel<StaffLookedCustomer> model(final StaffLookedCustomer pObject) {
            return Models.of(pObject);
        }

        @Override
        public long size() {
            return StaffLookedCustomerListPage.this.staffLookedCustomerService.getLookedCustomers(DEFAULT_SS_DESCRIPTOR, null).size();
        }
    }
}
