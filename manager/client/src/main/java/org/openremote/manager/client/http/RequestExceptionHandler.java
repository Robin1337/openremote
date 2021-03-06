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
package org.openremote.manager.client.http;

import org.openremote.manager.client.Environment;
import org.openremote.manager.client.event.bus.EventBus;
import org.openremote.manager.client.i18n.ManagerMessages;
import org.openremote.Consumer;
import org.openremote.manager.shared.event.ui.ShowFailureEvent;
import org.openremote.manager.shared.http.*;
import org.openremote.manager.shared.validation.ConstraintViolation;

public interface RequestExceptionHandler {

    static void handleRequestException(RequestException ex,
                                       Environment environment) {
        handleRequestException(ex, environment.getEventBus(), environment.getMessages());
    }

    static void handleRequestException(RequestException ex,
                                       EventBus eventBus,
                                       ManagerMessages managerMessages) {
        String msg = ex.getMessage();
        if (ex instanceof NoResponseException) {
            msg = managerMessages.noResponseFromServer();
        } else if (ex instanceof UnexpectedStatusRequestException) {
            UnexpectedStatusRequestException unexpectedEx = (UnexpectedStatusRequestException) ex;
            msg = managerMessages.unexpectedResponseStatus(unexpectedEx.getStatusCode(), unexpectedEx.getExpectedStatusCode());
        } else if (ex instanceof BadRequestException) {
            msg = managerMessages.badRequest();
        } else if (ex instanceof ConflictRequestException) {
            msg = managerMessages.conflictRequest();
        } else if (ex instanceof EntityMarshallingRequestException) {
            EntityMarshallingRequestException marshallingEx = (EntityMarshallingRequestException) ex;
            msg = managerMessages.errorMarshallingResponse(
                marshallingEx.getCause() != null ? marshallingEx.getCause().getMessage() : managerMessages.unknownError()
            );
        }

        eventBus.dispatch(new ShowFailureEvent(managerMessages.requestFailed(msg), 10000));
    }

    static void handleRequestException(RequestException ex,
                                       Environment environment,
                                       Consumer<ConstraintViolation[]> constraintViolationHandler) {
        handleRequestException(ex, environment.getEventBus(), environment.getMessages(), constraintViolationHandler);
    }

    static void handleRequestException(RequestException ex,
                                       EventBus eventBus,
                                       ManagerMessages managerMessages,
                                       Consumer<ConstraintViolation[]> constraintViolationHandler) {
        if (ex instanceof BadRequestException) {
            BadRequestException badRequestException = (BadRequestException) ex;
            if (badRequestException.getConstraintViolationReport() != null
                && badRequestException.getConstraintViolationReport().hasViolations()
                && constraintViolationHandler != null) {
                constraintViolationHandler.accept(
                    badRequestException.getConstraintViolationReport().getAllViolations()
                );
            } else {
                handleRequestException(ex, eventBus, managerMessages);
            }
        } else {
            handleRequestException(ex, eventBus, managerMessages);
        }
    }
}