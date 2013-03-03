package com.redhat.theses

class SupervisionService {

    /**
     * Creates and persists supervision for given topic and each element from list of Supervision
     * @param supervisions list of at least partially initialized Supervision instances
     * @return List of created supervisions
     */
    def saveAll(List<Supervision> supervisions) {
        supervisions.each {
            if (!Supervision.findByTopicAndSupervisorAndUniversity(it.topic, it.supervisor, it.university)) {
                it.save()
            }
        }
    }
}
