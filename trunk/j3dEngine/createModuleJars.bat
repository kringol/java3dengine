echo off

cd classes/production

jar cvf j3dengine.gapi.jar -C gapi .
jar cvf j3dengine.core.jar -C core .
jar cvf j3dengine.processors.jar -C processors .
jar cvf j3dengine.demo.jar -C demo .

cd ../../

echo "==============================================="
echo "Module jars created in ./classes/production"
echo "==============================================="