rs.initiate({_id: "rs-shd-01", version: 1,
    members: [
        { _id: 0, host : "mongo-shd01-a:27017" },
        { _id: 1, host : "mongo-shd01-b:27017" },
        { _id: 2, host : "mongo-shd01-c:27017" },
    ] })