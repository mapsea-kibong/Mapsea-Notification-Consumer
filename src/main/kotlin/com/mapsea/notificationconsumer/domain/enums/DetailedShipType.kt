package com.mapsea.notificationconsumer.domain.enums

enum class DetailedShipType {
    // ===== [BULK_CARRIER 계열] =====
    DRY_BULK_CARRIER,
    ORE_CARRIER,
    GRAIN_CARRIER,
    COAL_CARRIER,
    CEMENT_CARRIER,

    // ===== [CONTAINER_SHIP 계열] =====
    FULLY_CONTAINER_SHIP,
    SEMI_CONTAINER_SHIP,

    // ===== [TANKER_SHIPS 계열] =====
    CRUDE_OIL_TANKER,
    PRODUCT_TANKER,
    CHEMICAL_TANKER,
    LNG_CARRIER,
    LPG_CARRIER,

    // ===== [CAR_CARRIER 계열] =====
    PCC,
    PCTC,
    RO_RO_CARRIER,
    RO_PAX_SHIP,
    CONVENTIONAL_CAR_CARRIER,
    MINI_CAR_CARRIER,

    // ===== [PASSENGER_SHIPS 계열] =====
    CRUISE_SHIP,
    FERRY,
    PASSENGER_RO_PAX_SHIP,

    // ===== [SPECIAL_PURPOSE_SHIPS 계열] =====
    OFFSHORE_SUPPLY_VESSEL,
    CABLE_LAYER,
    RESEARCH_VESSEL,
    SAR_SHIP,
}
