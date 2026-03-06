package com.orbital3d.web.application.service;

import com.orbital3d.web.application.service.type.ClientCode;

/**
 * Client code services.
 */
public interface ClientCodeService {
    /**
     * Resolve client new code.
     * 
     * @return Resolved client new code. If the client code cannot be resolved, a random UUID client code will be generated and returned.
     */
    ClientCode resolveClientCode();
}
