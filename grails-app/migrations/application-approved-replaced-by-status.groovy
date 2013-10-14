databaseChangeLog = {

	changeSet(author: "vdedik (generated)", id: "1381239553308-1") {
		addColumn(tableName: "application") {
			column(name: "status", type: "varchar(255)")
		}

        grailsChange {
            change {
                sql.eachRow("select * from application") { row ->
                    def updateValue = row.approved ? 'APPROVED' : 'PENDING'
                    sql.execute("update application set status = '${updateValue}' where id = ${row.id}")
                }
            }
            rollback {
            }
        }

        addNotNullConstraint(tableName: "application", columnName: "status")
	}

	changeSet(author: "vdedik (generated)", id: "1381239553308-2") {
		dropColumn(columnName: "approved", tableName: "application")
	}
}
