package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport

class ApplicationService {

    def thesisService

    Application approve(Application application, Thesis thesis) {
        if (application.approved) {
            application.errors.reject('application.already.approved')
            return null
        }

        application.approved = true
        def createdThesis = thesisService.save(thesis)
        application.thesis = createdThesis

        def persistedApplication = application.save()

        if (!createdThesis || !persistedApplication) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            persistedApplication = null
        }

        persistedApplication
    }

}
