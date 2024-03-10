package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository : CustomRepository<User>()