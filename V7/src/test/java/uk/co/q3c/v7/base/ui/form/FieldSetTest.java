/*
 * Copyright (C) 2013 David Sowerby
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.co.q3c.v7.base.ui.form;

import static org.fest.assertions.Assertions.*;

import java.util.Locale;

import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.q3c.v7.base.data.TestEntity;
import uk.co.q3c.v7.i18n.AnnotationI18NTranslator;
import uk.co.q3c.v7.i18n.CurrentLocale;
import uk.co.q3c.v7.i18n.I18NModule;

import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;
import com.vaadin.data.Validator.InvalidValueException;

@RunWith(MycilaJunitRunner.class)
@GuiceContext({ I18NModule.class })
public class FieldSetTest {

	@Inject
	AnnotationI18NTranslator translator;

	@Inject
	CurrentLocale currentLocale;

	TestBeanFieldSet fieldSet;
	TestEntity te, te2;

	@Before
	public void setup() {
		fieldSet = new TestBeanFieldSet(translator);
		te = new TestEntity();
		te.setFirstName("Mango");
		te.setLastName("Chutney");

		te2 = new TestEntity();
		te2.setFirstName("Pickled");
		te2.setLastName("Eggs");
	}

	@Test
	public void setBean() {

		// given

		// when
		fieldSet.setBean(te);
		// then
		assertThat(fieldSet.getFieldGroup()).isNotNull();
		assertThat(fieldSet.getFirstName().getValue()).isEqualTo("Mango");
		assertThat(fieldSet.getLastName().getValue()).isEqualTo("Chutney");
	}

	@Test
	public void setBeanTwice() {

		// given
		fieldSet.setBean(te);
		// when
		fieldSet.setBean(te2);
		// then
		assertThat(fieldSet.getFieldGroup()).isNotNull();
		assertThat(fieldSet.getFirstName().getValue()).isEqualTo("Pickled");
		assertThat(fieldSet.getLastName().getValue()).isEqualTo("Eggs");
	}

	@Test
	public void i18N_default() {

		// given

		// when
		fieldSet.setBean(te);
		// then
		assertThat(fieldSet.getFirstName().getCaption()).isEqualTo("First Name");
		assertThat(fieldSet.getLastName().getCaption()).isEqualTo("Last Name");
		assertThat(fieldSet.getLastName().getDescription()).isEqualTo("the last name or family name");

	}

	@Test
	public void i18N_de() {

		// given
		fieldSet.setBean(te);
		currentLocale.addListener(fieldSet);
		// when
		currentLocale.setLocale(Locale.GERMAN);
		// then
		assertThat(fieldSet.getFirstName().getCaption()).isEqualTo("Vorname");
		assertThat(fieldSet.getLastName().getCaption()).isEqualTo("Nachname");
		assertThat(fieldSet.getLastName().getDescription()).isEqualTo("die Nachname oder der Familienname");

	}

	@Test(expected = InvalidValueException.class)
	public void validationFailure() {

		// given
		te.setFirstName("P");
		// when
		fieldSet.setBean(te);
		// then
		fieldSet.getFirstName().validate();

	}

}
