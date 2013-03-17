package com.redhat.theses

import org.springframework.transaction.interceptor.TransactionAspectSupport as TAS

class FaqService {

    def saveMany(List faq) {
        def success = faq.inject(true) { acc, q -> q?.save() && acc}

        if (!success) {
            TAS.currentTransactionStatus().setRollbackOnly()
        }

        success
    }
}
