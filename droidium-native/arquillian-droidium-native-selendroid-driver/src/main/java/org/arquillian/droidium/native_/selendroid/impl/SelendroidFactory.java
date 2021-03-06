/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.droidium.native_.selendroid.impl;

import java.lang.annotation.Annotation;
import java.util.logging.Logger;

import io.selendroid.SelendroidDriver;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Place where SelendroidDriver is configured, created and destroyed.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class SelendroidFactory extends AbstractSelendroidFactory implements
    Configurator<SelendroidDriver, SelendroidConfiguration>,
    Instantiator<SelendroidDriver, SelendroidConfiguration>,
    Destructor<SelendroidDriver> {

    private static final Logger logger = Logger.getLogger(SelendroidFactory.class.getName());

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public void destroyInstance(SelendroidDriver instance) {
        instance.quit();
    }

    @Override
    public SelendroidDriver createInstance(SelendroidConfiguration configuration) {
        SelendroidDriver driver = null;
        try {
            driver = new SelendroidDriver(configuration.getRemoteAddress(), new DesiredCapabilities());
        } catch (Exception e) {
            logger.info("Unable to create an instance of SelendroidDriver: " + e.getMessage());
            e.printStackTrace();
        }
        return driver;
    }

    @Override
    public SelendroidConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {
        return new SelendroidConfiguration().configure(descriptor, qualifier);
    }

}
