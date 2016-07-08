How to use the Thesis management system
=======================================

[![Build Status](https://travis-ci.org/redhatcz/Thesis-management-system.svg?branch=master)](https://travis-ci.org/redhatcz/Thesis-management-system)

Basics
------

The Thesis management system is a system for management of theses, there are 
two main entities: Topic, which represents the topic of a thesis and Thesis, 
which is based upon the topic and represents the thesis at a university. 
More detail description follows.

###Topic
Topic represents the topic of a thesis, the whole idea is that a person from 
a company, which we call the owner, creates a topic of a thesis that students 
can apply for. When a student applies for a topic, the owner can approve the 
application and create a thesis for the student. 

These are the fields of Topic:

 * Title [required] - Represents the title of the topic.
 * Secondary Title - Represents the title in a different language.
 * Lead Paragraph [required] - Lead Paragraph of the topic that is visible on 
 the page with the list of topics.
 * Description [required] - Description of the topic, you can use markdown.
 * Secondary Description - Description of the topic in a different language.
 * Owner [required] - Owner of the topic, which is the person that supervises 
 the topic for the company.
 * Supervisions - Supervisor is a user that supervises the thesis created from 
 a topic at the university and every supervisor is assigned a university. This 
 combination is called supervision. There can be zero or more supervisions for 
 one topic. 
 * Enabled - This flag marks if the topic is enabled or disabled, i.e. if a 
 student can apply for it.
 * Univerisites - The universities that the topic is offered for.
 * Types - Bachelor, Diploma or both.
 * Categories and Tags - Topic can belong to zero or more catogories and/or 
 tags.

###Thesis
Thesis represents the student's thesis at the university. There can be zero or 
more theses created for one topic, but the topic is required.

Thesis fields:

 * Title [required] - The title of the thesis thet should be the same as at the 
 university.
 * Assignee [required] - The student assigned to the thesis.
 * Supervisor [required] - Supervisor that also supervises the thesis at the 
 university.
 * Status - Can be either In progress, Finished, Failed or Postponed.
 * Grade - A, B, C, D, E or F
 * Abstract - Abstract of the thesis, the student should fill this one in.
 * Tags - The student should also fill in the tags.

###Comments
Both topics and theses can be commented on, users with role Admin, Owner or 
Supervisor can add private comments that are not visible to students and 
anonymous.

###Applications
Any student can apply for a topic as long as it is enabled. When applying for 
a topic, the user must enter the university which they apply for. The student 
can show his applications in his profile.
Owner of the topic can approve the application by clicking on the button 
'approve' and then filling in the thesis creation form.

###Configuration
The administrator can configure some of the properties of the system, e.g. 
email domain addresses that a user can sign up for the system with. The 
configuration can be accessed by clicking on the gear icon in the right top 
corner and then clicking on the button 'Site Configuration'.

Registration
------------
Only students with email domains that are allowed by the administrator can sign 
up for the Thesis management system.

Roles
-----
There are four roles - Admin, Owner, Supervisor and Student. The Admin has the 
highest authority and can configure the system, manage users and universities, 
manage any topic or thesis and add private commments. Owner can create topics 
and manage topics that are owned by them or manage theses that are created 
from their topic and add private comments. Supervisor can create theses and 
manage theses that are supervised by them. Student can apply for any topic and 
upload files and add abstract or tags to their theses.

How to deploy the Thesis management system on OpenShift Online
==============================================================

1. Create an openshift JBoss EWS 2 cartridge and add Postgres and MongoDB 
cartridge.
2. Add remote openshift repository to your local project git repository.
3. Create custom environment variables on openshift with mailgun and url settings:

        rhc env set OPENSHIFT_MAIL_FROM="Example User <mailgun@<your_mailgun_domain>>" \
                    OPENSHIFT_MAILGUN_API_URL="https://api.mailgun.net/v3/<your_mailgun_domain>/messages" \
                    OPENSHIFT_MAILGUN_API_KEY="<your_mailgun_key>" \
                    OPENSHIFT_SERVER_URL="https://exampledomain.com" \
                    -a YourAppName

4. Push your local repository to OpenShift.

_Note:_ You need medium gear, otherwise grails won't be able to build the app. 
Or you can use small gear and build the application your self.

How to deploy the Thesis management system locally for development
==================================================================

1. Install PostgreSQL and MongoDB, e.g.:

        # dnf install postgresql-server mongodb-server

2. Start PostgreSQL and MongoDB, e.g.:

        # systemctl start postgresql.service
        # systemctl start mongodb.service

3. Install Oralce JDK 1.7 and set environment variable JAVA_HOME
4. Create a [mailgun account](https://mailgun.com).
5. Export these environment variables:

        export LOCALHOST_MAIL_FROM="postmaster@<your_mailgun_domain>"
        export LOCALHOST_MAILGUN_API_URL="https://api.mailgun.net/v3/<your_mailgun_domain>/messages"
        export LOCALHOST_MAILGUN_API_KEY="<your_mailgun_key>"

6. There is a `email.sh.tpl` file in the source directory, you can rename the
file to `email.sh`, modify it to suit your mailgun settings and `source` it
to set these mailgun variables.
7. Navigate to the source directory and run `./grailsw run-app`.
8. Alternatively, you can download Grails 2.2.x from [official grails website](https://grails.org/download.html),
install it and run `grails run-app`.

License
=======

The Thesis Management System is released under the [MIT License](http://www.opensource.org/licenses/MIT).
