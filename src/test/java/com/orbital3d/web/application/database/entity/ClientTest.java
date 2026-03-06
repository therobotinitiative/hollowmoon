
package com.orbital3d.web.application.database.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.orbital3d.web.forestmoon.ForestMoonApplication;

@SpringBootTest(classes = ForestMoonApplication.class)
public class ClientTest {
    @Test
    public void testClientAllValuesEquals() {
        Client c1 = Client.of("Test Client", "cc-123");
        c1.setId(1L);
        Client c2 = Client.of("Test Client", "cc-123");
        c2.setId(1L);
        assert c1.equals(c2) : "Clients with same id and name should be equal";
    }

    @Test
    public void testClientDifferentIdNotEquals() {
        Client c1 = Client.of("Test Client", "cc-123");
        c1.setId(100L);
        Client c2 = Client.of("Test Client", "cc-123");
        c2.setId(c1.getId() + 1L);
        assert !c1.equals(c2) : "Clients with different ids should not be equal";
    }

    @Test
    public void testClientDifferentNameNotEquals() {
        Client c1 = Client.of("Test Client", "cc-123");
        Client c2 = Client.of("Test Client 2", "cc-123");
        assert !c1.equals(c2) : "Clients with different names should not be equal";
    }

    @Test
    public void testClientNullNameNotEquals() {
        Client c1 = Client.of("Test Client", "cc-123");
        Client c2 = Client.of(null, "cc-123");
        assert !c1.equals(c2) : "Clients with null name should not be equal to clients with non-null name";
    }

    @Test
    public void testClientNullNameEquals() {
        Client c1 = Client.of(null, "cc-123");
        Client c2 = Client.of(null, "cc-123");
        assert c1.equals(c2) : "Clients with null name not should be equal to each other";
    }

    @Test
    public void testClientNullClientCodeNotEquals() {
        Client c1 = Client.of("Test Client", "cc-123");
        Client c2 = Client.of("Test Client", null);
        assert !c1.equals(c2) : "Clients with null client code should not be equal to clients with non-null client code";
    }

    @Test
    public void testClientNullClientCodeEquals() {
        Client c1 = Client.of("Test Client", null);
        Client c2 = Client.of("Test Client", null);
        assert c1.equals(c2) : "Clients with null client code should be equal to each other";
    }

    @Test
    public void testClientNullIdNotEquals() {
        Client c1 = Client.of("Test Client", "cc-123");
        Client c2 = Client.of("Test Client", "cc-123");
        c2.setId(1L);
        assert !c1.equals(c2) : "Clients with null id should not be equal to clients with non-null id";
    }

    @Test
    public void testClientHashCodeConsistency() {
        Client c1 = Client.of("Test Client", "cc-123");
        c1.setId(1L);
        Client c2 = Client.of("Test Client", "cc-123");
        c2.setId(1L);
        assert c1.hashCode() == c2.hashCode() : "Equal clients should have the same hash code";
    }

    @Test
    public void testClientHashCodeDifferent() {
        Client c1 = Client.of("Test Client", "cc-123");
        c1.setId(1L);
        Client c2 = Client.of("Test Client", "cc-123");
        c2.setId(2L);
        assert c1.hashCode() != c2.hashCode() : "Clients with different ids should have different hash codes";
    }   
}
