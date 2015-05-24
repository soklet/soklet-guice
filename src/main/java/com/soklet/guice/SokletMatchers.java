/*
 * Copyright 2015 Transmogrify LLC.
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

package com.soklet.guice;

import java.lang.reflect.AnnotatedElement;

import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.soklet.web.annotation.DELETE;
import com.soklet.web.annotation.GET;
import com.soklet.web.annotation.HEAD;
import com.soklet.web.annotation.PATCH;
import com.soklet.web.annotation.POST;
import com.soklet.web.annotation.PUT;

/**
 * Convenience {@link com.google.inject.matcher.Matcher}s for use in <a href="http://soklet.com">Soklet</a>
 * applications.
 * 
 * @author <a href="http://revetkn.com">Mark Allen</a>
 * @since 1.0.1
 */
public final class SokletMatchers {
  private static final Matcher<AnnotatedElement> HTTP_METHOD_MATCHER = Matchers.annotatedWith(GET.class)
    .or(Matchers.annotatedWith(POST.class)).or(Matchers.annotatedWith(PUT.class))
    .or(Matchers.annotatedWith(DELETE.class)).or(Matchers.annotatedWith(HEAD.class))
    .or(Matchers.annotatedWith(HEAD.class)).or(Matchers.annotatedWith(PATCH.class));

  private SokletMatchers() {}

  /**
   * A {@link com.google.inject.matcher.Matcher} that matches HTTP-annotated methods (
   * {@link com.soklet.web.annotation.GET}, {@link com.soklet.web.annotation.POST}, etc.)
   * 
   * @return a {@link com.google.inject.matcher.Matcher} that matches HTTP-annotated methods
   */
  public static Matcher<AnnotatedElement> httpMethodMatcher() {
    return HTTP_METHOD_MATCHER;
  }
}
