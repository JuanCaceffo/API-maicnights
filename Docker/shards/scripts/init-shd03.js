rs.initiate({_id: "rs-shd-03", version: 1,
    members: [
        { _id: 0, host : "mongo-shd03-a:27017" },
        { _id: 1, host : "mongo-shd03-b:27017" },
        { _id: 2, host : "mongo-shd03-c:27017" },
    ] })