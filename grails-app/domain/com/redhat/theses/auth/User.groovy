package com.redhat.theses.auth

import groovy.transform.ToString
import com.redhat.theses.University
import com.redhat.theses.Membership

@ToString(includes='username')
class User {

	transient springSecurityService

	String username
	String password
    String fullName;
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    static hasMany = [universities: University]
    static belongsTo = University

	static constraints = {
		username blank: false, unique: true
		password blank: false
        fullName blank: false
	}

	static mapping = {
        universities fetch: 'join'
	}


    boolean isMember(University university){
        Membership.countByUserAndUniversity(this, university) > 0
    }

    Set<University> getUniversities() {
        Membership.findAllByUser(this).collect {it.university} as Set
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
}
