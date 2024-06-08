rs.initiate({_id: "rs-shd-02", version: 1,
    members: [
        { _id: 0, host : "mongo-shd02-a:27017" },
        { _id: 1, host : "mongo-shd02-b:27017" },
        { _id: 2, host : "mongo-shd02-c:27017" },
    ] })