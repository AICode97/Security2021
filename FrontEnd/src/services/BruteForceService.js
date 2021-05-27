export default class BruteForceService {

    bruteForceToken = "LASSE_BRUTE_FORCE"
    timeout = 1000 * 60 * 15 // 15 mins
    maxCounts = 3

    registerAttempt() {
        const existing = localStorage.getItem(this.bruteForceToken)

        const payload = existing != null ? JSON.parse(existing) : { count: 0, timestamp: "" }

        payload.count += 1
        payload.timestamp = new Date().toISOString()
        localStorage.setItem(this.bruteForceToken, JSON.stringify(payload))
    }

    isAllowed() {
        const existing = localStorage.getItem(this.bruteForceToken)

        if (existing == null) {
            return true
        }

        const now = new Date()
        const payload = JSON.parse(existing)
        
        const timestamp = Date.parse(payload.timestamp)

        const difference = Math.abs(now - timestamp)

        if (difference > this.timeout) {
            localStorage.removeItem(this.bruteForceToken)
            return true
        }

        return payload.count < this.maxCounts
    }

    purge() {
        localStorage.removeItem(this.bruteForceToken)
    }
}