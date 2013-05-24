package com.redhat.theses.auth

class User {

    /**
     * Dependency injection of grails.plugins.springsecurity.SpringSecurityService
     */
	transient springSecurityService

	String password
    String fullName
    String email
    Date dateCreated
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    boolean sendMail = true

    //static searchable = true

    static hasMany = [roles: Role]

	static constraints = {
		password blank: false
        fullName blank: false
        email email: true, blank: false, unique: true
	}

    static mapping = {
        table "`user`"
    }
    static bucketMapping = "user"

    static hibernateFilters = {
        enabledFilter(condition:"enabled='1' and account_expired='0' and account_locked='0'", default: true)
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
