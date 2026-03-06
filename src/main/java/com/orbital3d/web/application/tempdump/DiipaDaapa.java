package com.orbital3d.web.application.tempdump;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.service.ClientService;
import com.orbital3d.web.application.service.PageService;
import com.orbital3d.web.application.service.SiteService;

/**
 * If you can't understand this class and it's purpose I sugggest you read the package and class name
 * and if you still don't get it then I suggest using your brain and if that doesn't help then
 * change your profession because programming clearly isn't for you.
 */
@Component
public class DiipaDaapa {
	private static final Logger LOG = LoggerFactory.getLogger(DiipaDaapa.class);

	@Autowired
	private SiteService ss;

	@Autowired
	private PageService ps;

	@Autowired
	private ClientService cr;

	//@PostConstruct
	protected void valmista() {
		LOG.debug("**** Generating puppu data ****");

		Set<Client> clients = new HashSet<>();
		for (int i = 0; i < 5; i++) {
			clients.add(cr.add(Client.of("client" + i)));
		}

		Set<Site> sl = new HashSet<>();
		for (Client c : clients) {
			for (int i = 0; i < (Math.random() * 25.0); i++) {
				Site s = ss.add(Site.of(c, "Site" + i));
				sl.add(s);
			}
		}

		for (Site s : sl) {
			for (int i = 0; i < (Math.random() * 20.0); i++) {
				//ps.addPage(s.getId(), "page" + i);
			}
		}
		LOG.debug("**** Done generating ***");
	}
}
