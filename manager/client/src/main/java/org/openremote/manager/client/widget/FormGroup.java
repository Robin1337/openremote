/*
 * Copyright 2016, OpenRemote Inc.
 *
 * See the CONTRIBUTORS.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.manager.client.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.ui.*;

/**
 * A form has form groups, each group is a combination of label,
 * control field, etc. typically rendered in a single line/block.
 */
public class FormGroup extends FlowPanel implements HasWidgets {

    protected FlowPanel labelPanel = new FlowPanel();
    protected FormLabel formLabel;
    protected FormField formField;
    protected FormGroupActions formGroupActions;
    protected Label infoLabel;

    public FormGroup() {
        getElement().addClassName("layout horizontal center or-FormGroup");
        labelPanel.setStyleName("layout vertical");
        add(labelPanel);
    }

    @UiChild(tagname = "label", limit = 1)
    public void addFormLabel(FormLabel formLabel) {
        if (this.formLabel != null)
            throw new IllegalStateException("Form label already set");
        this.formLabel = formLabel;
        labelPanel.add(formLabel);
    }

    @UiChild(tagname = "field", limit = 1)
    public void addFormField(FormField formField) {
        if (this.formField != null)
            throw new IllegalStateException("Form field already set");
        this.formField = formField;
        add(formField);

        if (this.formLabel != null) {
            formField.setFormFieldId(this.formLabel.getFormFieldId());
        }
    }

    @UiChild(tagname = "actions", limit = 1)
    public void addFormGroupActions(FormGroupActions formGroupActions) {
        this.formGroupActions = formGroupActions;
        add(formGroupActions);
    }

    @UiChild(tagname = "info", limit = 1)
    public void addInfolabel(Label infoLabel) {
        if (this.infoLabel != null)
            throw new IllegalStateException("Form info label already set");
        this.infoLabel = infoLabel;
        infoLabel.setStyleName("or-FormInfoLabel");
        labelPanel.add(infoLabel);
    }

    public FormLabel getFormLabel() {
        return formLabel;
    }

    public FormField getFormField() {
        return formField;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Style getStyle() {
        return getElement().getStyle();
    }

    public void setError(boolean error) {
        getElement().removeClassName("error");
        if (error) {
            getElement().addClassName("error");
        }
    }

    public void setAlignStart(boolean alignStart) {
        if (alignStart) {
            removeStyleName("center");
            addStyleName("start");
        } else {
            removeStyleName("start");
            addStyleName("center");
        }
    }

    public void setOpaque(boolean opaque) {
        getElement().removeClassName("opaque");
        if (opaque) {
            getElement().addClassName("opaque");
        }
    }
}
