package com.mapsea.notificationconsumer.util

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.util.MultiValueMap

object AlimtalkUtil {
    private val mapper = ObjectMapper()

    fun createAlimtalkButton(requestBody: MultiValueMap<String, Any>, buttons: List<ObjectNode>) {
        val jsonElements = mapper.createArrayNode()
        jsonElements.add(
            mapper.createObjectNode().apply {
                put("name", "채널 추가")
                put("linkType", "AC")
                put("linkTypeName", "채널")
            },
        )
        buttons.forEach { jsonElements.add(it) }
        val buttonObj = mapper.createObjectNode()
        buttonObj.set<com.fasterxml.jackson.databind.node.ArrayNode>("button", jsonElements)
        requestBody.add("button_1", buttonObj)
    }
}
