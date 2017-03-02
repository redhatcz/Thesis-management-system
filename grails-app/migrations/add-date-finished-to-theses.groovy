databaseChangeLog = {

	changeSet(author: "phala (generated)", id: "1480516155023-1") {
		addColumn(tableName: "thesis") {
			column(name: "date_finished", type: "timestamp")
		}
	}
}
