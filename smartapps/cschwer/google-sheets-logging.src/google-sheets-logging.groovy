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
    state.queue = []
    state.failureCount=0
    state.scheduled=false
}

def handleTemperatureEvent(evt) {
    queueValue(evt) { it.toString() }
}

def handleContactEvent(evt) {
	sendValue(evt) { it == "open" ? "true" : "false" }
}


private sendValue(evt, Closure convert) {
	def keyId = URLEncoder.encode(evt.displayName.trim()+ " " +evt.name)
	def value = convert(evt.value)
    
	log.debug "Logging to GoogleSheets ${keyId} = ${value}"
    
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


private queueValue(evt, Closure convert) {
	def keyId = URLEncoder.encode(evt.displayName.trim()+ " " +evt.name)
	def value = convert(evt.value)
    
    log.debug "Logging to queue ${keyId} = ${value}"

	if( state.queue == [] ) {
      def eventTime = URLEncoder.encode(evt.date.format( 'M-d-yyyy HH:mm:ss' ))
      state.queue << "Time=${eventTime}"
    }
    
    state.queue << "${keyId}=${value}"
    
    log.debug(state.queue)
    
    scheduleQueue()
}

def scheduleQueue() {

	log.debug "scheduled ${state.scheduled}"
    log.debug "failurecount ${state.failureCount}"
    log.debug(state.queue)
    if(!state.scheduled && state.failureCount < 3) {
    	runIn(60*3, runSchedule)
        state.scheduled=true
    }
    
    if(state.failureCount >= 3) {
    log.debug "reseting queue"
    	state.queue = []
        state.failureCount = 0
    }
}

def runSchedule() {
	state.scheduled=false
    processQueue()
}

def processQueue() {

    log.debug "processQueue"
    log.debug(state.queue)
    if (state.queue != []) {
        log.debug "Events: ${state.queue}"
        def url = "https://script.google.com/macros/s/${urlKey}/exec?"
        for ( e in state.queue ) {
        log.debug(e)
		    url+="${e}&"
		}
        url = url[0..-2]
        try {
		    log.debug "${url}"
    
			def putParams = [
				uri: url]

			httpGet(putParams) { response ->
    			log.debug(response.status)
				if (response.status != 200 ) {
					log.debug "Google logging failed, status = ${response.status}"
                    state.failureCount++
                    scheduleQueue()
				} else {
        			log.debug "Google accepted event(s)"
            		state.queue = []
        		}
			}
        } catch(e) {
            def errorInfo = "Error sending value: ${e}"
            log.error errorInfo
        }
	}
}

