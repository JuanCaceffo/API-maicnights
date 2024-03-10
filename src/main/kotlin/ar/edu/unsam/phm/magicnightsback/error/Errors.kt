package ar.edu.unsam.phm.magicnightsback.error

object UserErrors{
  const val MSG_NOT_ENOUGH_CREDIT = "No tiene saldo suficiente"
}


class BusinessException(msg:String) : RuntimeException(msg)