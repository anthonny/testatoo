/**
 * Copyright (C) 2008 Ovea <dev@ovea.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package experimental.dsl

import org.testatoo.config.AbstractTestatooModule
import org.testatoo.config.Scope
import org.testatoo.config.cartridge.TestatooCartridge

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @date 2013-05-01
 */
class MyModule extends AbstractTestatooModule {

    static int seleniumPort = -1;

    @Override
    protected void configure() {

        if (seleniumPort == -1) {
            seleniumPort = findFreePort();
        }

        seleniumServers().registerProvider(createSeleniumServer()
            .port(4444)
            .useSingleWindow(true)
            .build())
            .scope(Scope.TEST_CLASS);

        seleniumSessions()
            .registerProvider(createSeleniumSession()
            .website("http://localhost:8080")
            .browser("*googlechrome")
            .serverHost("localhost")
            .serverPort(4444)
            .build())
            .scope(Scope.TEST_CLASS)
            .withTimeout(5000)
            .inCartridge(TestatooCartridge.HTML4);

        useAnnotations();
    }
}
