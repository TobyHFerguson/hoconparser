# hoconparser
This is a parser that will perform all includes and substitutions from a hocon file (and its included files), returning the result as JSON.

You can keep the original comments with the `--comments` flag, and/or the original location (as comments) of an included file with `--origins`

An example use of parsing a conf file:

```
java -jar target/hocon-0.0.1-SNAPSHOT.jar -o -c aws.conf
```