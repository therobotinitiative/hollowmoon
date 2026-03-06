package com.orbital3d.web.forestmoon.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.qos.logback.classic.Logger;

@Controller
public class DefaultIndex {
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(DefaultIndex.class);

	@RequestMapping(method = RequestMethod.GET, path = "/")
	String index() {
		LOG.warn("default index, recommended that you override this");
		return "default/default-index";
	}
}
