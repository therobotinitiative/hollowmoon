package com.orbital3d.web.application.service;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.forestmoon.ForestMoonApplication;

@SpringBootTest(classes = ForestMoonApplication.class)
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        // Clean up before each test
        clientService.all().forEach(c -> clientService.delete(c.getId()));
    }

    @Test
    public void testAddClientOk() {
        Client c = Client.of("Test Client", "test-code-123");
        Client sc = clientService.add(c);
        assert sc.getId() != null;
        assert sc.getName().equals(c.getName());
        assert sc.getClientCode().equals(c.getClientCode());
    }
    
    @Test
    public void testAddClientNullCodeFail() {
        Client c = Client.of("Test Client", null);
        try {
            clientService.add(c);
            assert false : "Expected exception for null client code";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testAddClientNullName() {
        Client c = Client.of(null, "test-code-123");
        try {
            clientService.add(c);
            assert false : "Expected exception for null client name";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testAddClientNullClient() {
        try {
            clientService.add(null);
            assert false : "Expected exception for null client";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testAddClientDuplicateCodeFail() {
        Client c1 = Client.of("Test Client 1", "duplicate-code");
        Client c2 = Client.of("Test Client 2", "duplicate-code");
        clientService.add(c1);
        try {
            clientService.add(c2);
            assert false : "Expected exception for duplicate client code";
        } catch (DataIntegrityViolationException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testGetClientByCodeOk() {
        Client c = Client.of("Test Client", "test-code-123");
        clientService.add(c);
        Client sc = clientService.findByClientCode("test-code-123");
        assert sc != null;
        assert sc.getName().equals(c.getName());
        assert sc.getClientCode().equals(c.getClientCode());
    }

    @Test
    public void testGetClientByCodeNotFound() {
        try {
            clientService.findByClientCode("non-existent-code");
            assert false : "Expected null for non-existent client code";
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testGetClientNullCode() {
        try {
            clientService.findByClientCode(null);
            assert false : "Expected exception for null client code";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }  

    @Test
    public void testRemoveClientOk() {
        Client c = Client.of("Test Client", "test-code-123");
        Client sc = clientService.add(c);
        clientService.delete(sc.getId());
        try {
            clientService.find(sc.getId());
            assert false : "Expected exception for removed client";
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testRemoveClientNotFound() {
        try {
            clientService.delete(999L);
            assert false : "Expected exception for non-existent client ID";
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testUpdateClientOk() {
        Client c = Client.of("Test Client", "test-code-123");
        Client sc = clientService.add(c);
        sc.setName("Updated Client");
        Client updated = clientService.update(sc);
        assert updated != null;
        assert updated.getId().equals(sc.getId());
        assert updated.getName().equals("Updated Client");
    }

    @Test
    public void testUpdateClientNotFound() {
        Client c = Client.of("Non-existent Client", "non-existent-code");
        c.setId(999L);
        try {
            clientService.update(c);
            assert false : "Expected exception for updating non-existent client";
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testUpdateDuplicateClientCodeFail() {
        Client c1 = Client.of("Test Client 1", "code-1");
        Client c2 = Client.of("Test Client 2", "code-2");
        Client sc1 = clientService.add(c1);
        Client sc2 = clientService.add(c2);
        sc2.setClientCode(sc1.getClientCode());
        try {
            clientService.update(sc2);
            assert false : "Expected exception for duplicate client code on update";
        } catch (DataIntegrityViolationException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testUpdateClientNullCode() {
        Client c = Client.of("Test Client", "test-code-123");
        Client sc = clientService.add(c);
        sc.setClientCode(null);
        try {
            clientService.update(sc);
            assert false : "Expected exception for null client code on update";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testUpdateClientNullName() {
        Client c = Client.of("Test Client", "test-code-123");
        Client sc = clientService.add(c);
        sc.setName(null);
        try {
            clientService.update(sc);
            assert false : "Expected exception for null client name on update";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testUpdateClientNullClient() {
        try {
            clientService.update(null);
            assert false : "Expected exception for null client on update";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testGetClients() {
        Client c1 = Client.of("Test Client 1", "code-1");
        Client c2 = Client.of("Test Client 2", "code-2");
        clientService.add(c1);
        clientService.add(c2);
        assert clientService.all().size() == 2;
    }

    @Test
    public void testGetClientsEmpty() {
        assert clientService.all().isEmpty();
    }

    @Test
    public void testFindByIdOk() {
        Client c = Client.of("Test Client", "test-code-123");
        Client sc = clientService.add(c);
        Client found = clientService.find(sc.getId()).get();
        assert found != null;
        assert found.getId().equals(sc.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        try {
            clientService.find(999L);
            assert false : "Expected exception for non-existent client ID";
        } catch (NoSuchElementException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void testFindByIdNull() {
        try {
            clientService.find((Long) null);
            assert false : "Expected exception for null client ID";
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
