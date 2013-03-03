package com.redhat.theses

import com.redhat.theses.util.Commons

class OrganizationService {

    def delete(Organization organization) {
        return Commons.delete(organization)
    }
}
