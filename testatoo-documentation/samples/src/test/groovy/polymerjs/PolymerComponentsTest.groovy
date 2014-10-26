/**
 * Copyright (C) 2013 Ovea (dev@ovea.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package polymerjs

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.firefox.FirefoxDriver
import org.testatoo.core.Testatoo
import org.testatoo.core.evaluator.webdriver.WebDriverEvaluator

import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.input.Mouse.clickOn
import static org.testatoo.core.property.Properties.title
import static org.testatoo.core.state.States.getSelected
import static org.testatoo.core.state.States.getUnSelected

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@Ignore // TODO fix why it fail on CI
@RunWith(JUnit4)
class PolymerComponentsTest {

    @BeforeClass
    public static void setup() {
        Testatoo.evaluator =  new WebDriverEvaluator(new FirefoxDriver())
        evaluator.registerScripts(this.getClass().getResourceAsStream('/polymerjs/custom.js').text)
        open 'http://localhost:8080/polymerjs/index.html'
    }

    @AfterClass public static void tearDown() { evaluator.close() }

    @Test
    public void test_tab_panel() {
        TabPanel tab_panel = $('#myTab') as TabPanel

        expect tab_panel has 3.tabs
        expect tab_panel.tabs[0] has title('Home')
        expect tab_panel.tabs[1] has title('Profile')
        expect tab_panel.tabs[2] has title('Options')

        clickOn tab_panel.tabs[1]

        expect tab_panel.tabs[0] is unSelected
        expect tab_panel.tabs[1] is selected
        expect tab_panel.tabs[2] is unSelected

        clickOn tab_panel.tabs[2]

        expect tab_panel.tabs[0] is unSelected
        expect tab_panel.tabs[1] is unSelected
        expect tab_panel.tabs[2] is selected
    }
}
