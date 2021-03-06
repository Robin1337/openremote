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
package org.openremote.container.message;

import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.LoggingErrorHandlerBuilder;
import org.apache.camel.impl.DefaultStreamCachingStrategy;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.spi.StreamCachingStrategy;
import org.openremote.container.Container;
import org.openremote.container.ContainerService;
import org.openremote.container.web.DefaultWebsocketComponent;
import org.openremote.container.web.WebService;
import org.openremote.container.web.socket.WebsocketComponent;

import java.util.logging.Logger;

import static org.openremote.container.util.MapAccess.getString;

public class MessageBrokerService implements ContainerService {

    private static final Logger LOG = Logger.getLogger(MessageBrokerService.class.getName());

    public static final String WEBSOCKET_PATH = "/websocket";

    public static final String MESSAGE_SESSION_ALLOWED_ORIGIN = "MESSAGE_SESSION_ALLOWED_ORIGIN";
    public static final String MESSAGE_SESSION_ALLOWED_ORIGIN_DEFAULT = null;

    protected MessageBrokerContext context;
    protected ProducerTemplate producerTemplate;

    @Override
    public void init(Container container) throws Exception {
        context = new MessageBrokerContext();
        producerTemplate = context.createProducerTemplate();

        // TODO make configurable in environment
        context.disableJMX();

        // TODO might need this for errorhandler?
        context.setAllowUseOriginalMessage(false);

        // Don't use JMS, we do our own correlation
        context.setUseBreadcrumb(false);

        // TODO: Wait 5 seconds before forcing a route to stop?
        context.getShutdownStrategy().setTimeout(5);

        context.setStreamCaching(true);
        StreamCachingStrategy streamCachingStrategy = new DefaultStreamCachingStrategy();
        streamCachingStrategy.setSpoolThreshold(524288); // Half megabyte
        context.setStreamCachingStrategy(streamCachingStrategy);

        context.setErrorHandlerBuilder(new LoggingErrorHandlerBuilder() {
            @Override
            public Processor createErrorHandler(RouteContext routeContext, Processor processor) {
                // TODO: Custom error handler?
                return super.createErrorHandler(routeContext, processor);
            }
        });

        context.getRegistry().put(Container.class.getName(), container);

        String allowedOrigin = getString(container.getConfig(), MESSAGE_SESSION_ALLOWED_ORIGIN, MESSAGE_SESSION_ALLOWED_ORIGIN_DEFAULT);
        WebsocketComponent websocketComponent = new DefaultWebsocketComponent(
            container.getService(WebService.class),
            allowedOrigin
        );

        context.addComponent(WebsocketComponent.NAME, websocketComponent);
    }

    @Override
    public void configure(Container container) throws Exception {
        // Start now so route endpoints are added before WebService is configured
        LOG.info("Starting Camel message broker");
        context.start();
    }

    @Override
    public void start(Container container) throws Exception {
    }

    @Override
    public void stop(Container container) throws Exception {
        if (context != null) {
            context.stop();
        }
    }

    public MessageBrokerContext getContext() {
        return context;
    }

    public ProducerTemplate getProducerTemplate() {
        return producerTemplate;
    }
}
