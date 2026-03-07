package com.orbital3d.web.application.database.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.orbital3d.web.forestmoon.ForestMoonApplication;

@SpringBootTest(classes = ForestMoonApplication.class)
public class PageTest {
    private  static final Client TEST_CLIENT = Client.of("Test Client", "cc-123");
    private static final String TEST_SITE_NAME = "Test Site";
    private static final String TEST_PAGE_NAME = "Test Page";
    
    @Test
    public void testPageAllValuesEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        p1.setId(1L);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        p2.setId(1L);
        assert p1.equals(p2) : "Pages with same id, name and owner should be equal";
    }   

    @Test
    public void testPageDifferentIdNotEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        p1.setId(100L);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        p2.setId(p1.getId() + 1L);
        assert !p1.equals(p2) : "Pages with different ids should not be equal";
    }

    @Test
    public void testPageDifferentNameNotEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), "Test Page 2");
        assert !p1.equals(p2) : "Pages with different names should not be equal";
    }

    @Test
    public void testPageNullNameNotEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), (String) null);
        assert !p1.equals(p2) : "Pages with null name should not be equal to pages with non-null name";
    }

    @Test
    public void testPageNullNameEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), (String) null);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), (String) null);
        assert p1.equals(p2) : "Pages with null name should be equal to each other";
    }

    @Test
    public void testPageNullOwnerNotEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), TEST_PAGE_NAME);
        Page p2 = Page.of(null, TEST_PAGE_NAME);
        assert !p1.equals(p2) : "Pages with null owner should not be equal to pages with non-null owner";
    }

    @Test
    public void testPageNullOwnerEquals() {
        Page p1 = Page.of(null, TEST_PAGE_NAME);
        Page p2 = Page.of(null, TEST_PAGE_NAME);
        assert p1.equals(p2) : "Pages with null owner should be equal to each other";
    }

    @Test
    public void testPageNullOwnerAndNameEquals() {
        Page p1 = Page.of(null, (String) null);
        Page p2 = Page.of(null, (String) null);
        assert p1.equals(p2) : "Pages with null owner and name should be equal to each other";
    }

    @Test
    public void testPageNullOwnerAndNameNotEquals() {
        Page p1 = Page.of(null, (String) null);
        Page p2 = Page.of(null, TEST_PAGE_NAME);
        assert !p1.equals(p2) : "Pages with null owner and name should not be equal to pages with null owner and non-null name";
    }

    @Test
    public void testPageNullOwnerAndNameNotEquals2() {
        Page p1 = Page.of(null, (String) null);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), (String) null);
        assert !p1.equals(p2) : "Pages with null owner and name should not be equal to pages with non-null owner and null name";
    }

    @Test
    public void testPaggeNameNullDifferentIdsNotEquals() {
        Page p1 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), (String) null);
        p1.setId(100L);
        Page p2 = Page.of(Site.of(TEST_CLIENT, TEST_SITE_NAME), (String) null);
        p2.setId(p1.getId() + 1L);
        assert !p1.equals(p2) : "Pages with null name and different ids should not be equal";
    }
}
