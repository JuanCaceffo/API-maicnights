package ar.edu.unsam.phm.magicnightsback.domain.interfaces

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Point
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowDateDTO

interface ShowData {
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
    val totalOpinions: Int
}

interface ShowDetails {
    val geolocation: Point
    val comments: List<Comment>
}

interface ShowDates {
    val dates: List<ShowDateDTO>
}