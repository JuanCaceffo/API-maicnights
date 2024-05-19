package ar.edu.unsam.phm.magicnightsback.domain.interfaces

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Point
import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowDateDTO

interface ShowData {
    val id: String
    val showImg: String
    val showName: String
    val bandName: String
    val facilityName: String
    val prices: List<Double>
}

interface ShowTicket{ // Igual a ShowData pero con id Long
    val id: Long
    val showImg: String
    val showName: String
    val bandName: String
    val facilityName: String
    val prices: List<Double>
}

interface ShowWithFriends {
    val friendsImgs: List<String>
    val totalFriendsAttending: Int
}

interface ShowStats {
    val rating: Double
    val totalComments: Int
}

interface ShowDetails {
    val geolocation: String
    val comments: Set<CommentDTO>
}

interface ShowDates {
    val dates: List<ShowDateDTO>
}