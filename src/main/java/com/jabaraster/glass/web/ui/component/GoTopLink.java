/**
 * 
 */
package com.jabaraster.glass.web.ui.component;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.jabaraster.glass.web.ui.page.TopPage;

/**
 * @author jabaraster
 */
public class GoTopLink extends Panel {
    private static final long serialVersionUID = 462571181336907335L;

    /**
     * @param pId -
     */
    public GoTopLink(final String pId) {
        super(pId);
        this.add(new BookmarkablePageLink<>("goTop", TopPage.class)); //$NON-NLS-1$
    }
}
