/**
 * SmartThings example Code for Google sheets logging
 *
 * Copyright 2016 Charles Schwer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 *
 */

definition(
		name: "Google Sheets Logging",
		namespace: "cschwer",
		author: "Charles Schwer",
		description: "Log to Google Sheets",
		category: "My Apps",
		iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
		iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
		iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
	section("Log devices...") {
		input "contacts", "capability.contactSensor", title: "Doors open/close", required: false, multiple: true
		input "temperatures", "capability.temperatureMeasurement", title: "Temperatures", required:false, multiple: true
	}

	section ("Google Sheets script url key...") {
		input "urlKey", "text", title: "URL key"
	}
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	
	subscribe(temperatures, "temperature", handleTemperatureEvent)
	subscribe(contacts, "contact", handleContactEvent)
}

def handleTemperatureEvent(evt) {
	sendValue(evt) { it.toString() }
}

def handleContactEvent(evt) {
	sendValue(evt) { it == "open" ? "true" : "false" }
}

private sendValue(evt, Closure convert) {
	def keyId = URLEncoder.encode(evt.displayName.trim()+ " " +evt.name)
	def value = convert(evt.value)
    
	log.debug "Logging to GoogleSheets ${compId}, ${streamId} = ${value}"
    
	def url = "https://script.google.com/macros/s/${urlKey}/exec?${keyId}=${value}"
    log.debug "${url}"
	def putParams = [
		uri: url]

	httpGet(putParams) { response ->
    	log.debug(response.status)
		if (response.status != 200 ) {
			log.debug "Google logging failed, status = ${response.status}"
		}
	}
}