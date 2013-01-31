package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport

class ApplicationService {

    def thesisService

    def Application approve(Application application) {
        if (application.approvedByOwner) {
            return
        }

        application.approvedByOwner = true
        def createdThesis = thesisService.createFromApplication(application)
        application.thesis = createdThesis

        def persistedApplication = application.save()

        if (!createdThesis || !persistedApplication) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly()
            persistedApplication = null;
        }

        persistedApplication
    }

}
