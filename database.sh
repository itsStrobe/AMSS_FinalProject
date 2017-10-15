#!/usr/bin/env bash

if [[ $# -eq 1 && $1 == 'reset' ]]; then
    java -cp ./Libraries/sqlite-jdbc-3.16.1.jar:./bin amss.db.ResetDatabase
elif [[ $# -eq 1 && $1 == 'drop' ]]; then
    java -cp ./Libraries/sqlite-jdbc-3.16.1.jar:./bin amss.db.DropDatabase
elif [[ $# -eq 1 && $1 == 'create' ]]; then
    java -cp ./Libraries/sqlite-jdbc-3.16.1.jar:./bin amss.db.CreateDatabase
else
    echo 'Error: Especifica (create/reset/drop) como un parametro'
fi
