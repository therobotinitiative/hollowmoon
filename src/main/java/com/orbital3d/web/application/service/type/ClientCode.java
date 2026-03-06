package com.orbital3d.web.application.service.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Client code.
 * 
 * @param clientCode Client code.
 */
public record ClientCode(@JsonProperty("clientcode") String clientCode) {
}
