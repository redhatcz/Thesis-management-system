package com.redhat.theses

import com.redhat.theses.util.Commons

class UniversityService {

    /**
     * Deletes an university safely
     *
     * @return true if success, false if otherwise
     */
    def delete(University university) {
        return Commons.delete(university)
    }
}
