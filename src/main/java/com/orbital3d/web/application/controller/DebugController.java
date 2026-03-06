package com.orbital3d.web.application.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.web.application.controller.dto.AddPage;
import com.orbital3d.web.application.database.entity.Page;
import com.orbital3d.web.application.service.ClientCodeService;
import com.orbital3d.web.application.service.PageService;
import com.orbital3d.web.application.service.type.ClientCode;

/**
 * Debug REST Controller for testing purposes only.
 */
@RestController()
@RequestMapping("/debug")
// Used by framework
@SuppressWarnings("unused")
public class DebugController {
	private static final Logger LOG = LoggerFactory.getLogger(DebugController.class);

	@Autowired
	private PageService pageService;

	@Autowired
	private ClientCodeService clientCodeService;

	@GetMapping("/cc")
	ClientCode getClientCode() {
		LOG.info("GET /clientcode");
		return clientCodeService.resolveClientCode();
	}
	
	@GetMapping("/test")
	String test() {
		LOG.info("GET /test");
		return "Hello, World!";
	}

	@GetMapping("/page/{siteId}")
	Set<Page> getPages(@PathVariable String siteId) {
		LOG.info("GET /page/{}", siteId);
//		Set<Page> pages = pageService.getPages(Site.of(Long.parseLong(siteId)));
		return null;
	}

	@PostMapping("/page/{siteId}")
	@Transactional
	Page addPage(@PathVariable String siteId, @RequestBody AddPage pageName) {
		LOG.info("POST /page/{} page name: {}", siteId, pageName);
		return null;//pageService.addPage(Long.parseLong(siteId), pageName.page());
	}

}
