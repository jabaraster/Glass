package com.jabaraster.glass.web.ui.page;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 *
 */
@SuppressWarnings("synthetic-access")
public class TopPage extends WebPageBase {
    private static final long serialVersionUID = -4965903336608758671L;

    /**
     * 
     */
    public TopPage() {
        add(new BookmarkablePageLink<>("goCustomerList", CustomerListPage.class)); //$NON-NLS-1$
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("Top"); //$NON-NLS-1$
    }
}
