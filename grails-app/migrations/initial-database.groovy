databaseChangeLog = {

	changeSet(author: "vdedik (generated)", id: "1381238445043-1") {
		createTable(tableName: "application") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "applicationPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "applicant_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "approved", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "note", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "thesis_id", type: "int8")

			column(name: "topic_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "university_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-2") {
		createTable(tableName: "article") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "articlePK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-3") {
		createTable(tableName: "category") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "categoryPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-4") {
		createTable(tableName: "comment") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "commentPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "article_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "content", type: "text") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "private_comment", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-5") {
		createTable(tableName: "faq") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "faqPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "answer", type: "text") {
				constraints(nullable: "false")
			}

			column(name: "locale", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "question", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-6") {
		createTable(tableName: "pending_email_confirmation") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pending_emailPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "confirmation_event", type: "varchar(80)")

			column(name: "confirmation_token", type: "varchar(80)") {
				constraints(nullable: "false")
			}

			column(name: "email_address", type: "varchar(80)") {
				constraints(nullable: "false")
			}

			column(name: "timestamp", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "user_token", type: "varchar(500)")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-7") {
		createTable(tableName: "subscription") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "subscriptionPK")
			}

			column(name: "article_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "subscriber_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-8") {
		createTable(tableName: "supervision") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "supervisionPK")
			}

			column(name: "supervisor_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "topic_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "university_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-9") {
		createTable(tableName: "tag") {
			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tagPK")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-10") {
		createTable(tableName: "thesis") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "thesisPK")
			}

			column(name: "assignee_id", type: "int8")

			column(name: "date_created", type: "timestamp")

			column(name: "description", type: "text")

			column(name: "grade", type: "varchar(255)")

			column(name: "notes", type: "text")

			column(name: "status", type: "varchar(255)")

			column(name: "supervisor_id", type: "int8")

			column(name: "thesis_abstract", type: "text")

			column(name: "topic_id", type: "int8")

			column(name: "type", type: "varchar(255)")

			column(name: "university_id", type: "int8")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-11") {
		createTable(tableName: "thesis_tag") {
			column(name: "thesis_tags_id", type: "int8")

			column(name: "tag_title", type: "varchar(255)")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-12") {
		createTable(tableName: "topic") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "topicPK")
			}

			column(name: "date_created", type: "timestamp")

			column(name: "description", type: "text")

			column(name: "enabled", type: "bool")

			column(name: "lead", type: "text")

			column(name: "owner_id", type: "int8")

			column(name: "secondary_description", type: "text")

			column(name: "secondary_title", type: "varchar(255)")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-13") {
		createTable(tableName: "topic_category") {
			column(name: "topic_categories_id", type: "int8")

			column(name: "category_id", type: "int8")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-14") {
		createTable(tableName: "topic_tag") {
			column(name: "topic_tags_id", type: "int8")

			column(name: "tag_title", type: "varchar(255)")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-15") {
		createTable(tableName: "topic_types") {
			column(name: "topic_id", type: "int8")

			column(name: "type", type: "varchar(255)")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-16") {
		createTable(tableName: "topic_university") {
			column(name: "topic_universities_id", type: "int8")

			column(name: "university_id", type: "int8")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-17") {
		createTable(tableName: "university") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "universityPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "acronym", type: "varchar(10)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-18") {
		createTable(tableName: "user") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "full_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password_expired", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "send_mail", type: "bool") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-19") {
		createTable(tableName: "user_roles") {
			column(name: "user_id", type: "int8")

			column(name: "role", type: "varchar(255)")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-46") {
		createIndex(indexName: "title_uniq_1381238444966", tableName: "category", unique: "true") {
			column(name: "title")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-47") {
		createIndex(indexName: "question_uniq_1381238444967", tableName: "faq", unique: "true") {
			column(name: "question")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-48") {
		createIndex(indexName: "emailconf_timestamp_Idx", tableName: "pending_email_confirmation") {
			column(name: "timestamp")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-49") {
		createIndex(indexName: "emailconf_token_Idx", tableName: "pending_email_confirmation") {
			column(name: "confirmation_token")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-50") {
		createIndex(indexName: "unique_supervisor_id", tableName: "supervision", unique: "true") {
			column(name: "topic_id")

			column(name: "university_id")

			column(name: "supervisor_id")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-51") {
		createIndex(indexName: "name_uniq_1381238444983", tableName: "university", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-52") {
		createIndex(indexName: "email_uniq_1381238444950", tableName: "user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-53") {
		createSequence(sequenceName: "hibernate_sequence")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-54") {
		createSequence(sequenceName: "seq_article_id")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-20") {
		addForeignKeyConstraint(baseColumnNames: "applicant_id", baseTableName: "application", constraintName: "FK5CA405507E712562", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-21") {
		addForeignKeyConstraint(baseColumnNames: "thesis_id", baseTableName: "application", constraintName: "FK5CA40550C2712F81", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "thesis", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-22") {
		addForeignKeyConstraint(baseColumnNames: "topic_id", baseTableName: "application", constraintName: "FK5CA40550593DA933", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-23") {
		addForeignKeyConstraint(baseColumnNames: "university_id", baseTableName: "application", constraintName: "FK5CA405501C0B04C1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "university", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-24") {
		addForeignKeyConstraint(baseColumnNames: "article_id", baseTableName: "comment", constraintName: "FK38A5EE5F544CAD93", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "article", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-25") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "comment", constraintName: "FK38A5EE5F3E1F37F9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-26") {
		addForeignKeyConstraint(baseColumnNames: "article_id", baseTableName: "subscription", constraintName: "FK1456591D544CAD93", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "article", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-27") {
		addForeignKeyConstraint(baseColumnNames: "subscriber_id", baseTableName: "subscription", constraintName: "FK1456591DA4A5B63C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-28") {
		addForeignKeyConstraint(baseColumnNames: "supervisor_id", baseTableName: "supervision", constraintName: "FKBFF40323DA39CF3C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-29") {
		addForeignKeyConstraint(baseColumnNames: "topic_id", baseTableName: "supervision", constraintName: "FKBFF40323593DA933", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-30") {
		addForeignKeyConstraint(baseColumnNames: "university_id", baseTableName: "supervision", constraintName: "FKBFF403231C0B04C1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "university", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-31") {
		addForeignKeyConstraint(baseColumnNames: "assignee_id", baseTableName: "thesis", constraintName: "FKCBDB5ACCAEB8A775", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-32") {
		addForeignKeyConstraint(baseColumnNames: "supervisor_id", baseTableName: "thesis", constraintName: "FKCBDB5ACCDA39CF3C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-33") {
		addForeignKeyConstraint(baseColumnNames: "topic_id", baseTableName: "thesis", constraintName: "FKCBDB5ACC593DA933", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-34") {
		addForeignKeyConstraint(baseColumnNames: "university_id", baseTableName: "thesis", constraintName: "FKCBDB5ACC1C0B04C1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "university", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-35") {
		addForeignKeyConstraint(baseColumnNames: "tag_title", baseTableName: "thesis_tag", constraintName: "FK660603A771150526", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "title", referencedTableName: "tag", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-36") {
		addForeignKeyConstraint(baseColumnNames: "thesis_tags_id", baseTableName: "thesis_tag", constraintName: "FK660603A7DD982CA1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "thesis", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-37") {
		addForeignKeyConstraint(baseColumnNames: "owner_id", baseTableName: "topic", constraintName: "FK696CD2FAA05E811", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-38") {
		addForeignKeyConstraint(baseColumnNames: "category_id", baseTableName: "topic_category", constraintName: "FKB3A3860EAC697481", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "category", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-39") {
		addForeignKeyConstraint(baseColumnNames: "topic_categories_id", baseTableName: "topic_category", constraintName: "FKB3A3860E58484E16", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-40") {
		addForeignKeyConstraint(baseColumnNames: "tag_title", baseTableName: "topic_tag", constraintName: "FK172B8C8A71150526", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "title", referencedTableName: "tag", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-41") {
		addForeignKeyConstraint(baseColumnNames: "topic_tags_id", baseTableName: "topic_tag", constraintName: "FK172B8C8AD8A0B6F9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-42") {
		addForeignKeyConstraint(baseColumnNames: "topic_id", baseTableName: "topic_types", constraintName: "FKFA85A969593DA933", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-43") {
		addForeignKeyConstraint(baseColumnNames: "topic_universities_id", baseTableName: "topic_university", constraintName: "FK16DBAF9E247B9E86", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "topic", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-44") {
		addForeignKeyConstraint(baseColumnNames: "university_id", baseTableName: "topic_university", constraintName: "FK16DBAF9E1C0B04C1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "university", referencesUniqueColumn: "false")
	}

	changeSet(author: "vdedik (generated)", id: "1381238445043-45") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_roles", constraintName: "FK734299493E1F37F9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
