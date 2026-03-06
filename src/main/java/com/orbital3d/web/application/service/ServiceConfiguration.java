
package com.orbital3d.web.application.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.orbital3d.web.application.service.impl.ClientCodeServiceImpl;

@Configuration
public class ServiceConfiguration {
	/**
	 * Conditional bean creation, using {@link ClientCodeService} as default implementation.
	 */
	@ConditionalOnMissingBean
	@Bean
	public ClientCodeService clientCodeService() {
		return new ClientCodeServiceImpl();
	}
}
