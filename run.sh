#!/bin/bash

$SPARK_HOME/bin/spark-submit --class org.dbpedia.convert.Converter --master local[2]  --files conf/application.conf target/json-dump-1.0-jar-with-dependencies.jar
