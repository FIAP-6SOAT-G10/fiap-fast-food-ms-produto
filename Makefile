build:
	mvn compile

unit-test:
	mvn test -P unit-test

integration-test:
	mvn test -P integration-test

system-test:
	mvn test -P bdd-test

production:
	mvn clean install -DskipTests -Pprd -q