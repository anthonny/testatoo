/**
 * Copyright (C) 2014 Ovea (dev@ovea.com)
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
package org.testatoo.input

import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.firefox.FirefoxDriver
import org.testatoo.core.Testatoo
import org.testatoo.core.component.*
import org.testatoo.core.component.list.DropDown
import org.testatoo.core.evaluator.webdriver.WebDriverEvaluator
import org.testatoo.core.property.Title
import org.testatoo.core.state.States

import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.input.Key.*
import static org.testatoo.core.input.Mouse.*
import static org.testatoo.core.property.Properties.*
import static org.testatoo.core.state.States.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class MouseTest {

    @BeforeClass
    public static void setup() {
        Testatoo.evaluator = new WebDriverEvaluator(new FirefoxDriver())
        open 'http://localhost:8080/mouse.html'
    }

    @AfterClass
    public static void tearDown() { Testatoo.evaluator.close() }

    @Test
    public void click() {
        Button button = $('#button_1') as Button
        button.should { have text('Button') }
        clickOn button
        button.should { have text('Button Clicked!') }

        CheckBox checkBox = $('#checkbox') as CheckBox
        checkBox.should { be States.unchecked }
        clickOn checkBox
        checkBox.should { be States.checked }

        Radio radio = $('#radio') as Radio
        radio.should { be States.unchecked }
        clickOn radio
        radio.should { be States.checked }

        DropDown dropDown = $('#elements') as DropDown
        dropDown.should { have selectedItems('Helium') }

        clickOn dropDown.items[2]
        dropDown.should { have  selectedItems('Polonium') }
    }

    @Test
    public void doubleClick() {
        Button button = $('#button_2') as Button
        button.should { have text('Button') }
        doubleClickOn button
        button.should { have text('Button Double Clicked!') }
    }

    @Test
    public void rightClick() {
        Button button = $('#button_5') as Button
        button.should { have text('Button') }
        rightClickOn button
        button.should { have text('Button Right Clicked!') }
    }

    @Test
    public void mouseOver() {
        Button button = $('#button_3') as Button
        button.should { have text('Button') }
        hoveringMouseOn button
        button.should { have text('Button Mouse Over!') }
    }

    @Test
    public void mouseOut() {
        Button button = $('#button_4') as Button
        button.should { have text('Button') }

        // To simulate mouse out

        // 1 - mouse over the component
        hoveringMouseOn button
        // 2 - mouse over an another component
        hoveringMouseOn $('#button_5') as Button
        // The mouse out is triggered
        button.should { have text('Button Mouse Out!') }
    }

    @Ignore
    @Test
    public void dragAndDrop() {
        DropPanel dropPanel = $('#droppable') as DropPanel
        dropPanel.should { have title('Drop here') }

        Panel dragPanel = $('#draggable') as Panel

        drag dragPanel on dropPanel
        waitUntil { dropPanel.has(title('Dropped!')) }
    }

    @Test
    public void test_mouse_with_key_modifier() {
        $('#span_Ctrl_mouseleft').should { be States.missing }
        $('#span_Shift_mouseleft').should { be States.missing }

        CTRL.click $('#_Ctrl_mouseleft') as Panel
        SHIFT.click $('#_Shift_mouseleft') as Panel

        $('#span_Ctrl_mouseleft').should { be States.available }
        $('#span_Shift_mouseleft').should { be States.available }

        // Not testable cause Rightclick Handled by the browser
        CTRL.rightClick $('#_Ctrl_mouseright') as Panel
        [CTRL, ALT].rightClick $('#_Ctrl_mouseright') as Panel

        $('#span_Alt_Shift_mouseleft').should { be States.missing }
        (ALT + SHIFT).click $('#_Alt_Shift_mouseleft') as Panel
        $('#span_Alt_Shift_mouseleft').should { be States.available }

        $('#span_Crtl_Shift_mouseleft').should { be States.missing }
        [CTRL, SHIFT].click $('#_Ctrl_Shift_mouseleft') as Panel
        $('#span_Crtl_Shift_mouseleft').should { be States.missing }

        // For code coverage
        [SPACE].click $('#_Ctrl_Shift_mouseleft') as Panel
        // TODO Math why intelliJ warning
        ['data'].click $('#_Ctrl_Shift_mouseleft') as Panel
    }

    class DropPanel extends Panel {
        DropPanel() {
            support Title, { Component c -> c.evaluator.getString("\$('#${id}').find('h1').text()") }
        }
    }
}