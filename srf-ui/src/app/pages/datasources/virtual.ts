export class VirtualDataSource {

    private type: string
    id: number
    sid: string
    name: string
    enabled: boolean
    updatePeriod: number
    updatePeriodType: number
    datapoints: any[]

    constructor(name: string) {
        this.type = "VIRTUAL"
        this.id = -1
        this.sid = ""
        this.name = name
        this.enabled = false
        this.updatePeriod = 1
        this.updatePeriodType = 5
        this.datapoints = []
    }

    getType() {
        return this.type
    }
}

export class VirtualDataPoint {

    sid: string
    name: string
    enabled: boolean
    settable: boolean
    dataType: number
    changeType: number
    minValue: number
    maxValue: number

    constructor(name: string, min: number, max: number) {
        this.sid = "VDS_DP"
        this.name = name
        this.enabled = false
        this.settable = true
        this.dataType = 0
        this.changeType = 0
        this.minValue = min
        this.maxValue = max
    }
}