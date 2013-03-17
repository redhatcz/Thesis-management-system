package com.redhat.theses

import com.redhat.theses.auth.User
import com.redhat.theses.util.Commons

class SupervisionService {

    /**
     * Creates and persists supervision for given topic and each element from list of Supervision
     * @param supervisions list of at least partially initialized Supervision instances
     * @return List of created supervisions
     */
    def saveMany(List<Supervision> supervisions, User supervisor, Topic topic) {
        def oldSupervisions = Supervision.findAllByTopicAndSupervisor(topic, supervisor)

        def success = supervisions.every {
            def supervision = Supervision.findByTopicAndSupervisorAndUniversity(it.topic, it.supervisor, it.university)
            if (!supervision) {
                it.save()
            } else {
                oldSupervisions.remove(supervision)
                true
            }
        }
        success = success && oldSupervisions.every { Commons.delete(it) }
        success
    }
}
