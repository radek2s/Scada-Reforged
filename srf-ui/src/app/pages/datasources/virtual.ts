export class VirtualDataSource {

    private type: string
    id: number
    sid: string
    name: string
    enabled: boolean
    updatePeriod: number
    updatePeriodType: number

    constructor(name: string) {
        this.type = "VIRTUAL"
        this.id = -1
        this.sid = ""
        this.name = name
        this.enabled = false
        this.updatePeriod = 1
        this.updatePeriodType = 5
    }

    getType() {
        return this.type
    }
}