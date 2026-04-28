package com.mapsea.notificationconsumer.domain.enums

enum class AisStatus(
    val number: Int,
    val koreanName: String,
    val englishName: String,
) {
    UNDER_WAY_USING_ENGINE(0, "기관 주행 중", "Under Way Using Engine"),
    AT_ANCHOR(1, "닻을 내린", "At Anchor"),
    NOT_UNDER_COMMAND(2, "명령 불능", "Not Under Command"),
    RESTRICTED_MANEUVERABILITY(3, "기동성 제한", "Restricted Maneuverability"),
    CONSTRAINED_BY_HER_DRAUGHT(4, "흘수에 의해 제한됨", "Constrained by her Draught"),
    MOORED(5, "계류 중", "Moored"),
    AGROUND(6, "좌초", "Aground"),
    ENGAGED_IN_FISHING(7, "어업 중", "Engaged in Fishing"),
    UNDER_WAY_SAILING(8, "항해 중", "Under Way Sailing"),
    RESERVED_FOR_FUTURE_AMENDMENT_DG_HS_MP_IMO_C(9, "향후 개정 예정 - DG, HS, MP, IMO C", "Reserved for future amendment - DG, HS, MP, IMO C"),
    RESERVED_FOR_FUTURE_AMENDMENT_DG_HS_MP_IMO_A(10, "향후 개정 예정 - DG, HS, MP, IMO A", "Reserved for future amendment - DG, HS, MP, IMO A"),
    POWER_DRIVEN_VESSEL_TOWING_ASTERN(11, "추진력 있는 선박 - 후미 견인", "Power-driven vessel towing astern"),
    POWER_DRIVEN_VESSEL_PUSHING_AHEAD_OR_TOWING_ALONGSIDE(12, "추진력 있는 선박 - 전방 밀기 또는 옆으로 견인", "Power-driven vessel pushing ahead or towing alongside"),
    RESERVED_FOR_FUTURE_USE(13, "향후 사용 예정", "Reserved for future use"),
    AISSART_ACTIVE_MOBAIS_EPIRBAIS(14, "AISSART 활성, MOB-AIS, EPIRB-AIS", "AISSART (active), MOB-AIS, EPIRB-AIS"),
    UNDEFINED_DEFAULT(15, "미정의 - 기본값", "Undefined - Default");

    companion object {
        fun fromNumber(number: Int): AisStatus? = entries.firstOrNull { it.number == number }
    }
}
