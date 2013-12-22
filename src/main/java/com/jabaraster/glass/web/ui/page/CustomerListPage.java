/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.general.Empty;
import jabara.general.IProducer2;
import jabara.general.Sort;
import jabara.jpa.entity.EntityBase_;
import jabara.wicket.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.jabaraster.glass.entity.ECarNumber;
import com.jabaraster.glass.entity.ECustomer;
import com.jabaraster.glass.entity.ECustomer_;
import com.jabaraster.glass.service.ICustomerService;
import com.jabaraster.glass.web.ui.component.AttributeColumn;
import com.jabaraster.glass.web.ui.component.DateTimeColumn;
import com.jabaraster.glass.web.ui.component.DeleteLinkColumn;
import com.jabaraster.glass.web.ui.component.EditLinkColumn;

/**
 * @author jabaraster
 */
public class CustomerListPage extends WebPageBase {
    private static final long                               serialVersionUID = -3360337825317293285L;

    @Inject
    private ICustomerService                                customerService;

    private Link<?>                                         adder;
    private AjaxFallbackDefaultDataTable<ECustomer, String> customers;

    /**
     * 
     */
    public CustomerListPage() {
        this.add(getAdder());
        this.add(getCustomers());
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly("お客様情報一覧"); //$NON-NLS-1$
    }

    private Link<?> getAdder() {
        if (this.adder == null) {
            this.adder = new BookmarkablePageLink<>("adder", NewCustomerPage.class); //$NON-NLS-1$
        }
        return this.adder;
    }

    private AjaxFallbackDefaultDataTable<ECustomer, String> getCustomers() {
        if (this.customers == null) {
            final List<IColumn<ECustomer, String>> columns = new ArrayList<>();
            columns.add(new AttributeColumn<ECustomer>(ECustomer.getMeta(), EntityBase_.id));
            columns.add(new AttributeColumn<ECustomer>(ECustomer.getMeta(), ECustomer_.name));
            columns.add(new CarNumberColumn(Models.readOnly("車番"))); //$NON-NLS-1$
            columns.add(new DateTimeColumn<ECustomer>(ECustomer.getMeta(), EntityBase_.created));
            columns.add(new DateTimeColumn<ECustomer>(ECustomer.getMeta(), EntityBase_.updated));

            @SuppressWarnings("serial")
            final IProducer2<ECustomer, PageParameters> p = new IProducer2<ECustomer, PageParameters>() {
                @SuppressWarnings("boxing")
                @Override
                public PageParameters produce(final ECustomer pArgument) {
                    final PageParameters ret = new PageParameters();
                    ret.set(0, pArgument.getId().longValue());
                    return ret;
                }
            };
            columns.add(new EditLinkColumn<>(Models.readOnly("編集"), UpdateCustomerPage.class, p)); //$NON-NLS-1$
            columns.add(new DeleteLinkColumn<>(Models.readOnly("削除"), DeleteCustomerPage.class, p)); //$NON-NLS-1$
            this.customers = new AjaxFallbackDefaultDataTable<>( //
                    "customers" // //$NON-NLS-1$
                    , columns //
                    , new CustomerProvider() //
                    , 20);
        }
        return this.customers;
    }

    private static class CarNumberColumn extends AbstractColumn<ECustomer, String> {
        private static final long serialVersionUID = 2427582161917555589L;

        public CarNumberColumn(final IModel<String> pDisplayModel) {
            super(pDisplayModel);
        }

        @Override
        public void populateItem(final Item<ICellPopulator<ECustomer>> pCellItem, final String pComponentId, final IModel<ECustomer> pRowModel) {
            final ECarNumber carNumber = pRowModel.getObject().getCarNumber();
            final String s = carNumber == null ? Empty.STRING : carNumber.getDisplayString();
            pCellItem.add(new Label(pComponentId, s));
        }

    }

    private class CustomerProvider extends SortableDataProvider<ECustomer, String> {
        private static final long serialVersionUID = 5157752002123792811L;

        CustomerProvider() {
            this.setSort(EntityBase_.id.getName(), SortOrder.ASCENDING);
        }

        @Override
        public Iterator<? extends ECustomer> iterator(final long pFirst, final long pCount) {
            final SortParam<String> so = getSort();
            final Sort sort = so.isAscending() ? Sort.asc(so.getProperty()) : Sort.desc(so.getProperty());

            if (pFirst > Integer.MAX_VALUE) {
                throw new IllegalStateException();
            }
            if (pCount > Integer.MAX_VALUE) {
                throw new IllegalStateException();
            }
            return CustomerListPage.this.customerService.get(sort, (int) pFirst, (int) pCount).iterator();
        }

        @Override
        public IModel<ECustomer> model(final ECustomer pObject) {
            return Models.of(pObject);
        }

        @Override
        public long size() {
            return CustomerListPage.this.customerService.countAll();
        }
    }

}
