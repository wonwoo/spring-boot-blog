#! /bin/sh
# Clear and re-configure the search index.
if [ $# != 2 ]; then cat << EOM
    usage: $0 ENDPOINT INDEX
    where ENDPOINT is the path to the qbox.io endpoint (or http://localhost:9200)
      and INDEX is the name of the endpoint, e.g. 'word-press'
EOM
    exit
fi

ENDPOINT=$1
INDEX=$2

echo "\nDelete the existing index."
curl -XDELETE --header "content-type: application/JSON" "$ENDPOINT/$INDEX"
sleep 1

echo "\nCreate the index again."
curl -X PUT --header "content-type: application/JSON" $ENDPOINT/$INDEX  -d @./blog/src/main/resources/elasticsearch/settings.json
sleep 1
echo "\nClosing index."
curl -XPOST --header "content-type: application/JSON" "$ENDPOINT/$INDEX/_close"
sleep 1
#curl -XPUT --header "content-type: application/JSON" $ENDPOINT/$INDEX/_settings -d @./blog/src/main/resources/elasticsearch/settings.json
#sleep 1
echo "\nInstall the mapping - do once per type/mapping file."
curl -XPOST --header "content-type: application/JSON" "$ENDPOINT/$INDEX/post/_mapping" -d @./blog/src/main/resources/elasticsearch/post.json
sleep 1
curl -XPOST --header "content-type: application/JSON" "$ENDPOINT/$INDEX/_open"
sleep 1

echo "\nCheck the settings."
curl $ENDPOINT/$INDEX/_settings?pretty=true
sleep 1

echo "\nCheck the mappings."
curl $ENDPOINT/$INDEX/_mapping?pretty=true