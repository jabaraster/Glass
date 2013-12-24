/**
 * 
 */
package com.jabaraster.glass.web.ui.page;

import jabara.wicket.ComponentCssHeaderItem;
import jabara.wicket.Models;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;

import com.jabaraster.glass.web.ui.component.GoTopLink;

/**
 * @author jabaraster
 */
public class PostLookPage extends WebPageBase {
    private static final long serialVersionUID = -3092155077299050388L;

    private GoTopLink         goTopLink;

    /**
     * 
     */
    public PostLookPage() {
        this.add(getGoTopLink());
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(PostLookPage.class));
    }

    /**
     * @see com.jabaraster.glass.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Models.readOnly("スタッフが見た車番を登録"); //$NON-NLS-1$
    }

    private GoTopLink getGoTopLink() {
        if (this.goTopLink == null) {
            this.goTopLink = new GoTopLink("goTop"); //$NON-NLS-1$
        }
        return this.goTopLink;
    }

}
