/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.validation.client.constraints;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link jakarta.validation.constraints.DecimalMax} constraint validator
 * implementation for a {@link String}.
 */
public class DecimalMaxValidatorForString extends
    AbstractDecimalMaxValidator<String> {

  @Override
  public final boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    BigDecimal bigValue;
    try {
      bigValue = new BigDecimal(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return isValid(bigValue);
  }
}
