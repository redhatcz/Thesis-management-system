package com.redhat.theses

import com.redhat.theses.util.Commons

class UniversityService {

    def delete(University university) {
        return Commons.delete(university)
    }
}
