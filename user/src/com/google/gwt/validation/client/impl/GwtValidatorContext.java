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
package com.google.gwt.validation.client.impl;

import com.google.gwt.validation.client.AbstractGwtValidatorFactory;

import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.TraversableResolver;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorContext;
import jakarta.validation.valueextraction.ValueExtractor;
import jakarta.validation.ParameterNameProvider;
import jakarta.validation.ClockProvider;


/**
 * GWT {@link ValidatorContext}.
 */
public final class GwtValidatorContext implements ValidatorContext {

  private final AbstractGwtValidatorFactory validatorFactory;

  private final ConstraintValidatorFactory factoryConstraintValidatorfactory;
  private final MessageInterpolator factoryMessageInterpolator;
  private final TraversableResolver factoryTraversableResolver;

  private ConstraintValidatorFactory constraintValidatorfactory = null;
  private MessageInterpolator messageInterpolator = null;
  private TraversableResolver traversableResolver = null;
  private ClockProvider clockProvider;
  private ParameterNameProvider parameterNameProvider;

  public GwtValidatorContext(AbstractGwtValidatorFactory validatorFactory) {
    this.validatorFactory = validatorFactory;

    factoryConstraintValidatorfactory = validatorFactory
        .getConstraintValidatorFactory();
    constraintValidatorfactory = validatorFactory
        .getConstraintValidatorFactory();

    factoryMessageInterpolator = validatorFactory.getMessageInterpolator();
    messageInterpolator = validatorFactory.getMessageInterpolator();

    factoryTraversableResolver = validatorFactory.getTraversableResolver();
    traversableResolver = validatorFactory.getTraversableResolver();
  }

  @Override
  public ValidatorContext constraintValidatorFactory(
      ConstraintValidatorFactory constraintValidatorfactory) {
    if (constraintValidatorfactory == null) {
      this.constraintValidatorfactory = factoryConstraintValidatorfactory;
    } else {
      this.constraintValidatorfactory = constraintValidatorfactory;
    }
    return this;
  }

  @Override
  public Validator getValidator() {
    AbstractGwtValidator validator = validatorFactory.createValidator();
    validator.init(constraintValidatorfactory, messageInterpolator,
        traversableResolver);
    return validator;
  }

  @Override
  public ValidatorContext messageInterpolator(
      MessageInterpolator messageInterpolator) {
    if (messageInterpolator == null) {
      this.messageInterpolator = factoryMessageInterpolator;
    } else {
      this.messageInterpolator = messageInterpolator;
    }
    return this;
  }

  @Override
  public ValidatorContext traversableResolver(
      TraversableResolver traversableResolver) {
    if (traversableResolver == null) {
      this.traversableResolver = factoryTraversableResolver;
    } else {
      this.traversableResolver = traversableResolver;
    }
    return this;
  }

  @Override
  public ValidatorContext addValueExtractor(ValueExtractor<?> extractor) {
    return this;
  }

  @Override
  public ValidatorContext clockProvider(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
    return this;
  }

  @Override
  public ValidatorContext parameterNameProvider(ParameterNameProvider parameterNameProvider) {
    this.parameterNameProvider = parameterNameProvider;
    return this;
  }
}
