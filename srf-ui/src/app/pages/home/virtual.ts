export class VirtualDataSource {

    private type: string
    private id: number
    private sid: string
    private name: string
    private enabled: boolean
    private updatePeriod: number
    private updatePeriodType: number

    constructor(name: string) {
        this.type = "VIRTUAL"
        this.id = -1
        this.sid = ""
        this.name = name
        this.enabled = true
        this.updatePeriod = 1
        this.updatePeriodType = 5
    }
}