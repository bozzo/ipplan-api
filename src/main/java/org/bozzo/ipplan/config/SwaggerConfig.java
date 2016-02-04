/*
 * Copyright (C) 2016
 *     Boris Barnier <b.barnier@gmail.com>
 *   
 * This file is part of ipplan-api.
 * 
 * ipplan-api is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ipplan-api is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ipplan-api.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.bozzo.ipplan.config;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author boris
 *
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {
	
	@Autowired
    private TypeResolver typeResolver;

    // DeferredResult<List<T>> to List<T>
    protected AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType,
            Type sourceSubGenericType, Type targetType, Type targetGenericType) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(sourceType,
                        typeResolver.resolve(sourceGenericType, sourceSubGenericType)),
                typeResolver.resolve(targetType, targetGenericType));
    }

    // Collection<T> to List<T>
    protected AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType,
            Type targetType, Type targetGenericType) {
        return AlternateTypeRules.newRule(typeResolver.resolve(sourceType, sourceGenericType),
                typeResolver.resolve(targetType, targetGenericType));
    }
	
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .alternateTypeRules(
                        getAlternateTypeRule(Collection.class, WildcardType.class, List.class,
                                WildcardType.class),
                        getAlternateTypeRule(Stream.class, WildcardType.class, List.class,
                                WildcardType.class),
                        getAlternateTypeRule(DeferredResult.class, List.class, WildcardType.class,
                                List.class, WildcardType.class))
                .ignoredParameterTypes(PagedResourcesAssembler.class, Pageable.class)
                .directModelSubstitute(MessageSourceResolvable.class, String.class)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
        		"IpPlan API",
              "A Java implemented API to interact with Ipplan Database",
              "0.0.1",
              null,
              null,
              "GNU GENERAL PUBLIC LICENSE Version 3",
              "https://www.gnu.org/licenses/gpl.html"
        );
    }
}
