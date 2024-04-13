set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE magicnights;
    CREATE USER mad WITH ENCRYPTED PASSWORD 'asdf';
    GRANT ALL PRIVILEGES ON DATABASE magicnights TO mad;
EOSQL