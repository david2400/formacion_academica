package com.kleverkids.formacion_academica.shared.common.domain.model;

import java.time.Instant;

public interface Auditable {
    boolean eliminado();
    Integer usrCrea();
    Integer usrMod();
    Instant createdAt();
    Instant updatedAt();
    
    static Auditable of(boolean eliminado, Integer usrCrea, Integer usrMod, Instant createdAt, Instant updatedAt) {
        return new AuditRecord(eliminado, usrCrea, usrMod, createdAt, updatedAt);
    }
    
    record AuditRecord(boolean eliminado, Integer usrCrea, Integer usrMod, Instant createdAt, Instant updatedAt) implements Auditable {}
}
