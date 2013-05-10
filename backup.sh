#!/bin/sh
echo "Backing up PostgreSQL database..."
pg_dump $OPENSHIFT_APP_NAME -h $OPENSHIFT_POSTGRESQL_DB_HOST -U $OPENSHIFT_POSTGRESQL_DB_USERNAME > $OPENSHIFT_DATA_DIR/${OPENSHIFT_APP_NAME}_backup_postgres.sql
echo "Done. Backup saved in $OPENSHIFT_DATA_DIR/${OPENSHIFT_APP_NAME}_backup_postgres.sql"

echo "Backing up MongoDB..."
mongodump --host $OPENSHIFT_MONGODB_DB_HOST --port $OPENSHIFT_MONGODB_DB_PORT --username $OPENSHIFT_MONGODB_DB_USERNAME --password $OPENSHIFT_MONGODB_DB_PASSWORD --out $OPENSHIFT_DATA_DIR/${OPENSHIFT_APP_NAME}_backup_mongo
echo "Done. Backup saved in $OPENSHIFT_DATA_DIR/${OPENSHIFT_APP_NAME}_backup_mongo"
