package com.redhat.theses.auth

enum Role {
    ADMIN,
    SUPERVISOR,
    OWNER,
    STUDENT

    static Role getHighest(Collection<Role> roles) {
        if (roles.contains(Role.ADMIN)) {
            Role.ADMIN
        } else if (roles.contains(Role.OWNER)) {
            Role.OWNER
        } else if (roles.contains(Role.SUPERVISOR)) {
            Role.SUPERVISOR
        } else {
            Role.STUDENT
        }
    }
}
