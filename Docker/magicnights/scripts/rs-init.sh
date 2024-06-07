#!/bin/bash

DELAY=25

mongosh <<EOF
var config = {
    "_id" : "mnrs",
    "version": 1,
    "members" : [
        {
            "_id" : 0,
            "host" : "mongo1:27017",
            "priority" : 2
        },
        {
            "_id" : 1,
            "host" : "mongo2:27017",
            "priority" : 1
        },
        {
            "_id" : 2,
            "host" : "mongo3:27017",
            "priority" : 1
        },
        {
            "_id" : 3,
            "host" : "mongo4:27017",
            "arbiterOnly" : true
        }
    ]
};
rs.initiate(config, { force: true });
EOF

echo "** Waiting for ${DELAY} seconds for replicaset configuration to be applied **"

sleep $DELAY

mongosh mongodb://mongo1:27017/admin < /scripts/init.js