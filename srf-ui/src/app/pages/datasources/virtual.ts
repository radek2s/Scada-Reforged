export class VirtualDataSource {

    private type: string
    id: number
    sid: string
    name: string
    enabled: boolean
    updatePeriod: number
    updatePeriodType: string
    datapoints: any[]

    constructor(name: string) {
        this.type = "VIRTUAL"
        this.id = -1
        this.sid = ""
        this.name = name
        this.enabled = false
        this.updatePeriod = 5
        this.updatePeriodType = "SECONDS"
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
    dataType: string
    changeType: number
    minValue: number
    maxValue: number

    constructor(name: string, min: number, max: number) {
        this.sid = "VDS_DP"
        this.name = name
        this.enabled = false
        this.settable = true
        this.dataType = 'DOUBLE'
        this.changeType = 0
        this.minValue = min
        this.maxValue = max
    }
}