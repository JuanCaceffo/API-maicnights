package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface SeatRepository : CrudRepository<Seat, Long>, CustomCrudRepository<Seat>
