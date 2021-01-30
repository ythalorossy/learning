CURRENT_DATE=`date '+%Y/%m/%d'`
LESSON="$(openssl rand -base64 12)"
mvn clean package -Dmaven.test.skip=true;
java -jar ./target/linkedin-batch-v2-0.0.1-SNAPSHOT.jar "run.date(date)=$CURRENT_DATE" "lesson=$LESSON";