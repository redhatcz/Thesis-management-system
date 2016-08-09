databaseChangeLog = {

	changeSet(author: "phala (generated)", id: "1470734955033-1") {
		createIndex(indexName: "unique_applicant_id", tableName: "application", unique: "true") {
			column(name: "topic_id")

			column(name: "applicant_id")
		}
	}
}
