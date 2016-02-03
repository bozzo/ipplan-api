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

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
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
	
//	private SpringSwaggerConfig springSwaggerConfig;
//	 
//    @Autowired
//    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
//        this.springSwaggerConfig = springSwaggerConfig;
//    }
// 
//    @Bean
//    public SwaggerSpringMvcPlugin swagger() {
//        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
//                .apiInfo(new ApiInfo(
//                        "IpPlan API",
//                        "A Java implemented API to interact with Ipplan Database",
//                        null,
//                        null,
//                        "GNU GENERAL PUBLIC LICENSE Version 3",
//                        "https://www.gnu.org/licenses/gpl.html"
//                ))
//                .useDefaultResponseMessages(false)
//                .ignoredParameterTypes(ModelAndView.class)
//                .ignoredParameterTypes(PagedResourcesAssembler.class)
//                .ignoredParameterTypes(Pageable.class)
//                .includePatterns("/api/.*");
//    }
	
	@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
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
