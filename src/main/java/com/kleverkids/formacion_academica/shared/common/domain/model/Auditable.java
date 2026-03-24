package com.kleverkids.formacion_academica.shared.common.domain.model;

import java.time.Instant;

public interface Auditable {
    boolean activo();
    Integer usrCrea();
    Integer usrMod();
    Instant createdAt();
    Instant updatedAt();
    
    static Auditable of(boolean activo, Integer usrCrea, Integer usrMod, Instant createdAt, Instant updatedAt) {
        return new AuditRecord(activo, usrCrea, usrMod, createdAt, updatedAt);
    }
    
    record AuditRecord(boolean activo, Integer usrCrea, Integer usrMod, Instant createdAt, Instant updatedAt) implements Auditable {}
}
