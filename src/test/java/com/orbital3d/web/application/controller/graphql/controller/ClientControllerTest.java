package com.orbital3d.web.application.controller.graphql.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.orbital3d.web.application.database.entity.Client;
import com.orbital3d.web.application.database.repository.ClientReposity;
import com.orbital3d.web.application.service.ClientCodeService;
import com.orbital3d.web.application.service.type.ClientCode;
import com.orbital3d.web.forestmoon.ForestMoonApplication;

@SpringBootTest(classes = ForestMoonApplication.class)
class ClientControllerTest {

	@Autowired
	private ExecutionGraphQlService graphQlService;

	private GraphQlTester graphQlTester;

	@Autowired
	private ClientReposity clientRepository;

	private static final String TEST_CLIENT_CODE = "test-code-123";
	@SuppressWarnings("unused")
	private static final String OLD_CLIENT_CODE = "old-code-456";
	@SuppressWarnings("unused")
	private static final String CHANGED_CLIENT_CODE = "changed-code-456";
	private static final String UPDATED_CLIENT_CODE = "updated-code-456";
	private static final String NEW_CLIENT_NAME = "New Name";

	/**
	 * Override ClientCodeService to avoid external HTTP calls during tests.
	 */
	@TestConfiguration
	// Invoked by framework
    @SuppressWarnings("unused")
	static class TestConfig {
		@Bean
		@Primary
		ClientCodeService clientCodeService() {
			return () -> new ClientCode(TEST_CLIENT_CODE);
		}
	}

	// Used byframework
	@SuppressWarnings("unused")
	@BeforeEach
	void setUp() {
		graphQlTester = ExecutionGraphQlServiceTester.create(graphQlService);
		clientRepository.deleteAll();
		// Insert per-test data here
		clientRepository.save(Client.of("Test Client A", "code-a"));
		clientRepository.save(Client.of("Test Client B", "code-b"));
	}

	@Test	
	void allClients_returnsAllClients() {
		graphQlTester.document("{ allClients { id name clientCode } }")
				.execute()
				.path("allClients")
				.entityList(Client.class)
				.hasSize(2);
	}

	@Test
	void clientByClientCode_returnsMatchingClient() {
		graphQlTester.document("query($code: String) { clientByClientCode(clientCode: $code) { name clientCode } }")
				.variable("code", "code-a")
				.execute()
				.path("clientByClientCode.name")
				.entity(String.class)
				.isEqualTo("Test Client A");
	}

	@Test
	void addClient_withClientCode_createsClient() {
		graphQlTester.document("mutation { addClient(clientName: \"New Client\", clientCode: \"new-code\") { id name clientCode } }")
				.execute()
				.path("addClient.name")
				.entity(String.class)
				.isEqualTo("New Client");

		assertThat(clientRepository.findByClientCode("new-code")).isPresent();
	}

	@Test
	void addClient_withoutClientCode_usesGeneratedCode() {
		graphQlTester.document("mutation { addClient(clientName: \"Auto Code Client\") { id name clientCode } }")
				.execute()
				.path("addClient.clientCode")
				.entity(String.class)
				.isEqualTo(TEST_CLIENT_CODE);
	}

	@Test
	void removeClient_removesAndReturnsClient() {
		Client saved = clientRepository.save(Client.of("To Remove", "remove-code"));

		graphQlTester.document("mutation($id: ID!) { removeClient(clientId: $id) }")
				.variable("id", saved.getId())
				.execute()
				.path("removeClient")
				.entity(Boolean.class)
				.isEqualTo(true);

		assertThat(clientRepository.findById(saved.getId())).isEmpty();
	}

	@Test
	void renameClient_updatesName() {
		Client saved = clientRepository.save(Client.of("Old Name", UPDATED_CLIENT_CODE));

		graphQlTester.document("mutation($name: String!, $id: ID!) { renameClient(clientName: $name, clientId: $id) { name } }")
				.variable("name", NEW_CLIENT_NAME)
				.variable("id", saved.getId())
				.execute()
				.path("renameClient.name")
				.entity(String.class)
				.isEqualTo(NEW_CLIENT_NAME);
	}

	@Test
	void changeClientCode_updatesCode() {
		Client saved = clientRepository.save(Client.of("Code Client", "old-code"));

		graphQlTester.document("mutation($code: String!, $id: ID!) { changeClientCode(clientCode: $code, clientId: $id) { clientCode } }")
				.variable("code", UPDATED_CLIENT_CODE)
				.variable("id", saved.getId())
				.execute()
				.path("changeClientCode.clientCode")
				.entity(String.class)
				.isEqualTo(UPDATED_CLIENT_CODE);
	}
}
