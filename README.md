# JSONDump

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/52a7d67276394abd9ecc0fc4d0acfe4c)](https://www.codacy.com/app/ryanseq2016/JSONDump?utm_source=github.com&utm_medium=referral&utm_content=dbpedia/JSONDump&utm_campaign=badger)

Core to generate a single json dump for a DBpedia release

## Description

Given a set of N-Quad / N-Triple files, this script uses spark to read all files as an RDD and processes them.
For every subject in all RDF files, it generates a one-line JSON.

Example input:
```
<http://wikidata.dbpedia.org/resource/Q53619> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10011322> .
<http://wikidata.dbpedia.org/resource/Q53619> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10011710> .
<http://wikidata.dbpedia.org/resource/Q53619> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10011866> .
<http://wikidata.dbpedia.org/resource/Q53619> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10081503> .
<http://wikidata.dbpedia.org/resource/Q53619> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10081526> .
<http://wikidata.dbpedia.org/resource/Q16013229> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10006240> .
<http://wikidata.dbpedia.org/resource/Q16013229> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10100684> .
<http://wikidata.dbpedia.org/resource/Q16013229> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10100754> .
<http://wikidata.dbpedia.org/resource/Q16013229> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10100876> .
<http://wikidata.dbpedia.org/resource/Q16013229> <http://purl.org/dc/terms/subject> <http://wikidata.dbpedia.org/resource/Q10101343> .
```

Example output:
```
{"@id":"http://wikidata.dbpedia.org/resource/Q16013229","subject":["http://wikidata.dbpedia.org/resource/Q10006240","http://wikidata.dbpedia.org/resource/Q10100684","http://wikidata.dbpedia.org/resource/Q10100754","http://wikidata.dbpedia.org/resource/Q10100876","http://wikidata.dbpedia.org/resource/Q10101343"]}
{"@id":"http://wikidata.dbpedia.org/resource/Q53619","subject":["http://wikidata.dbpedia.org/resource/Q10011322","http://wikidata.dbpedia.org/resource/Q10011710","http://wikidata.dbpedia.org/resource/Q10011866","http://wikidata.dbpedia.org/resource/Q10081503","http://wikidata.dbpedia.org/resource/Q10081526"]}
```

The subject is denoted with `@id` and we generate JSON key/value pairs for all the RDF triples with the resource as subject.
The values as grouped by predicate and we use the `local name` of the predicate IRI as key.
We use the lexical form of the object(s) of the triple as value(s).

The goal is to make the code aligned with JSON-LD, auto-prefixing IRIs and generating the appropriate `@context` based on the files it processes (or using a predefined context)
At the moment, the code is generic and can work with any N-Triple files.

`N-Quads` are supported from the parser but the algorithm ignores the graph IRI.

Use the Github issue tracker to submit any issues and Pull Requests are more that welcome ;)

## Experimental DBpedia 2016-10 release

see http://downloads.dbpedia.org/temporary/jsondump/ for an experimental jsonl 2016-10 release

## Prerequisites 
* Install Spark and set SPARK_HOME environment variable

## Running JSONDump
1. Update configuration in conf/application.conf
   * Update **app.data.input** to set input file path pattern (only one at the moment)
   * Update **app.data.output** to set output directory
2. Use the following command to run JSONDump
  ```
 ./run.sh
  ```
  You can update`run.sh` to change spark submit arguments.


## Maintainers

* Ryan Sequeira [@seqryan](https://github.com/seqryan)
* Dimitris Kontokostas [@jimkont](https://github.com/jimkont)
