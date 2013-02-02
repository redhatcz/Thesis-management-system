package com.redhat.theses

import com.redhat.theses.auth.User

class Thesis extends Article{

    Topic topic
    User assignee
    Membership sMembership
    Status status
    Grade grade
    String thesisAbstract


    static constraints = {
        grade nullable: true
        sMembership nullable: true
        thesisAbstract nullable: true

        sMembership validator: {sMembership, thesis ->
            sMembership == null ||
            sMembership.id &&
            Supervision.findByTopicAndMembership(thesis.topic, sMembership) &&
            Membership.findByUserAndOrganization(thesis.assignee, sMembership.organization)
        }

        topic validator: {topic ->
            topic?.id != null && Topic.get(topic.id)
        }

        assignee validator: {assignee ->
            assignee?.id != null && User.get(assignee.id)
        }
    }

    static enum Status {
        IN_PROGRESS,
        FINISHED,
        FAILED,
        POSPONED
    }
    static enum Grade {
        A, B, C, D, E, F
    }

    public List<String> getFileURLs(){
       return null;
    }

    public Supervision getSupervision() {
        return Supervision.findByTopicAndMembership(topic, sMembership)
    }

}
