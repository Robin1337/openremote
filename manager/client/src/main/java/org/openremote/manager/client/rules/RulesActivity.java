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
package org.openremote.manager.client.rules;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import elemental.client.Browser;
import org.openremote.manager.client.event.bus.EventBus;
import org.openremote.manager.client.event.bus.EventRegistration;
import org.openremote.manager.client.mvp.AppActivity;
import org.openremote.manager.client.service.SecurityService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Logger;

public class RulesActivity
        extends AppActivity<RulesPlace>
        implements RulesView.Presenter {

    private static final Logger LOG = Logger.getLogger(RulesActivity.class.getName());

    final RulesView view;
    final PlaceController placeController;
    final EventBus eventBus;
    final SecurityService securityService;

    @Inject
    public RulesActivity(RulesView view,
                         PlaceController placeController,
                         EventBus eventBus,
                         SecurityService securityService) {
        this.view = view;
        this.placeController = placeController;
        this.eventBus = eventBus;
        this.securityService = securityService;
    }

    @Override
    protected AppActivity<RulesPlace> init(RulesPlace place) {
        return this;
    }

    @Override
    public void start(AcceptsOneWidget container, EventBus eventBus, Collection<EventRegistration> registrations) {
        container.setWidget(view.asWidget());
        view.setPresenter(this);
    }

    @Override
    public String getFrameSource() {
        LOG.warning("TODO: Rules iframe handling not implemented for 'localhost', use http://<Your Docker Host>:8084/ instead.");
        return "http://" + Browser.getWindow().getLocation().getHostname() +  ":8084/?logLevel=OFF#shell";
    }
}
