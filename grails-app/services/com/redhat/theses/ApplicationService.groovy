package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport

class ApplicationService {

    /**
     * Dependency injection of com.redhat.theses.ThesisService
     */
    def thesisService

    /**
     * Approves application
     *
     * @return persisted application on success, false otherwise
     */
    Application approve(Application application, Thesis thesis) {
        if (application.status == AppStatus.APPROVED) {
            application.errors.reject('application.already.approved')
            return null
        }

        application.status = AppStatus.APPROVED
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
