#!/usr/bin/env bash
pushd ../
mvn install
cd core
mvn install
popd
mvn clean package assembly:single assembly:single
