databaseChangeLog = {

	changeSet(author: "phala (generated)", id: "1470216108216-1") {
		createTable(tableName: "link") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "linkPK")
			}

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "phala (generated)", id: "1470216108216-2") {
		createTable(tableName: "thesis_link") {
			column(name: "thesis_links_id", type: "int8")

			column(name: "link_id", type: "int8")

			column(name: "links_idx", type: "int4")
		}
	}

	changeSet(author: "phala (generated)", id: "1470216108216-4") {
		createSequence(sequenceName: "seq_link_id")
	}

	changeSet(author: "phala (generated)", id: "1470216108216-3") {
		addForeignKeyConstraint(baseColumnNames: "link_id", baseTableName: "thesis_link", constraintName: "FK5AB6ED8DA3E79E81", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "link", referencesUniqueColumn: "false")
	}
}
