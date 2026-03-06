package com.orbital3d.web.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.entity.Site;
import com.orbital3d.web.application.database.repository.ClientReposity;
import com.orbital3d.web.application.database.repository.SiteRepository;
import com.orbital3d.web.forestmoon.ForestMoonApplication;


@SpringBootTest(classes = ForestMoonApplication.class)
public class SiteServiceTest {
    @Autowired
    private SiteService siteService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ClientReposity clientRepository;

    private static final Client TEST_CLIENT = Client.of("Test Client", "test-code-123");
    private static final Client TEST_CLIENT_1 = Client.of("Test Client 1", "test-code-456");

    @BeforeEach
    public void setup() {
        siteRepository.deleteAll();
        //clientRepository.deleteAll();
    }

    @Test
    public void testAddSiteOk() {
        clientService.add(TEST_CLIENT);
        Site s = Site.of(TEST_CLIENT, "Test Site");
        Site sc = siteService.add(s);
        assert sc.getId() != null;
        assert sc.getName().equals(s.getName());
        assert sc.getOwner().equals(s.getOwner());
    }

    @Test
    public void testAddSiteNullName() {
        Site s = Site.of(TEST_CLIENT, (String) null);
        try {
            siteService.add(s);
            assert false : "Expected exception for null site name";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testAddSiteNullClient() {
        Site s = Site.of(null, "Test Site");
        try {
            siteService.add(s);
            assert false : "Expected exception for null site client";
        } catch (IllegalArgumentException e) {  
            // Expected exception, test passes
        }
    }

    @Test
    public void testAddSiteNullSite() {
        try {
            siteService.add(null);
            assert false : "Expected exception for null site";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testGetSites() {
        clientService.add(TEST_CLIENT);
        clientService.add(TEST_CLIENT_1);
        Site s1 = Site.of(TEST_CLIENT, "Test Site 1");
        Site s2 = Site.of(TEST_CLIENT_1, "Test Site 2");
        Site ss1 = siteService.add(s1);
        Site ss2 = siteService.add(s2);
        assert ss1 != null;
        assert ss2 != null;
        assert siteService.find(TEST_CLIENT).size() == 1;
        assert siteService.find(TEST_CLIENT).contains(ss1);
        assert siteService.find(TEST_CLIENT_1).size() == 1;
        assert siteService.find(TEST_CLIENT_1).contains(ss2);
    }

    @Test
    public void testGetSitesEmpty() {
        assert siteService.find(TEST_CLIENT).isEmpty();
    }

    @Test
    public void testDeleteAllSitesUnderClientDoNotRemoveClient() {
        Client st = clientService.add(TEST_CLIENT);
        Site s1 = Site.of(TEST_CLIENT, "Test Site 1");
        Site s2 = Site.of(TEST_CLIENT, "Test Site 2");
        Site ss1 = siteService.add(s1);
        Site ss2 = siteService.add(s2);
        assert siteService.find(TEST_CLIENT).size() == 2;
        siteService.delete(ss1.getId());
        siteService.delete(ss2.getId());
        assert siteService.find(TEST_CLIENT).isEmpty();
        assert clientService.find(st.getId()).get() != null;
        assert clientService.findByClientCode(st.getClientCode()) != null;
        assert !clientService.all().isEmpty();
        assert clientService.all().contains(st);
    }
}
