db.createUser({
    user: 'user',
    pwd: 'magic1234',
    roles: [
        { role: 'readWrite', db: 'magicnights' }
    ]
});