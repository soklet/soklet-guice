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

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.soklet.converter.ValueConverterRegistry;
import com.soklet.util.InstanceProvider;
import com.soklet.web.exception.DefaultExceptionStatusMapper;
import com.soklet.web.exception.ExceptionStatusMapper;
import com.soklet.web.request.DefaultRequestHandler;
import com.soklet.web.request.RequestContext;
import com.soklet.web.request.RequestHandler;
import com.soklet.web.response.DefaultResponseHandler;
import com.soklet.web.response.ResponseHandler;
import com.soklet.web.response.writer.ApiResponseWriter;
import com.soklet.web.response.writer.BinaryResponseWriter;
import com.soklet.web.response.writer.DefaultApiResponseWriter;
import com.soklet.web.response.writer.DefaultBinaryResponseWriter;
import com.soklet.web.response.writer.DefaultPageResponseWriter;
import com.soklet.web.response.writer.DefaultRedirectResponseWriter;
import com.soklet.web.response.writer.PageResponseWriter;
import com.soklet.web.response.writer.RedirectResponseWriter;
import com.soklet.web.routing.DefaultRouteMatcher;
import com.soklet.web.routing.RouteMatcher;

/**
 * A <a href="https://github.com/google/guice">Guice</a> module that provides default implementations of <a
 * href="http://soklet.com">Soklet</a> interfaces.
 * 
 * @author <a href="http://revetkn.com">Mark Allen</a>
 * @since 1.0.0
 */
public class SokletModule extends AbstractModule {
  @Override
  protected void configure() {}

  /**
   * Provides a default {@link RouteMatcher}.
   * 
   * @return a {@link RouteMatcher}
   */
  @Provides
  @Singleton
  public RouteMatcher provideRouteMatcher() {
    return new DefaultRouteMatcher();
  }

  /**
   * Provides a default {@link ResponseHandler}.
   * 
   * @return a {@link ResponseHandler}
   */
  @Inject
  @Provides
  @Singleton
  public ResponseHandler provideResponseHandler(PageResponseWriter pageResponseWriter,
      ApiResponseWriter apiResponseWriter, BinaryResponseWriter binaryResponseWriter,
      RedirectResponseWriter redirectResponseWriter, ExceptionStatusMapper exceptionStatusMapper) {
    return new DefaultResponseHandler(pageResponseWriter, apiResponseWriter, binaryResponseWriter,
      redirectResponseWriter, exceptionStatusMapper);
  }

  @Provides
  @Singleton
  public PageResponseWriter providePageResponseWriter() {
    return new DefaultPageResponseWriter();
  }

  @Provides
  @Singleton
  public ApiResponseWriter provideApiResponseWriter() {
    return new DefaultApiResponseWriter();
  }

  @Provides
  @Singleton
  public BinaryResponseWriter provideBinaryResponseWriter() {
    return new DefaultBinaryResponseWriter();
  }

  @Provides
  @Singleton
  public RedirectResponseWriter provideRedirectResponseWriter() {
    return new DefaultRedirectResponseWriter();
  }

  @Provides
  @Singleton
  public ExceptionStatusMapper provideExceptionStatusMapper() {
    return new DefaultExceptionStatusMapper();
  }

  @Provides
  public RequestContext provideRequestContext() {
    return RequestContext.get();
  }

  /**
   * Provides a default {@link RequestHandler}.
   * 
   * @param instanceProvider
   *          instance factory for objects that handle routes
   * @return a {@link RequestHandler}
   */
  @Inject
  @Provides
  @Singleton
  public RequestHandler provideRequestHandler(InstanceProvider instanceProvider) {
    return new DefaultRequestHandler(instanceProvider, new ValueConverterRegistry());
  }

  /**
   * Provides an {@link InstanceProvider} which uses a <a href="https://github.com/google/guice">Guice</a>
   * {@link Injector} to create instances.
   * 
   * @param injector
   *          <a href="https://github.com/google/guice">Guice</a> {@link Injector} used to create instances
   * 
   * @return an {@link InstanceProvider}
   */
  @Inject
  @Provides
  @Singleton
  public InstanceProvider provideInstanceProvider(Injector injector) {
    return new InstanceProvider() {
      @Override
      public <T> T provide(Class<T> instanceClass) {
        return injector.getInstance(instanceClass);
      }
    };
  }
}