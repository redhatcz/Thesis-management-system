package com.redhat.theses.auth

import com.redhat.theses.Membership
import com.redhat.theses.Organization

class User {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
	transient springSecurityService

	String password
    String fullName
    String email
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		password blank: false
        fullName blank: false
        email email: true, blank: false, unique: true
	}

    static mapping = {
        table "`user`"
    }

    boolean isMember(Organization organization){
        Membership.countByUserAndOrganization(this, organization) > 0
    }

    List<Organization> getOrganizations() {
        Membership.findAllByUser(this).collect {it.organization}
    }

    List<Organization> getOrganizations(Class clazz) {
        Membership.findAllByUser(this).collect {it.organization}.findAll {it.class == clazz}
    }

	List<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

    String toString(){
        fullName
    }
}
