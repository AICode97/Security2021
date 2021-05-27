export default class AbstractService {

    host() {
        return process.env.REACT_APP_SERVICE_HOST
    }

    namespace() {
        return ''
    }

    constructUrl(endpoint) {
        return `${this.host()}/${this.namespace()}/${endpoint}`
    }
}