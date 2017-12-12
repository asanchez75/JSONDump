# JSONDump

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/52a7d67276394abd9ecc0fc4d0acfe4c)](https://www.codacy.com/app/ryanseq2016/JSONDump?utm_source=github.com&utm_medium=referral&utm_content=dbpedia/JSONDump&utm_campaign=badger)

Core to generate a single json dump for a DBpedia release

## Prerequisites 
* Install Spark and set SPARK_HOME environment variable

## Running JSONDump
1. Update configuration in conf/application.conf
  * Update **app.data.input** to set input file path
  * Update **app.data.output** to set output directory
2. Use the following command to run JSONDump.
  ```
 ./run.sh
  ```
  You can update run.sh to change spark submit arguments.

## Maintainers

* Dimitris Kontokostas [@jimkont](https://github.com/jimkont)
* Ryan Sequeira [@seqryan](https://github.com/seqryan)
