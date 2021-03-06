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
package uk.co.q3c.v7.base.useropt;

import org.joda.time.DateTime;

/**
 * 
 * A set of user options. Although not mandatory, typically the option group and option are the simple class name and
 * field of the object (respectively) requiring the option value. <br>
 * <br>
 * The {@link UserOptionStore} into the constructor of the implementation of this interface to enable use of different
 * storage methods. See {@link DefaultUserOption} for an example <br>
 * <br>
 * all the get methods follow the same principle - a default value is supplied by the caller and returned if there is no
 * value for the required option in the store. This means that the default value is set by the caller from a point close
 * to where that value is used.
 * 
 * @author David Sowerby 17 Jul 2013
 * 
 */
public interface UserOption {

	public void setOption(String optionGroup, String option, int value);

	public void setOption(String optionGroup, String option, String value);

	public void setOption(String optionGroup, String option, DateTime value);

	public void setOption(String optionGroup, String option, double value);

	public void setOption(String optionGroup, String option, boolean value);

	public int getOptionAsInt(String optionGroup, String option, int defaultValue);

	public String getOptionAsString(String optionGroup, String option, String defaultValue);

	public DateTime getOptionAsDateTime(String optionGroup, String option, DateTime defaultValue);

	public double getOptionAsDouble(String optionGroup, String option, double defaultValue);

	public boolean getOptionAsBoolean(String optionGroup, String option, boolean defaultValue);

}
