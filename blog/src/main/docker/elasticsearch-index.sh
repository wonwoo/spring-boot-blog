#! /bin/sh
# Clear and re-configure the search index.
ENDPOINT=localhost:9200
INDEX=wordpress

echo "\nDelete the existing index."
curl -XDELETE "$ENDPOINT/$INDEX"
sleep 1

echo "\nCreate the index again."
curl -X PUT $ENDPOINT/$INDEX -d '{ "number_of_shards" : 5, "number_of_replicas" : 0}'
sleep 1
echo "\nClosing index."
curl -XPOST "$ENDPOINT/$INDEX/_close"
sleep 1
curl -XPUT $ENDPOINT/$INDEX/_settings -d @../resources/elasticsearch/settings.json
sleep 1
echo "\nInstall the mapping - do once per type/mapping file."
curl -XPOST "$ENDPOINT/$INDEX/post/_mapping" -d @../resources/elasticsearch/post.json
sleep 1
curl -XPOST "$ENDPOINT/$INDEX/_open"
sleep 1

echo "\nCheck the settings."
curl $ENDPOINT/$INDEX/_settings?pretty=true
sleep 1

echo "\nCheck the mappings."
curl $ENDPOINT/$INDEX/_mapping?pretty=true