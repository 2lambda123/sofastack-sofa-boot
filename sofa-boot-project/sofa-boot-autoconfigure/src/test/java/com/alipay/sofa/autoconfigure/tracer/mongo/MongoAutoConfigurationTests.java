/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.autoconfigure.tracer.mongo;

import com.alipay.sofa.boot.autoconfigure.tracer.mongo.MongoAutoConfiguration;
import com.alipay.sofa.boot.tracer.mongodb.SofaTracerCommandListenerCustomizer;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MongoAutoConfiguration}.
 *
 * @author huzijie
 * @version MongoAutoConfigurationTests.java, v 0.1 2023年01月11日 10:38 AM huzijie Exp $
 */
public class MongoAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                                                             .withConfiguration(AutoConfigurations
                                                                 .of(MongoAutoConfiguration.class));

    @Test
    public void registerMongoBean() {
        this.contextRunner
                .run((context) -> assertThat(context)
                        .hasSingleBean(SofaTracerCommandListenerCustomizer.class));
    }

    @Test
    public void noMongoBeanWhenMongoClientClassNotExist() {
        this.contextRunner.withClassLoader(new FilteredClassLoader(MongoClient.class))
                .run((context) -> assertThat(context)
                        .doesNotHaveBean(SofaTracerCommandListenerCustomizer.class));
    }

    @Test
    public void noMongoBeanWhenSofaTracerCommandListenerCustomizerClassNotExist() {
        this.contextRunner.withClassLoader(new FilteredClassLoader(SofaTracerCommandListenerCustomizer.class))
                .run((context) -> assertThat(context)
                        .doesNotHaveBean(SofaTracerCommandListenerCustomizer.class));
    }

    @Test
    public void noMongoBeanWhenPropertySetFalse() {
        this.contextRunner
                .withPropertyValues("sofa.boot.tracer.mongodb.enabled=false")
                .run((context) -> assertThat(context)
                        .doesNotHaveBean(SofaTracerCommandListenerCustomizer.class));
    }
}