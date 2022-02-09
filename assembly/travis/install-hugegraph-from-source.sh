#!/bin/bash

set -ev

if [[ $# -ne 1 ]]; then
    echo "Must pass commit id of hugegraph repo"
    exit 1
fi

BRANCH_ID=$1
HUGEGRAPH_GIT_URL="https://github.com/starhugegraph/hugegraph.git"

git clone ${HUGEGRAPH_GIT_URL}
cd hugegraph
git checkout -b gh-dis-release origin/gh-dis-release

chmod +x install-hstore-dependency.sh
./install-hstore-dependency.sh

# copy local graph config file
TRAVIS_DIR=./hugegraph-dist/src/assembly/travis/lib
cp $TRAVIS_DIR/graphs/hugegraph.properties ../

mvn package -DskipTests
mv hugegraph-*.tar.gz ../
cd ../
rm -rf hugegraph
tar -zxvf hugegraph-*.tar.gz

cd hugegraph-*/

# create local graph
cp ../hugegraph.properties conf/graphs/
bin/init-store.sh

# start
bin/start-hugegraph.sh
