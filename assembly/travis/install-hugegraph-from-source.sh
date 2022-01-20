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
mvn package -DskipTests
mv hugegraph-*.tar.gz ../
cd ../
rm -rf hugegraph
tar -zxvf hugegraph-*.tar.gz

cd hugegraph-*/
chmod +x install-hstore-dependency.sh
./install-hstore-dependency.sh
bin/start-hugegraph.sh
