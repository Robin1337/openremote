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
package org.openremote.manager.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import org.openremote.manager.client.admin.overview.AdminOverviewPlace;
import org.openremote.manager.client.admin.tenant.AdminTenantPlace;
import org.openremote.manager.client.admin.tenant.AdminTenantsPlace;
import org.openremote.manager.client.admin.users.AdminUserPlace;
import org.openremote.manager.client.admin.users.AdminUsersPlace;
import org.openremote.manager.client.assets.AssetsDashboardPlace;
import org.openremote.manager.client.assets.asset.AssetPlace;
import org.openremote.manager.client.flows.FlowsPlace;
import org.openremote.manager.client.map.MapPlace;
import org.openremote.manager.client.rules.RulesPlace;
import org.openremote.manager.client.user.UserAccountPlace;

@WithTokenizers(
    { // You might have to restart SuperDevMode after changing tokenizers
        MapPlace.Tokenizer.class,
        AssetsDashboardPlace.Tokenizer.class,
        AssetPlace.Tokenizer.class,
        RulesPlace.Tokenizer.class,
        FlowsPlace.Tokenizer.class,
        AdminOverviewPlace.Tokenizer.class,
        AdminTenantsPlace.Tokenizer.class,
        AdminTenantPlace.Tokenizer.class,
        AdminUsersPlace.Tokenizer.class,
        AdminUserPlace.Tokenizer.class,
        UserAccountPlace.Tokenizer.class
    }
)
public interface ManagerHistoryMapper extends PlaceHistoryMapper {
}
