package com.redhat.theses.auth

import com.redhat.theses.Membership
import com.redhat.theses.Organization

class User {

	transient springSecurityService

	String username
	String password
    String fullName
    String email
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true
		password blank: false
        fullName blank: false
        email email: true, blank: false
	}


    boolean isMember(Organization organization){
        Membership.countByUserAndOrganization(this, organization) > 0
    }

    Set<Organization> getOrganizations() {
        Membership.findAllByUser(this).collect {it.organization} as Set
    }

    Set<Organization> getOrganizations(Class clazz) {
        Membership.findAllByUser(this).collect {it.organization}.findAll {it.class == clazz} as Set
    }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
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
