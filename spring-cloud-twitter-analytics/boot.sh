
wget -O docker-compose.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/v2.7.0/spring-cloud-dataflow-server/docker-compose.yml

wget -O docker-compose-prometheus.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/v2.7.0/spring-cloud-dataflow-server/docker-compose-prometheus.yml

wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.7.0/spring-cloud-dataflow-shell-2.7.0.jar


export DATAFLOW_VERSION=2.7.0 
export SKIPPER_VERSION=2.6.0

export STREAM_APPS_URI=https://dataflow.spring.io/Einstein-BUILD-SNAPSHOT-stream-applications-kafka-maven

docker-compose -f ./docker-compose.yml -f ./docker-compose-prometheus.yml up

Api key
pNFzspwQYhjD2YKGI2wStBDUD

Api key secret
BgJK0YkuxDR1uYdXqmseLwyB8znevotsEPfL1vc5NiOL4kKTVj

Bearer token:
AAAAAAAAAAAAAAAAAAAAAOnhCQEAAAAAOuEPbarsOZHy3%2FwFF%2F06Ok7VBP4%3D6ElOlYyKElhfu2gydRbCbCQo9PDQ5KjhvZcBNJuzx0LqgUCj1L

Access token:
73236654-CSkBGGaDT469tXS15DtrxtOcfkyOmu9hFhUAOmmj7

Access token secret:
qBaFdI3nLvPsEELTTSdJkKnfU6jEzK1kk8c3UxoF6cOuY


dataflow:>stream create tweets --definition "twitterstream --consumerKey=pNFzspwQYhjD2YKGI2wStBDUD --consumerSecret=BgJK0YkuxDR1uYdXqmseLwyB8znevotsEPfL1vc5NiOL4kKTVj --accessToken=73236654-CSkBGGaDT469tXS15DtrxtOcfkyOmu9hFhUAOmmj7 --accessTokenSecret=qBaFdI3nLvPsEELTTSdJkKnfU6jEzK1kk8c3UxoF6cOuY | log"

dataflow:>stream create tweetlang  --definition ":tweets.twitterstream > counter --counter.name=language --counter.tag.expression.lang=#jsonPath(payload,'$..lang')" --deploy

# Test to get information of the location
dataflow:>stream create tweetlocation  --definition ":tweets.twitterstream > counter --counter.name=location --counter.tag.expression.loc=#jsonPath(payload,'$..location')" --deploy

dataflow:>stream create tagcount  --definition ":tweets.twitterstream > counter --counter.name=hashtags --counter.tag.expression.htag=#jsonPath(payload,'$.entities.hashtags[*].text')" --deploy

dataflow:>stream deploy tweets

dataflow:>stream list


# Spring Clould Data Flow Dashboard
http://localhost:9393

# Grafana
http://localhost:3000
admin:admin

# Import the grafana-twitter-scdf-analytics.json dashboard
https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow-samples/master/src/main/asciidoc/monitoring/grafana-twitter-scdf-analytics.json
