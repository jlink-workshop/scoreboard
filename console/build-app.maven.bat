cd ..
mvn install
cd core
mvn install
cd ../console
mvn clean compile assembly:single
