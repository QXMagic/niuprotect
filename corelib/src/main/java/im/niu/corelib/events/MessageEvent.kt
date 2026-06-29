package im.niu.corelib.events


open class MessageEvent {
    
    var type: EventType

    constructor(type: EventType) {
        this.type = type
    }

}