rs.initiate({
    _id: "rs-cfg", configsvr: true, version: 1,
    members: [
        { _id: 0, host : 'mongo-config01:27017' },
        { _id: 1, host : 'mongo-config02:27017' },
        { _id: 2, host : 'mongo-config03:27017' }
    ]
})