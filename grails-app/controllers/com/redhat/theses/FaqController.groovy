package com.redhat.theses

import grails.plugins.springsecurity.Secured
import org.springframework.context.i18n.LocaleContextHolder as LCH

class FaqController {

    def faqService

    static defaultAction = "list"

    // Id parameter is being used for locale in this action
    def list(String id) {

        def locale = LCH.locale

        if (id) {
            locale = new Locale(id)
        }

        def faqInstanceList = Faq.findAllByLocale(locale)
        if (!faqInstanceList) {
            locale = Locale.ENGLISH
            faqInstanceList = Faq.findAllByLocale(locale)
        }

        [faqInstanceList: faqInstanceList, locales: locales, currentLocale: locale.toString()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        def faqCommand = new FaqCommand()
        faqCommand.faq.add(new Faq())

        [faqCommand: faqCommand, locales: locales]
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
        def faqCommand = new FaqCommand()
        bindData(faqCommand, params.faqCommand)

        faqCommand.faq = faqCommand.faq.findAll()

        if (!faqCommand.validate() || !faqService.saveMany(faqCommand.faq)) {
            render(view: 'create', model:
                    [faqCommand: faqCommand, locales: locales])
            return
        }

        if (faqCommand.faq.size() > 1) {
            flash.message = message(code: 'faq.created.many.message')
        } else {
            flash.message = message(code: 'faq.created.message')
        }
        redirect(action: "list")
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        def faqInstance = Faq.get(id)

        if (!faqInstance) {
            flash.message = message(code: 'faq.not.found.message')
            redirect(action: 'list')
        }

        [faqInstance: faqInstance, locales: locales]
    }

    @Secured(['ROLE_ADMIN'])
    def update() {
        Long id = params.faq.long("id")
        Long version = params.faq.long("version")
        def faqInstance = Faq.get(id)

        if (!faqInstance) {
            flash.message = message(code: 'faq.not.found.message')
            redirect(action: 'list')
        }

        if (version != null && faqInstance.version > version) {
            faqInstance.errors.rejectValue("version", "faq.optimistic.lock.error")
            render(view: "edit", model:
                    [faqInstance: faqInstance, locales: locales])
            return
        }

        faqInstance.properties = params.faq
        if (!faqInstance.save()) {
            render(view: "edit", model: [faqInstance: faqInstance, locales: locales])
            return
        }

        flash.message = message(code: 'faq.updated.message')
        redirect(action: "list")
    }

    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        def faqInstance = Faq.get(id)

        if (!faqInstance) {
            flash.message = message(code: 'faq.not.found.message')
            redirect(action: 'list')
        }

        if (faqInstance.delete()) {
            flash.message = message(code: 'faq.deleted.message')
            redirect(action: 'list')
        } else {
            flash.message = message(code: 'faq.deleted.message')
            redirect(action: 'list', fragment: "faq$id")
        }
    }

    private getLocales() {
        [en: g.message(code: 'locale.default.label'),
                cs: g.message(code: 'locale.cs.label')]
    }
}
