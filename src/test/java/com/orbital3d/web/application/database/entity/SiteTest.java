package com.orbital3d.web.application.database.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.orbital3d.web.forestmoon.ForestMoonApplication;

@SpringBootTest(classes = ForestMoonApplication.class)
public class SiteTest {
    private  static final Client TEST_CLIENT = Client.of("Test Client", "cc-123");
    private static final String TEST_SITE_NAME_1 = "Test Site 1";
    private static final String TEST_SITE_NAME_2 = "Test Site 2";

    @Test
    public void testSiteAllValuesEquals() {
        Site s1 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        s1.setId(1L);
        Site s2 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        s2.setId(1L);
        assert s1.equals(s2) : "Sites with same id, name and owner should be equal";
    }

    @Test
    public void testSiteDifferentIdNotEquals() {
        Site s1 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        s1.setId(100L);
        Site s2 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        s2.setId(s1.getId() + 1L);
        assert !s1.equals(s2) : "Sites with different ids should not be equal";
    }

    @Test
    public void testSiteDifferentNameNotEquals() {
        Site s1 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        Site s2 = Site.of(TEST_CLIENT, TEST_SITE_NAME_2);
        assert !s1.equals(s2) : "Sites with different names should not be equal";
    }

    @Test
    public void testSiteNullNameNotEquals() {
        Site s1 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        Site s2 = Site.of(TEST_CLIENT, (String) null);
        assert !s1.equals(s2) : "Sites with null name should not be equal to sites with non-null name";
    }

    @Test
    public void testSiteNullNameEquals() {
        Site s1 = Site.of(TEST_CLIENT, (String) null);
        Site s2 = Site.of(TEST_CLIENT, (String) null);
        assert s1.equals(s2) : "Sites with null name should be equal to each other";
    }

    @Test
    public void testSiteNullClientNotEquals() {
        Site s1 = Site.of(null, TEST_SITE_NAME_1);
        Site s2 = Site.of(TEST_CLIENT, TEST_SITE_NAME_1);
        assert !s1.equals(s2) : "Sites with null client should not be equal to sites with non-null client";
    }

    @Test
    public void testSiteNullClientEquals() {
        Site s1 = Site.of(null, TEST_SITE_NAME_1);
        Site s2 = Site.of(null, TEST_SITE_NAME_1);
        assert s1.equals(s2) : "Sites with null client should be equal to each other";
    }

    @Test
    public void testSiteNullClientAndNameSameIdEquals() {
        Site s1 = Site.of(null, (String) null);
        s1.setId(1L);
        Site s2 = Site.of(null, (String) null);
        s2.setId(1L);
        assert s1.equals(s2) : "Sites with null client and null name should be equal to each other";

    }
    
    @Test
    public void testSiteNullClientAndNameDifferentIdNotEquals() {
        Site s1 = Site.of(null, (String) null);
        s1.setId(1L);
        Site s2 = Site.of(null, (String) null);
        s2.setId(100L);
        assert !s1.equals(s2) : "Sites with null client and null name should not be equal if they have different ids";
    }
}
