databaseChangeLog = {

	changeSet(author: "phala (generated)", id: "1472640506724-1") {
		createTable(tableName: "role_request") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "role_requestPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "applicant_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "phala (generated)", id: "1472640506724-3") {
		dropIndex(indexName: "unique_applicant_id", tableName: "application")
	}

	changeSet(author: "phala (generated)", id: "1472640506724-2") {
		addForeignKeyConstraint(baseColumnNames: "applicant_id", baseTableName: "role_request", constraintName: "FK581F43C67E712562", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
