package com.redhat.theses.auth

enum Role {
    ADMIN,
    SUPERVISOR,
    OWNER,
    STUDENT

    String toString() {
        "ROLE_${this.name()}"
    }
}
