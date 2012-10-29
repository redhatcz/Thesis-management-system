package com.redhat.theses

class SupervisionService {

    def serviceMethod() {

    }

    /**
     * Creates and persists supervision for given topic and each element from list of Membership
     * @param topic
     * @param Memberships list of at least partially initialized Membership instances
     * @return List of created supervisions
     */
    def List<Supervision> saveMany(Topic topic, List memberships) {
        memberships.findAll().unique().collect {
            def membership = Membership.get(it.id)
            if (membership) {
                def supervision = Supervision.findByTopicAndMembership(topic, it) ?: new Supervision(topic: topic, membership: membership)
                supervision.save(flush: true)
             } else {
                null
            }
        }
    }
}
